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
import DTO.Tecnico;
import DTO.Reparaciones;
import DTO.Telefono;
import DTO.Direccion;
import DTO.Pedido;
import DTO.Tarjeta;
import DTO.Usuario;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Pedro
 */
public class PedidoJpaController implements Serializable {

    public PedidoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Pedido pedido) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Tecnico codigoTecnico = pedido.getCodigoTecnico();
            if (codigoTecnico != null) {
                codigoTecnico = em.getReference(codigoTecnico.getClass(), codigoTecnico.getCodigoTecnico());
                pedido.setCodigoTecnico(codigoTecnico);
            }
            Reparaciones codigoReparacion = pedido.getCodigoReparacion();
            if (codigoReparacion != null) {
                codigoReparacion = em.getReference(codigoReparacion.getClass(), codigoReparacion.getCodigoReparacion());
                pedido.setCodigoReparacion(codigoReparacion);
            }
            Telefono codigoTelefono = pedido.getCodigoTelefono();
            if (codigoTelefono != null) {
                codigoTelefono = em.getReference(codigoTelefono.getClass(), codigoTelefono.getCodigoTelefono());
                pedido.setCodigoTelefono(codigoTelefono);
            }
            Direccion codigoDireccion = pedido.getCodigoDireccion();
            if (codigoDireccion != null) {
                codigoDireccion = em.getReference(codigoDireccion.getClass(), codigoDireccion.getCodigoDireccion());
                pedido.setCodigoDireccion(codigoDireccion);
            }
            Tarjeta codigoTarjeta = pedido.getCodigoTarjeta();
            if (codigoTarjeta != null) {
                codigoTarjeta = em.getReference(codigoTarjeta.getClass(), codigoTarjeta.getCodigoTarjeta());
                pedido.setCodigoTarjeta(codigoTarjeta);
            }
            Usuario codigoUsuario = pedido.getCodigoUsuario();
            if (codigoUsuario != null) {
                codigoUsuario = em.getReference(codigoUsuario.getClass(), codigoUsuario.getCodigoUsuario());
                pedido.setCodigoUsuario(codigoUsuario);
            }
            em.persist(pedido);
            if (codigoTecnico != null) {
                codigoTecnico.getPedidoList().add(pedido);
                codigoTecnico = em.merge(codigoTecnico);
            }
            if (codigoReparacion != null) {
                codigoReparacion.getPedidoList().add(pedido);
                codigoReparacion = em.merge(codigoReparacion);
            }
            if (codigoTelefono != null) {
                codigoTelefono.getPedidoList().add(pedido);
                codigoTelefono = em.merge(codigoTelefono);
            }
            if (codigoDireccion != null) {
                codigoDireccion.getPedidoList().add(pedido);
                codigoDireccion = em.merge(codigoDireccion);
            }
            if (codigoTarjeta != null) {
                codigoTarjeta.getPedidoList().add(pedido);
                codigoTarjeta = em.merge(codigoTarjeta);
            }
            if (codigoUsuario != null) {
                codigoUsuario.getPedidoList().add(pedido);
                codigoUsuario = em.merge(codigoUsuario);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Pedido pedido) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Pedido persistentPedido = em.find(Pedido.class, pedido.getCodigo());
            Tecnico codigoTecnicoOld = persistentPedido.getCodigoTecnico();
            Tecnico codigoTecnicoNew = pedido.getCodigoTecnico();
            Reparaciones codigoReparacionOld = persistentPedido.getCodigoReparacion();
            Reparaciones codigoReparacionNew = pedido.getCodigoReparacion();
            Telefono codigoTelefonoOld = persistentPedido.getCodigoTelefono();
            Telefono codigoTelefonoNew = pedido.getCodigoTelefono();
            Direccion codigoDireccionOld = persistentPedido.getCodigoDireccion();
            Direccion codigoDireccionNew = pedido.getCodigoDireccion();
            Tarjeta codigoTarjetaOld = persistentPedido.getCodigoTarjeta();
            Tarjeta codigoTarjetaNew = pedido.getCodigoTarjeta();
            Usuario codigoUsuarioOld = persistentPedido.getCodigoUsuario();
            Usuario codigoUsuarioNew = pedido.getCodigoUsuario();
            if (codigoTecnicoNew != null) {
                codigoTecnicoNew = em.getReference(codigoTecnicoNew.getClass(), codigoTecnicoNew.getCodigoTecnico());
                pedido.setCodigoTecnico(codigoTecnicoNew);
            }
            if (codigoReparacionNew != null) {
                codigoReparacionNew = em.getReference(codigoReparacionNew.getClass(), codigoReparacionNew.getCodigoReparacion());
                pedido.setCodigoReparacion(codigoReparacionNew);
            }
            if (codigoTelefonoNew != null) {
                codigoTelefonoNew = em.getReference(codigoTelefonoNew.getClass(), codigoTelefonoNew.getCodigoTelefono());
                pedido.setCodigoTelefono(codigoTelefonoNew);
            }
            if (codigoDireccionNew != null) {
                codigoDireccionNew = em.getReference(codigoDireccionNew.getClass(), codigoDireccionNew.getCodigoDireccion());
                pedido.setCodigoDireccion(codigoDireccionNew);
            }
            if (codigoTarjetaNew != null) {
                codigoTarjetaNew = em.getReference(codigoTarjetaNew.getClass(), codigoTarjetaNew.getCodigoTarjeta());
                pedido.setCodigoTarjeta(codigoTarjetaNew);
            }
            if (codigoUsuarioNew != null) {
                codigoUsuarioNew = em.getReference(codigoUsuarioNew.getClass(), codigoUsuarioNew.getCodigoUsuario());
                pedido.setCodigoUsuario(codigoUsuarioNew);
            }
            pedido = em.merge(pedido);
            if (codigoTecnicoOld != null && !codigoTecnicoOld.equals(codigoTecnicoNew)) {
                codigoTecnicoOld.getPedidoList().remove(pedido);
                codigoTecnicoOld = em.merge(codigoTecnicoOld);
            }
            if (codigoTecnicoNew != null && !codigoTecnicoNew.equals(codigoTecnicoOld)) {
                codigoTecnicoNew.getPedidoList().add(pedido);
                codigoTecnicoNew = em.merge(codigoTecnicoNew);
            }
            if (codigoReparacionOld != null && !codigoReparacionOld.equals(codigoReparacionNew)) {
                codigoReparacionOld.getPedidoList().remove(pedido);
                codigoReparacionOld = em.merge(codigoReparacionOld);
            }
            if (codigoReparacionNew != null && !codigoReparacionNew.equals(codigoReparacionOld)) {
                codigoReparacionNew.getPedidoList().add(pedido);
                codigoReparacionNew = em.merge(codigoReparacionNew);
            }
            if (codigoTelefonoOld != null && !codigoTelefonoOld.equals(codigoTelefonoNew)) {
                codigoTelefonoOld.getPedidoList().remove(pedido);
                codigoTelefonoOld = em.merge(codigoTelefonoOld);
            }
            if (codigoTelefonoNew != null && !codigoTelefonoNew.equals(codigoTelefonoOld)) {
                codigoTelefonoNew.getPedidoList().add(pedido);
                codigoTelefonoNew = em.merge(codigoTelefonoNew);
            }
            if (codigoDireccionOld != null && !codigoDireccionOld.equals(codigoDireccionNew)) {
                codigoDireccionOld.getPedidoList().remove(pedido);
                codigoDireccionOld = em.merge(codigoDireccionOld);
            }
            if (codigoDireccionNew != null && !codigoDireccionNew.equals(codigoDireccionOld)) {
                codigoDireccionNew.getPedidoList().add(pedido);
                codigoDireccionNew = em.merge(codigoDireccionNew);
            }
            if (codigoTarjetaOld != null && !codigoTarjetaOld.equals(codigoTarjetaNew)) {
                codigoTarjetaOld.getPedidoList().remove(pedido);
                codigoTarjetaOld = em.merge(codigoTarjetaOld);
            }
            if (codigoTarjetaNew != null && !codigoTarjetaNew.equals(codigoTarjetaOld)) {
                codigoTarjetaNew.getPedidoList().add(pedido);
                codigoTarjetaNew = em.merge(codigoTarjetaNew);
            }
            if (codigoUsuarioOld != null && !codigoUsuarioOld.equals(codigoUsuarioNew)) {
                codigoUsuarioOld.getPedidoList().remove(pedido);
                codigoUsuarioOld = em.merge(codigoUsuarioOld);
            }
            if (codigoUsuarioNew != null && !codigoUsuarioNew.equals(codigoUsuarioOld)) {
                codigoUsuarioNew.getPedidoList().add(pedido);
                codigoUsuarioNew = em.merge(codigoUsuarioNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = pedido.getCodigo();
                if (findPedido(id) == null) {
                    throw new NonexistentEntityException("The pedido with id " + id + " no longer exists.");
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
            Pedido pedido;
            try {
                pedido = em.getReference(Pedido.class, id);
                pedido.getCodigo();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The pedido with id " + id + " no longer exists.", enfe);
            }
            Tecnico codigoTecnico = pedido.getCodigoTecnico();
            if (codigoTecnico != null) {
                codigoTecnico.getPedidoList().remove(pedido);
                codigoTecnico = em.merge(codigoTecnico);
            }
            Reparaciones codigoReparacion = pedido.getCodigoReparacion();
            if (codigoReparacion != null) {
                codigoReparacion.getPedidoList().remove(pedido);
                codigoReparacion = em.merge(codigoReparacion);
            }
            Telefono codigoTelefono = pedido.getCodigoTelefono();
            if (codigoTelefono != null) {
                codigoTelefono.getPedidoList().remove(pedido);
                codigoTelefono = em.merge(codigoTelefono);
            }
            Direccion codigoDireccion = pedido.getCodigoDireccion();
            if (codigoDireccion != null) {
                codigoDireccion.getPedidoList().remove(pedido);
                codigoDireccion = em.merge(codigoDireccion);
            }
            Tarjeta codigoTarjeta = pedido.getCodigoTarjeta();
            if (codigoTarjeta != null) {
                codigoTarjeta.getPedidoList().remove(pedido);
                codigoTarjeta = em.merge(codigoTarjeta);
            }
            Usuario codigoUsuario = pedido.getCodigoUsuario();
            if (codigoUsuario != null) {
                codigoUsuario.getPedidoList().remove(pedido);
                codigoUsuario = em.merge(codigoUsuario);
            }
            em.remove(pedido);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Pedido> findPedidoEntities() {
        return findPedidoEntities(true, -1, -1);
    }

    public List<Pedido> findPedidoEntities(int maxResults, int firstResult) {
        return findPedidoEntities(false, maxResults, firstResult);
    }

    private List<Pedido> findPedidoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Pedido.class));
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

    public Pedido findPedido(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Pedido.class, id);
        } finally {
            em.close();
        }
    }

    public int getPedidoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Pedido> rt = cq.from(Pedido.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
