/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import DAO.exceptions.NonexistentEntityException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import DTO.Reparaciones;
import DTO.Reparacionestelefono;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Pedro
 */
public class ReparacionestelefonoJpaController implements Serializable {

    public ReparacionestelefonoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Reparacionestelefono reparacionestelefono) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Reparaciones codigoReparacion = reparacionestelefono.getCodigoReparacion();
            if (codigoReparacion != null) {
                codigoReparacion = em.getReference(codigoReparacion.getClass(), codigoReparacion.getCodigoReparacion());
                reparacionestelefono.setCodigoReparacion(codigoReparacion);
            }
            em.persist(reparacionestelefono);
            if (codigoReparacion != null) {
                codigoReparacion.getReparacionestelefonoList().add(reparacionestelefono);
                codigoReparacion = em.merge(codigoReparacion);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Reparacionestelefono reparacionestelefono) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Reparacionestelefono persistentReparacionestelefono = em.find(Reparacionestelefono.class, reparacionestelefono.getCodigoReparacionTelefono());
            Reparaciones codigoReparacionOld = persistentReparacionestelefono.getCodigoReparacion();
            Reparaciones codigoReparacionNew = reparacionestelefono.getCodigoReparacion();
            if (codigoReparacionNew != null) {
                codigoReparacionNew = em.getReference(codigoReparacionNew.getClass(), codigoReparacionNew.getCodigoReparacion());
                reparacionestelefono.setCodigoReparacion(codigoReparacionNew);
            }
            reparacionestelefono = em.merge(reparacionestelefono);
            if (codigoReparacionOld != null && !codigoReparacionOld.equals(codigoReparacionNew)) {
                codigoReparacionOld.getReparacionestelefonoList().remove(reparacionestelefono);
                codigoReparacionOld = em.merge(codigoReparacionOld);
            }
            if (codigoReparacionNew != null && !codigoReparacionNew.equals(codigoReparacionOld)) {
                codigoReparacionNew.getReparacionestelefonoList().add(reparacionestelefono);
                codigoReparacionNew = em.merge(codigoReparacionNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = reparacionestelefono.getCodigoReparacionTelefono();
                if (findReparacionestelefono(id) == null) {
                    throw new NonexistentEntityException("The reparacionestelefono with id " + id + " no longer exists.");
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
            Reparacionestelefono reparacionestelefono;
            try {
                reparacionestelefono = em.getReference(Reparacionestelefono.class, id);
                reparacionestelefono.getCodigoReparacionTelefono();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The reparacionestelefono with id " + id + " no longer exists.", enfe);
            }
            Reparaciones codigoReparacion = reparacionestelefono.getCodigoReparacion();
            if (codigoReparacion != null) {
                codigoReparacion.getReparacionestelefonoList().remove(reparacionestelefono);
                codigoReparacion = em.merge(codigoReparacion);
            }
            em.remove(reparacionestelefono);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Reparacionestelefono> findReparacionestelefonoEntities() {
        return findReparacionestelefonoEntities(true, -1, -1);
    }

    public List<Reparacionestelefono> findReparacionestelefonoEntities(int maxResults, int firstResult) {
        return findReparacionestelefonoEntities(false, maxResults, firstResult);
    }

    private List<Reparacionestelefono> findReparacionestelefonoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Reparacionestelefono.class));
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

    public Reparacionestelefono findReparacionestelefono(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Reparacionestelefono.class, id);
        } finally {
            em.close();
        }
    }

    public int getReparacionestelefonoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Reparacionestelefono> rt = cq.from(Reparacionestelefono.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
