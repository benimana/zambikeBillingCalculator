/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package persistence;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author issa
 */
@Entity
@Table(name = "clients")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Clients.findAll", query = "SELECT c FROM Clients c")
    , @NamedQuery(name = "Clients.findByClientNo", query = "SELECT c FROM Clients c WHERE c.clientNo = :clientNo")
    , @NamedQuery(name = "Clients.findByClientName", query = "SELECT c FROM Clients c WHERE c.clientName = :clientName")
    , @NamedQuery(name = "Clients.findByClientArea", query = "SELECT c FROM Clients c WHERE c.clientArea = :clientArea")})
public class Clients implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "clientNo")
    private Integer clientNo;
    @Column(name = "clientName")
    private String clientName;
    @Column(name = "clientArea")
    private Integer clientArea;
    @OneToMany(mappedBy = "clientNo")
    private Collection<Booking> bookingCollection;
    @OneToMany(mappedBy = "clientNo")
    private Collection<Receipt> receiptCollection;

    public Clients() {
    }

    public Clients(Integer clientNo) {
        this.clientNo = clientNo;
    }

    public Integer getClientNo() {
        return clientNo;
    }

    public void setClientNo(Integer clientNo) {
        this.clientNo = clientNo;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public Integer getClientArea() {
        return clientArea;
    }

    public void setClientArea(Integer clientArea) {
        this.clientArea = clientArea;
    }

    @XmlTransient
    public Collection<Booking> getBookingCollection() {
        return bookingCollection;
    }

    public void setBookingCollection(Collection<Booking> bookingCollection) {
        this.bookingCollection = bookingCollection;
    }

    @XmlTransient
    public Collection<Receipt> getReceiptCollection() {
        return receiptCollection;
    }

    public void setReceiptCollection(Collection<Receipt> receiptCollection) {
        this.receiptCollection = receiptCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (clientNo != null ? clientNo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Clients)) {
            return false;
        }
        Clients other = (Clients) object;
        if ((this.clientNo == null && other.clientNo != null) || (this.clientNo != null && !this.clientNo.equals(other.clientNo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "persistence.Clients[ clientNo=" + clientNo + " ]";
    }
    
}
