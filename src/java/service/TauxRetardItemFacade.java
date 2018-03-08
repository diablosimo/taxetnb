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
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Aniela
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
    
  public int creer(TauxRetardItem tauxRetardItem){
        if (tauxRetardItem==null){
            return -1;
        }else
            if (tauxRetardItem.getTauxPremierMois()==null){
            return -2;
        }else if(tauxRetardItem.getTauxAutreMois()==null){
            return -3;
        }
        else{
               create(tauxRetardItem);
            return 1;
        }
    }
    public TauxRetard findByCategorie(CategorieTerrain categorie) {
        String reqette = "SELECT t FROM TauxTaxeRetardItem t WHERE t.categorie.id=" + categorie.getId();
        List<TauxRetard> lst = em.createQuery(reqette).getResultList();
        if (lst != null && !lst.isEmpty()) {
            return lst.get(0);
        } else {
            return null;
        }
    }

    public List<TauxRetardItem> findByCrit(Double pMin, Double pMax, Double paMin, Double paMax) {

        String reqette = "SELECT t FROM TauxTaxeRetardItem t WHERE 1=1 ";

        reqette += SearchUtil.addConstraintMinMax("t", "tauxPremierMois", pMin, pMax);
        reqette += SearchUtil.addConstraintMinMax("t", "tauxAutreMois", paMin, paMax);
        return em.createQuery(reqette).getResultList();

    }

  

}
