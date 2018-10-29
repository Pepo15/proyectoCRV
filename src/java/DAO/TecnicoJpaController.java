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
import DTO.Administrador;
import DTO.Pedido;
import DTO.Tecnico;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Pedro
 */
public class TecnicoJpaController implements Serializable {

    public TecnicoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Tecnico tecnico) {
        if (tecnico.getPedidoList() == null) {
            tecnico.setPedidoList(new ArrayList<Pedido>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Administrador codigoAdministrador = tecnico.getCodigoAdministrador();
            if (codigoAdministrador != null) {
                codigoAdministrador = em.getReference(codigoAdministrador.getClass(), codigoAdministrador.getCodigoAdministrador());
                tecnico.setCodigoAdministrador(codigoAdministrador);
            }
            List<Pedido> attachedPedidoList = new ArrayList<Pedido>();
            for (Pedido pedidoListPedidoToAttach : tecnico.getPedidoList()) {
                pedidoListPedidoToAttach = em.getReference(pedidoListPedidoToAttach.getClass(), pedidoListPedidoToAttach.getCodigo());
                attachedPedidoList.add(pedidoListPedidoToAttach);
            }
            tecnico.setPedidoList(attachedPedidoList);
            em.persist(tecnico);
            if (codigoAdministrador != null) {
                codigoAdministrador.getTecnicoList().add(tecnico);
                codigoAdministrador = em.merge(codigoAdministrador);
            }
            for (Pedido pedidoListPedido : tecnico.getPedidoList()) {
                Tecnico oldCodigoTecnicoOfPedidoListPedido = pedidoListPedido.getCodigoTecnico();
                pedidoListPedido.setCodigoTecnico(tecnico);
                pedidoListPedido = em.merge(pedidoListPedido);
                if (oldCodigoTecnicoOfPedidoListPedido != null) {
                    oldCodigoTecnicoOfPedidoListPedido.getPedidoList().remove(pedidoListPedido);
                    oldCodigoTecnicoOfPedidoListPedido = em.merge(oldCodigoTecnicoOfPedidoListPedido);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Tecnico tecnico) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Tecnico persistentTecnico = em.find(Tecnico.class, tecnico.getCodigoTecnico());
            Administrador codigoAdministradorOld = persistentTecnico.getCodigoAdministrador();
            Administrador codigoAdministradorNew = tecnico.getCodigoAdministrador();
            List<Pedido> pedidoListOld = persistentTecnico.getPedidoList();
            List<Pedido> pedidoListNew = tecnico.getPedidoList();
            if (codigoAdministradorNew != null) {
                codigoAdministradorNew = em.getReference(codigoAdministradorNew.getClass(), codigoAdministradorNew.getCodigoAdministrador());
                tecnico.setCodigoAdministrador(codigoAdministradorNew);
            }
            List<Pedido> attachedPedidoListNew = new ArrayList<Pedido>();
            for (Pedido pedidoListNewPedidoToAttach : pedidoListNew) {
                pedidoListNewPedidoToAttach = em.getReference(pedidoListNewPedidoToAttach.getClass(), pedidoListNewPedidoToAttach.getCodigo());
                attachedPedidoListNew.add(pedidoListNewPedidoToAttach);
            }
            pedidoListNew = attachedPedidoListNew;
            tecnico.setPedidoList(pedidoListNew);
            tecnico = em.merge(tecnico);
            if (codigoAdministradorOld != null && !codigoAdministradorOld.equals(codigoAdministradorNew)) {
                codigoAdministradorOld.getTecnicoList().remove(tecnico);
                codigoAdministradorOld = em.merge(codigoAdministradorOld);
            }
            if (codigoAdministradorNew != null && !codigoAdministradorNew.equals(codigoAdministradorOld)) {
                codigoAdministradorNew.getTecnicoList().add(tecnico);
                codigoAdministradorNew = em.merge(codigoAdministradorNew);
            }
            for (Pedido pedidoListOldPedido : pedidoListOld) {
                if (!pedidoListNew.contains(pedidoListOldPedido)) {
                    pedidoListOldPedido.setCodigoTecnico(null);
                    pedidoListOldPedido = em.merge(pedidoListOldPedido);
                }
            }
            for (Pedido pedidoListNewPedido : pedidoListNew) {
                if (!pedidoListOld.contains(pedidoListNewPedido)) {
                    Tecnico oldCodigoTecnicoOfPedidoListNewPedido = pedidoListNewPedido.getCodigoTecnico();
                    pedidoListNewPedido.setCodigoTecnico(tecnico);
                    pedidoListNewPedido = em.merge(pedidoListNewPedido);
                    if (oldCodigoTecnicoOfPedidoListNewPedido != null && !oldCodigoTecnicoOfPedidoListNewPedido.equals(tecnico)) {
                        oldCodigoTecnicoOfPedidoListNewPedido.getPedidoList().remove(pedidoListNewPedido);
                        oldCodigoTecnicoOfPedidoListNewPedido = em.merge(oldCodigoTecnicoOfPedidoListNewPedido);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = tecnico.getCodigoTecnico();
                if (findTecnico(id) == null) {
                    throw new NonexistentEntityException("The tecnico with id " + id + " no longer exists.");
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
            Tecnico tecnico;
            try {
                tecnico = em.getReference(Tecnico.class, id);
                tecnico.getCodigoTecnico();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The tecnico with id " + id + " no longer exists.", enfe);
            }
            Administrador codigoAdministrador = tecnico.getCodigoAdministrador();
            if (codigoAdministrador != null) {
                codigoAdministrador.getTecnicoList().remove(tecnico);
                codigoAdministrador = em.merge(codigoAdministrador);
            }
            List<Pedido> pedidoList = tecnico.getPedidoList();
            for (Pedido pedidoListPedido : pedidoList) {
                pedidoListPedido.setCodigoTecnico(null);
                pedidoListPedido = em.merge(pedidoListPedido);
            }
            em.remove(tecnico);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Tecnico> findTecnicoEntities() {
        return findTecnicoEntities(true, -1, -1);
    }

    public List<Tecnico> findTecnicoEntities(int maxResults, int firstResult) {
        return findTecnicoEntities(false, maxResults, firstResult);
    }

    private List<Tecnico> findTecnicoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Tecnico.class));
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

    public Tecnico findTecnico(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Tecnico.class, id);
        } finally {
            em.close();
        }
    }

    public int getTecnicoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Tecnico> rt = cq.from(Tecnico.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
    //Creamos el metodo que devuelve un tecnico segun su nick
    public Tecnico findTecnicoByNick(String nick) {
        EntityManager em = getEntityManager();
        try {
        Query query=em.createNamedQuery("Tecnico.findByNick").setParameter("nick", nick);
        
        Tecnico tecnico = (Tecnico) query.getResultList().get(0);
        
        return tecnico;
        }
        catch(Exception e){
            return null;
        }
        
        finally {
            em.close();
        }
    }	
	
    
}
