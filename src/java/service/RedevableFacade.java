/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import bean.Redevable;
import bean.Terrain;
import controller.util.HashageUtil;
import controller.util.MailUtil;
import controller.util.SearchUtil;
import java.util.List;
import javax.ejb.Stateless;
import javax.mail.MessagingException;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author simob
 */
@Stateless
public class RedevableFacade extends AbstractFacade<Redevable> {

    @PersistenceContext(unitName = "taxeTNBPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public RedevableFacade() {
        super(Redevable.class);
    }

    public Redevable findRedevable(Redevable redevable) {
        return (Redevable) em.createQuery("SELECT r FROM Redevable r WHERE r.cin='" + redevable.getCin() + "'").getSingleResult();
    }

    public Redevable findByEmail(String login) {
        return (Redevable) em.createQuery("SELECT r FROM Redevable r WHERE r.email='" + login + "'").getSingleResult();
    }

    public Object[] seConnecter(Redevable redevable) {
        if (redevable == null || redevable.getCin() == null) {
            //System.out.println("saisir votre mot de passe");
            return new Object[]{-3, null};
        }
        redevable = findRedevable(redevable);
        System.out.println("ha l redevable" + redevable);
        if (redevable == null) {
            return new Object[]{-1, null};
        } else if (!redevable.getMotDePasse().equals(redevable.getMotDePasse())) {
            return new Object[]{-2, null};
        } else {
            return new Object[]{1, redevable};
        }
    }

    public int modifierMd(String login, String oldPassword, String newPassword, String confirmation) {
        Redevable loadRedevable = findByEmail(login);
        if (!confirmation.equals(newPassword)) {
            return -1;
        } else if (!loadRedevable.getMotDePasse().equals(oldPassword)) {
            return -2;
        } else if (oldPassword.equals(newPassword)) {
            return -3;
        }
        loadRedevable.setMotDePasse(newPassword);
        edit(loadRedevable);
        return 1;
    }

    public Redevable findByIdSecondaire(String cin, String nif) {
        String requete = "SELECT r FROM Redevable r WHERE 1=1";
        requete += SearchUtil.addConstraint("r", "cin", "=", cin);
        requete += SearchUtil.addConstraint("r", "nif", "=", nif);
        Redevable red = getUniqueResult(requete);
        return red;
    }

    public List<Redevable> findByNomOrPrenom(String nom, String prenom) {
        String requete = "SELECT r FROM Redevable r WHERE 1=1";
        requete += SearchUtil.addConstraint("r", "nom", "=", nom);
        requete += SearchUtil.addConstraint("r", "prenom", "=", prenom);

        List<Redevable> redevables = getEntityManager().createQuery(requete).getResultList();
        return redevables;
    }

    public List<Terrain> findTersByIdSecondaire1(String cin) {
        String requete = "SELECT t FROM Terrain t WHERE 1=1";
        requete += SearchUtil.addConstraint("t", "redevable.cin", "=", cin);
        List<Terrain> ters = getEntityManager().createQuery(requete).getResultList();
        return ters;
    }

    public List<Terrain> findTersByIdSecondaire2(String nif) {
        String requete = "SELECT t FROM Terrain t WHERE 1=1";
        requete += SearchUtil.addConstraint("t", "redevable.nif", "=", nif);

        List<Terrain> ters = getEntityManager().createQuery(requete).getResultList();
        return ters;
    }

    public List<Terrain> findTersByRedevable(Redevable redevable) {
        String requete = "SELECT t FROM Terrain t WHERE 1=1";
        requete += SearchUtil.addConstraint("t", "redevable.id", "=", redevable.getId());
        List<Terrain> ters = getEntityManager().createQuery(requete).getResultList();
        return ters;
    }

    public boolean constraintOnAttributs(Redevable redevable) {
        Redevable searched = findByIdSecondaire(redevable.getCin(), redevable.getNif());
        return searched == null;
    }

    public boolean creerRedevevable(Redevable redevable) throws MessagingException {
        long id = generate("Redevable", "id");
        redevable.setId(id);
        String mdp = HashageUtil.generate();
        redevable.setMotDePasse(HashageUtil.sha256(mdp));
        edit(redevable);
        boolean result = MailUtil.sendMail(mdp, redevable.getEmail(), "Envoi email");
        return result;
    }

}
