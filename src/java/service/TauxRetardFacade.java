/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import bean.TauxRetard;
import bean.TauxRetardItem;
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
public class TauxRetardFacade extends AbstractFacade<TauxRetard> {

    @PersistenceContext(unitName = "taxeTNBPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
    @EJB
    private TauxRetardItemFacade tauxRetardItemFacade;
    @EJB
    private CategorieTerrainFacade categorieTerrainFacade;

   

    public TauxRetardFacade() {
        super(TauxRetard.class);
    }
     public int ajouter(List<TauxRetardItem> tauxRetardItems, Date dateApplication) {
        TauxRetard tauxRetard = new TauxRetard(generate("TauxRetard", "id"),dateApplication);
        if (tauxRetardItems.size() == categorieTerrainFacade.findAll().size()) {
            create(tauxRetard);
            for (int i = 0; i < tauxRetardItems.size(); i++) {
                TauxRetardItem item = tauxRetardItems.get(i);
                item.setTauxRetard(tauxRetard);
                tauxRetardItemFacade.create(item);

            }
            return 1;
        }
        return -1;
    }
    
      public List<TauxRetard> findByDate(Date dMin,Date dMax) {
        
        String reqette = "SELECT t FROM TauxRetard t WHERE 1=1 ";
        
        reqette+=SearchUtil.addConstraintMinMaxDate("t","dateApplication", dMin, dMax);
        return em.createQuery(reqette).getResultList();
        
    }
      public TauxRetard findCurrentOne(){
         List<TauxRetard> res=em.createQuery("SELECT tr FROM TauxRetard tr ORDER BY tr.dateApplication DESC").getResultList();
         if(res!=null && res.isEmpty()!=true){
             return res.get(0);
         }
         return null;
      }
}
