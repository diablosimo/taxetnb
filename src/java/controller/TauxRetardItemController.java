package controller;

import bean.TauxRetardItem;
import controller.util.JsfUtil;
import controller.util.JsfUtil.PersistAction;
import service.TauxRetardItemFacade;

import java.io.Serializable;
import java.util.Date;
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
import service.TauxRetardFacade;

@Named("tauxRetardItemController")
@SessionScoped
public class TauxRetardItemController implements Serializable {

    @EJB
    private service.TauxRetardItemFacade ejbFacade;
    private List<TauxRetardItem> items = null;
    private TauxRetardItem selected;
    private Double premierMin;
    private Double premierMax;
    private Double autreMin;
    private Double autreMax;
   private Date dateApplication;
   @EJB
   private TauxRetardFacade tauxRetardFacade;
   
    public TauxRetardItemController() {
    }
    
  public String cree(){
        tauxRetardFacade.ajouter(items,dateApplication);
        return "/tauTaxe/newjsf1";
    }
   public void fillInList(){
       items=ejbFacade.Add(selected,items);
   }
    
public void findByMaxMin(){
   items=ejbFacade.findByCrit(premierMin, premierMax,autreMin, autreMax);
}

    public TauxRetardItemFacade getEjbFacade() {
        return ejbFacade;
    }

    public void setEjbFacade(TauxRetardItemFacade ejbFacade) {
        this.ejbFacade = ejbFacade;
    }

    public Date getDateApplication() {
        if(dateApplication==null){
            dateApplication=new Date();
        }
        return dateApplication;
    }

    public void setDateApplication(Date dateApplication) {
        this.dateApplication = dateApplication;
    }

    public TauxRetardItem getSelected() {
        if(selected==null)
            selected=new TauxRetardItem();
        return selected;
    }

    public Double getPremierMin() {
        return premierMin;
    }

    public void setPremierMin(Double premierMin) {
        this.premierMin = premierMin;
    }

    public Double getPremierMax() {
        return premierMax;
    }

    public void setPremierMax(Double premierMax) {
        this.premierMax = premierMax;
    }

    public Double getAutreMin() {
        return autreMin;
    }

    public void setAutreMin(Double autreMin) {
        this.autreMin = autreMin;
    }

    public Double getAutreMax() {
        return autreMax;
    }

    public void setAutreMax(Double autreMax) {
        this.autreMax = autreMax;
    }

    public void setSelected(TauxRetardItem selected) {
        this.selected = selected;
    }

    protected void setEmbeddableKeys() {
    }

    protected void initializeEmbeddableKey() {
    }

    private TauxRetardItemFacade getFacade() {
        return ejbFacade;
    }

    public TauxRetardItem prepareCreate() {
        selected = new TauxRetardItem();
        initializeEmbeddableKey();
        return selected;
    }

    public void create() {
        persist(PersistAction.CREATE, ResourceBundle.getBundle("/Bundle").getString("TauxRetardItemCreated"));
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public void update() {
        persist(PersistAction.UPDATE, ResourceBundle.getBundle("/Bundle").getString("TauxRetardItemUpdated"));
    }

    public void destroy() {
        persist(PersistAction.DELETE, ResourceBundle.getBundle("/Bundle").getString("TauxRetardItemDeleted"));
        if (!JsfUtil.isValidationFailed()) {
            selected = null; // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public List<TauxRetardItem> getItems() {
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

    public TauxRetardItem getTauxRetardItem(java.lang.Long id) {
        return getFacade().find(id);
    }

    public List<TauxRetardItem> getItemsAvailableSelectMany() {
        return getFacade().findAll();
    }

    public List<TauxRetardItem> getItemsAvailableSelectOne() {
        return getFacade().findAll();
    }

    @FacesConverter(forClass = TauxRetardItem.class)
    public static class TauxRetardItemControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            TauxRetardItemController controller = (TauxRetardItemController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "tauxRetardItemController");
            return controller.getTauxRetardItem(getKey(value));
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
            if (object instanceof TauxRetardItem) {
                TauxRetardItem o = (TauxRetardItem) object;
                return getStringKey(o.getId());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), TauxRetardItem.class.getName()});
                return null;
            }
        }

    }

}
