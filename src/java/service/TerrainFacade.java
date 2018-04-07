/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import bean.Terrain;
import controller.util.DateUtil;
import controller.util.SearchUtil;
import java.util.ArrayList;
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
public class TerrainFacade extends AbstractFacade<Terrain> {

    @PersistenceContext(unitName = "taxeTNBPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public TerrainFacade() {
        super(Terrain.class);
    }
    
    public int quitancer(Long numLot){
        return numLot.intValue();
    }
    
    public List<Terrain> notifier(int annee, int typeDernierNot, int dureeNot) {
        Date dateP = DateUtil.parse(annee + "-04-30");
        Long ldateP = dateP.getTime();
        Date dateToDay = new Date();
        Long ldateToDay = dateToDay.getTime();
        if (ldateToDay >= ldateP) {
            String req = "SELECT te FROM terrain te WHERE 1=1 ";
            req += SearchUtil.addConstraint("te", "dernierPaiement.annee", "<", annee);
            req += " AND datediff(dateToDay,te.DatedernierNotification) > '" + dureeNot + "'";
            req += SearchUtil.addConstraint("te", "typeDernierNotification", "=", typeDernierNot);
            return em.createQuery(req).getResultList();
        } else {
            return null;
        }
    }

    public List<Terrain> testQ() {
        List<Terrain> res = new ArrayList();
        Date dateToDay = new Date();
        Date ldateToDay = DateUtil.convertFormUtilToSql(dateToDay);
        System.out.println(ldateToDay);
        String req = "SELECT te FROM Terrain te WHERE 1=1";
       req+=" AND FUNCTION('DATEDIFF','" + ldateToDay + "', te.DatedernierNotification)> 30";
        res = em.createQuery(req).getResultList();
        System.out.println(res.size());
        System.out.println(res.get(0).getNumeroLot());
        System.out.println(res.size());
        
        return res;
    }


}
