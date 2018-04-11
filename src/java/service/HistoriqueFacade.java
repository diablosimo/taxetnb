/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import bean.Historique;
import bean.Utilisateur;
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
public class HistoriqueFacade extends AbstractFacade<Historique> {

    @PersistenceContext(unitName = "taxeTNBPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public HistoriqueFacade() {
        super(Historique.class);
    }
    
  
    

    public List<Historique> rechercher(Date dateMin, Date dateMax, int type, Utilisateur user) {
        String requette = "SELECT h FROM Historique h where  1=1 ";
        requette += SearchUtil.addConstraintMinMaxDate("h", "actionDate", dateMin, dateMax);
        if (type != 0) {
            requette += SearchUtil.addConstraint("h", "type", "=", type);
        }
        if (user != null) {
            requette += SearchUtil.addConstraint("h", "utilisateur.matricule", "=", user.getMatricule());
        }
        requette+=" ORDER BY h.actionDate DESC";
        
        return em.createQuery(requette).getResultList();

    }

  
    
}
