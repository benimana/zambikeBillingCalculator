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
import persistence.Booking;
import persistence.Clients;

/**
 *
 * @author issa
 */
public class BookingJpaController implements Serializable {

    public BookingJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Booking booking) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Clients clientNo = booking.getClientNo();
            if (clientNo != null) {
                clientNo = em.getReference(clientNo.getClass(), clientNo.getClientNo());
                booking.setClientNo(clientNo);
            }
            em.persist(booking);
            if (clientNo != null) {
                clientNo.getBookingCollection().add(booking);
                clientNo = em.merge(clientNo);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Booking booking) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Booking persistentBooking = em.find(Booking.class, booking.getBookingNo());
            Clients clientNoOld = persistentBooking.getClientNo();
            Clients clientNoNew = booking.getClientNo();
            if (clientNoNew != null) {
                clientNoNew = em.getReference(clientNoNew.getClass(), clientNoNew.getClientNo());
                booking.setClientNo(clientNoNew);
            }
            booking = em.merge(booking);
            if (clientNoOld != null && !clientNoOld.equals(clientNoNew)) {
                clientNoOld.getBookingCollection().remove(booking);
                clientNoOld = em.merge(clientNoOld);
            }
            if (clientNoNew != null && !clientNoNew.equals(clientNoOld)) {
                clientNoNew.getBookingCollection().add(booking);
                clientNoNew = em.merge(clientNoNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = booking.getBookingNo();
                if (findBooking(id) == null) {
                    throw new NonexistentEntityException("The booking with id " + id + " no longer exists.");
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
            Booking booking;
            try {
                booking = em.getReference(Booking.class, id);
                booking.getBookingNo();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The booking with id " + id + " no longer exists.", enfe);
            }
            Clients clientNo = booking.getClientNo();
            if (clientNo != null) {
                clientNo.getBookingCollection().remove(booking);
                clientNo = em.merge(clientNo);
            }
            em.remove(booking);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Booking> findBookingEntities() {
        return findBookingEntities(true, -1, -1);
    }

    public List<Booking> findBookingEntities(int maxResults, int firstResult) {
        return findBookingEntities(false, maxResults, firstResult);
    }

    private List<Booking> findBookingEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Booking.class));
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

    public Booking findBooking(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Booking.class, id);
        } finally {
            em.close();
        }
    }

    public int getBookingCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Booking> rt = cq.from(Booking.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
