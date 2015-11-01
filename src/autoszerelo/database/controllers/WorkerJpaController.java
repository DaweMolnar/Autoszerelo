/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package autoszerelo.database.controllers;

import autoszerelo.database.entities.Workers;
import java.util.List;
import java.io.Serializable;
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
public class WorkerJpaController implements Serializable{
    public WorkerJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;
    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }
    public void create(Workers worker) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(worker);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }
    
    public void edit(Workers worker) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            worker = em.merge(worker);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = worker.getId();
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
            Workers worker;
            try {
                worker = em.getReference(Workers.class, id);
                worker.getId();
            } catch (EntityNotFoundException enfe) {
                throw enfe;
                //throw new NonexistentEntityException("The patient with id " + id + " no longer exists.", enfe);
            }
            em.remove(worker);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Workers> findWorkerEntities() {
        return findWorkerEntities(true, -1, -1);
    }

    public List<Workers> findWorkerEntities(int maxResults, int firstResult) {
        return findWorkerEntities(false, maxResults, firstResult);
    }

    private List<Workers> findWorkerEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Workers.class));
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

    public Workers findWorker(Long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Workers.class, id);
        } finally {
            em.close();
        }
    }

    public int getWorkerCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Workers> rt = cq.from(Workers.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
