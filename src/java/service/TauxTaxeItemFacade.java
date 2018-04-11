/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import bean.CategorieTerrain;
import bean.TauxTaxe;
import bean.TauxTaxeItem;
import controller.util.SearchUtil;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Aniela
 */
@Stateless
public class TauxTaxeItemFacade extends AbstractFacade<TauxTaxeItem> {

    @EJB
    TauxTaxeFacade tauxTaxeFacade;
    @PersistenceContext(unitName = "taxeTNBPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public List<TauxTaxeItem> findByTauxTaxe(TauxTaxe tauxTaxe) {
        String reqette = "SELECT t FROM TauxTaxeItem t WHERE t.tauxTaxe.id=" + tauxTaxe.getId() + "";
        List<TauxTaxeItem> tauxTaxeItems= em.createQuery(reqette).getResultList();
        System.out.println(tauxTaxeItems);
        return tauxTaxeItems;
    }

    public TauxTaxeItemFacade() {
        super(TauxTaxeItem.class);
    }

    public int ajouter(TauxTaxeItem tauxTaxeItem) {
        if (tauxTaxeItem == null) {
            return -1;
        } else if (tauxTaxeItem.getCategorieTerrain() == null) {
            return -2;
        } else if (tauxTaxeItem.getTaux() == null) {
            return -3;
        } else {
            create(tauxTaxeItem);
            return 1;
        }
    }

    public TauxTaxeItem findCurrentOneByCategorie(CategorieTerrain categorieTerrain) {
        List<TauxTaxeItem> tauxTaxeItems = tauxTaxeFacade.findCurrentOne().getTauxTaxeItems();
        for (int i = 0; i < tauxTaxeItems.size(); i++) {
            TauxTaxeItem get = tauxTaxeItems.get(i);
            if (get.getCategorieTerrain().getNom().equals(categorieTerrain.getNom())) {
                return get;
            }
        }
        return null;
    }

    public TauxTaxeItem findByCategorie(CategorieTerrain categorie) {
        String reqette = "SELECT t FROM TauxTaxeItem t WHERE t.categorie.id=" + categorie.getId();
        List<TauxTaxeItem> lst = em.createQuery(reqette).getResultList();
        if (lst != null && !lst.isEmpty()) {
            return lst.get(0);
        } else {
            return null;
        }
    }

    public List<TauxTaxeItem> findByTauMxMn(Double tauxMin, Double tauxMax) {

        String reqette = "SELECT t FROM TauxTaxeItem t WHERE 1=1 ";

        reqette += SearchUtil.addConstraintMinMax("t", "taux", tauxMin, tauxMax);
        return em.createQuery(reqette).getResultList();

    }

    public void clone(TauxTaxeItem tauxTaxeItemOriginal, TauxTaxeItem tauxTaxeItemCloned) {
        tauxTaxeItemOriginal.setCategorieTerrain(tauxTaxeItemOriginal.getCategorieTerrain());
        tauxTaxeItemCloned.setTaux(tauxTaxeItemOriginal.getTaux());

    }

    public TauxTaxeItem clone(TauxTaxeItem tauxTaxeItem) {
        TauxTaxeItem cloned = new TauxTaxeItem();
        clone(tauxTaxeItem, cloned);
        return cloned;
    }
}
