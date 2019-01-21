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
@Table(name = "receipt")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Receipt.findAll", query = "SELECT r FROM Receipt r")
    , @NamedQuery(name = "Receipt.findByReceiptNo", query = "SELECT r FROM Receipt r WHERE r.receiptNo = :receiptNo")
    , @NamedQuery(name = "Receipt.findByBankCharges", query = "SELECT r FROM Receipt r WHERE r.bankCharges = :bankCharges")
    , @NamedQuery(name = "Receipt.findByVat", query = "SELECT r FROM Receipt r WHERE r.vat = :vat")
    , @NamedQuery(name = "Receipt.findByTotalcost", query = "SELECT r FROM Receipt r WHERE r.totalcost = :totalcost")})
public class Receipt implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "receiptNo")
    private Integer receiptNo;
    @Column(name = "bankCharges")
    private Integer bankCharges;
    @Column(name = "vat")
    private Integer vat;
    @Column(name = "totalcost")
    private Integer totalcost;
    @JoinColumn(name = "clientNo", referencedColumnName = "clientNo")
    @ManyToOne
    private Clients clientNo;

    public Receipt() {
    }

    public Receipt(Integer receiptNo) {
        this.receiptNo = receiptNo;
    }

    public Integer getReceiptNo() {
        return receiptNo;
    }

    public void setReceiptNo(Integer receiptNo) {
        this.receiptNo = receiptNo;
    }

    public Integer getBankCharges() {
        return bankCharges;
    }

    public void setBankCharges(Integer bankCharges) {
        this.bankCharges = bankCharges;
    }

    public Integer getVat() {
        return vat;
    }

    public void setVat(Integer vat) {
        this.vat = vat;
    }

    public Integer getTotalcost() {
        return totalcost;
    }

    public void setTotalcost(Integer totalcost) {
        this.totalcost = totalcost;
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
        hash += (receiptNo != null ? receiptNo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Receipt)) {
            return false;
        }
        Receipt other = (Receipt) object;
        if ((this.receiptNo == null && other.receiptNo != null) || (this.receiptNo != null && !this.receiptNo.equals(other.receiptNo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "persistence.Receipt[ receiptNo=" + receiptNo + " ]";
    }
    
}
