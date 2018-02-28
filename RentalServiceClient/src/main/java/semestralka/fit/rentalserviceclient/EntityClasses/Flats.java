/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package semestralka.fit.rentalserviceclient.EntityClasses;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author bekbolot
 */
@Entity
@Table(name="FLATS")
@XmlRootElement
public class Flats implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    @Column(name="ADDRESS")
    private String address;
    
    @Column(name="PRICEPERMONTH")
    private Long pricePerMonth;
    
    @OneToMany(mappedBy = "rentedFlat")
    List <Clients> clients;
    
    
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="SERVICE_ID")
    private Services service;


    public Services getService() {
        return service;
    }
    public String getServiceName() {
        if( service != null )
            return service.getName();
        else
            return "";
    }

    public void setService(Services service) {
        this.service = service;
    }
    
    

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Long getPricePerMonth() {
        return pricePerMonth;
    }

    public void setPricePerMonth(Long pricePerMonth) {
        this.pricePerMonth = pricePerMonth;
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
        if (!(object instanceof Flats)) {
            return false;
        }
        Flats other = (Flats) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.semestralka.fit.rentalservice.Flats[ id=" + id + " ]";
    }
    
}
