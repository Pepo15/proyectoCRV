/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import DAO.exceptions.NonexistentEntityException;
import DAO.exceptions.PreexistingEntityException;
import DTO.Premioconfoto;
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
public class PremioconfotoJpaController implements Serializable {

    public PremioconfotoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Premioconfoto premioconfoto) throws PreexistingEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(premioconfoto);
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findPremioconfoto(premioconfoto.getCodigoPremio()) != null) {
                throw new PreexistingEntityException("Premioconfoto " + premioconfoto + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Premioconfoto premioconfoto) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            premioconfoto = em.merge(premioconfoto);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                int id = premioconfoto.getCodigoPremio();
                if (findPremioconfoto(id) == null) {
                    throw new NonexistentEntityException("The premioconfoto with id " + id + " no longer exists.");
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
            Premioconfoto premioconfoto;
            try {
                premioconfoto = em.getReference(Premioconfoto.class, id);
                premioconfoto.getCodigoPremio();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The premioconfoto with id " + id + " no longer exists.", enfe);
            }
            em.remove(premioconfoto);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Premioconfoto> findPremioconfotoEntities() {
        return findPremioconfotoEntities(true, -1, -1);
    }

    public List<Premioconfoto> findPremioconfotoEntities(int maxResults, int firstResult) {
        return findPremioconfotoEntities(false, maxResults, firstResult);
    }

    private List<Premioconfoto> findPremioconfotoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Premioconfoto.class));
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

    public Premioconfoto findPremioconfoto(int id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Premioconfoto.class, id);
        } finally {
            em.close();
        }
    }

    public int getPremioconfotoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Premioconfoto> rt = cq.from(Premioconfoto.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
