/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package autoszerelo.database.entities;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author dmolnar
 */
@Entity
@Table(name = "PARTUSAGE")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Partusage.findAll", query = "SELECT p FROM Partusage p"),
    @NamedQuery(name = "Partusage.findById", query = "SELECT p FROM Partusage p WHERE p.id = :id"),
    @NamedQuery(name = "Partusage.findByJobid", query = "SELECT p FROM Partusage p WHERE p.jobid = :jobid"),
    @NamedQuery(name = "Partusage.findByPartid", query = "SELECT p FROM Partusage p WHERE p.partid = :partid")})
public class Partusage implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    @Basic(optional = false)
    @Column(name = "ID")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "JOBID")
    private int jobid;
    @Basic(optional = false)
    @Column(name = "PARTID")
    private int partid;

    public Partusage() {
    }

    public Partusage(Integer id) {
        this.id = id;
    }

    public Partusage(Integer id, int jobid, int partid) {
        this.id = id;
        this.jobid = jobid;
        this.partid = partid;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getJobid() {
        return jobid;
    }

    public void setJobid(int jobid) {
        this.jobid = jobid;
    }

    public int getPartid() {
        return partid;
    }

    public void setPartid(int partid) {
        this.partid = partid;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Partusage)) {
            return false;
        }
        Partusage other = (Partusage) object;
        return !((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id)));
    }

    @Override
    public String toString() {
        return "autoszerelo.Partusage[ id=" + id + " ]";
    }
    
}
