/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import bean.CategorieTerrain;
import bean.Quartier;
import bean.Redevable;
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

    public List<TaxeAnnuelle> findByAllCriteria(Date datePresentationMin, Date datePresentationMax, int annee,
            BigDecimal montantMin, BigDecimal montantMax,
            Long numLot, String cin, String nif, CategorieTerrain categorieTerrain,
            Rue rue, Quartier quartier, Secteur secteur) {
        String req = "SELECT ta FROM TaxeAnnuelle ta WHERE 1=1";
        if (annee > 0) {
            req += SearchUtil.addConstraint("ta", "annee", "=", annee);
        }
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

    //pour la recherche des paiement coté utilisateur
    public List<TaxeAnnuelle> findByCriteria(Date datePresentationMin, Date datePresentationMax, int annee,
            BigDecimal montantMin, BigDecimal montantMax,
            Long numLot, String cin, String nif) {
        String req = "SELECT ta FROM TaxeAnnuelle ta WHERE 1=1";
        if (annee > 0) {
            req += SearchUtil.addConstraint("ta", "annee", "=", annee);
            System.out.println("hani f req annee");
        }
        req += SearchUtil.addConstraintMinMaxDate("ta", "datePresentaion", datePresentationMin, datePresentationMax);
        req += SearchUtil.addConstraintMinMax("ta", "montantTotal", montantMin, montantMax);
        req += SearchUtil.addConstraint("ta", "terrain.numeroLot", "=", numLot);
        if (!cin.equals("")) {
            req += SearchUtil.addConstraint("ta", "terrain.redevable.cin", "=", cin);
        }
        if (!nif.equals("")) {
            req += SearchUtil.addConstraint("ta", "terrain.redevable.nif", "=", nif);
        }
        return em.createQuery(req).getResultList();
    }

    //pour la recherche des paiements coté client
    public List<TaxeAnnuelle> findForClient(Redevable redevable, int anneeMin, int anneeMax, Long numeroLot) {
        if (redevable == null) {
            return null;
        }
        String req = "SELECT ta FROM TaxeAnnuelle ta WHERE ta.terrain.redevable.id='" + redevable.getId() + "'";
        if (anneeMin > 0 && anneeMax > 0) {
            req += SearchUtil.addConstraintMinMax("ta", "annee", anneeMin, anneeMax);
        }
        req += SearchUtil.addConstraint("ta", "terrain.numeroLot", "=", numeroLot);
        return em.createQuery(req).getResultList();
    }

    public List<TaxeAnnuelle> findByTerrain(Terrain terrain) {
        return em.createQuery("SELECT ta FROM TaxeAnnuelle ta  WHERE ta.terrain.numeroLot='" + terrain.getNumeroLot() + "' ORDER BY ta.annee DESC").getResultList();
    }

    public TaxeAnnuelle findLastOneByTerrain(Terrain terrain) {
        return (TaxeAnnuelle) em.createQuery("SELECT ta FROM TaxeAnnuelle ta  WHERE ta.terrain.numeroLot='" + terrain.getNumeroLot() + "' ORDER BY ta.annee DESC").getResultList().get(0);
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
    public Object[] verifyAndCreate(TaxeAnnuelle taxeAnnuelle, int annee) {
        Terrain loadedTerain = terrainFacade.find(taxeAnnuelle.getTerrain().getNumeroLot());
        if (loadedTerain == null) {
            return new Object[]{-1, null};
        }
        taxeAnnuelle = findByTerrainAndAnnee(loadedTerain, annee);
        if (taxeAnnuelle != null) {//taxe DEJA PAYEE
            return new Object[]{-2, null};
        } else {
            List<TaxeAnnuelle> taxes = findByTerrain(loadedTerain);
            if (taxes.get(0) != null) {// derniere annee payée+1=annee volue payée 
                if (taxes.get(0).getAnnee() != annee - 1) {
                    return new Object[]{-3, null};
                }
            }
            if (DateUtil.getDebutAnnee(annee).getTime() > new Date().getTime()) {//l'annee n'a pas encore commencée
                return new Object[]{-4, null};
            }
            int nbMoisRetard = DateUtil.getNombreMoisRetard(annee);
            if (nbMoisRetard > 6) {// le redevable doit regler le probleme avec la DIRECTION REGIONALE DES IMPOTS et non avec la commune
                return new Object[]{-5, null};
            } else {
                taxeAnnuelle = new TaxeAnnuelle(annee, nbMoisRetard, new Date(), DateUtil.getDebutAnnee(annee));
                taxeAnnuelle.setTerrain(loadedTerain);
                return new Object[]{1, taxeAnnuelle};
            }
        }
    }

    public TaxeAnnuelle create(TaxeAnnuelle taxeAnnuelle, Boolean simuler) {
        if (taxeAnnuelle == null) {
            return null;
        } else {
            taxeAnnuelle = calcul(taxeAnnuelle);
            if (simuler == false) {
                create(taxeAnnuelle);
                Terrain terrain = taxeAnnuelle.getTerrain();
                terrain.setDernierPaiement(taxeAnnuelle);
                terrainFacade.edit(terrain);
            }
            return taxeAnnuelle;
        }
    }

    public TaxeAnnuelle calcul(TaxeAnnuelle taxeAnnuelle) {
        if (taxeAnnuelle == null) {
            return null;
        } else {
            taxeAnnuelle = tauxTaxeItemFacade.attachToTaxeAnnuelle(taxeAnnuelle);
            System.out.println("ha taux TAXE ITEM" + taxeAnnuelle.getTauxTaxeItem().toString());
            taxeAnnuelle = tauxRetardItemFacade.attachToTaxeAnnuelle(taxeAnnuelle);
            System.out.println("ha taux RETARD ITEM " + taxeAnnuelle.getTauxRetardItem().toString());
            taxeAnnuelle.setMontantTotal(taxeAnnuelle.getMontant().add(taxeAnnuelle.getMontantRetard()));
            return taxeAnnuelle;
        }
    }

//    public void insertInDB(TaxeAnnuelle taxeAnnuelle) {
//        if (taxeAnnuelle != null) {
//            create(taxeAnnuelle);
//            Terrain terrain = taxeAnnuelle.getTerrain();
//            terrain.setDernierPaiement(taxeAnnuelle);
//            terrainFacade.edit(terrain);
//        }
//    }
    //false
//    public TaxeAnnuelle calcul(TaxeAnnuelle taxeAnnuelle, int mois) {
//        System.out.println("hani fl calcul");
//        System.out.println("ha tauxTaxItem " + taxeAnnuelle.getTauxTaxeItem().getTaux());
//        System.out.println("ha surface " + taxeAnnuelle.getTerrain().getSurface());
//        System.out.println("ha l7ssab" + taxeAnnuelle.getTauxTaxeItem().getTaux().multiply(taxeAnnuelle.getTerrain().getSurface()));
//        taxeAnnuelle.setMontant(taxeAnnuelle.getTauxTaxeItem().getTaux().multiply(taxeAnnuelle.getTerrain().getSurface()));
//        if (mois >= 1) {
//            taxeAnnuelle.setPremierMoisRetard(taxeAnnuelle.getTauxRetardItem().getTauxPremierMois().multiply(taxeAnnuelle.getMontant()));
//            if (mois > 1) {
//                taxeAnnuelle.setAutreMoisRetard(taxeAnnuelle.getTauxRetardItem().getTauxAutreMois().multiply(taxeAnnuelle.getMontant()).multiply(new BigDecimal(mois - 1)));
//            } else {
//                taxeAnnuelle.setAutreMoisRetard(new BigDecimal("0.00"));
//            }
//        } else {
//            taxeAnnuelle.setPremierMoisRetard(new BigDecimal("0.00"));
//            taxeAnnuelle.setAutreMoisRetard(new BigDecimal("0.00"));
//        }
//        taxeAnnuelle.setMontantRetard(taxeAnnuelle.getPremierMoisRetard().add(taxeAnnuelle.getAutreMoisRetard()));
//        taxeAnnuelle.setMontantTotal(taxeAnnuelle.getMontant().add(taxeAnnuelle.getMontantRetard()));
//        return taxeAnnuelle;
//    }
//    public TaxeAnnuelle create(TaxeAnnuelle taxeAnnuelle, int annee) {
//        if (taxeAnnuelle == null) {
//            return null;
//        }
//        taxeAnnuelle.setId(generate("TaxeAnnuelle", "id"));
//        System.out.println("1 debut du paiement");
//        System.out.println("2 terrain du taxe" + taxeAnnuelle.getTerrain());
//        System.out.println("3 categorie " + taxeAnnuelle.getTerrain().getCategorieTerrain());
//        taxeAnnuelle.setTauxTaxeItem(tauxTaxeItemFacade.findByCategorie(taxeAnnuelle.getTerrain().getCategorieTerrain()));
//        System.out.println("ha taux TAXE ITEM" + taxeAnnuelle.getTauxTaxeItem().toString());
//        taxeAnnuelle.setTauxRetardItem(tauxRetardItemFacade.findCurrentOneByCategorie(taxeAnnuelle.getTerrain().getCategorieTerrain()));
//        System.out.println("ha taux RETARD ITEM " + taxeAnnuelle.getTauxRetardItem().toString());
//        taxeAnnuelle = calcul(taxeAnnuelle, taxeAnnuelle.getNbrMoisRetard());
//        System.out.println("ha terrain " + taxeAnnuelle.getTerrain().toString());
//
////        taxeAnnuelle.getTerrain().setDernierPaiement(taxeAnnuelle);
//        //create(taxeAnnuelle.clone(taxeAnnuelle));
//        //terrainFacade.edit(taxeAnnuelle.getTerrain());
//        return taxeAnnuelle;
//    }
}
