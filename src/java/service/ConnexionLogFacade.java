/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import bean.ConnexionLog;
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
public class ConnexionLogFacade extends AbstractFacade<ConnexionLog> {

    @PersistenceContext(unitName = "taxeTNBPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ConnexionLogFacade() {
        super(ConnexionLog.class);
    }

    public List<ConnexionLog> rechercher(Date dateMin, Date dateMax, int type, Utilisateur user) {
        String requette = "SELECT c FROM ConnexionLog c where  1=1 ";
        requette += SearchUtil.addConstraintMinMaxDate("c", "actionDate", dateMin, dateMax);
        if (type != 0) {
            requette += SearchUtil.addConstraint("c", "type", "=", type);
        }
        if (user != null) {
            requette += SearchUtil.addConstraint("c", "utilisateur.matricule", "=", user.getMatricule());
        }
        requette += " ORDER BY c.actionDate DESC";

        return em.createQuery(requette).getResultList();

    }

    public void createHistoryLog(Utilisateur loadedUser, int type) {
        ConnexionLog connexionHistory = new ConnexionLog();
        connexionHistory.setUtilisateur(loadedUser);
        if (type == 1) {
            connexionHistory.setType(1);
            connexionHistory.setActionDate(new Date());
        }
        if (type == 2) {
            connexionHistory.setType(2);
            connexionHistory.setActionDate(new Date());
        }

        create(connexionHistory);
    }
}
