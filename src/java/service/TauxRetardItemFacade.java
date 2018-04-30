/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import bean.CategorieTerrain;
import bean.TauxRetard;
import bean.TauxRetardItem;
import bean.TaxeAnnuelle;
import controller.util.SearchUtil;
import java.math.BigDecimal;
import java.util.ArrayList;
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
public class TauxRetardItemFacade extends AbstractFacade<TauxRetardItem> {

    @PersistenceContext(unitName = "taxeTNBPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public TauxRetardItemFacade() {
        super(TauxRetardItem.class);
    }
    @EJB
    private TauxRetardFacade tauxRetardFacade;

    public List<TauxRetardItem> Add(TauxRetardItem tauxRetardItem, List<TauxRetardItem> tauxRetardItems) {
        int x = 0;

        for (int i = 0; i < tauxRetardItems.size(); i++) {
            TauxRetardItem item = tauxRetardItems.get(i);
            if (tauxRetardItem.getCategorieTerrain().getId() == item.getCategorieTerrain().getId()) {
                System.out.println("in condition of ct");
                x = 1;
            }
        }
        if (x == 0) {
            // tauxTaxeItem.getTauxTaxe().setDateApplication(new Date());
            tauxRetardItems.add(clone(tauxRetardItem));
        }

        return tauxRetardItems;

    }

    public List<TauxRetardItem> findByCrit(Double premierMin, Double premierMax, Double autreMin, Double autreMax) {

        String reqette = "SELECT t FROM TauxTaxeRetardItem t WHERE 1=1 ";

        reqette += SearchUtil.addConstraintMinMax("t", "tauxPremierMois", premierMin, premierMax);
        reqette += SearchUtil.addConstraintMinMax("t", "tauxAutreMois", autreMin, autreMax);
        return em.createQuery(reqette).getResultList();

    }

    public List<TauxRetardItem> findByTauxRetard(TauxRetard tauxRetard) {

        String reqette = "SELECT t FROM TauxRetardItem t WHERE t.tauxRetard.id=" + tauxRetard.getId() + "";
        return em.createQuery(reqette).getResultList();

    }

    public void clone(TauxRetardItem tauxRetardItemOriginal, TauxRetardItem tauxTaxeItemCloned) {

        tauxTaxeItemCloned.setTauxRetard(tauxRetardItemOriginal.getTauxRetard());
        tauxTaxeItemCloned.setCategorieTerrain(tauxRetardItemOriginal.getCategorieTerrain());
        tauxTaxeItemCloned.setTauxAutreMois(tauxRetardItemOriginal.getTauxAutreMois());
        tauxTaxeItemCloned.setTauxPremierMois(tauxRetardItemOriginal.getTauxPremierMois());

    }

    public TauxRetardItem clone(TauxRetardItem tauxRetardItem) {
        TauxRetardItem cloned = new TauxRetardItem();
        clone(tauxRetardItem, cloned);
        return cloned;
    }

    //false
    public TauxRetardItem findByCategorie(CategorieTerrain categorieTerrain) {
        List<TauxRetardItem> items = new ArrayList();
        String req = "SELECT tri FROM TauxRetard tr JOIN tr.tauxRetardItems tri WHERE 1=1";
        req += " AND tr.tauxRetardItems.id=tri.id";
        req += SearchUtil.addConstraint("tri", "categorieTerrain.id", "=", categorieTerrain.getId());
        req += "ORDER BY tr.dateApplication DESC";
        items = em.createQuery(req).getResultList();
        if (items.isEmpty() == true || items == null) {
            return null;
        } else {
            return items.get(0);
        }
    }

    public TaxeAnnuelle attachToTaxeAnnuelle(TaxeAnnuelle taxeAnnuelle) {
        if (taxeAnnuelle != null) {
            taxeAnnuelle.setTauxRetardItem(findCurrentOneByCategorie(taxeAnnuelle.getTerrain().getCategorieTerrain()));
            taxeAnnuelle = calculRetard(taxeAnnuelle);
            return taxeAnnuelle;
        }
        return null;
    }

    public TaxeAnnuelle calculRetard(TaxeAnnuelle taxeAnnuelle) {
        if (taxeAnnuelle == null) {
            return null;
        } else {
            int nbMoisRetard = taxeAnnuelle.getNbrMoisRetard();
            if (nbMoisRetard >= 1) {
                taxeAnnuelle.setPremierMoisRetard(taxeAnnuelle.getTauxRetardItem().getTauxPremierMois().multiply(taxeAnnuelle.getMontant()));
                if (taxeAnnuelle.getNbrMoisRetard() > 1) {
                    taxeAnnuelle.setAutreMoisRetard(taxeAnnuelle.getTauxRetardItem().getTauxAutreMois().multiply(taxeAnnuelle.getMontant()).multiply(new BigDecimal(nbMoisRetard - 1)));
                }
            }
            taxeAnnuelle.setMontantRetard(taxeAnnuelle.getPremierMoisRetard().add(taxeAnnuelle.getAutreMoisRetard()));
            return taxeAnnuelle;
        }
    }

    public TauxRetardItem findCurrentOneByCategorie(CategorieTerrain categorieTerrain) {
        if (categorieTerrain == null) {
            return null;
        } else {
            TauxRetardItem tauxRetardItem = new TauxRetardItem();
            tauxRetardItem = (TauxRetardItem) em.createQuery("SELECT trt FROM TauxRetardItem trt LEFT JOIN trt.tauxRetard tr  WHERE trt.categorieTerrain.id='" + categorieTerrain.getId() + "' ORDER BY tr.dateApplication DESC ").getResultList().get(0);
            System.out.println("service findTauxRetartItem " + tauxRetardItem.toString());
            return tauxRetardItem;
        }
    }
}
