/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import bean.CategorieTerrain;
import bean.TauxTaxe;
import bean.TauxTaxeItem;
import bean.TaxeAnnuelle;
import controller.util.SearchUtil;
import java.util.List;
import javax.ejb.EJB;
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
    @EJB
    TauxTaxeFacade tauxTaxeFacade;

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

    public List<TauxTaxeItem> Add(TauxTaxeItem tauxTaxeItem, List<TauxTaxeItem> tauxTaxeItems) {
        int x = 0;
        for (int i = 0; i < tauxTaxeItems.size(); i++) {
            TauxTaxeItem item = tauxTaxeItems.get(i);
            if (tauxTaxeItem.getCategorieTerrain().getId() == item.getCategorieTerrain().getId()) {
                System.out.println("in condition of ct");
                x = 1;
            }
        }
        if (x == 0) {
            // tauxTaxeItem.getTauxTaxe().setDateApplication(new Date());
            tauxTaxeItems.add(clone(tauxTaxeItem));

        }
        return tauxTaxeItems;
    }

    public List<TauxTaxeItem> findByTauxTaxe(TauxTaxe tauxTaxe) {
        String reqette = "SELECT t FROM TauxTaxeItem t WHERE t.tauxTaxe.id=" + tauxTaxe.getId() + "";
        List<TauxTaxeItem> tauxTaxeItems = em.createQuery(reqette).getResultList();
        System.out.println(tauxTaxeItems);
        return tauxTaxeItems;
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

    public List<TauxTaxeItem> findByTauMxMn(Double tauxMin, Double tauxMax) {

        String reqette = "SELECT t FROM TauxTaxeItem t WHERE 1=1 ";

        reqette += SearchUtil.addConstraintMinMax("t", "taux", tauxMin, tauxMax);
        return em.createQuery(reqette).getResultList();

    }

    public void clone(TauxTaxeItem tauxTaxeItemOriginal, TauxTaxeItem tauxTaxeItemCloned) {
        tauxTaxeItemOriginal.setCategorieTerrain(tauxTaxeItemOriginal.getCategorieTerrain());
        tauxTaxeItemCloned.setTaux(tauxTaxeItemOriginal.getTaux());
        tauxTaxeItemCloned.setCategorieTerrain(tauxTaxeItemOriginal.getCategorieTerrain());

    }

    public TauxTaxeItem clone(TauxTaxeItem tauxTaxeItem) {
        TauxTaxeItem cloned = new TauxTaxeItem();
        clone(tauxTaxeItem, cloned);
        return cloned;
    }
}
