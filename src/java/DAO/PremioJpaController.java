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
import DTO.Canjear;
import java.util.ArrayList;
import java.util.List;
import DTO.Foto;
import DTO.Premio;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Pedro
 */
public class PremioJpaController implements Serializable {

    public PremioJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Premio premio) {
        if (premio.getCanjearList() == null) {
            premio.setCanjearList(new ArrayList<Canjear>());
        }
        if (premio.getFotoList() == null) {
            premio.setFotoList(new ArrayList<Foto>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Canjear> attachedCanjearList = new ArrayList<Canjear>();
            for (Canjear canjearListCanjearToAttach : premio.getCanjearList()) {
                canjearListCanjearToAttach = em.getReference(canjearListCanjearToAttach.getClass(), canjearListCanjearToAttach.getCodigoCanjear());
                attachedCanjearList.add(canjearListCanjearToAttach);
            }
            premio.setCanjearList(attachedCanjearList);
            List<Foto> attachedFotoList = new ArrayList<Foto>();
            for (Foto fotoListFotoToAttach : premio.getFotoList()) {
                fotoListFotoToAttach = em.getReference(fotoListFotoToAttach.getClass(), fotoListFotoToAttach.getCodigoFoto());
                attachedFotoList.add(fotoListFotoToAttach);
            }
            premio.setFotoList(attachedFotoList);
            em.persist(premio);
            for (Canjear canjearListCanjear : premio.getCanjearList()) {
                Premio oldCodigoPremioOfCanjearListCanjear = canjearListCanjear.getCodigoPremio();
                canjearListCanjear.setCodigoPremio(premio);
                canjearListCanjear = em.merge(canjearListCanjear);
                if (oldCodigoPremioOfCanjearListCanjear != null) {
                    oldCodigoPremioOfCanjearListCanjear.getCanjearList().remove(canjearListCanjear);
                    oldCodigoPremioOfCanjearListCanjear = em.merge(oldCodigoPremioOfCanjearListCanjear);
                }
            }
            for (Foto fotoListFoto : premio.getFotoList()) {
                Premio oldCodigoPremioOfFotoListFoto = fotoListFoto.getCodigoPremio();
                fotoListFoto.setCodigoPremio(premio);
                fotoListFoto = em.merge(fotoListFoto);
                if (oldCodigoPremioOfFotoListFoto != null) {
                    oldCodigoPremioOfFotoListFoto.getFotoList().remove(fotoListFoto);
                    oldCodigoPremioOfFotoListFoto = em.merge(oldCodigoPremioOfFotoListFoto);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Premio premio) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Premio persistentPremio = em.find(Premio.class, premio.getCodigoPremio());
            List<Canjear> canjearListOld = persistentPremio.getCanjearList();
            List<Canjear> canjearListNew = premio.getCanjearList();
            List<Foto> fotoListOld = persistentPremio.getFotoList();
            List<Foto> fotoListNew = premio.getFotoList();
            List<String> illegalOrphanMessages = null;
            for (Canjear canjearListOldCanjear : canjearListOld) {
                if (!canjearListNew.contains(canjearListOldCanjear)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Canjear " + canjearListOldCanjear + " since its codigoPremio field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            List<Canjear> attachedCanjearListNew = new ArrayList<Canjear>();
            for (Canjear canjearListNewCanjearToAttach : canjearListNew) {
                canjearListNewCanjearToAttach = em.getReference(canjearListNewCanjearToAttach.getClass(), canjearListNewCanjearToAttach.getCodigoCanjear());
                attachedCanjearListNew.add(canjearListNewCanjearToAttach);
            }
            canjearListNew = attachedCanjearListNew;
            premio.setCanjearList(canjearListNew);
            List<Foto> attachedFotoListNew = new ArrayList<Foto>();
            for (Foto fotoListNewFotoToAttach : fotoListNew) {
                fotoListNewFotoToAttach = em.getReference(fotoListNewFotoToAttach.getClass(), fotoListNewFotoToAttach.getCodigoFoto());
                attachedFotoListNew.add(fotoListNewFotoToAttach);
            }
            fotoListNew = attachedFotoListNew;
            premio.setFotoList(fotoListNew);
            premio = em.merge(premio);
            for (Canjear canjearListNewCanjear : canjearListNew) {
                if (!canjearListOld.contains(canjearListNewCanjear)) {
                    Premio oldCodigoPremioOfCanjearListNewCanjear = canjearListNewCanjear.getCodigoPremio();
                    canjearListNewCanjear.setCodigoPremio(premio);
                    canjearListNewCanjear = em.merge(canjearListNewCanjear);
                    if (oldCodigoPremioOfCanjearListNewCanjear != null && !oldCodigoPremioOfCanjearListNewCanjear.equals(premio)) {
                        oldCodigoPremioOfCanjearListNewCanjear.getCanjearList().remove(canjearListNewCanjear);
                        oldCodigoPremioOfCanjearListNewCanjear = em.merge(oldCodigoPremioOfCanjearListNewCanjear);
                    }
                }
            }
            for (Foto fotoListOldFoto : fotoListOld) {
                if (!fotoListNew.contains(fotoListOldFoto)) {
                    fotoListOldFoto.setCodigoPremio(null);
                    fotoListOldFoto = em.merge(fotoListOldFoto);
                }
            }
            for (Foto fotoListNewFoto : fotoListNew) {
                if (!fotoListOld.contains(fotoListNewFoto)) {
                    Premio oldCodigoPremioOfFotoListNewFoto = fotoListNewFoto.getCodigoPremio();
                    fotoListNewFoto.setCodigoPremio(premio);
                    fotoListNewFoto = em.merge(fotoListNewFoto);
                    if (oldCodigoPremioOfFotoListNewFoto != null && !oldCodigoPremioOfFotoListNewFoto.equals(premio)) {
                        oldCodigoPremioOfFotoListNewFoto.getFotoList().remove(fotoListNewFoto);
                        oldCodigoPremioOfFotoListNewFoto = em.merge(oldCodigoPremioOfFotoListNewFoto);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = premio.getCodigoPremio();
                if (findPremio(id) == null) {
                    throw new NonexistentEntityException("The premio with id " + id + " no longer exists.");
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
            Premio premio;
            try {
                premio = em.getReference(Premio.class, id);
                premio.getCodigoPremio();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The premio with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Canjear> canjearListOrphanCheck = premio.getCanjearList();
            for (Canjear canjearListOrphanCheckCanjear : canjearListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Premio (" + premio + ") cannot be destroyed since the Canjear " + canjearListOrphanCheckCanjear + " in its canjearList field has a non-nullable codigoPremio field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            List<Foto> fotoList = premio.getFotoList();
            for (Foto fotoListFoto : fotoList) {
                fotoListFoto.setCodigoPremio(null);
                fotoListFoto = em.merge(fotoListFoto);
            }
            em.remove(premio);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Premio> findPremioEntities() {
        return findPremioEntities(true, -1, -1);
    }

    public List<Premio> findPremioEntities(int maxResults, int firstResult) {
        return findPremioEntities(false, maxResults, firstResult);
    }

    private List<Premio> findPremioEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Premio.class));
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

    public Premio findPremio(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Premio.class, id);
        } finally {
            em.close();
        }
    }

    public int getPremioCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Premio> rt = cq.from(Premio.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
