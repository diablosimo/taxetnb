/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import bean.CategorieTerrain;
import bean.TauxTaxeItem;
import java.util.Date;
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

    TauxTaxeItem findByCategorie(CategorieTerrain categorieTerrain) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    TauxTaxeItem findByCategorieAndDate(CategorieTerrain categorieTerrain) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    TauxTaxeItem findByCategorieAndDate(CategorieTerrain categorieTerrain, Date dateTaxe) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
