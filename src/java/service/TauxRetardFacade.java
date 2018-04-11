/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import bean.TauxRetard;
import controller.util.SearchUtil;
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
public class TauxRetardFacade extends AbstractFacade<TauxRetard> {

    @PersistenceContext(unitName = "taxeTNBPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public TauxRetardFacade() {
        super(TauxRetard.class);
    }
      public int ajouter(TauxRetard tauxRetard) {
        if (tauxRetard == null) {
            return -1;
        } else if (tauxRetard.getDateApplication()== null) {
            return -2;
        } else if (tauxRetard.getTauxRetardItems()== null) {
            return -3;
        } else {
            create(tauxRetard);
            return 1;
        }
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
