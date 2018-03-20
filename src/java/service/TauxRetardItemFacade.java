/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import bean.CategorieTerrain;
import bean.TauxRetard;
import bean.TauxRetardItem;
import controller.util.SearchUtil;
import java.util.ArrayList;
import java.util.Date;
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

    TauxRetardItem findByCategorieAndDate(CategorieTerrain categorieTerrain) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    TauxRetardItem findByCategorieAndDate(CategorieTerrain categorieTerrain, Date datePresentaion) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    List<TauxRetard> findByMinDate(Date dateLimite, CategorieTerrain categorieTerrain) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
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
