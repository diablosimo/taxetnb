/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import bean.Terrain;
import bean.Quartier;
import bean.TaxeAnnuelle;
import com.sun.tools.xjc.reader.xmlschema.bindinfo.BIConversion.User;
import controller.util.FrenchNumberToWords;

import controller.util.SearchUtil;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Aniela
 */
@Stateless
public class TaxeAnnuelFacade extends AbstractFacade<TaxeAnnuelle> {

    @PersistenceContext(unitName = "taxeTNBPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public TaxeAnnuelFacade() {
        super(TaxeAnnuelle.class);
    }

    @EJB
    private UtilisateurFacade userFacade;

    public void create(Terrain terrain, int annee) {
        TaxeAnnuelle taxeAnnuel = findByTerrainAndAnnee(terrain, annee);
        if (taxeAnnuel == null) {
            taxeAnnuel = new TaxeAnnuelle();
            taxeAnnuel.setAnnee(annee);
            // taxeAnnuel.setnbrMoisRetard(0);
            taxeAnnuel.setTerrain(terrain);
         //   taxeAnnuel.setMontant(Dou);
            create(taxeAnnuel);

        }
    }

    public List<TaxeAnnuelle> findTaxeAnnuelByCretere(Double montantMin, Double montantMax, int nombreTaxeMin, int nombreTaxetMax, Long numLaut, int annee) {
        String requete = "SELECT tax FROM TaxeAnnuel tax where 1=1";
        if (annee > 0) {
            requete += " AND tax.annee =" + annee;
        }
        if (!numLaut.equals("")) {
            requete += " AND tax.locale.reference='" + numLaut + "'";
        }
        if (nombreTaxeMin > 0) {
            requete += " AND tax.nbrTrimesterPaye >='" + nombreTaxeMin + "'";
        }
        if (nombreTaxetMax > 0) {
            requete += " AND tax.nbrTrimesterPaye <='" + nombreTaxetMax + "'";
        }
        requete += SearchUtil.addConstraintMinMax("tax", "taxeTotale", montantMin, montantMax);

        return em.createQuery(requete).getResultList();
    }

    public TaxeAnnuelle findByTerrainAndAnnee(Terrain terrain, int annee) {
        String requette = "SELECT tax FROM TaxeAnnuel tax where 1=1";
        requette += " AND tax.annee =" + annee;
        requette += " AND tax.terrain.numeroLot =" + terrain.getNumeroLot();
        List<TaxeAnnuelle> list = em.createQuery(requette).getResultList();
        if (list != null && !list.isEmpty()) {
            return list.get(0);
        } else {
            return null;
        }
    }

    public void delete(TaxeAnnuelle taxeAnnuel) {
        if (taxeAnnuel != null && taxeAnnuel.getId() != null) {
            remove(taxeAnnuel);
        }
    }

}
