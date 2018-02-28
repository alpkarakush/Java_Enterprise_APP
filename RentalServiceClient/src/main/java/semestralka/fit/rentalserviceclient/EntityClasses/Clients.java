/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package semestralka.fit.rentalserviceclient.EntityClasses;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author bekbolot
 * A select statement must have a FROM clause
 */
@Entity
@Table(name="CLIENTS")
@XmlRootElement
public class Clients implements Serializable {
    
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    @Column(name="NAME")
    @NotNull
    private String name;
    
    @Column(name="SURENAME")
    @NotNull
    private String surename;
    
    @Column(name="TELNUMBER")
    private String telNumber;
    
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="FLAT_ID")
    private Flats rentedFlat;

    public Flats getRentedFlat() {
        return rentedFlat;
    }
    public Long getRentedFlatId() {
        if( rentedFlat != null )
            return rentedFlat.getId();
        else
            return Long.parseLong("-1");
    }
    public String getRentedFlatAddress() {
        if( rentedFlat != null )
            return rentedFlat.getAddress();
        else
            return "";
    }

    public void setRentedFlat(Flats rentedFlat) {
        this.rentedFlat = rentedFlat;
    }

    
    
    

    public Clients() {}
    
    public Clients(String name, String surename, String telNum) 
    {
        this.name = name;
        this.surename = surename;
        this.telNumber = telNum;
    }
    
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurename() {
        return surename;
    }

    public void setSurename(String surename) {
        this.surename = surename;
    }

    public String getTelNumber() {
        return telNumber;
    }

    public void setTelNumber(String telNumber) {
        this.telNumber = telNumber;
    }
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
        if (!(object instanceof Clients)) {
            return false;
        }
        Clients other = (Clients) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.semestralka.fit.rentalservice.Clients[ id=" + id + " ]";
    }
    
}
