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
import DTO.Administrador;
import DTO.Foto;
import java.util.ArrayList;
import java.util.List;
import DTO.Caracteristicastelefono;
import DTO.Pedido;
import DTO.Reparacionestelefono;
import DTO.Telefono;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;

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
        if (telefono.getCaracteristicastelefonoList() == null) {
            telefono.setCaracteristicastelefonoList(new ArrayList<Caracteristicastelefono>());
        }
        if (telefono.getPedidoList() == null) {
            telefono.setPedidoList(new ArrayList<Pedido>());
        }
        if (telefono.getReparacionestelefonoList() == null) {
            telefono.setReparacionestelefonoList(new ArrayList<Reparacionestelefono>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
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
            List<Caracteristicastelefono> attachedCaracteristicastelefonoList = new ArrayList<Caracteristicastelefono>();
            for (Caracteristicastelefono caracteristicastelefonoListCaracteristicastelefonoToAttach : telefono.getCaracteristicastelefonoList()) {
                caracteristicastelefonoListCaracteristicastelefonoToAttach = em.getReference(caracteristicastelefonoListCaracteristicastelefonoToAttach.getClass(), caracteristicastelefonoListCaracteristicastelefonoToAttach.getCodigoCaracteristica());
                attachedCaracteristicastelefonoList.add(caracteristicastelefonoListCaracteristicastelefonoToAttach);
            }
            telefono.setCaracteristicastelefonoList(attachedCaracteristicastelefonoList);
            List<Pedido> attachedPedidoList = new ArrayList<Pedido>();
            for (Pedido pedidoListPedidoToAttach : telefono.getPedidoList()) {
                pedidoListPedidoToAttach = em.getReference(pedidoListPedidoToAttach.getClass(), pedidoListPedidoToAttach.getCodigo());
                attachedPedidoList.add(pedidoListPedidoToAttach);
            }
            telefono.setPedidoList(attachedPedidoList);
            List<Reparacionestelefono> attachedReparacionestelefonoList = new ArrayList<Reparacionestelefono>();
            for (Reparacionestelefono reparacionestelefonoListReparacionestelefonoToAttach : telefono.getReparacionestelefonoList()) {
                reparacionestelefonoListReparacionestelefonoToAttach = em.getReference(reparacionestelefonoListReparacionestelefonoToAttach.getClass(), reparacionestelefonoListReparacionestelefonoToAttach.getCodigoReparacionTelefono());
                attachedReparacionestelefonoList.add(reparacionestelefonoListReparacionestelefonoToAttach);
            }
            telefono.setReparacionestelefonoList(attachedReparacionestelefonoList);
            em.persist(telefono);
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
            for (Caracteristicastelefono caracteristicastelefonoListCaracteristicastelefono : telefono.getCaracteristicastelefonoList()) {
                Telefono oldCodigoTelefonoOfCaracteristicastelefonoListCaracteristicastelefono = caracteristicastelefonoListCaracteristicastelefono.getCodigoTelefono();
                caracteristicastelefonoListCaracteristicastelefono.setCodigoTelefono(telefono);
                caracteristicastelefonoListCaracteristicastelefono = em.merge(caracteristicastelefonoListCaracteristicastelefono);
                if (oldCodigoTelefonoOfCaracteristicastelefonoListCaracteristicastelefono != null) {
                    oldCodigoTelefonoOfCaracteristicastelefonoListCaracteristicastelefono.getCaracteristicastelefonoList().remove(caracteristicastelefonoListCaracteristicastelefono);
                    oldCodigoTelefonoOfCaracteristicastelefonoListCaracteristicastelefono = em.merge(oldCodigoTelefonoOfCaracteristicastelefonoListCaracteristicastelefono);
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
            for (Reparacionestelefono reparacionestelefonoListReparacionestelefono : telefono.getReparacionestelefonoList()) {
                Telefono oldCodigoTelefonoOfReparacionestelefonoListReparacionestelefono = reparacionestelefonoListReparacionestelefono.getCodigoTelefono();
                reparacionestelefonoListReparacionestelefono.setCodigoTelefono(telefono);
                reparacionestelefonoListReparacionestelefono = em.merge(reparacionestelefonoListReparacionestelefono);
                if (oldCodigoTelefonoOfReparacionestelefonoListReparacionestelefono != null) {
                    oldCodigoTelefonoOfReparacionestelefonoListReparacionestelefono.getReparacionestelefonoList().remove(reparacionestelefonoListReparacionestelefono);
                    oldCodigoTelefonoOfReparacionestelefonoListReparacionestelefono = em.merge(oldCodigoTelefonoOfReparacionestelefonoListReparacionestelefono);
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
            Administrador codigoAdministradorOld = persistentTelefono.getCodigoAdministrador();
            Administrador codigoAdministradorNew = telefono.getCodigoAdministrador();
            List<Foto> fotoListOld = persistentTelefono.getFotoList();
            List<Foto> fotoListNew = telefono.getFotoList();
            List<Caracteristicastelefono> caracteristicastelefonoListOld = persistentTelefono.getCaracteristicastelefonoList();
            List<Caracteristicastelefono> caracteristicastelefonoListNew = telefono.getCaracteristicastelefonoList();
            List<Pedido> pedidoListOld = persistentTelefono.getPedidoList();
            List<Pedido> pedidoListNew = telefono.getPedidoList();
            List<Reparacionestelefono> reparacionestelefonoListOld = persistentTelefono.getReparacionestelefonoList();
            List<Reparacionestelefono> reparacionestelefonoListNew = telefono.getReparacionestelefonoList();
            List<String> illegalOrphanMessages = null;
            for (Caracteristicastelefono caracteristicastelefonoListOldCaracteristicastelefono : caracteristicastelefonoListOld) {
                if (!caracteristicastelefonoListNew.contains(caracteristicastelefonoListOldCaracteristicastelefono)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Caracteristicastelefono " + caracteristicastelefonoListOldCaracteristicastelefono + " since its codigoTelefono field is not nullable.");
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
            for (Reparacionestelefono reparacionestelefonoListOldReparacionestelefono : reparacionestelefonoListOld) {
                if (!reparacionestelefonoListNew.contains(reparacionestelefonoListOldReparacionestelefono)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Reparacionestelefono " + reparacionestelefonoListOldReparacionestelefono + " since its codigoTelefono field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
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
            List<Caracteristicastelefono> attachedCaracteristicastelefonoListNew = new ArrayList<Caracteristicastelefono>();
            for (Caracteristicastelefono caracteristicastelefonoListNewCaracteristicastelefonoToAttach : caracteristicastelefonoListNew) {
                caracteristicastelefonoListNewCaracteristicastelefonoToAttach = em.getReference(caracteristicastelefonoListNewCaracteristicastelefonoToAttach.getClass(), caracteristicastelefonoListNewCaracteristicastelefonoToAttach.getCodigoCaracteristica());
                attachedCaracteristicastelefonoListNew.add(caracteristicastelefonoListNewCaracteristicastelefonoToAttach);
            }
            caracteristicastelefonoListNew = attachedCaracteristicastelefonoListNew;
            telefono.setCaracteristicastelefonoList(caracteristicastelefonoListNew);
            List<Pedido> attachedPedidoListNew = new ArrayList<Pedido>();
            for (Pedido pedidoListNewPedidoToAttach : pedidoListNew) {
                pedidoListNewPedidoToAttach = em.getReference(pedidoListNewPedidoToAttach.getClass(), pedidoListNewPedidoToAttach.getCodigo());
                attachedPedidoListNew.add(pedidoListNewPedidoToAttach);
            }
            pedidoListNew = attachedPedidoListNew;
            telefono.setPedidoList(pedidoListNew);
            List<Reparacionestelefono> attachedReparacionestelefonoListNew = new ArrayList<Reparacionestelefono>();
            for (Reparacionestelefono reparacionestelefonoListNewReparacionestelefonoToAttach : reparacionestelefonoListNew) {
                reparacionestelefonoListNewReparacionestelefonoToAttach = em.getReference(reparacionestelefonoListNewReparacionestelefonoToAttach.getClass(), reparacionestelefonoListNewReparacionestelefonoToAttach.getCodigoReparacionTelefono());
                attachedReparacionestelefonoListNew.add(reparacionestelefonoListNewReparacionestelefonoToAttach);
            }
            reparacionestelefonoListNew = attachedReparacionestelefonoListNew;
            telefono.setReparacionestelefonoList(reparacionestelefonoListNew);
            telefono = em.merge(telefono);
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
            for (Caracteristicastelefono caracteristicastelefonoListNewCaracteristicastelefono : caracteristicastelefonoListNew) {
                if (!caracteristicastelefonoListOld.contains(caracteristicastelefonoListNewCaracteristicastelefono)) {
                    Telefono oldCodigoTelefonoOfCaracteristicastelefonoListNewCaracteristicastelefono = caracteristicastelefonoListNewCaracteristicastelefono.getCodigoTelefono();
                    caracteristicastelefonoListNewCaracteristicastelefono.setCodigoTelefono(telefono);
                    caracteristicastelefonoListNewCaracteristicastelefono = em.merge(caracteristicastelefonoListNewCaracteristicastelefono);
                    if (oldCodigoTelefonoOfCaracteristicastelefonoListNewCaracteristicastelefono != null && !oldCodigoTelefonoOfCaracteristicastelefonoListNewCaracteristicastelefono.equals(telefono)) {
                        oldCodigoTelefonoOfCaracteristicastelefonoListNewCaracteristicastelefono.getCaracteristicastelefonoList().remove(caracteristicastelefonoListNewCaracteristicastelefono);
                        oldCodigoTelefonoOfCaracteristicastelefonoListNewCaracteristicastelefono = em.merge(oldCodigoTelefonoOfCaracteristicastelefonoListNewCaracteristicastelefono);
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
            for (Reparacionestelefono reparacionestelefonoListNewReparacionestelefono : reparacionestelefonoListNew) {
                if (!reparacionestelefonoListOld.contains(reparacionestelefonoListNewReparacionestelefono)) {
                    Telefono oldCodigoTelefonoOfReparacionestelefonoListNewReparacionestelefono = reparacionestelefonoListNewReparacionestelefono.getCodigoTelefono();
                    reparacionestelefonoListNewReparacionestelefono.setCodigoTelefono(telefono);
                    reparacionestelefonoListNewReparacionestelefono = em.merge(reparacionestelefonoListNewReparacionestelefono);
                    if (oldCodigoTelefonoOfReparacionestelefonoListNewReparacionestelefono != null && !oldCodigoTelefonoOfReparacionestelefonoListNewReparacionestelefono.equals(telefono)) {
                        oldCodigoTelefonoOfReparacionestelefonoListNewReparacionestelefono.getReparacionestelefonoList().remove(reparacionestelefonoListNewReparacionestelefono);
                        oldCodigoTelefonoOfReparacionestelefonoListNewReparacionestelefono = em.merge(oldCodigoTelefonoOfReparacionestelefonoListNewReparacionestelefono);
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
            List<Caracteristicastelefono> caracteristicastelefonoListOrphanCheck = telefono.getCaracteristicastelefonoList();
            for (Caracteristicastelefono caracteristicastelefonoListOrphanCheckCaracteristicastelefono : caracteristicastelefonoListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Telefono (" + telefono + ") cannot be destroyed since the Caracteristicastelefono " + caracteristicastelefonoListOrphanCheckCaracteristicastelefono + " in its caracteristicastelefonoList field has a non-nullable codigoTelefono field.");
            }
            List<Pedido> pedidoListOrphanCheck = telefono.getPedidoList();
            for (Pedido pedidoListOrphanCheckPedido : pedidoListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Telefono (" + telefono + ") cannot be destroyed since the Pedido " + pedidoListOrphanCheckPedido + " in its pedidoList field has a non-nullable codigoTelefono field.");
            }
            List<Reparacionestelefono> reparacionestelefonoListOrphanCheck = telefono.getReparacionestelefonoList();
            for (Reparacionestelefono reparacionestelefonoListOrphanCheckReparacionestelefono : reparacionestelefonoListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Telefono (" + telefono + ") cannot be destroyed since the Reparacionestelefono " + reparacionestelefonoListOrphanCheckReparacionestelefono + " in its reparacionestelefonoList field has a non-nullable codigoTelefono field.");
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
    public Telefono findTelefonoByNick(String nombre) {
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
    //Creamos el metodo que devuelve un telefono segun su marca
    public List findTodosTelefonosDistint() {
        EntityManager em = getEntityManager();
        
        TypedQuery q=em.createNamedQuery("Telefono.findDistinct",Telefono.class);
            
        List lista= q.getResultList();
        
        return lista;
    }
    //Creamos el metodo que devuelve un telefono segun su marca
    public List findTelefonoByMarca(String marca) {
        EntityManager em = getEntityManager();
        
        TypedQuery q=em.createNamedQuery("Telefono.findByMarca",Telefono.class);
            
        q.setParameter("marca",marca);
        
        List lista= q.getResultList();
        
        return lista;
    } 
    
}
