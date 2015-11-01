/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package autoszerelo.database.controllers;

import autoszerelo.database.entities.Partusage;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityNotFoundException;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

/**
 *
 * @author dmolnar
 */
public class PartUsageJpaController {
        public PartUsageJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;
    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }
    public void create(Partusage part) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(part);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }
    
    public void edit(Partusage part) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            part = em.merge(part);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = part.getId();
                //if (findPatient(id) == null) {
                  //  throw new NonexistentEntityException("The patient with id " + id + " no longer exists.");
                //}
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Long id) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Partusage part;
            try {
                part = em.getReference(Partusage.class, id);
                part.getId();
            } catch (EntityNotFoundException enfe) {
                throw enfe;
                //throw new NonexistentEntityException("The patient with id " + id + " no longer exists.", enfe);
            }
            em.remove(part);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Partusage> findPartUsageEntities() {
        return findPartUsageEntities(true, -1, -1);
    }

    public List<Partusage> findPartUsageEntities(int maxResults, int firstResult) {
        return findPartUsageEntities(false, maxResults, firstResult);
    }

    private List<Partusage> findPartUsageEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Partusage.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public Partusage findPartUsage(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Partusage.class, id);
        } finally {
            em.close();
        }
    }

    public int getPartUsageCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Partusage> rt = cq.from(Partusage.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
