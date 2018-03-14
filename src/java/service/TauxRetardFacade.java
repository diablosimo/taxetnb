/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import bean.TauxRetard;
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

    List<TauxRetard> findByMinDate(Date dateLimite) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
     public TauxRetard findCurrentOne(){
         List<TauxRetard> res=em.createQuery("SELECT tr FROM TauxRetard tr ORDER BY tr.dateApplication DESC").getResultList();
         if(res!=null && res.isEmpty()!=true){
             return res.get(0);
         }
         return null;
      }
    
}
