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
import DTO.Pedido;
import DTO.Tarjeta;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Pedro
 */
public class TarjetaJpaController implements Serializable {

    public TarjetaJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Tarjeta tarjeta) {
        if (tarjeta.getPedidoList() == null) {
            tarjeta.setPedidoList(new ArrayList<Pedido>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Usuario codigoUsuario = tarjeta.getCodigoUsuario();
            if (codigoUsuario != null) {
                codigoUsuario = em.getReference(codigoUsuario.getClass(), codigoUsuario.getCodigoUsuario());
                tarjeta.setCodigoUsuario(codigoUsuario);
            }
            List<Pedido> attachedPedidoList = new ArrayList<Pedido>();
            for (Pedido pedidoListPedidoToAttach : tarjeta.getPedidoList()) {
                pedidoListPedidoToAttach = em.getReference(pedidoListPedidoToAttach.getClass(), pedidoListPedidoToAttach.getCodigo());
                attachedPedidoList.add(pedidoListPedidoToAttach);
            }
            tarjeta.setPedidoList(attachedPedidoList);
            em.persist(tarjeta);
            if (codigoUsuario != null) {
                codigoUsuario.getTarjetaList().add(tarjeta);
                codigoUsuario = em.merge(codigoUsuario);
            }
            for (Pedido pedidoListPedido : tarjeta.getPedidoList()) {
                Tarjeta oldCodigoTarjetaOfPedidoListPedido = pedidoListPedido.getCodigoTarjeta();
                pedidoListPedido.setCodigoTarjeta(tarjeta);
                pedidoListPedido = em.merge(pedidoListPedido);
                if (oldCodigoTarjetaOfPedidoListPedido != null) {
                    oldCodigoTarjetaOfPedidoListPedido.getPedidoList().remove(pedidoListPedido);
                    oldCodigoTarjetaOfPedidoListPedido = em.merge(oldCodigoTarjetaOfPedidoListPedido);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Tarjeta tarjeta) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Tarjeta persistentTarjeta = em.find(Tarjeta.class, tarjeta.getCodigoTarjeta());
            Usuario codigoUsuarioOld = persistentTarjeta.getCodigoUsuario();
            Usuario codigoUsuarioNew = tarjeta.getCodigoUsuario();
            List<Pedido> pedidoListOld = persistentTarjeta.getPedidoList();
            List<Pedido> pedidoListNew = tarjeta.getPedidoList();
            List<String> illegalOrphanMessages = null;
            for (Pedido pedidoListOldPedido : pedidoListOld) {
                if (!pedidoListNew.contains(pedidoListOldPedido)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Pedido " + pedidoListOldPedido + " since its codigoTarjeta field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (codigoUsuarioNew != null) {
                codigoUsuarioNew = em.getReference(codigoUsuarioNew.getClass(), codigoUsuarioNew.getCodigoUsuario());
                tarjeta.setCodigoUsuario(codigoUsuarioNew);
            }
            List<Pedido> attachedPedidoListNew = new ArrayList<Pedido>();
            for (Pedido pedidoListNewPedidoToAttach : pedidoListNew) {
                pedidoListNewPedidoToAttach = em.getReference(pedidoListNewPedidoToAttach.getClass(), pedidoListNewPedidoToAttach.getCodigo());
                attachedPedidoListNew.add(pedidoListNewPedidoToAttach);
            }
            pedidoListNew = attachedPedidoListNew;
            tarjeta.setPedidoList(pedidoListNew);
            tarjeta = em.merge(tarjeta);
            if (codigoUsuarioOld != null && !codigoUsuarioOld.equals(codigoUsuarioNew)) {
                codigoUsuarioOld.getTarjetaList().remove(tarjeta);
                codigoUsuarioOld = em.merge(codigoUsuarioOld);
            }
            if (codigoUsuarioNew != null && !codigoUsuarioNew.equals(codigoUsuarioOld)) {
                codigoUsuarioNew.getTarjetaList().add(tarjeta);
                codigoUsuarioNew = em.merge(codigoUsuarioNew);
            }
            for (Pedido pedidoListNewPedido : pedidoListNew) {
                if (!pedidoListOld.contains(pedidoListNewPedido)) {
                    Tarjeta oldCodigoTarjetaOfPedidoListNewPedido = pedidoListNewPedido.getCodigoTarjeta();
                    pedidoListNewPedido.setCodigoTarjeta(tarjeta);
                    pedidoListNewPedido = em.merge(pedidoListNewPedido);
                    if (oldCodigoTarjetaOfPedidoListNewPedido != null && !oldCodigoTarjetaOfPedidoListNewPedido.equals(tarjeta)) {
                        oldCodigoTarjetaOfPedidoListNewPedido.getPedidoList().remove(pedidoListNewPedido);
                        oldCodigoTarjetaOfPedidoListNewPedido = em.merge(oldCodigoTarjetaOfPedidoListNewPedido);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = tarjeta.getCodigoTarjeta();
                if (findTarjeta(id) == null) {
                    throw new NonexistentEntityException("The tarjeta with id " + id + " no longer exists.");
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
            Tarjeta tarjeta;
            try {
                tarjeta = em.getReference(Tarjeta.class, id);
                tarjeta.getCodigoTarjeta();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The tarjeta with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Pedido> pedidoListOrphanCheck = tarjeta.getPedidoList();
            for (Pedido pedidoListOrphanCheckPedido : pedidoListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Tarjeta (" + tarjeta + ") cannot be destroyed since the Pedido " + pedidoListOrphanCheckPedido + " in its pedidoList field has a non-nullable codigoTarjeta field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Usuario codigoUsuario = tarjeta.getCodigoUsuario();
            if (codigoUsuario != null) {
                codigoUsuario.getTarjetaList().remove(tarjeta);
                codigoUsuario = em.merge(codigoUsuario);
            }
            em.remove(tarjeta);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Tarjeta> findTarjetaEntities() {
        return findTarjetaEntities(true, -1, -1);
    }

    public List<Tarjeta> findTarjetaEntities(int maxResults, int firstResult) {
        return findTarjetaEntities(false, maxResults, firstResult);
    }

    private List<Tarjeta> findTarjetaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Tarjeta.class));
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

    public Tarjeta findTarjeta(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Tarjeta.class, id);
        } finally {
            em.close();
        }
    }

    public int getTarjetaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Tarjeta> rt = cq.from(Tarjeta.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
