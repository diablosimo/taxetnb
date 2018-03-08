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
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Aniela
 */
@Stateless
public class TauxTaxeItemFacade extends AbstractFacade<TauxTaxeItem> {

    @PersistenceContext(unitName = "taxeTNBPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public TauxTaxeItemFacade() {
        super(TauxTaxeItem.class);
    }
public int ajouter(TauxTaxeItem tauxTaxeItem) {
        if (tauxTaxeItem == null) {
            return -1;
        } else if (tauxTaxeItem.getCategorieTerrain()== null) { 
            return -2;
        } else if (tauxTaxeItem.getTaux()== null) {
            return -3;
        } else {
            create(tauxTaxeItem);
            return 1;
        }
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
    public List<TauxTaxeItem> findByCrt(Double TMin,Double TMax) {
        
        String reqette = "SELECT t FROM TauxTaxeItem t WHERE 1=1 ";
        
        reqette+=SearchUtil.addConstraintMinMax("t","taux", TMin, TMax);
        return em.createQuery(reqette).getResultList();
        
    }
    
}
