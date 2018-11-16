/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import DAO.exceptions.IllegalOrphanException;
import DAO.exceptions.NonexistentEntityException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import DTO.Pedido;
import DTO.Reparaciones;
import java.util.ArrayList;
import java.util.List;
import DTO.Reparacionestelefono;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Pedro
 */
public class ReparacionesJpaController implements Serializable {

    public ReparacionesJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Reparaciones reparaciones) {
        if (reparaciones.getPedidoList() == null) {
            reparaciones.setPedidoList(new ArrayList<Pedido>());
        }
        if (reparaciones.getReparacionestelefonoList() == null) {
            reparaciones.setReparacionestelefonoList(new ArrayList<Reparacionestelefono>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Pedido> attachedPedidoList = new ArrayList<Pedido>();
            for (Pedido pedidoListPedidoToAttach : reparaciones.getPedidoList()) {
                pedidoListPedidoToAttach = em.getReference(pedidoListPedidoToAttach.getClass(), pedidoListPedidoToAttach.getCodigo());
                attachedPedidoList.add(pedidoListPedidoToAttach);
            }
            reparaciones.setPedidoList(attachedPedidoList);
            List<Reparacionestelefono> attachedReparacionestelefonoList = new ArrayList<Reparacionestelefono>();
            for (Reparacionestelefono reparacionestelefonoListReparacionestelefonoToAttach : reparaciones.getReparacionestelefonoList()) {
                reparacionestelefonoListReparacionestelefonoToAttach = em.getReference(reparacionestelefonoListReparacionestelefonoToAttach.getClass(), reparacionestelefonoListReparacionestelefonoToAttach.getCodigoReparacionTelefono());
                attachedReparacionestelefonoList.add(reparacionestelefonoListReparacionestelefonoToAttach);
            }
            reparaciones.setReparacionestelefonoList(attachedReparacionestelefonoList);
            em.persist(reparaciones);
            for (Pedido pedidoListPedido : reparaciones.getPedidoList()) {
                Reparaciones oldCodigoReparacionOfPedidoListPedido = pedidoListPedido.getCodigoReparacion();
                pedidoListPedido.setCodigoReparacion(reparaciones);
                pedidoListPedido = em.merge(pedidoListPedido);
                if (oldCodigoReparacionOfPedidoListPedido != null) {
                    oldCodigoReparacionOfPedidoListPedido.getPedidoList().remove(pedidoListPedido);
                    oldCodigoReparacionOfPedidoListPedido = em.merge(oldCodigoReparacionOfPedidoListPedido);
                }
            }
            for (Reparacionestelefono reparacionestelefonoListReparacionestelefono : reparaciones.getReparacionestelefonoList()) {
                Reparaciones oldCodigoReparacionOfReparacionestelefonoListReparacionestelefono = reparacionestelefonoListReparacionestelefono.getCodigoReparacion();
                reparacionestelefonoListReparacionestelefono.setCodigoReparacion(reparaciones);
                reparacionestelefonoListReparacionestelefono = em.merge(reparacionestelefonoListReparacionestelefono);
                if (oldCodigoReparacionOfReparacionestelefonoListReparacionestelefono != null) {
                    oldCodigoReparacionOfReparacionestelefonoListReparacionestelefono.getReparacionestelefonoList().remove(reparacionestelefonoListReparacionestelefono);
                    oldCodigoReparacionOfReparacionestelefonoListReparacionestelefono = em.merge(oldCodigoReparacionOfReparacionestelefonoListReparacionestelefono);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Reparaciones reparaciones) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Reparaciones persistentReparaciones = em.find(Reparaciones.class, reparaciones.getCodigoReparacion());
            List<Pedido> pedidoListOld = persistentReparaciones.getPedidoList();
            List<Pedido> pedidoListNew = reparaciones.getPedidoList();
            List<Reparacionestelefono> reparacionestelefonoListOld = persistentReparaciones.getReparacionestelefonoList();
            List<Reparacionestelefono> reparacionestelefonoListNew = reparaciones.getReparacionestelefonoList();
            List<String> illegalOrphanMessages = null;
            for (Reparacionestelefono reparacionestelefonoListOldReparacionestelefono : reparacionestelefonoListOld) {
                if (!reparacionestelefonoListNew.contains(reparacionestelefonoListOldReparacionestelefono)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Reparacionestelefono " + reparacionestelefonoListOldReparacionestelefono + " since its codigoReparacion field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            List<Pedido> attachedPedidoListNew = new ArrayList<Pedido>();
            for (Pedido pedidoListNewPedidoToAttach : pedidoListNew) {
                pedidoListNewPedidoToAttach = em.getReference(pedidoListNewPedidoToAttach.getClass(), pedidoListNewPedidoToAttach.getCodigo());
                attachedPedidoListNew.add(pedidoListNewPedidoToAttach);
            }
            pedidoListNew = attachedPedidoListNew;
            reparaciones.setPedidoList(pedidoListNew);
            List<Reparacionestelefono> attachedReparacionestelefonoListNew = new ArrayList<Reparacionestelefono>();
            for (Reparacionestelefono reparacionestelefonoListNewReparacionestelefonoToAttach : reparacionestelefonoListNew) {
                reparacionestelefonoListNewReparacionestelefonoToAttach = em.getReference(reparacionestelefonoListNewReparacionestelefonoToAttach.getClass(), reparacionestelefonoListNewReparacionestelefonoToAttach.getCodigoReparacionTelefono());
                attachedReparacionestelefonoListNew.add(reparacionestelefonoListNewReparacionestelefonoToAttach);
            }
            reparacionestelefonoListNew = attachedReparacionestelefonoListNew;
            reparaciones.setReparacionestelefonoList(reparacionestelefonoListNew);
            reparaciones = em.merge(reparaciones);
            for (Pedido pedidoListOldPedido : pedidoListOld) {
                if (!pedidoListNew.contains(pedidoListOldPedido)) {
                    pedidoListOldPedido.setCodigoReparacion(null);
                    pedidoListOldPedido = em.merge(pedidoListOldPedido);
                }
            }
            for (Pedido pedidoListNewPedido : pedidoListNew) {
                if (!pedidoListOld.contains(pedidoListNewPedido)) {
                    Reparaciones oldCodigoReparacionOfPedidoListNewPedido = pedidoListNewPedido.getCodigoReparacion();
                    pedidoListNewPedido.setCodigoReparacion(reparaciones);
                    pedidoListNewPedido = em.merge(pedidoListNewPedido);
                    if (oldCodigoReparacionOfPedidoListNewPedido != null && !oldCodigoReparacionOfPedidoListNewPedido.equals(reparaciones)) {
                        oldCodigoReparacionOfPedidoListNewPedido.getPedidoList().remove(pedidoListNewPedido);
                        oldCodigoReparacionOfPedidoListNewPedido = em.merge(oldCodigoReparacionOfPedidoListNewPedido);
                    }
                }
            }
            for (Reparacionestelefono reparacionestelefonoListNewReparacionestelefono : reparacionestelefonoListNew) {
                if (!reparacionestelefonoListOld.contains(reparacionestelefonoListNewReparacionestelefono)) {
                    Reparaciones oldCodigoReparacionOfReparacionestelefonoListNewReparacionestelefono = reparacionestelefonoListNewReparacionestelefono.getCodigoReparacion();
                    reparacionestelefonoListNewReparacionestelefono.setCodigoReparacion(reparaciones);
                    reparacionestelefonoListNewReparacionestelefono = em.merge(reparacionestelefonoListNewReparacionestelefono);
                    if (oldCodigoReparacionOfReparacionestelefonoListNewReparacionestelefono != null && !oldCodigoReparacionOfReparacionestelefonoListNewReparacionestelefono.equals(reparaciones)) {
                        oldCodigoReparacionOfReparacionestelefonoListNewReparacionestelefono.getReparacionestelefonoList().remove(reparacionestelefonoListNewReparacionestelefono);
                        oldCodigoReparacionOfReparacionestelefonoListNewReparacionestelefono = em.merge(oldCodigoReparacionOfReparacionestelefonoListNewReparacionestelefono);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = reparaciones.getCodigoReparacion();
                if (findReparaciones(id) == null) {
                    throw new NonexistentEntityException("The reparaciones with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws IllegalOrphanException, NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Reparaciones reparaciones;
            try {
                reparaciones = em.getReference(Reparaciones.class, id);
                reparaciones.getCodigoReparacion();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The reparaciones with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Reparacionestelefono> reparacionestelefonoListOrphanCheck = reparaciones.getReparacionestelefonoList();
            for (Reparacionestelefono reparacionestelefonoListOrphanCheckReparacionestelefono : reparacionestelefonoListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Reparaciones (" + reparaciones + ") cannot be destroyed since the Reparacionestelefono " + reparacionestelefonoListOrphanCheckReparacionestelefono + " in its reparacionestelefonoList field has a non-nullable codigoReparacion field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            List<Pedido> pedidoList = reparaciones.getPedidoList();
            for (Pedido pedidoListPedido : pedidoList) {
                pedidoListPedido.setCodigoReparacion(null);
                pedidoListPedido = em.merge(pedidoListPedido);
            }
            em.remove(reparaciones);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Reparaciones> findReparacionesEntities() {
        return findReparacionesEntities(true, -1, -1);
    }

    public List<Reparaciones> findReparacionesEntities(int maxResults, int firstResult) {
        return findReparacionesEntities(false, maxResults, firstResult);
    }

    private List<Reparaciones> findReparacionesEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Reparaciones.class));
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

    public Reparaciones findReparaciones(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Reparaciones.class, id);
        } finally {
            em.close();
        }
    }

    public int getReparacionesCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Reparaciones> rt = cq.from(Reparaciones.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
    //Creamos el metodo que devuelve una Reparacion segun su nombre
    public Reparaciones findReparacionByNombre(String nombre) {
        EntityManager em = getEntityManager();
        try {
        Query query=em.createNamedQuery("Reparaciones.findByNombre").setParameter("nombre", nombre);
        
        Reparaciones reparacion = (Reparaciones) query.getResultList().get(0);
        
        return reparacion;
}
        catch(Exception e){
            return null;
        }
        
        finally {
            em.close();
        }
    }
    
}
