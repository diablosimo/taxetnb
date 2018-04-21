/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import bean.TauxTaxe;
import bean.TauxTaxeItem;
import controller.util.SearchUtil;
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
public class TauxTaxeFacade extends AbstractFacade<TauxTaxe> {

    @PersistenceContext(unitName = "taxeTNBPU")
    private EntityManager em;
    @EJB
    private CategorieTerrainFacade categorieTerrainFacade;
    @EJB
    private TauxTaxeItemFacade tauxTaxeItemFacade;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public TauxTaxeFacade() {
        super(TauxTaxe.class);
    }

    public int ajouter(List<TauxTaxeItem> tauxTaxeItems, Date dateApplication) {
        TauxTaxe tauxTaxe = new TauxTaxe(generateId("TauxTaxe", "id"), dateApplication);
        System.out.println("1");
        if (tauxTaxeItems.size() == categorieTerrainFacade.findAll().size()) {
                    System.out.println("2");

            create(tauxTaxe);
            for (int i = 0; i < tauxTaxeItems.size(); i++) {
                TauxTaxeItem item = tauxTaxeItems.get(i);
                item.setTauxTaxe(tauxTaxe);
                tauxTaxeItemFacade.create(item);
                        System.out.println("3");


            }
            return 1;
        }
        return -1;
    }

    public List<TauxTaxe> findByDate(Date dateMin, Date dateMax) {
        String reqette = "SELECT t FROM TauxTaxe t WHERE 1=1 ";
        reqette += SearchUtil.addConstraintMinMaxDate("t", "dateApplication", dateMin, dateMax);
        return em.createQuery(reqette).getResultList();

    }

    public TauxTaxe findCurrentOne() {
        List<TauxTaxe> res = em.createQuery("SELECT tr FROM TauxTaxe tr ORDER BY tr.dateApplication DESC").getResultList();
        if (res != null && res.isEmpty() != true) {
            return res.get(0);
        }
        return null;
    }
}
