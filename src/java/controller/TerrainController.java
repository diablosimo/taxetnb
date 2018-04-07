package controller;

import bean.Terrain;
import controller.util.JsfUtil;
import controller.util.JsfUtil.PersistAction;
import service.TerrainFacade;

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

@Named("terrainController")
@SessionScoped
public class TerrainController implements Serializable {

    @EJB
    private service.TerrainFacade ejbFacade;
    private List<Terrain> items;
    private Terrain selected;
    private int x=0;

    public void testQ(){
        items=ejbFacade.testQ();
    }
    
    public void quitancer(){
        x=ejbFacade.quitancer(selected.getNumeroLot());
    }
    
    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }
    
    public TerrainController() {
    }

    public Terrain getSelected() {
      if (selected == null) {
            selected = new Terrain();
        }
        return selected;
    }

    public void setSelected(Terrain selected) {
        this.selected = selected;
    }

    protected void setEmbeddableKeys() {
    }

    protected void initializeEmbeddableKey() {
    }

    private TerrainFacade getFacade() {
        return ejbFacade;
    }

    public Terrain prepareCreate() {
        selected = new Terrain();
        initializeEmbeddableKey();
        return selected;
    }

    public void create() {
        persist(PersistAction.CREATE, ResourceBundle.getBundle("/Bundle").getString("TerrainCreated"));
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public void update() {
        persist(PersistAction.UPDATE, ResourceBundle.getBundle("/Bundle").getString("TerrainUpdated"));
    }

    public void destroy() {
        persist(PersistAction.DELETE, ResourceBundle.getBundle("/Bundle").getString("TerrainDeleted"));
        if (!JsfUtil.isValidationFailed()) {
            selected = null; // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public List<Terrain> getItems() {
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

    public Terrain getTerrain(java.lang.Long id) {
        return getFacade().find(id);
    }

    public List<Terrain> getItemsAvailableSelectMany() {
        return getFacade().findAll();
    }

    public List<Terrain> getItemsAvailableSelectOne() {
        return getFacade().findAll();
    }

    @FacesConverter(forClass = Terrain.class)
    public static class TerrainControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            TerrainController controller = (TerrainController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "terrainController");
            return controller.getTerrain(getKey(value));
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
            if (object instanceof Terrain) {
                Terrain o = (Terrain) object;
                return getStringKey(o.getNumeroLot());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), Terrain.class.getName()});
                return null;
            }
        }

    }

}
