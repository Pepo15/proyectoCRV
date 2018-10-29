/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import DAO.exceptions.IllegalOrphanException;
import DAO.exceptions.NonexistentEntityException;
import DTO.Administrador;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import DTO.Tecnico;
import java.util.ArrayList;
import java.util.List;
import DTO.Telefono;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Pedro
 */
public class AdministradorJpaController implements Serializable {

    public AdministradorJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Administrador administrador) {
        if (administrador.getTecnicoList() == null) {
            administrador.setTecnicoList(new ArrayList<Tecnico>());
        }
        if (administrador.getTelefonoList() == null) {
            administrador.setTelefonoList(new ArrayList<Telefono>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Tecnico> attachedTecnicoList = new ArrayList<Tecnico>();
            for (Tecnico tecnicoListTecnicoToAttach : administrador.getTecnicoList()) {
                tecnicoListTecnicoToAttach = em.getReference(tecnicoListTecnicoToAttach.getClass(), tecnicoListTecnicoToAttach.getCodigoTecnico());
                attachedTecnicoList.add(tecnicoListTecnicoToAttach);
            }
            administrador.setTecnicoList(attachedTecnicoList);
            List<Telefono> attachedTelefonoList = new ArrayList<Telefono>();
            for (Telefono telefonoListTelefonoToAttach : administrador.getTelefonoList()) {
                telefonoListTelefonoToAttach = em.getReference(telefonoListTelefonoToAttach.getClass(), telefonoListTelefonoToAttach.getCodigoTelefono());
                attachedTelefonoList.add(telefonoListTelefonoToAttach);
            }
            administrador.setTelefonoList(attachedTelefonoList);
            em.persist(administrador);
            for (Tecnico tecnicoListTecnico : administrador.getTecnicoList()) {
                Administrador oldCodigoAdministradorOfTecnicoListTecnico = tecnicoListTecnico.getCodigoAdministrador();
                tecnicoListTecnico.setCodigoAdministrador(administrador);
                tecnicoListTecnico = em.merge(tecnicoListTecnico);
                if (oldCodigoAdministradorOfTecnicoListTecnico != null) {
                    oldCodigoAdministradorOfTecnicoListTecnico.getTecnicoList().remove(tecnicoListTecnico);
                    oldCodigoAdministradorOfTecnicoListTecnico = em.merge(oldCodigoAdministradorOfTecnicoListTecnico);
                }
            }
            for (Telefono telefonoListTelefono : administrador.getTelefonoList()) {
                Administrador oldCodigoAdministradorOfTelefonoListTelefono = telefonoListTelefono.getCodigoAdministrador();
                telefonoListTelefono.setCodigoAdministrador(administrador);
                telefonoListTelefono = em.merge(telefonoListTelefono);
                if (oldCodigoAdministradorOfTelefonoListTelefono != null) {
                    oldCodigoAdministradorOfTelefonoListTelefono.getTelefonoList().remove(telefonoListTelefono);
                    oldCodigoAdministradorOfTelefonoListTelefono = em.merge(oldCodigoAdministradorOfTelefonoListTelefono);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Administrador administrador) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Administrador persistentAdministrador = em.find(Administrador.class, administrador.getCodigoAdministrador());
            List<Tecnico> tecnicoListOld = persistentAdministrador.getTecnicoList();
            List<Tecnico> tecnicoListNew = administrador.getTecnicoList();
            List<Telefono> telefonoListOld = persistentAdministrador.getTelefonoList();
            List<Telefono> telefonoListNew = administrador.getTelefonoList();
            List<String> illegalOrphanMessages = null;
            for (Tecnico tecnicoListOldTecnico : tecnicoListOld) {
                if (!tecnicoListNew.contains(tecnicoListOldTecnico)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Tecnico " + tecnicoListOldTecnico + " since its codigoAdministrador field is not nullable.");
                }
            }
            for (Telefono telefonoListOldTelefono : telefonoListOld) {
                if (!telefonoListNew.contains(telefonoListOldTelefono)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Telefono " + telefonoListOldTelefono + " since its codigoAdministrador field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            List<Tecnico> attachedTecnicoListNew = new ArrayList<Tecnico>();
            for (Tecnico tecnicoListNewTecnicoToAttach : tecnicoListNew) {
                tecnicoListNewTecnicoToAttach = em.getReference(tecnicoListNewTecnicoToAttach.getClass(), tecnicoListNewTecnicoToAttach.getCodigoTecnico());
                attachedTecnicoListNew.add(tecnicoListNewTecnicoToAttach);
            }
            tecnicoListNew = attachedTecnicoListNew;
            administrador.setTecnicoList(tecnicoListNew);
            List<Telefono> attachedTelefonoListNew = new ArrayList<Telefono>();
            for (Telefono telefonoListNewTelefonoToAttach : telefonoListNew) {
                telefonoListNewTelefonoToAttach = em.getReference(telefonoListNewTelefonoToAttach.getClass(), telefonoListNewTelefonoToAttach.getCodigoTelefono());
                attachedTelefonoListNew.add(telefonoListNewTelefonoToAttach);
            }
            telefonoListNew = attachedTelefonoListNew;
            administrador.setTelefonoList(telefonoListNew);
            administrador = em.merge(administrador);
            for (Tecnico tecnicoListNewTecnico : tecnicoListNew) {
                if (!tecnicoListOld.contains(tecnicoListNewTecnico)) {
                    Administrador oldCodigoAdministradorOfTecnicoListNewTecnico = tecnicoListNewTecnico.getCodigoAdministrador();
                    tecnicoListNewTecnico.setCodigoAdministrador(administrador);
                    tecnicoListNewTecnico = em.merge(tecnicoListNewTecnico);
                    if (oldCodigoAdministradorOfTecnicoListNewTecnico != null && !oldCodigoAdministradorOfTecnicoListNewTecnico.equals(administrador)) {
                        oldCodigoAdministradorOfTecnicoListNewTecnico.getTecnicoList().remove(tecnicoListNewTecnico);
                        oldCodigoAdministradorOfTecnicoListNewTecnico = em.merge(oldCodigoAdministradorOfTecnicoListNewTecnico);
                    }
                }
            }
            for (Telefono telefonoListNewTelefono : telefonoListNew) {
                if (!telefonoListOld.contains(telefonoListNewTelefono)) {
                    Administrador oldCodigoAdministradorOfTelefonoListNewTelefono = telefonoListNewTelefono.getCodigoAdministrador();
                    telefonoListNewTelefono.setCodigoAdministrador(administrador);
                    telefonoListNewTelefono = em.merge(telefonoListNewTelefono);
                    if (oldCodigoAdministradorOfTelefonoListNewTelefono != null && !oldCodigoAdministradorOfTelefonoListNewTelefono.equals(administrador)) {
                        oldCodigoAdministradorOfTelefonoListNewTelefono.getTelefonoList().remove(telefonoListNewTelefono);
                        oldCodigoAdministradorOfTelefonoListNewTelefono = em.merge(oldCodigoAdministradorOfTelefonoListNewTelefono);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = administrador.getCodigoAdministrador();
                if (findAdministrador(id) == null) {
                    throw new NonexistentEntityException("The administrador with id " + id + " no longer exists.");
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
            Administrador administrador;
            try {
                administrador = em.getReference(Administrador.class, id);
                administrador.getCodigoAdministrador();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The administrador with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Tecnico> tecnicoListOrphanCheck = administrador.getTecnicoList();
            for (Tecnico tecnicoListOrphanCheckTecnico : tecnicoListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Administrador (" + administrador + ") cannot be destroyed since the Tecnico " + tecnicoListOrphanCheckTecnico + " in its tecnicoList field has a non-nullable codigoAdministrador field.");
            }
            List<Telefono> telefonoListOrphanCheck = administrador.getTelefonoList();
            for (Telefono telefonoListOrphanCheckTelefono : telefonoListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Administrador (" + administrador + ") cannot be destroyed since the Telefono " + telefonoListOrphanCheckTelefono + " in its telefonoList field has a non-nullable codigoAdministrador field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(administrador);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Administrador> findAdministradorEntities() {
        return findAdministradorEntities(true, -1, -1);
    }

    public List<Administrador> findAdministradorEntities(int maxResults, int firstResult) {
        return findAdministradorEntities(false, maxResults, firstResult);
    }

    private List<Administrador> findAdministradorEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Administrador.class));
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

    public Administrador findAdministrador(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Administrador.class, id);
        } finally {
            em.close();
        }
    }

    public int getAdministradorCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Administrador> rt = cq.from(Administrador.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
    //Creamos el metodo que devuelve un administrador segun su nick
    public Administrador findAdministradorByNick(String nick) {
        EntityManager em = getEntityManager();
        try {
        Query query=em.createNamedQuery("Administrador.findByNick").setParameter("nick", nick);
        
        Administrador administrador = (Administrador) query.getResultList().get(0);
        
        return administrador;
        }
        catch(Exception e){
            return null;
        }
        finally {
            em.close();
        }
    }
    
}
