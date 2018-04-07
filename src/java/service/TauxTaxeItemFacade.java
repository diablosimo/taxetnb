/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import bean.CategorieTerrain;
import bean.TauxTaxeItem;
import bean.TaxeAnnuelle;
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

    public TauxTaxeItem findByCategorie(CategorieTerrain categorieTerrain) {
        System.out.println("ha debut diyal findtTTIByCat " + categorieTerrain.toString());
        List<TauxTaxeItem> lst = em.createQuery("SELECT t FROM TauxTaxeItem t WHERE t.categorieTerrain.id='" + categorieTerrain.getId() + "'").getResultList();
        System.out.println("ha tauxTaxe " + lst);
        if (lst != null && !lst.isEmpty()) {
            return lst.get(0);
        } else {
            return null;
        }
    }

    

    public TaxeAnnuelle attachToTaxeAnnuelle(TaxeAnnuelle taxeAnnuelle) {
        if (taxeAnnuelle != null) {
            taxeAnnuelle.setTauxTaxeItem(findByCategorie(taxeAnnuelle.getTerrain().getCategorieTerrain()));
            taxeAnnuelle.setMontant(taxeAnnuelle.getTauxTaxeItem().getTaux().multiply(taxeAnnuelle.getTerrain().getSurface()));
            return taxeAnnuelle;
        }
        return null;
    }

}
