package controller;

import bean.Quartier;
import bean.Rue;
import bean.Secteur;
import controller.util.JsfUtil;
import controller.util.JsfUtil.PersistAction;
import service.SecteurFacade;

import java.io.Serializable;
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

@Named("secteurController")
@SessionScoped
public class SecteurController implements Serializable {

    @EJB
    private service.SecteurFacade ejbFacade;
    private List<Secteur> items = null;
    private Secteur selected;
    private Quartier quartier;
    private List<Quartier> quartierItems = null;
    private List<Rue> ruesItems = null;
     private Rue rue;

    public Rue getRue() {
        return rue;
    }

    public void setRue(Rue rue) {
        this.rue = rue;
    }

    public void findQuartiersBySecteur(final AjaxBehaviorEvent event ) {

        quartierItems = ejbFacade.findQuartiersBySecteur(selected.getCodePostal());
       
    }
 public void findRuesBySecteur(final AjaxBehaviorEvent event ) {

       ruesItems = ejbFacade.findRuesByQuartier(quartier.getId());
       
    }
 public void setRuesItemsToNull(final AjaxBehaviorEvent event ) {

       ruesItems = null;
       
    }
 
    public Quartier getQuartier() {
        return quartier;
    }

    public void setQuartier(Quartier quartier) {
        this.quartier = quartier;
    }

    public SecteurFacade getEjbFacade() {
        return ejbFacade;
    }

    public void setEjbFacade(SecteurFacade ejbFacade) {
        this.ejbFacade = ejbFacade;
    }

    public List<Quartier> getQuartierItems() {
        
        return quartierItems;
    }

    public void setQuartierItems(List<Quartier> quartierItems) {
        this.quartierItems = quartierItems;
    }

    public List<Rue> getRuesItems() {
        return ruesItems;
    }

    public void setRuesItems(List<Rue> ruesItems) {
        this.ruesItems = ruesItems;
    }

   

    public SecteurController() {
    }

    public Secteur getSelected() {
        if(selected==null){
            selected=new Secteur();
        }
        return selected;
    }

    public void setSelected(Secteur selected) {
        this.selected = selected;
    }

    protected void setEmbeddableKeys() {
    }

    protected void initializeEmbeddableKey() {
    }

    private SecteurFacade getFacade() {
        return ejbFacade;
    }

    public Secteur prepareCreate() {
        selected = new Secteur();
        initializeEmbeddableKey();
        return selected;
    }

    public void create() {
        persist(PersistAction.CREATE, ResourceBundle.getBundle("/Bundle").getString("SecteurCreated"));
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public void update() {
        persist(PersistAction.UPDATE, ResourceBundle.getBundle("/Bundle").getString("SecteurUpdated"));
    }

    public void destroy() {
        persist(PersistAction.DELETE, ResourceBundle.getBundle("/Bundle").getString("SecteurDeleted"));
        if (!JsfUtil.isValidationFailed()) {
            selected = null; // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public List<Secteur> getItems() {
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

    public Secteur getSecteur(java.lang.Long id) {
        return getFacade().find(id);
    }

    public List<Secteur> getItemsAvailableSelectMany() {
        return getFacade().findAll();
    }

    public List<Secteur> getItemsAvailableSelectOne() {
        return getFacade().findAll();
    }

    @FacesConverter(forClass = Secteur.class)
    public static class SecteurControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            SecteurController controller = (SecteurController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "secteurController");
            return controller.getSecteur(getKey(value));
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
            if (object instanceof Secteur) {
                Secteur o = (Secteur) object;
                return getStringKey(o.getCodePostal());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), Secteur.class.getName()});
                return null;
            }
        }

    }

}
