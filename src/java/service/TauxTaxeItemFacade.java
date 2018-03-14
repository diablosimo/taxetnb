/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import bean.CategorieTerrain;
import bean.TauxTaxeItem;
import java.util.Date;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author simob
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

   

    TauxTaxeItem findByCategorieAndDate(CategorieTerrain categorieTerrain) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    TauxTaxeItem findByCategorieAndDate(CategorieTerrain categorieTerrain, Date dateTaxe) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    public TauxTaxeItem findByCategorie(CategorieTerrain categorieTerrain) {
        String reqette = "SELECT t FROM TauxTaxeItem t WHERE t.categorieTerrain.id=" + categorieTerrain.getId();
        List<TauxTaxeItem> lst = em.createQuery(reqette).getResultList();
        if (lst != null && !lst.isEmpty()) {
            return lst.get(0);
        } else {
            return null;
        }
    }
    
}
