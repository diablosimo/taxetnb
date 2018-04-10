/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import bean.Redevable;
import controller.util.HashageUtil;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author simob
 */
@Stateless
public class RedevableFacade extends AbstractFacade<Redevable> {

    @PersistenceContext(unitName = "taxeTNBPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public RedevableFacade() {
        super(Redevable.class);
    }
    
    
     public Redevable findRedevable( Redevable redevable) {
        return  (Redevable) em.createQuery("SELECT r FROM Redevable r WHERE r.cin='" + redevable.getCin()+ "'").getSingleResult();
    }
     public int seConnecter(Redevable redevable) {
         if(redevable==null|| redevable.getCin()==null){
             //System.out.println("saisir votre mot de passe");
             return -3;
         }
        Redevable ConnectRed = findRedevable(redevable);
        if (ConnectRed == null) {
            return -1;
        } else if (!ConnectRed.getMotDePasse().equals(HashageUtil.sha256( redevable.getMotDePasse()))) {
            return -2;
            
        }
        else {
            return 1;
        }
    }
}
