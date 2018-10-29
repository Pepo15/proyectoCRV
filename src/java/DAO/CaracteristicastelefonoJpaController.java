/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import DAO.exceptions.IllegalOrphanException;
import DAO.exceptions.NonexistentEntityException;
import DAO.exceptions.PreexistingEntityException;
import DTO.Caracteristicastelefono;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import DTO.Telefono;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Pedro
 */
public class CaracteristicastelefonoJpaController implements Serializable {

    public CaracteristicastelefonoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Caracteristicastelefono caracteristicastelefono) throws IllegalOrphanException, PreexistingEntityException, Exception {
        List<String> illegalOrphanMessages = null;
        Telefono telefonoOrphanCheck = caracteristicastelefono.getTelefono();
        if (telefonoOrphanCheck != null) {
            Caracteristicastelefono oldCaracteristicastelefonoOfTelefono = telefonoOrphanCheck.getCaracteristicastelefono();
            if (oldCaracteristicastelefonoOfTelefono != null) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("The Telefono " + telefonoOrphanCheck + " already has an item of type Caracteristicastelefono whose telefono column cannot be null. Please make another selection for the telefono field.");
            }
        }
        if (illegalOrphanMessages != null) {
            throw new IllegalOrphanException(illegalOrphanMessages);
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Telefono telefono = caracteristicastelefono.getTelefono();
            if (telefono != null) {
                telefono = em.getReference(telefono.getClass(), telefono.getCodigoTelefono());
                caracteristicastelefono.setTelefono(telefono);
            }
            em.persist(caracteristicastelefono);
            if (telefono != null) {
                telefono.setCaracteristicastelefono(caracteristicastelefono);
                telefono = em.merge(telefono);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findCaracteristicastelefono(caracteristicastelefono.getCodigoTelefono()) != null) {
                throw new PreexistingEntityException("Caracteristicastelefono " + caracteristicastelefono + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Caracteristicastelefono caracteristicastelefono) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Caracteristicastelefono persistentCaracteristicastelefono = em.find(Caracteristicastelefono.class, caracteristicastelefono.getCodigoTelefono());
            Telefono telefonoOld = persistentCaracteristicastelefono.getTelefono();
            Telefono telefonoNew = caracteristicastelefono.getTelefono();
            List<String> illegalOrphanMessages = null;
            if (telefonoNew != null && !telefonoNew.equals(telefonoOld)) {
                Caracteristicastelefono oldCaracteristicastelefonoOfTelefono = telefonoNew.getCaracteristicastelefono();
                if (oldCaracteristicastelefonoOfTelefono != null) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("The Telefono " + telefonoNew + " already has an item of type Caracteristicastelefono whose telefono column cannot be null. Please make another selection for the telefono field.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (telefonoNew != null) {
                telefonoNew = em.getReference(telefonoNew.getClass(), telefonoNew.getCodigoTelefono());
                caracteristicastelefono.setTelefono(telefonoNew);
            }
            caracteristicastelefono = em.merge(caracteristicastelefono);
            if (telefonoOld != null && !telefonoOld.equals(telefonoNew)) {
                telefonoOld.setCaracteristicastelefono(null);
                telefonoOld = em.merge(telefonoOld);
            }
            if (telefonoNew != null && !telefonoNew.equals(telefonoOld)) {
                telefonoNew.setCaracteristicastelefono(caracteristicastelefono);
                telefonoNew = em.merge(telefonoNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = caracteristicastelefono.getCodigoTelefono();
                if (findCaracteristicastelefono(id) == null) {
                    throw new NonexistentEntityException("The caracteristicastelefono with id " + id + " no longer exists.");
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
            Caracteristicastelefono caracteristicastelefono;
            try {
                caracteristicastelefono = em.getReference(Caracteristicastelefono.class, id);
                caracteristicastelefono.getCodigoTelefono();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The caracteristicastelefono with id " + id + " no longer exists.", enfe);
            }
            Telefono telefono = caracteristicastelefono.getTelefono();
            if (telefono != null) {
                telefono.setCaracteristicastelefono(null);
                telefono = em.merge(telefono);
            }
            em.remove(caracteristicastelefono);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Caracteristicastelefono> findCaracteristicastelefonoEntities() {
        return findCaracteristicastelefonoEntities(true, -1, -1);
    }

    public List<Caracteristicastelefono> findCaracteristicastelefonoEntities(int maxResults, int firstResult) {
        return findCaracteristicastelefonoEntities(false, maxResults, firstResult);
    }

    private List<Caracteristicastelefono> findCaracteristicastelefonoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Caracteristicastelefono.class));
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

    public Caracteristicastelefono findCaracteristicastelefono(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Caracteristicastelefono.class, id);
        } finally {
            em.close();
        }
    }

    public int getCaracteristicastelefonoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Caracteristicastelefono> rt = cq.from(Caracteristicastelefono.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
