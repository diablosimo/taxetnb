/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import bean.Notification;
import bean.Redevable;
import bean.TaxeAnnuelle;
import bean.Terrain;
import controller.util.DateUtil;
import controller.util.SearchUtil;
import java.util.ArrayList;
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
public class NotificationFacade extends AbstractFacade<Notification> {

    @PersistenceContext(unitName = "taxeTNBPU")
    private EntityManager em;
@EJB
TaxeAnnuelleFacade annuelleFacade;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public NotificationFacade() {
        super(Notification.class);
    }
   
    

    public List<Redevable> findNonPayants(List<Terrain> terrains) {
        List<Redevable> redevables = new ArrayList();
        for (int i = 0; i < terrains.size(); i++) {
            Terrain terrain = terrains.get(i);
            redevables.add(terrain.getRedevable());
        }
        return redevables;
    }
    public List<Terrain> notifier(int annee, int typeDernierNot){
        Integer dureeNot;
        switch (typeDernierNot) {
            case 0:
                dureeNot=null;
                break;
            case 1:
            case 2:
                dureeNot=30;
                break;
            default:
                return null;
        }
        return notifier(annee, typeDernierNot, dureeNot);
    }
    
    public List<Terrain> notifier(int annee, int typeDernierNot, Integer dureeNot) {
        List<Terrain> res = new ArrayList();
        Date dateP = DateUtil.parse(annee + "-03-30");
        Long ldateP = dateP.getTime();
        Date dateToDay = new Date();
        Date sdateToDay = DateUtil.convertFormUtilToSql(dateToDay);
        Long ldateToDay = dateToDay.getTime();
        if (ldateToDay >= ldateP) {
            String req = "SELECT te FROM Terrain te WHERE 1=1 ";
            req += SearchUtil.addConstraint("te", "dernierPaiement.annee", "<", annee);
            if (dureeNot != null) {
                req += " AND FUNCTION('DATEDIFF','" + sdateToDay + "',te.DatedernierNotification) > '" + dureeNot + "'";
            } else {
                req += SearchUtil.addConstraint("te", "DatedernierNotification", "=", null);
            }
            req += SearchUtil.addConstraint("te", "typeDernierNotification", "=", typeDernierNot);
            System.out.println(req);
            res = em.createQuery(req).getResultList();
            System.out.println(res);
            return res;
        } else {
            return null;
        }
    }
    public List<Notification> creerNotification(int annee, int typeDernierNot){
        List<Terrain> terrains = notifier( annee, typeDernierNot);
        if(terrains.isEmpty() || terrains ==null){
            return null;
        }else{
            List<Notification>  notifications= new ArrayList();
            for (int i = 0; i < terrains.size(); i++) {
                Terrain terrain = terrains.get(i);
                Notification notification= new Notification();
                notification.setId(generate("Notification", "id"));
                notification.setType(typeDernierNot+1);
                notification.setAnnee(annee);
                notification.setDateEnvoi(new Date());
                notification.setDateReception(new Date());
                notification.setTerrain(terrain);
                TaxeAnnuelle taxeAnnuelle = new TaxeAnnuelle();
                taxeAnnuelle =annuelleFacade.createForNotification(terrain, annee);
                notification.setMontantEstime(taxeAnnuelle.getMontantTotal());
                notification.setNombreMoisRetard(taxeAnnuelle.getNbrMoisRetard());
                notifications.add(notification);
            }
            return notifications;
        }
    }
}
