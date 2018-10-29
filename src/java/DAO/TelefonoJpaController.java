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
import DTO.Caracteristicastelefono;
import DTO.Administrador;
import DTO.Foto;
import java.util.ArrayList;
import java.util.List;
import DTO.Reparaciones;
import DTO.Pedido;
import DTO.Telefono;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Pedro
 */
public class TelefonoJpaController implements Serializable {

    public TelefonoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Telefono telefono) {
        if (telefono.getFotoList() == null) {
            telefono.setFotoList(new ArrayList<Foto>());
        }
        if (telefono.getReparacionesList() == null) {
            telefono.setReparacionesList(new ArrayList<Reparaciones>());
        }
        if (telefono.getPedidoList() == null) {
            telefono.setPedidoList(new ArrayList<Pedido>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Caracteristicastelefono caracteristicastelefono = telefono.getCaracteristicastelefono();
            if (caracteristicastelefono != null) {
                caracteristicastelefono = em.getReference(caracteristicastelefono.getClass(), caracteristicastelefono.getCodigoTelefono());
                telefono.setCaracteristicastelefono(caracteristicastelefono);
            }
            Administrador codigoAdministrador = telefono.getCodigoAdministrador();
            if (codigoAdministrador != null) {
                codigoAdministrador = em.getReference(codigoAdministrador.getClass(), codigoAdministrador.getCodigoAdministrador());
                telefono.setCodigoAdministrador(codigoAdministrador);
            }
            List<Foto> attachedFotoList = new ArrayList<Foto>();
            for (Foto fotoListFotoToAttach : telefono.getFotoList()) {
                fotoListFotoToAttach = em.getReference(fotoListFotoToAttach.getClass(), fotoListFotoToAttach.getCodigoFoto());
                attachedFotoList.add(fotoListFotoToAttach);
            }
            telefono.setFotoList(attachedFotoList);
            List<Reparaciones> attachedReparacionesList = new ArrayList<Reparaciones>();
            for (Reparaciones reparacionesListReparacionesToAttach : telefono.getReparacionesList()) {
                reparacionesListReparacionesToAttach = em.getReference(reparacionesListReparacionesToAttach.getClass(), reparacionesListReparacionesToAttach.getCodigoReparacion());
                attachedReparacionesList.add(reparacionesListReparacionesToAttach);
            }
            telefono.setReparacionesList(attachedReparacionesList);
            List<Pedido> attachedPedidoList = new ArrayList<Pedido>();
            for (Pedido pedidoListPedidoToAttach : telefono.getPedidoList()) {
                pedidoListPedidoToAttach = em.getReference(pedidoListPedidoToAttach.getClass(), pedidoListPedidoToAttach.getCodigo());
                attachedPedidoList.add(pedidoListPedidoToAttach);
            }
            telefono.setPedidoList(attachedPedidoList);
            em.persist(telefono);
            if (caracteristicastelefono != null) {
                Telefono oldTelefonoOfCaracteristicastelefono = caracteristicastelefono.getTelefono();
                if (oldTelefonoOfCaracteristicastelefono != null) {
                    oldTelefonoOfCaracteristicastelefono.setCaracteristicastelefono(null);
                    oldTelefonoOfCaracteristicastelefono = em.merge(oldTelefonoOfCaracteristicastelefono);
                }
                caracteristicastelefono.setTelefono(telefono);
                caracteristicastelefono = em.merge(caracteristicastelefono);
            }
            if (codigoAdministrador != null) {
                codigoAdministrador.getTelefonoList().add(telefono);
                codigoAdministrador = em.merge(codigoAdministrador);
            }
            for (Foto fotoListFoto : telefono.getFotoList()) {
                Telefono oldCodigoTelefonoOfFotoListFoto = fotoListFoto.getCodigoTelefono();
                fotoListFoto.setCodigoTelefono(telefono);
                fotoListFoto = em.merge(fotoListFoto);
                if (oldCodigoTelefonoOfFotoListFoto != null) {
                    oldCodigoTelefonoOfFotoListFoto.getFotoList().remove(fotoListFoto);
                    oldCodigoTelefonoOfFotoListFoto = em.merge(oldCodigoTelefonoOfFotoListFoto);
                }
            }
            for (Reparaciones reparacionesListReparaciones : telefono.getReparacionesList()) {
                Telefono oldCodigoTelefonoOfReparacionesListReparaciones = reparacionesListReparaciones.getCodigoTelefono();
                reparacionesListReparaciones.setCodigoTelefono(telefono);
                reparacionesListReparaciones = em.merge(reparacionesListReparaciones);
                if (oldCodigoTelefonoOfReparacionesListReparaciones != null) {
                    oldCodigoTelefonoOfReparacionesListReparaciones.getReparacionesList().remove(reparacionesListReparaciones);
                    oldCodigoTelefonoOfReparacionesListReparaciones = em.merge(oldCodigoTelefonoOfReparacionesListReparaciones);
                }
            }
            for (Pedido pedidoListPedido : telefono.getPedidoList()) {
                Telefono oldCodigoTelefonoOfPedidoListPedido = pedidoListPedido.getCodigoTelefono();
                pedidoListPedido.setCodigoTelefono(telefono);
                pedidoListPedido = em.merge(pedidoListPedido);
                if (oldCodigoTelefonoOfPedidoListPedido != null) {
                    oldCodigoTelefonoOfPedidoListPedido.getPedidoList().remove(pedidoListPedido);
                    oldCodigoTelefonoOfPedidoListPedido = em.merge(oldCodigoTelefonoOfPedidoListPedido);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Telefono telefono) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Telefono persistentTelefono = em.find(Telefono.class, telefono.getCodigoTelefono());
            Caracteristicastelefono caracteristicastelefonoOld = persistentTelefono.getCaracteristicastelefono();
            Caracteristicastelefono caracteristicastelefonoNew = telefono.getCaracteristicastelefono();
            Administrador codigoAdministradorOld = persistentTelefono.getCodigoAdministrador();
            Administrador codigoAdministradorNew = telefono.getCodigoAdministrador();
            List<Foto> fotoListOld = persistentTelefono.getFotoList();
            List<Foto> fotoListNew = telefono.getFotoList();
            List<Reparaciones> reparacionesListOld = persistentTelefono.getReparacionesList();
            List<Reparaciones> reparacionesListNew = telefono.getReparacionesList();
            List<Pedido> pedidoListOld = persistentTelefono.getPedidoList();
            List<Pedido> pedidoListNew = telefono.getPedidoList();
            List<String> illegalOrphanMessages = null;
            if (caracteristicastelefonoOld != null && !caracteristicastelefonoOld.equals(caracteristicastelefonoNew)) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("You must retain Caracteristicastelefono " + caracteristicastelefonoOld + " since its telefono field is not nullable.");
            }
            for (Reparaciones reparacionesListOldReparaciones : reparacionesListOld) {
                if (!reparacionesListNew.contains(reparacionesListOldReparaciones)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Reparaciones " + reparacionesListOldReparaciones + " since its codigoTelefono field is not nullable.");
                }
            }
            for (Pedido pedidoListOldPedido : pedidoListOld) {
                if (!pedidoListNew.contains(pedidoListOldPedido)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Pedido " + pedidoListOldPedido + " since its codigoTelefono field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (caracteristicastelefonoNew != null) {
                caracteristicastelefonoNew = em.getReference(caracteristicastelefonoNew.getClass(), caracteristicastelefonoNew.getCodigoTelefono());
                telefono.setCaracteristicastelefono(caracteristicastelefonoNew);
            }
            if (codigoAdministradorNew != null) {
                codigoAdministradorNew = em.getReference(codigoAdministradorNew.getClass(), codigoAdministradorNew.getCodigoAdministrador());
                telefono.setCodigoAdministrador(codigoAdministradorNew);
            }
            List<Foto> attachedFotoListNew = new ArrayList<Foto>();
            for (Foto fotoListNewFotoToAttach : fotoListNew) {
                fotoListNewFotoToAttach = em.getReference(fotoListNewFotoToAttach.getClass(), fotoListNewFotoToAttach.getCodigoFoto());
                attachedFotoListNew.add(fotoListNewFotoToAttach);
            }
            fotoListNew = attachedFotoListNew;
            telefono.setFotoList(fotoListNew);
            List<Reparaciones> attachedReparacionesListNew = new ArrayList<Reparaciones>();
            for (Reparaciones reparacionesListNewReparacionesToAttach : reparacionesListNew) {
                reparacionesListNewReparacionesToAttach = em.getReference(reparacionesListNewReparacionesToAttach.getClass(), reparacionesListNewReparacionesToAttach.getCodigoReparacion());
                attachedReparacionesListNew.add(reparacionesListNewReparacionesToAttach);
            }
            reparacionesListNew = attachedReparacionesListNew;
            telefono.setReparacionesList(reparacionesListNew);
            List<Pedido> attachedPedidoListNew = new ArrayList<Pedido>();
            for (Pedido pedidoListNewPedidoToAttach : pedidoListNew) {
                pedidoListNewPedidoToAttach = em.getReference(pedidoListNewPedidoToAttach.getClass(), pedidoListNewPedidoToAttach.getCodigo());
                attachedPedidoListNew.add(pedidoListNewPedidoToAttach);
            }
            pedidoListNew = attachedPedidoListNew;
            telefono.setPedidoList(pedidoListNew);
            telefono = em.merge(telefono);
            if (caracteristicastelefonoNew != null && !caracteristicastelefonoNew.equals(caracteristicastelefonoOld)) {
                Telefono oldTelefonoOfCaracteristicastelefono = caracteristicastelefonoNew.getTelefono();
                if (oldTelefonoOfCaracteristicastelefono != null) {
                    oldTelefonoOfCaracteristicastelefono.setCaracteristicastelefono(null);
                    oldTelefonoOfCaracteristicastelefono = em.merge(oldTelefonoOfCaracteristicastelefono);
                }
                caracteristicastelefonoNew.setTelefono(telefono);
                caracteristicastelefonoNew = em.merge(caracteristicastelefonoNew);
            }
            if (codigoAdministradorOld != null && !codigoAdministradorOld.equals(codigoAdministradorNew)) {
                codigoAdministradorOld.getTelefonoList().remove(telefono);
                codigoAdministradorOld = em.merge(codigoAdministradorOld);
            }
            if (codigoAdministradorNew != null && !codigoAdministradorNew.equals(codigoAdministradorOld)) {
                codigoAdministradorNew.getTelefonoList().add(telefono);
                codigoAdministradorNew = em.merge(codigoAdministradorNew);
            }
            for (Foto fotoListOldFoto : fotoListOld) {
                if (!fotoListNew.contains(fotoListOldFoto)) {
                    fotoListOldFoto.setCodigoTelefono(null);
                    fotoListOldFoto = em.merge(fotoListOldFoto);
                }
            }
            for (Foto fotoListNewFoto : fotoListNew) {
                if (!fotoListOld.contains(fotoListNewFoto)) {
                    Telefono oldCodigoTelefonoOfFotoListNewFoto = fotoListNewFoto.getCodigoTelefono();
                    fotoListNewFoto.setCodigoTelefono(telefono);
                    fotoListNewFoto = em.merge(fotoListNewFoto);
                    if (oldCodigoTelefonoOfFotoListNewFoto != null && !oldCodigoTelefonoOfFotoListNewFoto.equals(telefono)) {
                        oldCodigoTelefonoOfFotoListNewFoto.getFotoList().remove(fotoListNewFoto);
                        oldCodigoTelefonoOfFotoListNewFoto = em.merge(oldCodigoTelefonoOfFotoListNewFoto);
                    }
                }
            }
            for (Reparaciones reparacionesListNewReparaciones : reparacionesListNew) {
                if (!reparacionesListOld.contains(reparacionesListNewReparaciones)) {
                    Telefono oldCodigoTelefonoOfReparacionesListNewReparaciones = reparacionesListNewReparaciones.getCodigoTelefono();
                    reparacionesListNewReparaciones.setCodigoTelefono(telefono);
                    reparacionesListNewReparaciones = em.merge(reparacionesListNewReparaciones);
                    if (oldCodigoTelefonoOfReparacionesListNewReparaciones != null && !oldCodigoTelefonoOfReparacionesListNewReparaciones.equals(telefono)) {
                        oldCodigoTelefonoOfReparacionesListNewReparaciones.getReparacionesList().remove(reparacionesListNewReparaciones);
                        oldCodigoTelefonoOfReparacionesListNewReparaciones = em.merge(oldCodigoTelefonoOfReparacionesListNewReparaciones);
                    }
                }
            }
            for (Pedido pedidoListNewPedido : pedidoListNew) {
                if (!pedidoListOld.contains(pedidoListNewPedido)) {
                    Telefono oldCodigoTelefonoOfPedidoListNewPedido = pedidoListNewPedido.getCodigoTelefono();
                    pedidoListNewPedido.setCodigoTelefono(telefono);
                    pedidoListNewPedido = em.merge(pedidoListNewPedido);
                    if (oldCodigoTelefonoOfPedidoListNewPedido != null && !oldCodigoTelefonoOfPedidoListNewPedido.equals(telefono)) {
                        oldCodigoTelefonoOfPedidoListNewPedido.getPedidoList().remove(pedidoListNewPedido);
                        oldCodigoTelefonoOfPedidoListNewPedido = em.merge(oldCodigoTelefonoOfPedidoListNewPedido);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = telefono.getCodigoTelefono();
                if (findTelefono(id) == null) {
                    throw new NonexistentEntityException("The telefono with id " + id + " no longer exists.");
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
            Telefono telefono;
            try {
                telefono = em.getReference(Telefono.class, id);
                telefono.getCodigoTelefono();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The telefono with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Caracteristicastelefono caracteristicastelefonoOrphanCheck = telefono.getCaracteristicastelefono();
            if (caracteristicastelefonoOrphanCheck != null) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Telefono (" + telefono + ") cannot be destroyed since the Caracteristicastelefono " + caracteristicastelefonoOrphanCheck + " in its caracteristicastelefono field has a non-nullable telefono field.");
            }
            List<Reparaciones> reparacionesListOrphanCheck = telefono.getReparacionesList();
            for (Reparaciones reparacionesListOrphanCheckReparaciones : reparacionesListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Telefono (" + telefono + ") cannot be destroyed since the Reparaciones " + reparacionesListOrphanCheckReparaciones + " in its reparacionesList field has a non-nullable codigoTelefono field.");
            }
            List<Pedido> pedidoListOrphanCheck = telefono.getPedidoList();
            for (Pedido pedidoListOrphanCheckPedido : pedidoListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Telefono (" + telefono + ") cannot be destroyed since the Pedido " + pedidoListOrphanCheckPedido + " in its pedidoList field has a non-nullable codigoTelefono field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Administrador codigoAdministrador = telefono.getCodigoAdministrador();
            if (codigoAdministrador != null) {
                codigoAdministrador.getTelefonoList().remove(telefono);
                codigoAdministrador = em.merge(codigoAdministrador);
            }
            List<Foto> fotoList = telefono.getFotoList();
            for (Foto fotoListFoto : fotoList) {
                fotoListFoto.setCodigoTelefono(null);
                fotoListFoto = em.merge(fotoListFoto);
            }
            em.remove(telefono);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Telefono> findTelefonoEntities() {
        return findTelefonoEntities(true, -1, -1);
    }

    public List<Telefono> findTelefonoEntities(int maxResults, int firstResult) {
        return findTelefonoEntities(false, maxResults, firstResult);
    }

    private List<Telefono> findTelefonoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Telefono.class));
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

    public Telefono findTelefono(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Telefono.class, id);
        } finally {
            em.close();
        }
    }

    public int getTelefonoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Telefono> rt = cq.from(Telefono.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
    //Creamos el metodo que devuelve un telefono segun su nombre
    public Telefono findTecnicoByNick(String nombre) {
        EntityManager em = getEntityManager();
        try {
        Query query=em.createNamedQuery("Telefono.findByNombre").setParameter("nombre", nombre);
        
        Telefono telefono = (Telefono) query.getResultList().get(0);
        
        return telefono;
        }
        catch(Exception e){
            return null;
        }
        
        finally {
            em.close();
        }
    }
    
}
