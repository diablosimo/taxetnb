/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import bean.Quartier;
import bean.Rue;
import bean.Secteur;
import controller.util.SearchUtil;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author simob
 */
@Stateless
public class SecteurFacade extends AbstractFacade<Secteur> {

    @PersistenceContext(unitName = "taxeTNBPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public SecteurFacade() {
        super(Secteur.class);
    }

    public List<Quartier> findQuartiersBySecteur(Long codePostal) {
        String requete = "SELECT q FROM Quartier q WHERE 1=1";
        requete += SearchUtil.addConstraint("q", "secteur.codePostal", "=", codePostal);
        List<Quartier> quars = getEntityManager().createQuery(requete).getResultList();
        return quars;

    }

    public List<Rue> findRuesBySecteur(long codePostal) {
        List<Quartier> quars = findQuartiersBySecteur(codePostal);
        List<Rue> rues = new ArrayList();
        for (int i = 0; i < quars.size(); i++) {
            Quartier get = quars.get(i);
            List<Rue> rues2 = findRuesByQuartier(get.getId());
            if (rues2 != null) {
                rues.addAll(rues2);
            }
        }
        return rues;
    }

    public List<Rue> findRuesByQuartier(long idQuartier) {
        String requete = "SELECT r FROM Rue r WHERE 1=1";
        requete += SearchUtil.addConstraint("r", "quartier.id", "=", idQuartier);

        List<Rue> rues = getEntityManager().createQuery(requete).getResultList();

        return rues;
    }
}
