/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import DAO.exceptions.NonexistentEntityException;
import DTO.Foto;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import DTO.Premio;
import DTO.Telefono;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;

/**
 *
 * @author Pedro
 */
public class FotoJpaController implements Serializable {

    public FotoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Foto foto) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Premio codigoPremio = foto.getCodigoPremio();
            if (codigoPremio != null) {
                codigoPremio = em.getReference(codigoPremio.getClass(), codigoPremio.getCodigoPremio());
                foto.setCodigoPremio(codigoPremio);
            }
            Telefono codigoTelefono = foto.getCodigoTelefono();
            if (codigoTelefono != null) {
                codigoTelefono = em.getReference(codigoTelefono.getClass(), codigoTelefono.getCodigoTelefono());
                foto.setCodigoTelefono(codigoTelefono);
            }
            em.persist(foto);
            if (codigoPremio != null) {
                codigoPremio.getFotoList().add(foto);
                codigoPremio = em.merge(codigoPremio);
            }
            if (codigoTelefono != null) {
                codigoTelefono.getFotoList().add(foto);
                codigoTelefono = em.merge(codigoTelefono);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Foto foto) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Foto persistentFoto = em.find(Foto.class, foto.getCodigoFoto());
            Premio codigoPremioOld = persistentFoto.getCodigoPremio();
            Premio codigoPremioNew = foto.getCodigoPremio();
            Telefono codigoTelefonoOld = persistentFoto.getCodigoTelefono();
            Telefono codigoTelefonoNew = foto.getCodigoTelefono();
            if (codigoPremioNew != null) {
                codigoPremioNew = em.getReference(codigoPremioNew.getClass(), codigoPremioNew.getCodigoPremio());
                foto.setCodigoPremio(codigoPremioNew);
            }
            if (codigoTelefonoNew != null) {
                codigoTelefonoNew = em.getReference(codigoTelefonoNew.getClass(), codigoTelefonoNew.getCodigoTelefono());
                foto.setCodigoTelefono(codigoTelefonoNew);
            }
            foto = em.merge(foto);
            if (codigoPremioOld != null && !codigoPremioOld.equals(codigoPremioNew)) {
                codigoPremioOld.getFotoList().remove(foto);
                codigoPremioOld = em.merge(codigoPremioOld);
            }
            if (codigoPremioNew != null && !codigoPremioNew.equals(codigoPremioOld)) {
                codigoPremioNew.getFotoList().add(foto);
                codigoPremioNew = em.merge(codigoPremioNew);
            }
            if (codigoTelefonoOld != null && !codigoTelefonoOld.equals(codigoTelefonoNew)) {
                codigoTelefonoOld.getFotoList().remove(foto);
                codigoTelefonoOld = em.merge(codigoTelefonoOld);
            }
            if (codigoTelefonoNew != null && !codigoTelefonoNew.equals(codigoTelefonoOld)) {
                codigoTelefonoNew.getFotoList().add(foto);
                codigoTelefonoNew = em.merge(codigoTelefonoNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = foto.getCodigoFoto();
                if (findFoto(id) == null) {
                    throw new NonexistentEntityException("The foto with id " + id + " no longer exists.");
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
            Foto foto;
            try {
                foto = em.getReference(Foto.class, id);
                foto.getCodigoFoto();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The foto with id " + id + " no longer exists.", enfe);
            }
            Premio codigoPremio = foto.getCodigoPremio();
            if (codigoPremio != null) {
                codigoPremio.getFotoList().remove(foto);
                codigoPremio = em.merge(codigoPremio);
            }
            Telefono codigoTelefono = foto.getCodigoTelefono();
            if (codigoTelefono != null) {
                codigoTelefono.getFotoList().remove(foto);
                codigoTelefono = em.merge(codigoTelefono);
            }
            em.remove(foto);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Foto> findFotoEntities() {
        return findFotoEntities(true, -1, -1);
    }

    public List<Foto> findFotoEntities(int maxResults, int firstResult) {
        return findFotoEntities(false, maxResults, firstResult);
    }

    private List<Foto> findFotoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Foto.class));
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

    public Foto findFoto(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Foto.class, id);
        } finally {
            em.close();
        }
    }

    public int getFotoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Foto> rt = cq.from(Foto.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
    //Creamos el metodo que devuelve un telefono segun su nombre
    public Foto findFotoByCodigoPremio(Premio premio) {
        EntityManager em = getEntityManager();
        try {
        Query query=em.createNamedQuery("Foto.findByCodigoPremio").setParameter("codigoPremio", premio);
        
        Foto foto = (Foto) query.getResultList().get(0);
        
        return foto;
}
        catch(Exception e){
            return null;
        }
        
        finally {
            em.close();
        }
    }
    
    //Creamos el metodo que devuelve un telefono segun su marca
    public List findFotoByCodigoTelefono(Telefono telefono) {
        EntityManager em = getEntityManager();
        
        TypedQuery q=em.createNamedQuery("Foto.findByCodigoTelefono",Foto.class).setParameter("codigoTelefono", telefono);
            
        List lista= q.getResultList();
        
        return lista;
    }
    
    
    
}
