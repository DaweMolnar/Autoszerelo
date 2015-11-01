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
@Table(name = "WORKERS")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Workers.findAll", query = "SELECT w FROM Workers w"),
    @NamedQuery(name = "Workers.findById", query = "SELECT w FROM Workers w WHERE w.id = :id"),
    @NamedQuery(name = "Workers.findByName", query = "SELECT w FROM Workers w WHERE w.name = :name"),
    @NamedQuery(name = "Workers.findByAddress", query = "SELECT w FROM Workers w WHERE w.address = :address"),
    @NamedQuery(name = "Workers.findByPhone", query = "SELECT w FROM Workers w WHERE w.phone = :phone"),
    @NamedQuery(name = "Workers.findByWage", query = "SELECT w FROM Workers w WHERE w.wage = :wage")})
public class Workers implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "ID")
    private Integer id;
    @Column(name = "NAME")
    private String name;
    @Column(name = "ADDRESS")
    private String address;
    @Column(name = "PHONE")
    private String phone;
    @Basic(optional = false)
    @Column(name = "WAGE")
    private int wage;

    public Workers() {
    }

    public Workers(Integer id) {
        this.id = id;
    }

    public Workers(Integer id, int wage) {
        this.id = id;
        this.wage = wage;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getWage() {
        return wage;
    }

    public void setWage(int wage) {
        this.wage = wage;
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
        if (!(object instanceof Workers)) {
            return false;
        }
        Workers other = (Workers) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "autoszerelo.Workers[ id=" + id + " ]";
    }
    
}
