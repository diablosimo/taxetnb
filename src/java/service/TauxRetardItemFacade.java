/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import bean.CategorieTerrain;
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
