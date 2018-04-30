package controller;

import bean.Secteur;
import bean.Utilisateur;
import controller.util.JsfUtil;
import controller.util.JsfUtil.PersistAction;
import controller.util.SessionUtil;
import service.UtilisateurFacade;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.mail.MessagingException;
import javax.servlet.http.HttpSession;

@Named("utilisateurController")
@SessionScoped
public class UtilisateurController implements Serializable {

    @EJB
    private service.ConnexionLogFacade connexionLogFacade;
    @EJB
    private service.UtilisateurFacade ejbFacade;
    private List<Utilisateur> items = null;
    private Utilisateur selected;
    private Utilisateur utilisateur;
    private Utilisateur connectedUser;
    private Object[] cnx = new Object[]{0, null};
    

    private int type;
    private String nom;
    private String prenom;
    private String matricule;
    private Secteur secteur;

    public UtilisateurController() {
    }

    public Utilisateur getConnectedUser() {
        if(connectedUser==null)
            connectedUser=SessionUtil.getConnectedUser();
        return connectedUser;
    }

    public void setConnectedUser(Utilisateur connectedUser) {
        this.connectedUser = connectedUser;
    }

    
    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public Utilisateur getSelected() {
        if (selected == null) {
            selected = new Utilisateur();
        }
        return selected;
    }

    public void setSelected(Utilisateur selected) {
        this.selected = selected;
    }

    public UtilisateurFacade getEjbFacade() {
        return ejbFacade;
    }

    public void setEjbFacade(UtilisateurFacade ejbFacade) {
        this.ejbFacade = ejbFacade;
    }

    public Utilisateur getUtilisateur() {
        return utilisateur;
    }

    public void setUtilisateur(Utilisateur utilisateur) {
        this.utilisateur = utilisateur;
    }

    public Object[] getCnx() {
        return cnx;
    }

    public void setCnx(Object[] cnx) {
        this.cnx = cnx;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getMatricule() {
        return matricule;
    }

    public void setMatricule(String matricule) {
        this.matricule = matricule;
    }

    public Secteur getSecteur() {
        return secteur;
    }

    public void setSecteur(Secteur secteur) {
        this.secteur = secteur;
    }

    public String modifier() {
        ejbFacade.edit(selected);
        return "RechercheUser";
    }

    public String modifieItem(Utilisateur utilisateur) {
        selected = utilisateur;
        return "ModificatonUser";
    }

    public String sedeconnecte() {
        HttpSession session = SessionUtil.getSession();
                connexionLogFacade.createHistoryLog(getConnectedUser(), 2);
        session.invalidate();
        return "faces/start.xhtml";
    }

    public void search() {
        items = ejbFacade.findByCritaria(nom, prenom, matricule, secteur, type);
    }

    public String connexion() {
        cnx = ejbFacade.Connecter(selected, 3);
        if ((int) cnx[0] < 0) {
            return null;
        } else {
            SessionUtil.registerUser((Utilisateur) cnx[1]);
            return "AccueilUser";
        }
    }

    public String connexionAdmin() {
        cnx = ejbFacade.Connecter(selected, 2);
        System.out.println("ha res=" + cnx[1]);
        if ((int) cnx[0] < 0) {
            JsfUtil.addErrorMessage("Mot de passe incorrect");
            return null;
        } else {
            SessionUtil.registerUser((Utilisateur) cnx[1]);
            JsfUtil.addSuccessMessage("Mot de passe correct");
            return "AccueilAdmin";
        }
    }
    
    public String connexionSuperAdmin() {
        cnx = ejbFacade.Connecter(selected, 1);
        System.out.println("ha res=" + cnx[1]);
        if ((int) cnx[0] < 0) {
            JsfUtil.addErrorMessage("Mot de passe incorrect");
            return null;
        } else {
            SessionUtil.registerUser((Utilisateur) cnx[1]);
            JsfUtil.addSuccessMessage("Mot de passe correct");
            return "AccueilSuperAdmin";
        }
    }

    public void affiche() {
        JsfUtil.addSuccessMessage("Bonjour" + SessionUtil.getConnectedUser().getNom());
    }

    public void remove(Utilisateur item) {
        ejbFacade.removeByRib(item.getMatricule());
        items.remove(items.indexOf(item));
    }

    public String creation() throws MessagingException {
        ejbFacade.cree(selected);
        selected = null;
        return "AccueilSuperAdmin";
    }

    protected void setEmbeddableKeys() {
    }

    protected void initializeEmbeddableKey() {
    }

    private UtilisateurFacade getFacade() {
        return ejbFacade;
    }

    public Utilisateur prepareCreate() {
        selected = new Utilisateur();
        initializeEmbeddableKey();
        return selected;
    }

    public void create() {
        persist(PersistAction.CREATE, ResourceBundle.getBundle("/Bundle").getString("UtilisateurCreated"));
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public void update() {
        persist(PersistAction.UPDATE, ResourceBundle.getBundle("/Bundle").getString("UtilisateurUpdated"));
    }

    public void destroy() {
        persist(PersistAction.DELETE, ResourceBundle.getBundle("/Bundle").getString("UtilisateurDeleted"));
        if (!JsfUtil.isValidationFailed()) {
            selected = null; // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public List<Utilisateur> getItems() {
        if (items == null) {
            items = new ArrayList();
        }
        return items;
    }

    private void persist(PersistAction persistAction, String successMessage) {
        if (selected != null) {
            setEmbeddableKeys();
            try {
                if (persistAction != PersistAction.DELETE) {
                    getFacade().edit(selected);
                } else {
                    getFacade().remove(selected);
                }
                JsfUtil.addSuccessMessage(successMessage);
            } catch (EJBException ex) {
                String msg = "";
                Throwable cause = ex.getCause();
                if (cause != null) {
                    msg = cause.getLocalizedMessage();
                }
                if (msg.length() > 0) {
                    JsfUtil.addErrorMessage(msg);
                } else {
                    JsfUtil.addErrorMessage(ex, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
                }
            } catch (Exception ex) {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
                JsfUtil.addErrorMessage(ex, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            }
        }
    }

    public Utilisateur getUtilisateur(java.lang.String id) {
        return getFacade().find(id);
    }

    public List<Utilisateur> getItemsAvailableSelectMany() {
        return getFacade().findAll();
    }

    public List<Utilisateur> getItemsAvailableSelectOne() {
        return getFacade().findAll();
    }

    @FacesConverter(forClass = Utilisateur.class)
    public static class UtilisateurControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            UtilisateurController controller = (UtilisateurController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "utilisateurController");
            return controller.getUtilisateur(getKey(value));
        }

        java.lang.String getKey(String value) {
            java.lang.String key;
            key = value;
            return key;
        }

        String getStringKey(java.lang.String value) {
            StringBuilder sb = new StringBuilder();
            sb.append(value);
            return sb.toString();
        }

        @Override
        public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
            if (object == null) {
                return null;
            }
            if (object instanceof Utilisateur) {
                Utilisateur o = (Utilisateur) object;
                return getStringKey(o.getMatricule());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), Utilisateur.class.getName()});
                return null;
            }
        }

    }

}
