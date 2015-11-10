/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package autoszerelo.database.entities;

import autoszerelo.database.controllers.PartUsageJpaController;
import autoszerelo.database.util.DatabaseEngine;
import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author dmolnar
 */
@Entity
@Table(name = "JOB")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Job.findAll", query = "SELECT j FROM Job j"),
    @NamedQuery(name = "Job.findById", query = "SELECT j FROM Job j WHERE j.id = :id"),
    @NamedQuery(name = "Job.findByClientname", query = "SELECT j FROM Job j WHERE j.clientname = :clientname"),
    @NamedQuery(name = "Job.findByAddress", query = "SELECT j FROM Job j WHERE j.address = :address"),
    @NamedQuery(name = "Job.findByLicenseNo", query = "SELECT j FROM Job j WHERE j.licenseNo = :licenseNo"),
    @NamedQuery(name = "Job.findByWorkerid", query = "SELECT j FROM Job j WHERE j.workerid = :workerid"),
    @NamedQuery(name = "Job.findByDate", query = "SELECT j FROM Job j WHERE j.date = :date")})
public class Job implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "ID")
    private Integer id;
    @Column(name = "CLIENTNAME")
    private String clientname;
    @Column(name = "ADDRESS")
    private String address;
    @Column(name = "LICENSE_NO")
    private String licenseNo;
    @Column(name = "CLOSED")
    private Boolean closed;
    @Basic(optional = false)
    @Column(name = "WORKERID")
    private int workerid;
    @Column(name = "LENGTH")
    private int length;
    @Column(name = "DATE")
    @Temporal(TemporalType.DATE)
    private Date date;
    public Job() {
    }

    public Job(Integer id) {
        this.id = id;
    }

    public Job(Integer id, int workerid) {
        this.id = id;
        this.workerid = workerid;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getClientname() {
        return clientname;
    }

    public void setClientname(String clientname) {
        this.clientname = clientname;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getLicenseNo() {
        return licenseNo;
    }

    public void setLicenseNo(String licenseNo) {
        this.licenseNo = licenseNo;
    }

    public int getWorkerid() {
        return workerid;
    }

    public void setWorkerid(int workerid) {
        this.workerid = workerid;
    }

    public Date getDate() {
        return date;
    }
    
    public int getLength() {
        return length;
    }
    
    public void setLength(int length) {
        this.length = length;
    }
    
    public Boolean getState() {
        return closed;
    }
    public void setState(Boolean state) {
        this.closed = state;
    }
    public void setDate(Date date) {
        this.date = date;
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
        if (!(object instanceof Job)) {
            return false;
        }
        Job other = (Job) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "[" + id + "] Kliens: " + clientname;
    }
    
}
