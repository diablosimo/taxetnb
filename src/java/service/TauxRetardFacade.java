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
    
      public List<TauxRetard> findByDate(Date DMin,Date DMax) {
        
        String reqette = "SELECT t FROM TauxRetard t WHERE 1=1 ";
        
        reqette+=SearchUtil.addConstraintMinMaxDate("t","dateApplication", DMin, DMax);
        return em.createQuery(reqette).getResultList();
        
    }
}
