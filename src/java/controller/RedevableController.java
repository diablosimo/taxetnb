package controller;

import bean.Redevable;
import bean.TaxeAnnuelle;
import bean.Terrain;
import controller.util.HashageUtil;
import controller.util.JsfUtil;
import controller.util.JsfUtil.PersistAction;
import controller.util.SessionUtil;
import service.RedevableFacade;

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
import javax.faces.event.AjaxBehaviorEvent;
import javax.mail.MessagingException;
import javax.servlet.http.HttpSession;
import service.TaxeAnnuelleFacade;
import service.TerrainFacade;

@Named("redevableController")
@SessionScoped
public class RedevableController implements Serializable {

    @EJB
    private service.RedevableFacade ejbFacade;
    private List<Redevable> items = null;
    private Redevable selected;

    private List<Terrain> terrains;
    @EJB
    private TerrainFacade ejbTerr;
    private String login;
    private String oldPassword;
    private String newPassword;
    private String confirmation;
    private Object[] cnx = new Object[]{0, null};
    @EJB
    private TaxeAnnuelleFacade ejbTaxe;
    private List<TaxeAnnuelle> taxes;

    private List<Terrain> itemsSearch1 = null;
    private List<Redevable> redevables = null;
    private int typeRedevable;
    private String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getTypeRedevable() {
        return typeRedevable;
    }

    public void setTypeRedevable(int typeRedevable) {
        this.typeRedevable = typeRedevable;
    }

    public List<Redevable> getRedevables() {
        return redevables;
    }

    public void setRedevables(List<Redevable> redevables) {
        this.redevables = redevables;
    }

    public RedevableFacade getEjbFacade() {
        return ejbFacade;
    }

    public void setEjbFacade(RedevableFacade ejbFacade) {
        this.ejbFacade = ejbFacade;
    }

    public List<Terrain> getItemsSearch1() {
        return itemsSearch1;
    }

    public void setItemsSearch1(List<Terrain> itemsSearch1) {
        this.itemsSearch1 = itemsSearch1;
    }

    public void setAttributesToNull(final AjaxBehaviorEvent event ) {
      selected=null ;
    
    }
//    public String connexion() throws IOException {
//        int res = ejbFacade.seConnecter(selected);
//        if (res < 0) {
//            JsfUtil.addErrorMessage("mot de pqsse incorrect");
//            return null;
//        } else {
//            SessionUtil.registerRedevable(selected);
//            JsfUtil.addErrorMessage("Mot de passe correct");
//            return "List";
//        }
//    }
    public RedevableController() {
    }

    public Redevable getSelected() {
        if (selected == null) {
            selected = new Redevable();
        }
        return selected;
    }

    public void setSelected(Redevable selected) {
        this.selected = selected;
    }

    protected void setEmbeddableKeys() {
    }

    protected void initializeEmbeddableKey() {
    }

    private RedevableFacade getFacade() {
        return ejbFacade;
    }

    public void findTersByIdSecondaire1() {
        itemsSearch1 = ejbFacade.findTersByIdSecondaire1(selected.getCin());
        selected.setNif(null);
    }

    public void findTersByIdSecondaire2() {
        
        itemsSearch1 = ejbFacade.findTersByIdSecondaire2(selected.getNif());
        selected.setCin(null);
    }

    public void findByNomOrPrenom() {
        redevables = ejbFacade.findByNomOrPrenom(selected.getNom(), selected.getPrenom());
        itemsSearch1 = null;
    }

    public List<Terrain> giveTerrains() {
        terrains = ejbFacade.findTersByIdSecondaire1(selected.getCin());
        return terrains;
    }

    public Redevable prepareCreate() {
        selected = new Redevable();
        initializeEmbeddableKey();
        return selected;
    }

    public void create() throws MessagingException {
        if (ejbFacade.constraintOnAttributs(selected) == false) {
            message = " l'identifiant de ce redevable deja existe";
        } else if (ejbFacade.constraintOnAttributs(selected) == true) {
            ejbFacade.creerRedevevable(selected);

            persist(PersistAction.CREATE, ResourceBundle.getBundle("/Bundle").getString("RedevableCreated"));
            message = "le redevable a ete cree";
            
            if (!JsfUtil.isValidationFailed()) {
                items = null;    // Invalidate list of items to trigger re-query.
            }

        } else {
            message = " creation echoué";
        }
        selected=null ;
    }

    public void update() {
        persist(PersistAction.UPDATE, ResourceBundle.getBundle("/Bundle").getString("RedevableUpdated"));
    }

    public void destroy() {
        persist(PersistAction.DELETE, ResourceBundle.getBundle("/Bundle").getString("RedevableDeleted"));
        if (!JsfUtil.isValidationFailed()) {
            selected = null; // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public List<Redevable> getItems() {
        if (items == null) {
            items = getFacade().findAll();
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

    public Redevable getRedevable(java.lang.Long id) {
        return getFacade().find(id);
    }

    public List<Redevable> getItemsAvailableSelectMany() {
        return getFacade().findAll();
    }

    public List<Redevable> getItemsAvailableSelectOne() {
        return getFacade().findAll();
    }

    @FacesConverter(forClass = Redevable.class)
    public static class RedevableControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            RedevableController controller = (RedevableController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "redevableController");
            return controller.getRedevable(getKey(value));
        }

        java.lang.Long getKey(String value) {
            java.lang.Long key;
            key = Long.valueOf(value);
            return key;
        }

        String getStringKey(java.lang.Long value) {
            StringBuilder sb = new StringBuilder();
            sb.append(value);
            return sb.toString();
        }

        @Override
        public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
            if (object == null) {
                return null;
            }
            if (object instanceof Redevable) {
                Redevable o = (Redevable) object;
                return getStringKey(o.getId());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), Redevable.class.getName()});
                return null;
            }
        }

    }
    
    public TaxeAnnuelleFacade getEjbTaxe() {
        return ejbTaxe;
    }

    public void detail(Terrain terrain) {
        taxes = ejbTaxe.findByTerrain(terrain);
    }

    public void setEjbTaxe(TaxeAnnuelleFacade ejbTaxe) {
        this.ejbTaxe = ejbTaxe;
    }

    public List<TaxeAnnuelle> getTaxes() {
        if (taxes == null) {
            taxes = new ArrayList();
        }
        return taxes;
    }

    public void setTaxes(List<TaxeAnnuelle> taxes) {
        this.taxes = taxes;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public String getConfirmation() {
        return confirmation;
    }

    public void setConfirmation(String confirmation) {
        this.confirmation = confirmation;
    }
public void sedeconnecte(){
        HttpSession session= SessionUtil.getSession();
        session.invalidate();
    }
    
//    public void findTerrain(){
//        
//       
//        terrains=ejbTerr.findByRedevable(SessionUtil.getConnectedRedevable() );
//    }

   

    public List<Terrain> getTerrains() {
       if (terrains == null) {
            terrains = ejbTerr.findByRedevable(SessionUtil.getConnectedRedevable());

        }
        return terrains;
    }

    public void setTerrains(List<Terrain> terrains) {
        this.terrains = terrains;
    }

    public TerrainFacade getEjbTerr() {
        return ejbTerr;
    }

    public void setEjbTerr(TerrainFacade ejbTerr) {
        this.ejbTerr = ejbTerr;
    }

    public Object[] getCnx() {
        return cnx;
    }

    public void setCnx(Object[] cnx) {
        this.cnx = cnx;
    }

    

    public String connexion() {
        cnx = ejbFacade.seConnecter(selected);
        System.out.println("ha res=" + cnx[1]);
        if ((int) cnx[0] < 0) {
            JsfUtil.addErrorMessage("mot de pqsse incorrect");

            return null;
        } else {
            SessionUtil.registerRedevable((Redevable) cnx[1]);
            System.out.println("ha seesion" + SessionUtil.getConnectedRedevable());
            JsfUtil.addErrorMessage("Mot de passe correct");
            return "ListTerrain";
        }

    }

    public String deconnecte() {
        HttpSession session = SessionUtil.getSession();
        session.invalidate();
        selected = null;
        return "Connexion";
    }

    public String renitialiser() {

        ejbFacade.modifierMd(login, oldPassword, newPassword, confirmation);
        return "List";

    }

}
