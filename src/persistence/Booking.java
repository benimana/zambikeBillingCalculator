/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package persistence;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author issa
 */
@Entity
@Table(name = "booking")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Booking.findAll", query = "SELECT b FROM Booking b")
    , @NamedQuery(name = "Booking.findByBookingNo", query = "SELECT b FROM Booking b WHERE b.bookingNo = :bookingNo")})
public class Booking implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "bookingNo")
    private Integer bookingNo;
    @JoinColumn(name = "clientNo", referencedColumnName = "clientNo")
    @ManyToOne
    private Clients clientNo;

    public Booking() {
    }

    public Booking(Integer bookingNo) {
        this.bookingNo = bookingNo;
    }

    public Integer getBookingNo() {
        return bookingNo;
    }

    public void setBookingNo(Integer bookingNo) {
        this.bookingNo = bookingNo;
    }

    public Clients getClientNo() {
        return clientNo;
    }

    public void setClientNo(Clients clientNo) {
        this.clientNo = clientNo;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (bookingNo != null ? bookingNo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Booking)) {
            return false;
        }
        Booking other = (Booking) object;
        if ((this.bookingNo == null && other.bookingNo != null) || (this.bookingNo != null && !this.bookingNo.equals(other.bookingNo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "persistence.Booking[ bookingNo=" + bookingNo + " ]";
    }
    
}
