/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import bean.Historique;
import bean.Quartier;
import bean.Redevable;
import bean.Rue;
import bean.Secteur;
import bean.TauxRetard;
import bean.TauxRetardItem;
import bean.TauxTaxe;
import bean.TauxTaxeItem;
import bean.Terrain;
import bean.Utilisateur;
import controller.util.SearchUtil;
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author aniela
 */
@Stateless
public class HistoriqueFacade extends AbstractFacade<Historique> {

    @PersistenceContext(unitName = "taxeTNBPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public HistoriqueFacade() {
        super(Historique.class);
    }
    
  
     @EJB
    private UtilisateurFacade utilisateurFacade;
    @EJB
    private TerrainFacade terrainFacade;
    @EJB
    private TauxTaxeFacade tauxTaxeFacade;
    @EJB
    private TauxTaxeItemFacade tauxTaxeItemFacade;
    @EJB
    private TauxRetardFacade tauxRetardFacade;
    @EJB
    private TauxRetardItemFacade tauxRetardItemFacade;
    
    @EJB
    private QuartierFacade quartierFacade;
    @EJB
    private SecteurFacade secteurFacade;
    @EJB
    private RedevableFacade redevableFacade;
    @EJB
    private RueFacade rueFacade;


    public List<Historique> rechercher(Date dateMin, Date dateMax, int type, Utilisateur user) {
        String requette = "SELECT h FROM Historique h where  1=1 ";
        requette += SearchUtil.addConstraintMinMaxDate("h", "actionDate", dateMin, dateMax);
        System.out.println(requette);
        if (type != 0) {
            requette += SearchUtil.addConstraint("h", "type", "=", type);
           System.out.println(requette);
        }
        if (user != null ) {
            requette += SearchUtil.addConstraint("h", "utilisateur.matricule", "=", user.getMatricule());
        }
        requette+=" ORDER BY h.actionDate DESC";
         System.out.println(requette);
        return em.createQuery(requette).getResultList();

    }
     
    public Historique initHistorique(int action, Object entity) {
        Historique history = new Historique();
        history.setType(action);
        history.setUtilisateur(null);
        history.setNomBean(entity.getClass().getSimpleName());
        history.setActionDate(new Date());
        return history;
    }
    
    public void createHistorique(Object entity, int action) {
        Historique historique = initHistorique(action, entity);
        if (action == 1) {
            historique.setAncienValeur(entity.toString());
         
        }
        if (action == 2) {
            Object ancienEntity = findAncien(entity);
            historique.setNouvelleValeur(entity.toString());
            historique.setAncienValeur(ancienEntity.toString());
        }
        create(historique);
    }



   public Object findAncien(Object entity) {
        if (entity instanceof Utilisateur) {
            Utilisateur user = (Utilisateur) entity;
            return utilisateurFacade.find(user.getMatricule());
        } else if (entity instanceof Terrain) {
            Terrain terrain = (Terrain) entity;
            return terrainFacade.find(terrain.getNumeroLot());
        } else if (entity instanceof Rue) {
            Rue rue = (Rue) entity;
            return rueFacade.find(rue.getId());
        } else if (entity instanceof Quartier) {
            Quartier quartier = (Quartier) entity;
            return quartierFacade.find(quartier.getId());
       
        } else if (entity instanceof Secteur) {
            Secteur secteur = (Secteur) entity;
            return secteurFacade.find(secteur.getNom());
       
        } else if (entity instanceof TauxRetard) {
            TauxRetard tauxRetard = (TauxRetard) entity;
            return tauxRetardFacade.find(tauxRetard.getId());
        } else if (entity instanceof TauxRetardItem) {
            TauxRetardItem tauxRetardItem = (TauxRetardItem) entity;
            return tauxRetardItemFacade.find(tauxRetardItem.getId());
        } else if (entity instanceof TauxTaxe) {
            TauxTaxe tauxtaxe = (TauxTaxe) entity;
            return tauxTaxeFacade.find(tauxtaxe.getId());
        } else if (entity instanceof TauxTaxeItem) {
            TauxTaxeItem tauxTaxeItem = (TauxTaxeItem) entity;
            return tauxTaxeItemFacade.find(tauxTaxeItem.getId());
        
        } else if (entity instanceof Redevable) {
            Redevable redevable = (Redevable) entity;
            return redevableFacade.find(redevable.getCin());
        } else {
            return null;
        }
    }

    
}
