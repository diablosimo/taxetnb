/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import bean.Secteur;
import bean.Utilisateur;
import controller.util.EmailUtil;
import controller.util.HashageUtil;
import controller.util.RandomStringUtil;
import controller.util.SearchUtil;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.mail.MessagingException;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author simob
 */
@Stateless
public class UtilisateurFacade extends AbstractFacade<Utilisateur> {

    @PersistenceContext(unitName = "taxeTNBPU")
    private EntityManager em;
@EJB
private ConnexionLogFacade connexionLogFacade;
    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public UtilisateurFacade() {
        super(Utilisateur.class);
    }

    public int cree(Utilisateur utilisateur) throws MessagingException {
        //connectUtil houwa liu crea utilisateur on le recupere par session
        if (utilisateur == null) {
            return -1;
        } else if (utilisateur.getMatricule() == null || utilisateur.getMatricule().equals("")) {
            return -2;
        } else if (utilisateur.getEmail() == null || utilisateur.getEmail().equals("")) {
            return -4;
        } else if (utilisateur.getNom() == null || utilisateur.getNom().equals("")) {
            return -4;
        } else if (utilisateur.getPrenom() == null || utilisateur.getPrenom().equals("")) {
            return -5;
        } else if (utilisateur.getSecteur().getNom() == null || utilisateur.getSecteur().getNom().equals("")) {
            return -6;
        } else {

            String mdp = RandomStringUtil.generate();
            utilisateur.setMotDepasse(HashageUtil.sha256(mdp));
            create(utilisateur);
            EmailUtil.sendMail("taxe.tnb@gmail.com", "taxe@TNB2018", "Voici votre mot de passe:" + mdp, utilisateur.getEmail(), "mot de passe TNB-GIR", null);
            return 1;
        }
    }

    public int modifierMotDePasse(Utilisateur utilisateur) throws MessagingException {
        String mdp = RandomStringUtil.generate();
        utilisateur.setMotDepasse(HashageUtil.sha256(mdp));
        create(utilisateur);
        EmailUtil.sendMail("taxe.tnb@gmail.com", "taxe@TNB2018", "Voici votre mot de passe:" + mdp, utilisateur.getEmail(), "mot de passe TNB-GIR", null);
        return 1;
    }

    public Utilisateur findUtilisateur(Utilisateur utilisateur) {
        return (Utilisateur) em.createQuery("SELECT u FROM Utilisateur u WHERE u.matricule ='" + utilisateur.getMatricule() + "'").getSingleResult();
    }

    public int removeByRib(String matricule) {
        return em.createQuery("DELETE FROM Utilisateur u WHERE u.matricule ='" + matricule + "'").executeUpdate();

    }

    public int seConnecter(Utilisateur utilisateur) {
        Utilisateur loeadUser = findUtilisateur(utilisateur);
        if (loeadUser == null) {
            return -1;
        } else if (!loeadUser.getMotDepasse().equals(utilisateur.getMotDepasse())) {
            return -2;
        } else {
            return 1;

        }
    }

    public List<Utilisateur> findByCritaria(String nom, String prenom, String matricule, Secteur secteur, int type) {
        String requete = "SELECT u FROM Utilisateur u WHERE 1=1";
        if (nom != null) {
            if (!nom.equals("")) {
                requete += SearchUtil.addConstraint("u", "nom", "=", nom);
            }
        }
        if (prenom != null) {
            if (!prenom.equals("")) {
                requete += SearchUtil.addConstraint("u", "prenom", "=", prenom);
            }
        }
        if (matricule != null) {
            if (!matricule.equals("")) {
                requete += SearchUtil.addConstraint("u", "matricule", "=", matricule);
            }
        }
        if (type != 0) {
            requete += SearchUtil.addConstraint("u", "type", "=", type);
        }
        if (secteur != null) {
            requete += SearchUtil.addConstraint("u", "secteur.nom", "=", secteur.getNom());
        }

        return em.createQuery(requete).getResultList();
    }

    public int verificationExistance(Utilisateur utilisateur) {
        Utilisateur loeadUser = findUtilisateur(utilisateur);
        if (loeadUser.getMatricule().equals(utilisateur.getMatricule())) {
            return -1;

        } else {
            return 1;

        }
    }

    public int modifierUtilisateur(Utilisateur utilisateur) {

        Utilisateur u = find(utilisateur);
        if (u == null) {
            return -1;
        } else {

            u.setMotDepasse(utilisateur.getMotDepasse());
            u.setNom(utilisateur.getNom());
            u.setPrenom(utilisateur.getPrenom());
            u.getSecteur().setNom(utilisateur.getSecteur().getNom());
            edit(u);
            //Historique historique = new Historique(1, "-", utilisateur.toString(), "Utilisateur");
            // historique.setActionDate(new Date());
            // historique.setUtilisateur(connectUtil);
            // historiqueFacade.create(historique);
            return 1;
        }

    }

    public Utilisateur findByEmail(String email) {
        System.out.println("debut findbyemail:" + email);
        Utilisateur loaded = (Utilisateur) em.createQuery("SELECT u FROM Utilisateur u WHERE u.email='" + email + "'").getSingleResult();
        System.out.println("fin" + loaded);
        return loaded;
    }

    public Object[] Connecter(Utilisateur utilisateur, int type) {
        if (utilisateur == null || utilisateur.getEmail() == null) {
            return new Object[]{-3, null};
        }
        Utilisateur loadedUser = findByEmail(utilisateur.getEmail());
        System.out.println("hahowa utilisateur l" + loadedUser);
        System.out.println("hahowa utilisateur u" + utilisateur);
        if (loadedUser == null) {
            return new Object[]{-1, null};
        } else if (!loadedUser.getMotDepasse().equals(utilisateur.getMotDepasse())) {
            return new Object[]{-2, null};
        } else if (loadedUser.getType() != type) {
            System.out.println("hahowa le type " + loadedUser.getType());
            return new Object[]{-4, null};
        } else {
            loadedUser = clone(loadedUser);
            loadedUser.setMotDepasse(null);
            connexionLogFacade.createHistoryLog(loadedUser, 1);
            return new Object[]{1, loadedUser};
        }
    }

    public void clone(Utilisateur utilisateurSource, Utilisateur utilisateurDestination) {
        utilisateurDestination.setMatricule(utilisateurSource.getMatricule());
        utilisateurDestination.setNom(utilisateurSource.getNom());
        utilisateurDestination.setPrenom(utilisateurSource.getPrenom());
        utilisateurDestination.setType(utilisateurSource.getType());
        utilisateurDestination.setEmail(utilisateurSource.getEmail());
        utilisateurDestination.setSecteur(utilisateurSource.getSecteur());
        utilisateurDestination.setMotDepasse(utilisateurSource.getMotDepasse());
    }

    public Utilisateur clone(Utilisateur utilisateur) {
        Utilisateur cloned = new Utilisateur();
        clone(utilisateur, cloned);
        return cloned;
    }
}
