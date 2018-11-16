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
import DTO.Direccion;
import java.util.ArrayList;
import java.util.List;
import DTO.Canjear;
import DTO.Pedido;
import DTO.Tarjeta;
import DTO.Usuario;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Pedro
 */
public class UsuarioJpaController implements Serializable {

    public UsuarioJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Usuario usuario) {
        if (usuario.getDireccionList() == null) {
            usuario.setDireccionList(new ArrayList<Direccion>());
        }
        if (usuario.getCanjearList() == null) {
            usuario.setCanjearList(new ArrayList<Canjear>());
        }
        if (usuario.getPedidoList() == null) {
            usuario.setPedidoList(new ArrayList<Pedido>());
        }
        if (usuario.getTarjetaList() == null) {
            usuario.setTarjetaList(new ArrayList<Tarjeta>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Direccion> attachedDireccionList = new ArrayList<Direccion>();
            for (Direccion direccionListDireccionToAttach : usuario.getDireccionList()) {
                direccionListDireccionToAttach = em.getReference(direccionListDireccionToAttach.getClass(), direccionListDireccionToAttach.getCodigoDireccion());
                attachedDireccionList.add(direccionListDireccionToAttach);
            }
            usuario.setDireccionList(attachedDireccionList);
            List<Canjear> attachedCanjearList = new ArrayList<Canjear>();
            for (Canjear canjearListCanjearToAttach : usuario.getCanjearList()) {
                canjearListCanjearToAttach = em.getReference(canjearListCanjearToAttach.getClass(), canjearListCanjearToAttach.getCodigoCanjear());
                attachedCanjearList.add(canjearListCanjearToAttach);
            }
            usuario.setCanjearList(attachedCanjearList);
            List<Pedido> attachedPedidoList = new ArrayList<Pedido>();
            for (Pedido pedidoListPedidoToAttach : usuario.getPedidoList()) {
                pedidoListPedidoToAttach = em.getReference(pedidoListPedidoToAttach.getClass(), pedidoListPedidoToAttach.getCodigo());
                attachedPedidoList.add(pedidoListPedidoToAttach);
            }
            usuario.setPedidoList(attachedPedidoList);
            List<Tarjeta> attachedTarjetaList = new ArrayList<Tarjeta>();
            for (Tarjeta tarjetaListTarjetaToAttach : usuario.getTarjetaList()) {
                tarjetaListTarjetaToAttach = em.getReference(tarjetaListTarjetaToAttach.getClass(), tarjetaListTarjetaToAttach.getCodigoTarjeta());
                attachedTarjetaList.add(tarjetaListTarjetaToAttach);
            }
            usuario.setTarjetaList(attachedTarjetaList);
            em.persist(usuario);
            for (Direccion direccionListDireccion : usuario.getDireccionList()) {
                Usuario oldCodigoUsuarioOfDireccionListDireccion = direccionListDireccion.getCodigoUsuario();
                direccionListDireccion.setCodigoUsuario(usuario);
                direccionListDireccion = em.merge(direccionListDireccion);
                if (oldCodigoUsuarioOfDireccionListDireccion != null) {
                    oldCodigoUsuarioOfDireccionListDireccion.getDireccionList().remove(direccionListDireccion);
                    oldCodigoUsuarioOfDireccionListDireccion = em.merge(oldCodigoUsuarioOfDireccionListDireccion);
                }
            }
            for (Canjear canjearListCanjear : usuario.getCanjearList()) {
                Usuario oldCodigoUsuarioOfCanjearListCanjear = canjearListCanjear.getCodigoUsuario();
                canjearListCanjear.setCodigoUsuario(usuario);
                canjearListCanjear = em.merge(canjearListCanjear);
                if (oldCodigoUsuarioOfCanjearListCanjear != null) {
                    oldCodigoUsuarioOfCanjearListCanjear.getCanjearList().remove(canjearListCanjear);
                    oldCodigoUsuarioOfCanjearListCanjear = em.merge(oldCodigoUsuarioOfCanjearListCanjear);
                }
            }
            for (Pedido pedidoListPedido : usuario.getPedidoList()) {
                Usuario oldCodigoUsuarioOfPedidoListPedido = pedidoListPedido.getCodigoUsuario();
                pedidoListPedido.setCodigoUsuario(usuario);
                pedidoListPedido = em.merge(pedidoListPedido);
                if (oldCodigoUsuarioOfPedidoListPedido != null) {
                    oldCodigoUsuarioOfPedidoListPedido.getPedidoList().remove(pedidoListPedido);
                    oldCodigoUsuarioOfPedidoListPedido = em.merge(oldCodigoUsuarioOfPedidoListPedido);
                }
            }
            for (Tarjeta tarjetaListTarjeta : usuario.getTarjetaList()) {
                Usuario oldCodigoUsuarioOfTarjetaListTarjeta = tarjetaListTarjeta.getCodigoUsuario();
                tarjetaListTarjeta.setCodigoUsuario(usuario);
                tarjetaListTarjeta = em.merge(tarjetaListTarjeta);
                if (oldCodigoUsuarioOfTarjetaListTarjeta != null) {
                    oldCodigoUsuarioOfTarjetaListTarjeta.getTarjetaList().remove(tarjetaListTarjeta);
                    oldCodigoUsuarioOfTarjetaListTarjeta = em.merge(oldCodigoUsuarioOfTarjetaListTarjeta);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Usuario usuario) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Usuario persistentUsuario = em.find(Usuario.class, usuario.getCodigoUsuario());
            List<Direccion> direccionListOld = persistentUsuario.getDireccionList();
            List<Direccion> direccionListNew = usuario.getDireccionList();
            List<Canjear> canjearListOld = persistentUsuario.getCanjearList();
            List<Canjear> canjearListNew = usuario.getCanjearList();
            List<Pedido> pedidoListOld = persistentUsuario.getPedidoList();
            List<Pedido> pedidoListNew = usuario.getPedidoList();
            List<Tarjeta> tarjetaListOld = persistentUsuario.getTarjetaList();
            List<Tarjeta> tarjetaListNew = usuario.getTarjetaList();
            List<String> illegalOrphanMessages = null;
            for (Direccion direccionListOldDireccion : direccionListOld) {
                if (!direccionListNew.contains(direccionListOldDireccion)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Direccion " + direccionListOldDireccion + " since its codigoUsuario field is not nullable.");
                }
            }
            for (Canjear canjearListOldCanjear : canjearListOld) {
                if (!canjearListNew.contains(canjearListOldCanjear)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Canjear " + canjearListOldCanjear + " since its codigoUsuario field is not nullable.");
                }
            }
            for (Pedido pedidoListOldPedido : pedidoListOld) {
                if (!pedidoListNew.contains(pedidoListOldPedido)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Pedido " + pedidoListOldPedido + " since its codigoUsuario field is not nullable.");
                }
            }
            for (Tarjeta tarjetaListOldTarjeta : tarjetaListOld) {
                if (!tarjetaListNew.contains(tarjetaListOldTarjeta)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Tarjeta " + tarjetaListOldTarjeta + " since its codigoUsuario field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            List<Direccion> attachedDireccionListNew = new ArrayList<Direccion>();
            for (Direccion direccionListNewDireccionToAttach : direccionListNew) {
                direccionListNewDireccionToAttach = em.getReference(direccionListNewDireccionToAttach.getClass(), direccionListNewDireccionToAttach.getCodigoDireccion());
                attachedDireccionListNew.add(direccionListNewDireccionToAttach);
            }
            direccionListNew = attachedDireccionListNew;
            usuario.setDireccionList(direccionListNew);
            List<Canjear> attachedCanjearListNew = new ArrayList<Canjear>();
            for (Canjear canjearListNewCanjearToAttach : canjearListNew) {
                canjearListNewCanjearToAttach = em.getReference(canjearListNewCanjearToAttach.getClass(), canjearListNewCanjearToAttach.getCodigoCanjear());
                attachedCanjearListNew.add(canjearListNewCanjearToAttach);
            }
            canjearListNew = attachedCanjearListNew;
            usuario.setCanjearList(canjearListNew);
            List<Pedido> attachedPedidoListNew = new ArrayList<Pedido>();
            for (Pedido pedidoListNewPedidoToAttach : pedidoListNew) {
                pedidoListNewPedidoToAttach = em.getReference(pedidoListNewPedidoToAttach.getClass(), pedidoListNewPedidoToAttach.getCodigo());
                attachedPedidoListNew.add(pedidoListNewPedidoToAttach);
            }
            pedidoListNew = attachedPedidoListNew;
            usuario.setPedidoList(pedidoListNew);
            List<Tarjeta> attachedTarjetaListNew = new ArrayList<Tarjeta>();
            for (Tarjeta tarjetaListNewTarjetaToAttach : tarjetaListNew) {
                tarjetaListNewTarjetaToAttach = em.getReference(tarjetaListNewTarjetaToAttach.getClass(), tarjetaListNewTarjetaToAttach.getCodigoTarjeta());
                attachedTarjetaListNew.add(tarjetaListNewTarjetaToAttach);
            }
            tarjetaListNew = attachedTarjetaListNew;
            usuario.setTarjetaList(tarjetaListNew);
            usuario = em.merge(usuario);
            for (Direccion direccionListNewDireccion : direccionListNew) {
                if (!direccionListOld.contains(direccionListNewDireccion)) {
                    Usuario oldCodigoUsuarioOfDireccionListNewDireccion = direccionListNewDireccion.getCodigoUsuario();
                    direccionListNewDireccion.setCodigoUsuario(usuario);
                    direccionListNewDireccion = em.merge(direccionListNewDireccion);
                    if (oldCodigoUsuarioOfDireccionListNewDireccion != null && !oldCodigoUsuarioOfDireccionListNewDireccion.equals(usuario)) {
                        oldCodigoUsuarioOfDireccionListNewDireccion.getDireccionList().remove(direccionListNewDireccion);
                        oldCodigoUsuarioOfDireccionListNewDireccion = em.merge(oldCodigoUsuarioOfDireccionListNewDireccion);
                    }
                }
            }
            for (Canjear canjearListNewCanjear : canjearListNew) {
                if (!canjearListOld.contains(canjearListNewCanjear)) {
                    Usuario oldCodigoUsuarioOfCanjearListNewCanjear = canjearListNewCanjear.getCodigoUsuario();
                    canjearListNewCanjear.setCodigoUsuario(usuario);
                    canjearListNewCanjear = em.merge(canjearListNewCanjear);
                    if (oldCodigoUsuarioOfCanjearListNewCanjear != null && !oldCodigoUsuarioOfCanjearListNewCanjear.equals(usuario)) {
                        oldCodigoUsuarioOfCanjearListNewCanjear.getCanjearList().remove(canjearListNewCanjear);
                        oldCodigoUsuarioOfCanjearListNewCanjear = em.merge(oldCodigoUsuarioOfCanjearListNewCanjear);
                    }
                }
            }
            for (Pedido pedidoListNewPedido : pedidoListNew) {
                if (!pedidoListOld.contains(pedidoListNewPedido)) {
                    Usuario oldCodigoUsuarioOfPedidoListNewPedido = pedidoListNewPedido.getCodigoUsuario();
                    pedidoListNewPedido.setCodigoUsuario(usuario);
                    pedidoListNewPedido = em.merge(pedidoListNewPedido);
                    if (oldCodigoUsuarioOfPedidoListNewPedido != null && !oldCodigoUsuarioOfPedidoListNewPedido.equals(usuario)) {
                        oldCodigoUsuarioOfPedidoListNewPedido.getPedidoList().remove(pedidoListNewPedido);
                        oldCodigoUsuarioOfPedidoListNewPedido = em.merge(oldCodigoUsuarioOfPedidoListNewPedido);
                    }
                }
            }
            for (Tarjeta tarjetaListNewTarjeta : tarjetaListNew) {
                if (!tarjetaListOld.contains(tarjetaListNewTarjeta)) {
                    Usuario oldCodigoUsuarioOfTarjetaListNewTarjeta = tarjetaListNewTarjeta.getCodigoUsuario();
                    tarjetaListNewTarjeta.setCodigoUsuario(usuario);
                    tarjetaListNewTarjeta = em.merge(tarjetaListNewTarjeta);
                    if (oldCodigoUsuarioOfTarjetaListNewTarjeta != null && !oldCodigoUsuarioOfTarjetaListNewTarjeta.equals(usuario)) {
                        oldCodigoUsuarioOfTarjetaListNewTarjeta.getTarjetaList().remove(tarjetaListNewTarjeta);
                        oldCodigoUsuarioOfTarjetaListNewTarjeta = em.merge(oldCodigoUsuarioOfTarjetaListNewTarjeta);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = usuario.getCodigoUsuario();
                if (findUsuario(id) == null) {
                    throw new NonexistentEntityException("The usuario with id " + id + " no longer exists.");
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
            Usuario usuario;
            try {
                usuario = em.getReference(Usuario.class, id);
                usuario.getCodigoUsuario();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The usuario with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Direccion> direccionListOrphanCheck = usuario.getDireccionList();
            for (Direccion direccionListOrphanCheckDireccion : direccionListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Usuario (" + usuario + ") cannot be destroyed since the Direccion " + direccionListOrphanCheckDireccion + " in its direccionList field has a non-nullable codigoUsuario field.");
            }
            List<Canjear> canjearListOrphanCheck = usuario.getCanjearList();
            for (Canjear canjearListOrphanCheckCanjear : canjearListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Usuario (" + usuario + ") cannot be destroyed since the Canjear " + canjearListOrphanCheckCanjear + " in its canjearList field has a non-nullable codigoUsuario field.");
            }
            List<Pedido> pedidoListOrphanCheck = usuario.getPedidoList();
            for (Pedido pedidoListOrphanCheckPedido : pedidoListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Usuario (" + usuario + ") cannot be destroyed since the Pedido " + pedidoListOrphanCheckPedido + " in its pedidoList field has a non-nullable codigoUsuario field.");
            }
            List<Tarjeta> tarjetaListOrphanCheck = usuario.getTarjetaList();
            for (Tarjeta tarjetaListOrphanCheckTarjeta : tarjetaListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Usuario (" + usuario + ") cannot be destroyed since the Tarjeta " + tarjetaListOrphanCheckTarjeta + " in its tarjetaList field has a non-nullable codigoUsuario field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(usuario);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Usuario> findUsuarioEntities() {
        return findUsuarioEntities(true, -1, -1);
    }

    public List<Usuario> findUsuarioEntities(int maxResults, int firstResult) {
        return findUsuarioEntities(false, maxResults, firstResult);
    }

    private List<Usuario> findUsuarioEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Usuario.class));
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

    public Usuario findUsuario(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Usuario.class, id);
        } finally {
            em.close();
        }
    }

    public int getUsuarioCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Usuario> rt = cq.from(Usuario.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
    //Creamos el metodo que devuelve un usuario segun su nick
    public Usuario findUsuarioByNick(String nick) {
        EntityManager em = getEntityManager();
        try {
        Query query=em.createNamedQuery("Usuario.findByNick").setParameter("nick", nick);
        
        
            Usuario usuario = (Usuario) query.getResultList().get(0);

            return usuario;

}
        catch(Exception e){
            return null;
        }
        finally {
            em.close();
        }
    }
    
}
