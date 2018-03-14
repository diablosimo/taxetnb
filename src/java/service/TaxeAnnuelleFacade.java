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
import controller.util.DateUtil;
import java.math.BigDecimal;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import controller.util.SearchUtil;
import java.util.Date;
import javax.ejb.EJB;

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

    @EJB
    private TauxTaxeItemFacade tauxTaxeItemFacade;
    @EJB
    private TauxRetardItemFacade tauxRetardItemFacade;
    @EJB
    private TauxRetardFacade tauxRetardFacade;
    @EJB
    private TerrainFacade terrainFacade;

    public List<TaxeAnnuelle> findByCriteria(int annee, BigDecimal montantMin, BigDecimal montantMax, Long numLot, Date datePresentation) {
        String req = "SELECT ta FROM TaxeAnnuelle ta WHERE 1=1";
        if (annee > 0) {
            req += SearchUtil.addConstraint("ta", "annee", "=", annee);
        }
        req += SearchUtil.addConstraintMinMax("ta", "montantTotal", montantMin, montantMax);
//        if (!numLot.equals(new Long(""))) {
        req += SearchUtil.addConstraint("ta", "terrain.numeroLot", "=", numLot);
//       }
        req += SearchUtil.addConstraintDate("ta", "datePresentaion", "=", datePresentation);
        return em.createQuery(req).getResultList();
    }

    public List<TaxeAnnuelle> findByAllCriteria(Date datePresentationMin, Date datePresentationMax,
            BigDecimal montantMin, BigDecimal montantMax,
            Long numLot, String cin, String nif, CategorieTerrain categorieTerrain,
            Rue rue, Quartier quartier, Secteur secteur) {
        String req = "SELECT ta FROM TaxeAnnuelle ta WHERE 1=1";
        req += SearchUtil.addConstraintMinMaxDate("ta", "datePresentaion", datePresentationMin, datePresentationMax);
        req += SearchUtil.addConstraintMinMax("ta", "montantTotal", montantMin, montantMax);
        req += SearchUtil.addConstraint("ta", "terrain.numeroLot", "=", numLot);
        if (!cin.equals("")) {
            req += SearchUtil.addConstraint("ta", "terrain.redevable.cin", "=", cin);
        }
        if (!nif.equals("")) {
            req += SearchUtil.addConstraint("ta", "terrain.redevable.nif", "=", nif);
        }
        req += SearchUtil.addConstraint("ta", "terrain.categorieTerrain.id", "=", categorieTerrain.getId());
        if (rue.getId() == null) {
            if (quartier.getId() == null) {
                if (secteur.getCodePostal() != null) {
                    req += SearchUtil.addConstraint("ta", "terrain.rue.quartier.secteur.codePostal", "=", secteur.getCodePostal());
                    System.out.println("+requette du secteur");
                }
            } else {
                req += SearchUtil.addConstraint("ta", "terrain.rue.quartier.id", "=", quartier.getId());
                System.out.println("+requette du quartier");

            }
        } else {
            req += SearchUtil.addConstraint("ta", "terrain.rue.id", "=", rue.getId());
            System.out.println("+requette de la rue <" + rue.getId() + ">");

        }
        return em.createQuery(req).getResultList();
    }

    public List<TaxeAnnuelle> findByTerrain(Terrain terrain) {
        return em.createQuery("SELECT ta FROM TaxeAnnuelle ta  WHERE ta.terrain.numeroLot='" + terrain.getNumeroLot() + "' ORDER BY ta.annee DESC").getResultList();
    }

    public TaxeAnnuelle findByTerrainAndAnnee(Terrain terrain, int annee) {
        String req = "SELECT ta FROM TaxeAnnuelle ta WHERE 1=1";
        req += SearchUtil.addConstraint("ta", "terrain.numeroLot", "=", terrain.getNumeroLot());
        req += SearchUtil.addConstraint("ta", "annee", "=", annee);
        List<TaxeAnnuelle> res = em.createQuery(req).getResultList();
        if (res != null && !res.isEmpty()) {
            return res.get(0);
        } else {
            return null;
        }
    }

    /**
     *
     * @param taxeAnnuelle
     * @param annee
     * @return
     */
    public int verifier(TaxeAnnuelle taxeAnnuelle, int annee) {
        System.out.println("debut de verification");
        Terrain loadedTerain = terrainFacade.find(taxeAnnuelle.getTerrain().getNumeroLot());
        if (loadedTerain == null) {
            return -1;
        }
        taxeAnnuelle = findByTerrainAndAnnee(loadedTerain, annee);
        if (taxeAnnuelle != null) {//taxe DEJA PAYEE
            return -2;
        } else {
            List<TaxeAnnuelle> taxes = findByTerrain(loadedTerain);
            if (taxes.get(0) != null) {// derniere annee payée+1=annee volue payée 
                if (taxes.get(0).getAnnee() != annee - 1) {
                    return -3;
                }
            }
            if (DateUtil.getDebutAnnee(annee).getTime() > new Date().getTime()) {//l'annee n'a pas encore commencée
                return -3;
            }
            int nbMoisRetard = DateUtil.getNombreMoisRetard(annee);
            if (nbMoisRetard > 6) {// le redevable doit regler le probleme avec la DIRECTION REGIONALE DES IMPOTS et non avec la commune
                return -4;
            }else{
                taxeAnnuelle=new TaxeAnnuelle(annee, nbMoisRetard, new Date(), DateUtil.getDebutAnnee(annee));
                taxeAnnuelle.setTerrain(loadedTerain);
                return 1;
            }
        }
    }

    public int create(TaxeAnnuelle taxeAnnuelle, int annee) {
        System.out.println("debut du paiement");
        Terrain loadedTerain = terrainFacade.find(taxeAnnuelle.getTerrain().getNumeroLot());
        if (loadedTerain == null) {

            return -5;
        }
        taxeAnnuelle = findByTerrainAndAnnee(loadedTerain, annee);
        if (taxeAnnuelle != null) {//taxe DEJA PAYEE
            return -1;
        } else {
            List<TaxeAnnuelle> taxes = findByTerrain(loadedTerain);
            if (taxes.get(0) != null) {// derniere annee payée+1=annee volue payée 
                if (taxes.get(0).getAnnee() != annee - 1) {
                    return -2;
                }
            }
            if (DateUtil.getDebutAnnee(annee).getTime() > new Date().getTime()) {//l'annee n'a pas encore commencée
                return -3;
            }
            int nbMoisRetard = DateUtil.getNombreMoisRetard(annee);
            if (nbMoisRetard > 6) {// le redevable doit regler le probleme avec la DIRECTION REGIONALE DES IMPOTS et non avec la commune
                return -4;
            } else {
                taxeAnnuelle = new TaxeAnnuelle(annee, nbMoisRetard, new Date(), DateUtil.getDebutAnnee(annee));
                taxeAnnuelle.setTerrain(loadedTerain);
                taxeAnnuelle.setTauxTaxeItem(tauxTaxeItemFacade.findByCategorie(loadedTerain.getCategorieTerrain()));
                taxeAnnuelle.setTauxRetardItem(tauxRetardItemFacade.findCurrentOneByCategorie(loadedTerain.getCategorieTerrain()));
                taxeAnnuelle = calcul(taxeAnnuelle, nbMoisRetard);
                loadedTerain.setDernierPaiement(taxeAnnuelle);
                create(clone(taxeAnnuelle));
                terrainFacade.edit(loadedTerain);
                return 1;
            }

        }
    }

    public TaxeAnnuelle calcul(TaxeAnnuelle taxeAnnuelle, int mois) {
        taxeAnnuelle.setMontant(taxeAnnuelle.getTauxTaxeItem().getTaux().multiply(taxeAnnuelle.getTerrain().getSurface()));
        if (mois >= 1) {
            taxeAnnuelle.setPremierMoisRetard(taxeAnnuelle.getTauxRetardItem().getTauxPremierMois().multiply(taxeAnnuelle.getMontant()));
            if (mois > 1) {
                taxeAnnuelle.setAutreMoisRetard(taxeAnnuelle.getTauxRetardItem().getTauxAutreMois().multiply(taxeAnnuelle.getMontant()).multiply(new BigDecimal(mois - 1)));
            } else {
                taxeAnnuelle.setAutreMoisRetard(new BigDecimal("0.00"));
            }
        } else {
            taxeAnnuelle.setPremierMoisRetard(new BigDecimal("0.00"));
            taxeAnnuelle.setAutreMoisRetard(new BigDecimal("0.00"));
        }
        taxeAnnuelle.setMontantRetard(taxeAnnuelle.getPremierMoisRetard().add(taxeAnnuelle.getAutreMoisRetard()));
        taxeAnnuelle.setMontantTotal(taxeAnnuelle.getMontant().add(taxeAnnuelle.getMontantRetard()));
        return taxeAnnuelle;
    }

//    public List<TaxeAnnuelle> getTaxeNonPayeByTerrain(Terrain terrain){
//        Terrain loadedTerrain=terrainFacade.find(terrain.getNumeroLot());
//        if(loadedTerrain==null){
//            return null;
//        }
//        
//    }
    public void clone(TaxeAnnuelle taxeAnnuelleSource, TaxeAnnuelle taxeAnnuelleDestination) {
        taxeAnnuelleDestination.setAnnee(taxeAnnuelleSource.getAnnee());
        taxeAnnuelleDestination.setMontant(taxeAnnuelleSource.getMontant());
        taxeAnnuelleDestination.setNbrMoisRetard(taxeAnnuelleSource.getNbrMoisRetard());
        taxeAnnuelleDestination.setPremierMoisRetard(taxeAnnuelleSource.getPremierMoisRetard());
        taxeAnnuelleDestination.setAutreMoisRetard(taxeAnnuelleSource.getAutreMoisRetard());
        taxeAnnuelleDestination.setMontantRetard(taxeAnnuelleSource.getMontantRetard());
        taxeAnnuelleDestination.setMontantTotal(taxeAnnuelleSource.getMontantTotal());
        taxeAnnuelleDestination.setDatePresentaion(taxeAnnuelleSource.getDatePresentaion());
        taxeAnnuelleDestination.setDateTaxe(taxeAnnuelleSource.getDateTaxe());
        taxeAnnuelleDestination.setTerrain(taxeAnnuelleSource.getTerrain());
        taxeAnnuelleDestination.setTauxTaxeItem(taxeAnnuelleSource.getTauxTaxeItem());
        taxeAnnuelleDestination.setTauxRetardItem(taxeAnnuelleSource.getTauxRetardItem());
    }

    public TaxeAnnuelle clone(TaxeAnnuelle taxeAnnuelle) {
        TaxeAnnuelle cloned = new TaxeAnnuelle();
        clone(taxeAnnuelle, cloned);
        return cloned;
    }

}
