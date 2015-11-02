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
@Table(name = "PARTS")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Parts.findAll", query = "SELECT p FROM Parts p"),
    @NamedQuery(name = "Parts.findById", query = "SELECT p FROM Parts p WHERE p.id = :id"),
    @NamedQuery(name = "Parts.findByName", query = "SELECT p FROM Parts p WHERE p.name = :name"),
    @NamedQuery(name = "Parts.findByPrice", query = "SELECT p FROM Parts p WHERE p.price = :price")})
public class Parts implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "ID")
    private Integer id;
    @Column(name = "NAME")
    private String name;
    @Column(name = "PRICE")
    private Integer price;

    public Parts() {
    }

    public Parts(Integer id) {
        this.id = id;
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

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
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
        if (!(object instanceof Parts)) {
            return false;
        }
        Parts other = (Parts) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "autoszerelo.Parts[ id=" + id + " ]";
    }
    
}
