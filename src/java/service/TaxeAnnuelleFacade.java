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
import bean.Utilisateur;
import controller.util.DateUtil;
import controller.util.EmailUtil;
import controller.util.FrenchNumberToWords;
import controller.util.PdfUtil;
import java.math.BigDecimal;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import controller.util.SearchUtil;
import controller.util.SessionUtil;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import javax.ejb.EJB;
import javax.mail.MessagingException;
import net.sf.jasperreports.engine.JRException;

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
    @EJB
    private UtilisateurFacade utilisateurFacade;

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
            Rue rue, Quartier quartier, Secteur secteur, Utilisateur utilisateur) {
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
        if (categorieTerrain != null) {
            req += SearchUtil.addConstraint("ta", "terrain.categorieTerrain.id", "=", categorieTerrain.getId());
        }
        if (utilisateur != null) {
            req += SearchUtil.addConstraint("ta", "utilisateur.matricule", "=", utilisateur.getMatricule());
        }
        if (rue == null || rue.getId() == null) {
            if (quartier == null || quartier.getId() == null) {
                if (secteur != null && secteur.getCodePostal() != null) {
                    req += SearchUtil.addConstraint("ta", "terrain.rue.quartier.secteur.codePostal", "=", secteur.getCodePostal());
                }
            } else {
                req += SearchUtil.addConstraint("ta", "terrain.rue.quartier.id", "=", quartier.getId());
            }
        } else {
            req += SearchUtil.addConstraint("ta", "terrain.rue.id", "=", rue.getId());
            System.out.println("+requette de la rue <" + rue.getId() + ">");
        }
        System.out.println("ha la requette globale=> " + req);
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
        System.out.println("im in findByTerrain&annee");
        String req = "SELECT ta FROM TaxeAnnuelle ta WHERE 1=1";
        req += SearchUtil.addConstraint("ta", "terrain.numeroLot", "=", terrain.getNumeroLot());
        req += SearchUtil.addConstraint("ta", "annee", "=", annee);
        List<TaxeAnnuelle> res = em.createQuery(req).getResultList();
        System.out.println(res);
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
            if (DateUtil.getDebutAnnee(annee).getTime() > new Date().getTime()) {//l'annee n'a pas encore commencée
                return new Object[]{-4, null};
            }
            List<TaxeAnnuelle> taxes = findByTerrain(loadedTerain);
            if (taxes.get(0) != null) {// derniere annee payée+1=annee volue payée 
                if (taxes.get(0).getAnnee() < annee - 1) {
                    return new Object[]{-3, null};
                }
            }
            int nbMoisRetard = DateUtil.getNombreMoisRetard(annee);
            if (nbMoisRetard > 6) {// le redevable doit regler le probleme avec la DIRECTION REGIONALE DES IMPOTS et non avec la commune
                return new Object[]{-5, null};
            } else {
                taxeAnnuelle = new TaxeAnnuelle(annee, nbMoisRetard, new Date(), DateUtil.getDebutAnnee(annee));
                taxeAnnuelle.setId(generate("TaxeAnnuelle", "id"));
                taxeAnnuelle.setTerrain(loadedTerain);
                taxeAnnuelle = calcul(taxeAnnuelle);
                return new Object[]{1, taxeAnnuelle};
            }
        }
    }

    public TaxeAnnuelle create(TaxeAnnuelle taxeAnnuelle, Boolean simuler) {
        if (taxeAnnuelle == null) {
            return null;
        } else {
            //taxeAnnuelle = calcul(taxeAnnuelle);
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
          //  taxeAnnuelle = tauxTaxeItemFacade.attachToTaxeAnnuelle(taxeAnnuelle);
            System.out.println("ha taux TAXE ITEM" + taxeAnnuelle.getTauxTaxeItem().toString());
          //  taxeAnnuelle = tauxRetardItemFacade.attachToTaxeAnnuelle(taxeAnnuelle);
            System.out.println("ha taux RETARD ITEM " + taxeAnnuelle.getTauxRetardItem().toString());
            taxeAnnuelle.setMontantTotal(taxeAnnuelle.getMontant().add(taxeAnnuelle.getMontantRetard()));
            return taxeAnnuelle;
        }
    }

    public String printPdf(TaxeAnnuelle taxeAnnuelle) throws JRException, IOException, MessagingException {
        List myList = new ArrayList();
        myList.add(taxeAnnuelle);
        //Utilisateur utilisateur = utilisateurFacade.find(SessionUtil.getConnectedUser().getMatricule());
        Utilisateur utilisateur = utilisateurFacade.find("13089122");
        System.out.println(utilisateur);
        Redevable redevable = taxeAnnuelle.getTerrain().getRedevable();
        String cinNif;
        String prenom = taxeAnnuelle.getTerrain().getRedevable().getPrenom();
        if (taxeAnnuelle.getTerrain().getRedevable().getCin().equals("")) {
            cinNif = taxeAnnuelle.getTerrain().getRedevable().getNif();
        } else {
            cinNif = taxeAnnuelle.getTerrain().getRedevable().getCin();
        }
        if (taxeAnnuelle.getTerrain().getRedevable().getPrenom() == null) {
            prenom = "";
        }
        Map<String, Object> params = new HashMap();
        String fileName = null;
        fileName = "Quitance_" + taxeAnnuelle.getId();
        params.put("redevableId", cinNif);
        params.put("redevable", redevable.getNom().toUpperCase() + " " + prenom);
        params.put("terrain", taxeAnnuelle.getTerrain().toString());
        params.put("id", taxeAnnuelle.getId().toString());
        params.put("categorie", taxeAnnuelle.getTerrain().getCategorieTerrain().getNom());
        params.put("montantTotal", taxeAnnuelle.getMontantTotal());
        params.put("montantTotalWord", FrenchNumberToWords.convert(taxeAnnuelle.getMontantTotal()));
        params.put("utilisateur", utilisateur.getNom().toUpperCase() + " " + utilisateur.getPrenom().toUpperCase());
        //params.put("utilisateur", "BENMANSOUR MOHAMMED");
        System.out.println(params);
        System.out.println(taxeAnnuelle);
        PdfUtil.generatePdf(myList, params, fileName, "/jasper/Quitance.jasper");
        // sendQuitanceInEmail(redevable.getEmail(),"ci-joint vous trouverez le recu du paiement du terrain "+taxeAnnuelle.getTerrain().getNumeroLot() , "quitance "+taxeAnnuelle.getAnnee(), "C:\\Users\\simob\\Dropbox\\ARCHITECTURE REPARTIE\\Quitances\\"+"Quitance_" + taxeAnnuelle.getId() + ".pdf");
        return fileName;
    }

    public void sendQuitanceInEmail(String to, String message, String subject, String fileAttachment) throws MessagingException {
        EmailUtil.sendMail("taxe.tnb@gmail.com", "taxe@TNB2018", message, to, subject, fileAttachment);
    }

    public TaxeAnnuelle createForNotification(Terrain terrain,int annee){
        System.out.println("ha terrain="+terrain);
        System.out.println("ha annee="+annee);
        int nbMoisRetard=DateUtil.getNombreMoisRetard(annee);
        System.out.println("hq nb retard="+nbMoisRetard);
        TaxeAnnuelle taxeAnnuelle=new TaxeAnnuelle(annee, nbMoisRetard, new Date(), DateUtil.getDebutAnnee(annee));
        System.out.println("ha taxan="+taxeAnnuelle);
        taxeAnnuelle.setTerrain(terrain);
        taxeAnnuelle=calcul(taxeAnnuelle);
        System.out.println("hq taxeqnn appres calcul="+taxeAnnuelle);
        return taxeAnnuelle;
    }
}
