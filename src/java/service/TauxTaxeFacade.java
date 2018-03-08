/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import bean.TauxTaxe;
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
public class TauxTaxeFacade extends AbstractFacade<TauxTaxe> {

    @PersistenceContext(unitName = "taxeTNBPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public TauxTaxeFacade() {
        super(TauxTaxe.class);
    }
   
    public int ajouter(TauxTaxe tauxTaxe) {
        if (tauxTaxe == null) {
            return -1;
        } else if (tauxTaxe.getTauxTaxeItems()== null) {
            return -2;
        } else if (tauxTaxe.getDateApplication() == null) {
            return -3;
        } else {
            create(tauxTaxe);
            return 1;
        }
    }
    
  public List<TauxTaxe> findByDate(Date DMin,Date DMax) {
        
        String reqette = "SELECT t FROM TauxTaxe t WHERE 1=1 ";
        
        reqette+=SearchUtil.addConstraintMinMaxDate("t","dateApplication", DMin, DMax);
        return em.createQuery(reqette).getResultList();
        
    }
    
    
     

    
    
    
    
    
    
}
