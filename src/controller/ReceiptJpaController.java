/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import controller.exceptions.NonexistentEntityException;
import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import persistence.Clients;
import persistence.Receipt;

/**
 *
 * @author issa
 */
public class ReceiptJpaController implements Serializable {

    public ReceiptJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Receipt receipt) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Clients clientNo = receipt.getClientNo();
            if (clientNo != null) {
                clientNo = em.getReference(clientNo.getClass(), clientNo.getClientNo());
                receipt.setClientNo(clientNo);
            }
            em.persist(receipt);
            if (clientNo != null) {
                clientNo.getReceiptCollection().add(receipt);
                clientNo = em.merge(clientNo);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Receipt receipt) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Receipt persistentReceipt = em.find(Receipt.class, receipt.getReceiptNo());
            Clients clientNoOld = persistentReceipt.getClientNo();
            Clients clientNoNew = receipt.getClientNo();
            if (clientNoNew != null) {
                clientNoNew = em.getReference(clientNoNew.getClass(), clientNoNew.getClientNo());
                receipt.setClientNo(clientNoNew);
            }
            receipt = em.merge(receipt);
            if (clientNoOld != null && !clientNoOld.equals(clientNoNew)) {
                clientNoOld.getReceiptCollection().remove(receipt);
                clientNoOld = em.merge(clientNoOld);
            }
            if (clientNoNew != null && !clientNoNew.equals(clientNoOld)) {
                clientNoNew.getReceiptCollection().add(receipt);
                clientNoNew = em.merge(clientNoNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = receipt.getReceiptNo();
                if (findReceipt(id) == null) {
                    throw new NonexistentEntityException("The receipt with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Receipt receipt;
            try {
                receipt = em.getReference(Receipt.class, id);
                receipt.getReceiptNo();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The receipt with id " + id + " no longer exists.", enfe);
            }
            Clients clientNo = receipt.getClientNo();
            if (clientNo != null) {
                clientNo.getReceiptCollection().remove(receipt);
                clientNo = em.merge(clientNo);
            }
            em.remove(receipt);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Receipt> findReceiptEntities() {
        return findReceiptEntities(true, -1, -1);
    }

    public List<Receipt> findReceiptEntities(int maxResults, int firstResult) {
        return findReceiptEntities(false, maxResults, firstResult);
    }

    private List<Receipt> findReceiptEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Receipt.class));
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

    public Receipt findReceipt(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Receipt.class, id);
        } finally {
            em.close();
        }
    }

    public int getReceiptCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Receipt> rt = cq.from(Receipt.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
