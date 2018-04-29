/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import bean.CategorieTerrain;
import bean.Redevable;
import bean.TaxeAnnuelle;
import bean.Terrain;
import controller.util.SearchUtil;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
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
public class TerrainFacade extends AbstractFacade<Terrain> {

    @PersistenceContext(unitName = "taxeTNBPU")
    private EntityManager em;
    @EJB
    RedevableFacade redevableFacade;
    @EJB
    CategorieTerrainFacade categorieTerrainFacade;
    @EJB
    QuartierFacade quartierFacade;
    @EJB
    SecteurFacade secteurFacade;
    @EJB
    RueFacade rueFacade;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public TerrainFacade() {
        super(Terrain.class);
    }

    public List<Terrain> findByCinOrNif(String cin, String nif) {
        System.out.println("cin:" + cin);
        System.out.println("nif:" + nif);
        String req = "SELECT t FROM Terrain t WHERE 1=1";
        if (!cin.equals("")) {
            req += SearchUtil.addConstraint("t", "redevable.cin", "=", cin);
            System.out.println("im in req cin");
        } else if (!nif.equals("")) {
            req += SearchUtil.addConstraint("t", "redevable.nif", "=", nif);
            System.out.println("im in req nif");
        } else {
            return null;
        }
        List<Terrain> res = new ArrayList();
        System.out.println(req);
        res = em.createQuery(req).getResultList();
        System.out.println(res);
        return res;
    }

    public List<Terrain> findByRedevable(Redevable redevable) {
        System.out.println(redevable);
        if (!redevable.getCin().equals(""));
        return em.createQuery("SELECT t FROM  Terrain t WHERE t.redevable.id=" + redevable.getId()).getResultList();
    }

    public void creerTerrain(Terrain terrain) {
        long numerLot = generate("Terrain", "numeroLot");
        terrain.setNumeroLot(numerLot);
        terrain.setDateDeclaration(new Date());

        edit(terrain);

    }

    public List<Terrain> findByCriteria(BigDecimal surface, Long red, Long catTerrain) {

        String requete = "SELECT t FROM Terrain t WHERE 1=1";
        requete += SearchUtil.addConstraint("t", "surface", "=", surface);

        requete += SearchUtil.addConstraint("t", "redevable.id", "=", red);
        requete += SearchUtil.addConstraint("t", "categorieTerrain.id", "=", catTerrain);

        List<Terrain> ters = getEntityManager().createQuery(requete).getResultList();
        return ters;
    }

    public List<Terrain> findBySecteurAndQuartier(Long codePostal, Long idQuartier, Long catTerrain) {

        String requete = "SELECT t FROM Terrain t WHERE 1=1";

        requete += SearchUtil.addConstraint("t", "rue.quartier.id", "=", idQuartier);
        requete += SearchUtil.addConstraint("t", "rue.quartier.secteur.id", "=", codePostal);
        requete += SearchUtil.addConstraint("t", "categorieTerrain.id", "=", catTerrain);

        List<Terrain> ters = getEntityManager().createQuery(requete).getResultList();
        return ters;
    }

    public List<Terrain> findByLocalisation(Long idRue) {

        String requete = "SELECT t FROM Terrain t WHERE 1=1";
        requete += SearchUtil.addConstraint("t", "rue.id", "=", idRue);

        List<Terrain> ters = getEntityManager().createQuery(requete).getResultList();
        return ters;
    }

    public List<Terrain> findByDate(Date dateDeclaration, Date dateAchat, int dernierPaiement) {

        String requete = "SELECT t FROM Terrain t WHERE 1=1";
        requete += SearchUtil.addConstraintDate("t", "dateDeclaration", "=", dateDeclaration);

        requete += SearchUtil.addConstraintDate("t", "dateAchat", "=", dateAchat);
        if (dernierPaiement != 0) {
            requete += SearchUtil.addConstraint("t", "dernierPaiement.annee", "=", dernierPaiement);
        }
        List<Terrain> ters = getEntityManager().createQuery(requete).getResultList();
        return ters;
    }

    public int quitancer(long numeroLot) {
        int year = Calendar.getInstance().get(Calendar.YEAR);
        Terrain terrain = find(numeroLot);
        if (terrain == null) {
            return -2;
        }
        TaxeAnnuelle dernierPaiement = terrain.getDernierPaiement();
        if (dernierPaiement == null || dernierPaiement.getAnnee() == year) {
            return year;
        } else {
            return year - dernierPaiement.getAnnee();

        }
    }

//
    public int changerProprietaire(long numeroLot, long idAcheteur) {
        Redevable acheteur = redevableFacade.find(idAcheteur);

        int resultat = quitancer(numeroLot);
        if (resultat == Calendar.getInstance().get(Calendar.YEAR)) {
            Terrain terrain = find(numeroLot);
            Redevable vendeur = terrain.getRedevable();
            terrain.setRedevable(acheteur);
            terrain.setDateAchat(new Date());
            edit(terrain);
            return 1;
        } else {
            return -1;
        }

    }

    ///////////////////////////////////////
    ///////////:service Statistique//////////
    public List<Object[]> boucler(Long codePostal, Long idQuartier) {
        List<Object[]> NbsParCategorie = new ArrayList();

        for (long i = 1; i <= 4; i++) {
            List<Terrain> terrains = findBySecteurAndQuartier(codePostal, idQuartier, i);
            CategorieTerrain categorie = categorieTerrainFacade.find(i);
            NbsParCategorie.add(new Object[]{" '" + categorie.getNom() + "'", terrains.size()});
        }
        return NbsParCategorie;
    }

    public List<Object[]> CalculNbTerrainParCategorie(Long idQuartier) {
        List<Object[]> NbsParCategorie = boucler(null, idQuartier);
        return NbsParCategorie;
    }

    public List<Object[]> CalculNbTerrainParCategorieParSecteur(Long codePostal) {
        List<Object[]> NbsParCategorie = boucler(codePostal, null);
        return NbsParCategorie;
    }
}
