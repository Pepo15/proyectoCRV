/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import DAO.exceptions.NonexistentEntityException;
import DAO.exceptions.PreexistingEntityException;
import DTO.Premiosinfoto;
import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

/**
 *
 * @author Pedro
 */
public class PremiosinfotoJpaController implements Serializable {

    public PremiosinfotoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Premiosinfoto premiosinfoto) throws PreexistingEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(premiosinfoto);
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findPremiosinfoto(premiosinfoto.getCodigoPremio()) != null) {
                throw new PreexistingEntityException("Premiosinfoto " + premiosinfoto + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Premiosinfoto premiosinfoto) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            premiosinfoto = em.merge(premiosinfoto);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                int id = premiosinfoto.getCodigoPremio();
                if (findPremiosinfoto(id) == null) {
                    throw new NonexistentEntityException("The premiosinfoto with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(int id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Premiosinfoto premiosinfoto;
            try {
                premiosinfoto = em.getReference(Premiosinfoto.class, id);
                premiosinfoto.getCodigoPremio();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The premiosinfoto with id " + id + " no longer exists.", enfe);
            }
            em.remove(premiosinfoto);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Premiosinfoto> findPremiosinfotoEntities() {
        return findPremiosinfotoEntities(true, -1, -1);
    }

    public List<Premiosinfoto> findPremiosinfotoEntities(int maxResults, int firstResult) {
        return findPremiosinfotoEntities(false, maxResults, firstResult);
    }

    private List<Premiosinfoto> findPremiosinfotoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Premiosinfoto.class));
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

    public Premiosinfoto findPremiosinfoto(int id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Premiosinfoto.class, id);
        } finally {
            em.close();
        }
    }

    public int getPremiosinfotoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Premiosinfoto> rt = cq.from(Premiosinfoto.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
