/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import DAO.exceptions.NonexistentEntityException;
import DTO.Caracteristicastelefono;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import DTO.Telefono;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;

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

    public void create(Caracteristicastelefono caracteristicastelefono) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Telefono codigoTelefono = caracteristicastelefono.getCodigoTelefono();
            if (codigoTelefono != null) {
                codigoTelefono = em.getReference(codigoTelefono.getClass(), codigoTelefono.getCodigoTelefono());
                caracteristicastelefono.setCodigoTelefono(codigoTelefono);
            }
            em.persist(caracteristicastelefono);
            if (codigoTelefono != null) {
                codigoTelefono.getCaracteristicastelefonoList().add(caracteristicastelefono);
                codigoTelefono = em.merge(codigoTelefono);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Caracteristicastelefono caracteristicastelefono) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Caracteristicastelefono persistentCaracteristicastelefono = em.find(Caracteristicastelefono.class, caracteristicastelefono.getCodigoCaracteristica());
            Telefono codigoTelefonoOld = persistentCaracteristicastelefono.getCodigoTelefono();
            Telefono codigoTelefonoNew = caracteristicastelefono.getCodigoTelefono();
            if (codigoTelefonoNew != null) {
                codigoTelefonoNew = em.getReference(codigoTelefonoNew.getClass(), codigoTelefonoNew.getCodigoTelefono());
                caracteristicastelefono.setCodigoTelefono(codigoTelefonoNew);
            }
            caracteristicastelefono = em.merge(caracteristicastelefono);
            if (codigoTelefonoOld != null && !codigoTelefonoOld.equals(codigoTelefonoNew)) {
                codigoTelefonoOld.getCaracteristicastelefonoList().remove(caracteristicastelefono);
                codigoTelefonoOld = em.merge(codigoTelefonoOld);
            }
            if (codigoTelefonoNew != null && !codigoTelefonoNew.equals(codigoTelefonoOld)) {
                codigoTelefonoNew.getCaracteristicastelefonoList().add(caracteristicastelefono);
                codigoTelefonoNew = em.merge(codigoTelefonoNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = caracteristicastelefono.getCodigoCaracteristica();
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
                caracteristicastelefono.getCodigoCaracteristica();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The caracteristicastelefono with id " + id + " no longer exists.", enfe);
            }
            Telefono codigoTelefono = caracteristicastelefono.getCodigoTelefono();
            if (codigoTelefono != null) {
                codigoTelefono.getCaracteristicastelefonoList().remove(caracteristicastelefono);
                codigoTelefono = em.merge(codigoTelefono);
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
    
    //Creamos el metodo que devuelve unas caracteristicas segun su codigoTelefono
    public Caracteristicastelefono findCaracteristicasByTelefono(Telefono telefono) {
        EntityManager em = getEntityManager();
        try {
        Query query=em.createNamedQuery("Caracteristicastelefono.findByCodigoTelefono").setParameter("codigoTelefono", telefono);
        
        Caracteristicastelefono caracteristica = (Caracteristicastelefono) query.getResultList().get(0);
        
        return caracteristica;
}
        catch(Exception e){
            return null;
        }
        
        finally {
            em.close();
        }
    }
    
    //Creamos el metodo que devuelve los SO que hay entre todos los telefonos
    public List findTodosSODistint() {
        EntityManager em = getEntityManager();
        
        TypedQuery q=em.createNamedQuery("Caracteristicastelefono.findDistinctSO",Caracteristicastelefono.class);
            
        List lista= q.getResultList();
        
        return lista;
    }
    
    //Creamos el metodo que devuelve los SO que hay entre todos los telefonos
    public List findTodosRamDistint() {
        EntityManager em = getEntityManager();
        
        TypedQuery q=em.createNamedQuery("Caracteristicastelefono.findDistinctRam",Caracteristicastelefono.class);
            
        List lista= q.getResultList();
        
        return lista;
    }
    
     //Creamos el metodo que devuelve los SO que hay entre todos los telefonos
    public List findTodosPulgadasDistint() {
        EntityManager em = getEntityManager();
        
        TypedQuery q=em.createNamedQuery("Caracteristicastelefono.findDistinctPulgadas",Caracteristicastelefono.class);
            
        List lista= q.getResultList();
        
        return lista;
    }
    
     //Creamos el metodo que devuelve los SO que hay entre todos los telefonos
    public List findTodosAlmacenamientoDistint() {
        EntityManager em = getEntityManager();
        
        TypedQuery q=em.createNamedQuery("Caracteristicastelefono.findDistinctAlmacenamiento",Caracteristicastelefono.class);
            
        List lista= q.getResultList();
        
        return lista;
    }
    
     //Creamos el metodo que devuelve los SO que hay entre todos los telefonos
    public List findTodosCamaraTraseraDistint() {
        EntityManager em = getEntityManager();
        
        TypedQuery q=em.createNamedQuery("Caracteristicastelefono.findDistinctCamaraTrasera",Caracteristicastelefono.class);
            
        List lista= q.getResultList();
        
        return lista;
    }
    
     //Creamos el metodo que devuelve los SO que hay entre todos los telefonos
    public List findTodosCamaraDelanteraDistint() {
        EntityManager em = getEntityManager();
        
        TypedQuery q=em.createNamedQuery("Caracteristicastelefono.findDistinctCamaraDelantera",Caracteristicastelefono.class);
            
        List lista= q.getResultList();
        
        return lista;
    }
    
     //Creamos el metodo que devuelve los SO que hay entre todos los telefonos
    public List findTodosBateriaDistint() {
        EntityManager em = getEntityManager();
        
        TypedQuery q=em.createNamedQuery("Caracteristicastelefono.findDistinctBateria",Caracteristicastelefono.class);
            
        List lista= q.getResultList();
        
        return lista;
    }
    
     //Creamos el metodo que devuelve los SO que hay entre todos los telefonos
    public List findTodosProcesadorDistint() {
        EntityManager em = getEntityManager();
        
        TypedQuery q=em.createNamedQuery("Caracteristicastelefono.findDistinctProcesador",Caracteristicastelefono.class);
            
        List lista= q.getResultList();
        
        return lista;
    }
    
     //Creamos el metodo que devuelve los SO que hay entre todos los telefonos
    public List findTodosResolucionDistint() {
        EntityManager em = getEntityManager();
        
        TypedQuery q=em.createNamedQuery("Caracteristicastelefono.findDistinctResolucion",Caracteristicastelefono.class);
            
        List lista= q.getResultList();
        
        return lista;
    }
    
      //Creamos el metodo que devuelve los SO que hay entre todos los telefonos
    public List findTodosColorDistint() {
        EntityManager em = getEntityManager();
        
        TypedQuery q=em.createNamedQuery("Caracteristicastelefono.findDistinctColor",Caracteristicastelefono.class);
            
        List lista= q.getResultList();
        
        return lista;
    }
    
}
