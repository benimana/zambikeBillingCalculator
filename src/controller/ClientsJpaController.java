/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import controller.exceptions.NonexistentEntityException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import persistence.Booking;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import persistence.Clients;
import persistence.Receipt;

/**
 *
 * @author issa
 */
public class ClientsJpaController implements Serializable {

    public ClientsJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Clients clients) {
        if (clients.getBookingCollection() == null) {
            clients.setBookingCollection(new ArrayList<Booking>());
        }
        if (clients.getReceiptCollection() == null) {
            clients.setReceiptCollection(new ArrayList<Receipt>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Collection<Booking> attachedBookingCollection = new ArrayList<Booking>();
            for (Booking bookingCollectionBookingToAttach : clients.getBookingCollection()) {
                bookingCollectionBookingToAttach = em.getReference(bookingCollectionBookingToAttach.getClass(), bookingCollectionBookingToAttach.getBookingNo());
                attachedBookingCollection.add(bookingCollectionBookingToAttach);
            }
            clients.setBookingCollection(attachedBookingCollection);
            Collection<Receipt> attachedReceiptCollection = new ArrayList<Receipt>();
            for (Receipt receiptCollectionReceiptToAttach : clients.getReceiptCollection()) {
                receiptCollectionReceiptToAttach = em.getReference(receiptCollectionReceiptToAttach.getClass(), receiptCollectionReceiptToAttach.getReceiptNo());
                attachedReceiptCollection.add(receiptCollectionReceiptToAttach);
            }
            clients.setReceiptCollection(attachedReceiptCollection);
            em.persist(clients);
            for (Booking bookingCollectionBooking : clients.getBookingCollection()) {
                Clients oldClientNoOfBookingCollectionBooking = bookingCollectionBooking.getClientNo();
                bookingCollectionBooking.setClientNo(clients);
                bookingCollectionBooking = em.merge(bookingCollectionBooking);
                if (oldClientNoOfBookingCollectionBooking != null) {
                    oldClientNoOfBookingCollectionBooking.getBookingCollection().remove(bookingCollectionBooking);
                    oldClientNoOfBookingCollectionBooking = em.merge(oldClientNoOfBookingCollectionBooking);
                }
            }
            for (Receipt receiptCollectionReceipt : clients.getReceiptCollection()) {
                Clients oldClientNoOfReceiptCollectionReceipt = receiptCollectionReceipt.getClientNo();
                receiptCollectionReceipt.setClientNo(clients);
                receiptCollectionReceipt = em.merge(receiptCollectionReceipt);
                if (oldClientNoOfReceiptCollectionReceipt != null) {
                    oldClientNoOfReceiptCollectionReceipt.getReceiptCollection().remove(receiptCollectionReceipt);
                    oldClientNoOfReceiptCollectionReceipt = em.merge(oldClientNoOfReceiptCollectionReceipt);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Clients clients) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Clients persistentClients = em.find(Clients.class, clients.getClientNo());
            Collection<Booking> bookingCollectionOld = persistentClients.getBookingCollection();
            Collection<Booking> bookingCollectionNew = clients.getBookingCollection();
            Collection<Receipt> receiptCollectionOld = persistentClients.getReceiptCollection();
            Collection<Receipt> receiptCollectionNew = clients.getReceiptCollection();
            Collection<Booking> attachedBookingCollectionNew = new ArrayList<Booking>();
            for (Booking bookingCollectionNewBookingToAttach : bookingCollectionNew) {
                bookingCollectionNewBookingToAttach = em.getReference(bookingCollectionNewBookingToAttach.getClass(), bookingCollectionNewBookingToAttach.getBookingNo());
                attachedBookingCollectionNew.add(bookingCollectionNewBookingToAttach);
            }
            bookingCollectionNew = attachedBookingCollectionNew;
            clients.setBookingCollection(bookingCollectionNew);
            Collection<Receipt> attachedReceiptCollectionNew = new ArrayList<Receipt>();
            for (Receipt receiptCollectionNewReceiptToAttach : receiptCollectionNew) {
                receiptCollectionNewReceiptToAttach = em.getReference(receiptCollectionNewReceiptToAttach.getClass(), receiptCollectionNewReceiptToAttach.getReceiptNo());
                attachedReceiptCollectionNew.add(receiptCollectionNewReceiptToAttach);
            }
            receiptCollectionNew = attachedReceiptCollectionNew;
            clients.setReceiptCollection(receiptCollectionNew);
            clients = em.merge(clients);
            for (Booking bookingCollectionOldBooking : bookingCollectionOld) {
                if (!bookingCollectionNew.contains(bookingCollectionOldBooking)) {
                    bookingCollectionOldBooking.setClientNo(null);
                    bookingCollectionOldBooking = em.merge(bookingCollectionOldBooking);
                }
            }
            for (Booking bookingCollectionNewBooking : bookingCollectionNew) {
                if (!bookingCollectionOld.contains(bookingCollectionNewBooking)) {
                    Clients oldClientNoOfBookingCollectionNewBooking = bookingCollectionNewBooking.getClientNo();
                    bookingCollectionNewBooking.setClientNo(clients);
                    bookingCollectionNewBooking = em.merge(bookingCollectionNewBooking);
                    if (oldClientNoOfBookingCollectionNewBooking != null && !oldClientNoOfBookingCollectionNewBooking.equals(clients)) {
                        oldClientNoOfBookingCollectionNewBooking.getBookingCollection().remove(bookingCollectionNewBooking);
                        oldClientNoOfBookingCollectionNewBooking = em.merge(oldClientNoOfBookingCollectionNewBooking);
                    }
                }
            }
            for (Receipt receiptCollectionOldReceipt : receiptCollectionOld) {
                if (!receiptCollectionNew.contains(receiptCollectionOldReceipt)) {
                    receiptCollectionOldReceipt.setClientNo(null);
                    receiptCollectionOldReceipt = em.merge(receiptCollectionOldReceipt);
                }
            }
            for (Receipt receiptCollectionNewReceipt : receiptCollectionNew) {
                if (!receiptCollectionOld.contains(receiptCollectionNewReceipt)) {
                    Clients oldClientNoOfReceiptCollectionNewReceipt = receiptCollectionNewReceipt.getClientNo();
                    receiptCollectionNewReceipt.setClientNo(clients);
                    receiptCollectionNewReceipt = em.merge(receiptCollectionNewReceipt);
                    if (oldClientNoOfReceiptCollectionNewReceipt != null && !oldClientNoOfReceiptCollectionNewReceipt.equals(clients)) {
                        oldClientNoOfReceiptCollectionNewReceipt.getReceiptCollection().remove(receiptCollectionNewReceipt);
                        oldClientNoOfReceiptCollectionNewReceipt = em.merge(oldClientNoOfReceiptCollectionNewReceipt);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = clients.getClientNo();
                if (findClients(id) == null) {
                    throw new NonexistentEntityException("The clients with id " + id + " no longer exists.");
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
            Clients clients;
            try {
                clients = em.getReference(Clients.class, id);
                clients.getClientNo();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The clients with id " + id + " no longer exists.", enfe);
            }
            Collection<Booking> bookingCollection = clients.getBookingCollection();
            for (Booking bookingCollectionBooking : bookingCollection) {
                bookingCollectionBooking.setClientNo(null);
                bookingCollectionBooking = em.merge(bookingCollectionBooking);
            }
            Collection<Receipt> receiptCollection = clients.getReceiptCollection();
            for (Receipt receiptCollectionReceipt : receiptCollection) {
                receiptCollectionReceipt.setClientNo(null);
                receiptCollectionReceipt = em.merge(receiptCollectionReceipt);
            }
            em.remove(clients);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Clients> findClientsEntities() {
        return findClientsEntities(true, -1, -1);
    }

    public List<Clients> findClientsEntities(int maxResults, int firstResult) {
        return findClientsEntities(false, maxResults, firstResult);
    }

    private List<Clients> findClientsEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Clients.class));
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

    public Clients findClients(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Clients.class, id);
        } finally {
            em.close();
        }
    }

    public int getClientsCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Clients> rt = cq.from(Clients.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
