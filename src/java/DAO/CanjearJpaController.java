/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import DAO.exceptions.NonexistentEntityException;
import DTO.Canjear;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import DTO.Usuario;
import DTO.Direccion;
import DTO.Premio;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Pedro
 */
public class CanjearJpaController implements Serializable {

    public CanjearJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Canjear canjear) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Usuario codigoUsuario = canjear.getCodigoUsuario();
            if (codigoUsuario != null) {
                codigoUsuario = em.getReference(codigoUsuario.getClass(), codigoUsuario.getCodigoUsuario());
                canjear.setCodigoUsuario(codigoUsuario);
            }
            Direccion codigoDireccion = canjear.getCodigoDireccion();
            if (codigoDireccion != null) {
                codigoDireccion = em.getReference(codigoDireccion.getClass(), codigoDireccion.getCodigoDireccion());
                canjear.setCodigoDireccion(codigoDireccion);
            }
            Premio codigoPremio = canjear.getCodigoPremio();
            if (codigoPremio != null) {
                codigoPremio = em.getReference(codigoPremio.getClass(), codigoPremio.getCodigoPremio());
                canjear.setCodigoPremio(codigoPremio);
            }
            em.persist(canjear);
            if (codigoUsuario != null) {
                codigoUsuario.getCanjearList().add(canjear);
                codigoUsuario = em.merge(codigoUsuario);
            }
            if (codigoDireccion != null) {
                codigoDireccion.getCanjearList().add(canjear);
                codigoDireccion = em.merge(codigoDireccion);
            }
            if (codigoPremio != null) {
                codigoPremio.getCanjearList().add(canjear);
                codigoPremio = em.merge(codigoPremio);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Canjear canjear) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Canjear persistentCanjear = em.find(Canjear.class, canjear.getCodigoCanjear());
            Usuario codigoUsuarioOld = persistentCanjear.getCodigoUsuario();
            Usuario codigoUsuarioNew = canjear.getCodigoUsuario();
            Direccion codigoDireccionOld = persistentCanjear.getCodigoDireccion();
            Direccion codigoDireccionNew = canjear.getCodigoDireccion();
            Premio codigoPremioOld = persistentCanjear.getCodigoPremio();
            Premio codigoPremioNew = canjear.getCodigoPremio();
            if (codigoUsuarioNew != null) {
                codigoUsuarioNew = em.getReference(codigoUsuarioNew.getClass(), codigoUsuarioNew.getCodigoUsuario());
                canjear.setCodigoUsuario(codigoUsuarioNew);
            }
            if (codigoDireccionNew != null) {
                codigoDireccionNew = em.getReference(codigoDireccionNew.getClass(), codigoDireccionNew.getCodigoDireccion());
                canjear.setCodigoDireccion(codigoDireccionNew);
            }
            if (codigoPremioNew != null) {
                codigoPremioNew = em.getReference(codigoPremioNew.getClass(), codigoPremioNew.getCodigoPremio());
                canjear.setCodigoPremio(codigoPremioNew);
            }
            canjear = em.merge(canjear);
            if (codigoUsuarioOld != null && !codigoUsuarioOld.equals(codigoUsuarioNew)) {
                codigoUsuarioOld.getCanjearList().remove(canjear);
                codigoUsuarioOld = em.merge(codigoUsuarioOld);
            }
            if (codigoUsuarioNew != null && !codigoUsuarioNew.equals(codigoUsuarioOld)) {
                codigoUsuarioNew.getCanjearList().add(canjear);
                codigoUsuarioNew = em.merge(codigoUsuarioNew);
            }
            if (codigoDireccionOld != null && !codigoDireccionOld.equals(codigoDireccionNew)) {
                codigoDireccionOld.getCanjearList().remove(canjear);
                codigoDireccionOld = em.merge(codigoDireccionOld);
            }
            if (codigoDireccionNew != null && !codigoDireccionNew.equals(codigoDireccionOld)) {
                codigoDireccionNew.getCanjearList().add(canjear);
                codigoDireccionNew = em.merge(codigoDireccionNew);
            }
            if (codigoPremioOld != null && !codigoPremioOld.equals(codigoPremioNew)) {
                codigoPremioOld.getCanjearList().remove(canjear);
                codigoPremioOld = em.merge(codigoPremioOld);
            }
            if (codigoPremioNew != null && !codigoPremioNew.equals(codigoPremioOld)) {
                codigoPremioNew.getCanjearList().add(canjear);
                codigoPremioNew = em.merge(codigoPremioNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = canjear.getCodigoCanjear();
                if (findCanjear(id) == null) {
                    throw new NonexistentEntityException("The canjear with id " + id + " no longer exists.");
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
            Canjear canjear;
            try {
                canjear = em.getReference(Canjear.class, id);
                canjear.getCodigoCanjear();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The canjear with id " + id + " no longer exists.", enfe);
            }
            Usuario codigoUsuario = canjear.getCodigoUsuario();
            if (codigoUsuario != null) {
                codigoUsuario.getCanjearList().remove(canjear);
                codigoUsuario = em.merge(codigoUsuario);
            }
            Direccion codigoDireccion = canjear.getCodigoDireccion();
            if (codigoDireccion != null) {
                codigoDireccion.getCanjearList().remove(canjear);
                codigoDireccion = em.merge(codigoDireccion);
            }
            Premio codigoPremio = canjear.getCodigoPremio();
            if (codigoPremio != null) {
                codigoPremio.getCanjearList().remove(canjear);
                codigoPremio = em.merge(codigoPremio);
            }
            em.remove(canjear);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Canjear> findCanjearEntities() {
        return findCanjearEntities(true, -1, -1);
    }

    public List<Canjear> findCanjearEntities(int maxResults, int firstResult) {
        return findCanjearEntities(false, maxResults, firstResult);
    }

    private List<Canjear> findCanjearEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Canjear.class));
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

    public Canjear findCanjear(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Canjear.class, id);
        } finally {
            em.close();
        }
    }

    public int getCanjearCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Canjear> rt = cq.from(Canjear.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
