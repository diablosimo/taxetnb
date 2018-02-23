/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import bean.CategorieTerrain;
import bean.Quartier;
import bean.Rue;
import bean.Secteur;
import bean.TaxeAnnuelle;
import bean.Terrain;
import java.math.BigDecimal;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import controller.util.SearchUtil;
import java.util.Date;

/**
 *
 * @author simob
 */
@Stateless
public class TaxeAnnuelleFacade extends AbstractFacade<TaxeAnnuelle> {

    @PersistenceContext(unitName = "taxeTNBPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public TaxeAnnuelleFacade() {
        super(TaxeAnnuelle.class);
    }

    public List<TaxeAnnuelle> findByCriteria(int annee, BigDecimal montantMin, BigDecimal montantMax, Long numLot, Date datePresentation) {
        String req = "SELECT ta FROM TaxeAnnuelle ta WHERE 1=1";
        if (annee > 0) {
            req += SearchUtil.addConstraint("ta", "annee", "=", annee);
        }
        if (montantMin.compareTo(montantMax) != 1 || montantMin.compareTo(new BigDecimal(-0.01)) > 1 || montantMax.compareTo(new BigDecimal(-0.01)) > 1) {
            req += SearchUtil.addConstraintMinMax("ta", "montantTotal", montantMin, montantMax);
        }
        if (!numLot.equals(new Long(""))) {
            req += SearchUtil.addConstraint("ta", "terrain.numeroLot", "=", numLot);
        }
        req += SearchUtil.addConstraintDate("ta", "datePresentaion", "=", datePresentation);

        return em.createQuery(req).getResultList();
    }

    public List<TaxeAnnuelle> findByAllCriteria(Date datePresentationMin, Date datePresentationMax,
                                                BigDecimal montantMin, BigDecimal montantMax,
                                                Long numLot, String cin, String nif, CategorieTerrain categorieTerrain,
                                                Rue rue, Quartier quartier, Secteur secteur) {
        String req = "SELECT ta FROM TaxeAnnuelle ta WHERE 1=1";
        req+=SearchUtil.addConstraintMinMaxDate("ta", "datePresentaion", datePresentationMin, datePresentationMax);
        req+=SearchUtil.addConstraintMinMax("ta", "montantTotal", montantMin, montantMax);
        req+=SearchUtil.addConstraint("ta", "terrain.numeroLot", "=", numLot);
        if(!cin.equals("")){
            req+=SearchUtil.addConstraint("ta", "terrain.redevable.cin", "=", cin);
        }
        if(!nif.equals("")){
            req+=SearchUtil.addConstraint("ta", "terrain.redevable.nif", "=", nif);
        }
        req+=SearchUtil.addConstraint("ta", "terrain.categorieTerrain.id", "=", categorieTerrain.getId());
        if(rue==null){
            if(quartier==null){
                if(secteur!=null){
                    req+=SearchUtil.addConstraint("ta", "terrain.rue.quartier.secteur.codePostal", "=", secteur.getCodePostal());
                }
            }else{
                req+=SearchUtil.addConstraint("ta", "terrain.rue.quartier.id", "=", quartier.getId());
            }
        }else{
            req+=SearchUtil.addConstraint("ta", "terrain.rue.id", "=", rue.getId());
        }
        return em.createQuery(req).getResultList();
    }
    
    public List<TaxeAnnuelle> findByTerrain(Terrain terrain){
        return em.createQuery("SELECT ta FROM TaxeAnnuelle ta WHERE ta.terrain.numeroLot='"+terrain.getNumeroLot()).getResultList();
    }
    public TaxeAnnuelle findByTerrainAndAnnee(Terrain terrain,int annee){
        String req = "SELECT ta FROM TaxeAnnuelle ta WHERE 1=1";
        req+=SearchUtil.addConstraint("ta", "terrain.numeroLot", "=", terrain.getNumeroLot());
        req+=SearchUtil.addConstraint("ta", "annee", "=", annee);
        List<TaxeAnnuelle> res=em.createQuery(req).getResultList();
        if(res!=null && !res.isEmpty()){
            return res.get(0);
        }else{
            return null;
        }
    }
    
    
}
