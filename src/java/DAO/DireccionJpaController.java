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
import DTO.Usuario;
import DTO.Canjear;
import DTO.Direccion;
import java.util.ArrayList;
import java.util.List;
import DTO.Pedido;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Pedro
 */
public class DireccionJpaController implements Serializable {

    public DireccionJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Direccion direccion) {
        if (direccion.getCanjearList() == null) {
            direccion.setCanjearList(new ArrayList<Canjear>());
        }
        if (direccion.getPedidoList() == null) {
            direccion.setPedidoList(new ArrayList<Pedido>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Usuario codigoUsuario = direccion.getCodigoUsuario();
            if (codigoUsuario != null) {
                codigoUsuario = em.getReference(codigoUsuario.getClass(), codigoUsuario.getCodigoUsuario());
                direccion.setCodigoUsuario(codigoUsuario);
            }
            List<Canjear> attachedCanjearList = new ArrayList<Canjear>();
            for (Canjear canjearListCanjearToAttach : direccion.getCanjearList()) {
                canjearListCanjearToAttach = em.getReference(canjearListCanjearToAttach.getClass(), canjearListCanjearToAttach.getCodigoCanjear());
                attachedCanjearList.add(canjearListCanjearToAttach);
            }
            direccion.setCanjearList(attachedCanjearList);
            List<Pedido> attachedPedidoList = new ArrayList<Pedido>();
            for (Pedido pedidoListPedidoToAttach : direccion.getPedidoList()) {
                pedidoListPedidoToAttach = em.getReference(pedidoListPedidoToAttach.getClass(), pedidoListPedidoToAttach.getCodigo());
                attachedPedidoList.add(pedidoListPedidoToAttach);
            }
            direccion.setPedidoList(attachedPedidoList);
            em.persist(direccion);
            if (codigoUsuario != null) {
                codigoUsuario.getDireccionList().add(direccion);
                codigoUsuario = em.merge(codigoUsuario);
            }
            for (Canjear canjearListCanjear : direccion.getCanjearList()) {
                Direccion oldCodigoDireccionOfCanjearListCanjear = canjearListCanjear.getCodigoDireccion();
                canjearListCanjear.setCodigoDireccion(direccion);
                canjearListCanjear = em.merge(canjearListCanjear);
                if (oldCodigoDireccionOfCanjearListCanjear != null) {
                    oldCodigoDireccionOfCanjearListCanjear.getCanjearList().remove(canjearListCanjear);
                    oldCodigoDireccionOfCanjearListCanjear = em.merge(oldCodigoDireccionOfCanjearListCanjear);
                }
            }
            for (Pedido pedidoListPedido : direccion.getPedidoList()) {
                Direccion oldCodigoDireccionOfPedidoListPedido = pedidoListPedido.getCodigoDireccion();
                pedidoListPedido.setCodigoDireccion(direccion);
                pedidoListPedido = em.merge(pedidoListPedido);
                if (oldCodigoDireccionOfPedidoListPedido != null) {
                    oldCodigoDireccionOfPedidoListPedido.getPedidoList().remove(pedidoListPedido);
                    oldCodigoDireccionOfPedidoListPedido = em.merge(oldCodigoDireccionOfPedidoListPedido);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Direccion direccion) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Direccion persistentDireccion = em.find(Direccion.class, direccion.getCodigoDireccion());
            Usuario codigoUsuarioOld = persistentDireccion.getCodigoUsuario();
            Usuario codigoUsuarioNew = direccion.getCodigoUsuario();
            List<Canjear> canjearListOld = persistentDireccion.getCanjearList();
            List<Canjear> canjearListNew = direccion.getCanjearList();
            List<Pedido> pedidoListOld = persistentDireccion.getPedidoList();
            List<Pedido> pedidoListNew = direccion.getPedidoList();
            List<String> illegalOrphanMessages = null;
            for (Canjear canjearListOldCanjear : canjearListOld) {
                if (!canjearListNew.contains(canjearListOldCanjear)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Canjear " + canjearListOldCanjear + " since its codigoDireccion field is not nullable.");
                }
            }
            for (Pedido pedidoListOldPedido : pedidoListOld) {
                if (!pedidoListNew.contains(pedidoListOldPedido)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Pedido " + pedidoListOldPedido + " since its codigoDireccion field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (codigoUsuarioNew != null) {
                codigoUsuarioNew = em.getReference(codigoUsuarioNew.getClass(), codigoUsuarioNew.getCodigoUsuario());
                direccion.setCodigoUsuario(codigoUsuarioNew);
            }
            List<Canjear> attachedCanjearListNew = new ArrayList<Canjear>();
            for (Canjear canjearListNewCanjearToAttach : canjearListNew) {
                canjearListNewCanjearToAttach = em.getReference(canjearListNewCanjearToAttach.getClass(), canjearListNewCanjearToAttach.getCodigoCanjear());
                attachedCanjearListNew.add(canjearListNewCanjearToAttach);
            }
            canjearListNew = attachedCanjearListNew;
            direccion.setCanjearList(canjearListNew);
            List<Pedido> attachedPedidoListNew = new ArrayList<Pedido>();
            for (Pedido pedidoListNewPedidoToAttach : pedidoListNew) {
                pedidoListNewPedidoToAttach = em.getReference(pedidoListNewPedidoToAttach.getClass(), pedidoListNewPedidoToAttach.getCodigo());
                attachedPedidoListNew.add(pedidoListNewPedidoToAttach);
            }
            pedidoListNew = attachedPedidoListNew;
            direccion.setPedidoList(pedidoListNew);
            direccion = em.merge(direccion);
            if (codigoUsuarioOld != null && !codigoUsuarioOld.equals(codigoUsuarioNew)) {
                codigoUsuarioOld.getDireccionList().remove(direccion);
                codigoUsuarioOld = em.merge(codigoUsuarioOld);
            }
            if (codigoUsuarioNew != null && !codigoUsuarioNew.equals(codigoUsuarioOld)) {
                codigoUsuarioNew.getDireccionList().add(direccion);
                codigoUsuarioNew = em.merge(codigoUsuarioNew);
            }
            for (Canjear canjearListNewCanjear : canjearListNew) {
                if (!canjearListOld.contains(canjearListNewCanjear)) {
                    Direccion oldCodigoDireccionOfCanjearListNewCanjear = canjearListNewCanjear.getCodigoDireccion();
                    canjearListNewCanjear.setCodigoDireccion(direccion);
                    canjearListNewCanjear = em.merge(canjearListNewCanjear);
                    if (oldCodigoDireccionOfCanjearListNewCanjear != null && !oldCodigoDireccionOfCanjearListNewCanjear.equals(direccion)) {
                        oldCodigoDireccionOfCanjearListNewCanjear.getCanjearList().remove(canjearListNewCanjear);
                        oldCodigoDireccionOfCanjearListNewCanjear = em.merge(oldCodigoDireccionOfCanjearListNewCanjear);
                    }
                }
            }
            for (Pedido pedidoListNewPedido : pedidoListNew) {
                if (!pedidoListOld.contains(pedidoListNewPedido)) {
                    Direccion oldCodigoDireccionOfPedidoListNewPedido = pedidoListNewPedido.getCodigoDireccion();
                    pedidoListNewPedido.setCodigoDireccion(direccion);
                    pedidoListNewPedido = em.merge(pedidoListNewPedido);
                    if (oldCodigoDireccionOfPedidoListNewPedido != null && !oldCodigoDireccionOfPedidoListNewPedido.equals(direccion)) {
                        oldCodigoDireccionOfPedidoListNewPedido.getPedidoList().remove(pedidoListNewPedido);
                        oldCodigoDireccionOfPedidoListNewPedido = em.merge(oldCodigoDireccionOfPedidoListNewPedido);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = direccion.getCodigoDireccion();
                if (findDireccion(id) == null) {
                    throw new NonexistentEntityException("The direccion with id " + id + " no longer exists.");
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
            Direccion direccion;
            try {
                direccion = em.getReference(Direccion.class, id);
                direccion.getCodigoDireccion();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The direccion with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Canjear> canjearListOrphanCheck = direccion.getCanjearList();
            for (Canjear canjearListOrphanCheckCanjear : canjearListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Direccion (" + direccion + ") cannot be destroyed since the Canjear " + canjearListOrphanCheckCanjear + " in its canjearList field has a non-nullable codigoDireccion field.");
            }
            List<Pedido> pedidoListOrphanCheck = direccion.getPedidoList();
            for (Pedido pedidoListOrphanCheckPedido : pedidoListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Direccion (" + direccion + ") cannot be destroyed since the Pedido " + pedidoListOrphanCheckPedido + " in its pedidoList field has a non-nullable codigoDireccion field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Usuario codigoUsuario = direccion.getCodigoUsuario();
            if (codigoUsuario != null) {
                codigoUsuario.getDireccionList().remove(direccion);
                codigoUsuario = em.merge(codigoUsuario);
            }
            em.remove(direccion);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Direccion> findDireccionEntities() {
        return findDireccionEntities(true, -1, -1);
    }

    public List<Direccion> findDireccionEntities(int maxResults, int firstResult) {
        return findDireccionEntities(false, maxResults, firstResult);
    }

    private List<Direccion> findDireccionEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Direccion.class));
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

    public Direccion findDireccion(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Direccion.class, id);
        } finally {
            em.close();
        }
    }

    public int getDireccionCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Direccion> rt = cq.from(Direccion.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
