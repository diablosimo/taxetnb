package controller;

import bean.Redevable;
import bean.TaxeAnnuelle;
import controller.util.JsfUtil;
import controller.util.JsfUtil.PersistAction;
import service.TaxeAnnuelleFacade;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import service.TerrainFacade;

@Named("taxeAnnuelleController")
@SessionScoped
public class TaxeAnnuelleController implements Serializable {

    @EJB
    private service.TaxeAnnuelleFacade ejbFacade;
    private List<TaxeAnnuelle> items = null;
    private TaxeAnnuelle selected;

    private int typeRedevable = 0;
    private String cin = "";
    private String nif = "";
    private Long numLot;
    private BigDecimal montantMin;
    private BigDecimal montantMax;
    private int annee;
    private int anneeMin;
    private int anneeMax;
    private Redevable redevable;
    private Date datePresentationMin;
    private Date datePresentationMax;

    @EJB
    private service.TerrainFacade terrainFacade;
    private Object[] myObjects = new Object[]{1, null};
    private Boolean simuler = true;

    public void verify() {
        myObjects = ejbFacade.verifyAndCreate(selected, selected.getAnnee());
        selected = (TaxeAnnuelle) myObjects[1];
        if (myObjects[0] == (Object) 1) {
            execute("Paiement Autorisé", "");
        }
        //System.out.println(selected);
    }

    public void calcul() {
        if (myObjects[0] == (Object) 1) {
            selected = ejbFacade.calcul(selected);
        }
    }

    public void payer() {
        if (myObjects[0] == (Object) 1) {
            System.out.println("ha categorie 9bel mn return 1 " + selected.getTerrain().getCategorieTerrain());
            selected = ejbFacade.create(selected, simuler);
        }
    }

    public void findForClient(){
      items=ejbFacade.findForClient(redevable, anneeMin, anneeMax, numLot);
        System.out.println(items);
    }

    public void findByTerrain() {
        System.out.println(selected.getTerrain());
        items = ejbFacade.findByTerrain(selected.getTerrain());
    }

    public void findByCriteria() {
        System.out.println("111111111111111");
        items = ejbFacade.findByCriteria(selected.getAnnee(), montantMin, montantMax, selected.getTerrain().getNumeroLot(), selected.getDatePresentaion());
        //return "List";
    }

    public void findByAllCriteria() {
        items = ejbFacade.findByCriteria(datePresentationMin, datePresentationMax, annee, montantMin, montantMax, numLot, cin, nif);
        JsfUtil.addSuccessMessage("hello");
        System.out.println("successfoul" + datePresentationMin);
        System.out.println(annee);
    }

    public void execute(String title, String message) {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, title, message));
    }

    public int getAnneeMin() {
        return anneeMin;
    }

    public void setAnneeMin(int anneeMin) {
        this.anneeMin = anneeMin;
    }

    public int getAnneeMax() {
        return anneeMax;
    }

    public void setAnneeMax(int anneeMax) {
        this.anneeMax = anneeMax;
    }

    public Redevable getRedevable() {
        if (redevable == null) {
            redevable = new Redevable();
        }
        return redevable;
    }

    public void setRedevable(Redevable redevable) {
        this.redevable = redevable;
    }

    public int getTypeRedevable() {
        return typeRedevable;
    }

    public void setTypeRedevable(int typeRedevable) {
        this.typeRedevable = typeRedevable;
    }

    public String getCin() {
        return cin;
    }

    public void setCin(String cin) {
        this.cin = cin;
    }

    public String getNif() {
        return nif;
    }

    public void setNif(String nif) {
        this.nif = nif;
    }

    public Long getNumLot() {
        return numLot;
    }

    public void setNumLot(Long numLot) {
        this.numLot = numLot;
    }

    public int getAnnee() {
        return annee;
    }

    public void setAnnee(int annee) {
        this.annee = annee;
    }

    public TerrainFacade getTerrainFacade() {
        return terrainFacade;
    }

    public void setTerrainFacade(TerrainFacade terrainFacade) {
        this.terrainFacade = terrainFacade;
    }

    public Boolean getSimuler() {
        return simuler;
    }

    public void setSimuler(Boolean simuler) {
        this.simuler = simuler;
    }

    public Date getDatePresentationMin() {
        return datePresentationMin;
    }

    public void setDatePresentationMin(Date datePresentationMin) {
        this.datePresentationMin = datePresentationMin;
    }

    public Date getDatePresentationMax() {
        return datePresentationMax;
    }

    public void setDatePresentationMax(Date datePresentationMax) {
        this.datePresentationMax = datePresentationMax;
    }

    public Object[] getMyObjects() {
        return myObjects;
    }

    public void setMyObjects(Object[] myObjects) {
        this.myObjects = myObjects;
    }

    public TaxeAnnuelleFacade getEjbFacade() {
        return ejbFacade;
    }

    public void setEjbFacade(TaxeAnnuelleFacade ejbFacade) {
        this.ejbFacade = ejbFacade;
    }

    public BigDecimal getMontantMin() {
        return montantMin;
    }

    public void setMontantMin(BigDecimal montantMin) {
        this.montantMin = montantMin;
    }

    public BigDecimal getMontantMax() {
        return montantMax;
    }

    public void setMontantMax(BigDecimal montantMax) {
        this.montantMax = montantMax;
    }

    public TaxeAnnuelleController() {
    }

    public TaxeAnnuelle getSelected() {
        if (selected == null) {
            selected = new TaxeAnnuelle();
        }
        return selected;
    }

    public void setSelected(TaxeAnnuelle selected) {
        this.selected = selected;
    }

    protected void setEmbeddableKeys() {
    }

    protected void initializeEmbeddableKey() {
    }

    private TaxeAnnuelleFacade getFacade() {
        return ejbFacade;
    }

    public TaxeAnnuelle prepareCreate() {
        selected = new TaxeAnnuelle();
        initializeEmbeddableKey();
        return selected;
    }

    public void create() {
        persist(PersistAction.CREATE, ResourceBundle.getBundle("/Bundle").getString("TaxeAnnuelleCreated"));
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public void update() {
        persist(PersistAction.UPDATE, ResourceBundle.getBundle("/Bundle").getString("TaxeAnnuelleUpdated"));
    }

    public void destroy() {
        persist(PersistAction.DELETE, ResourceBundle.getBundle("/Bundle").getString("TaxeAnnuelleDeleted"));
        if (!JsfUtil.isValidationFailed()) {
            selected = null; // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public List<TaxeAnnuelle> getItems() {
//        if (items == null) {
//            items = getFacade().findAll();
//        }
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

    public TaxeAnnuelle getTaxeAnnuelle(java.lang.Long id) {
        return getFacade().find(id);
    }

    public List<TaxeAnnuelle> getItemsAvailableSelectMany() {
        return getFacade().findAll();
    }

    public List<TaxeAnnuelle> getItemsAvailableSelectOne() {
        return getFacade().findAll();
    }

    @FacesConverter(forClass = TaxeAnnuelle.class)
    public static class TaxeAnnuelleControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            TaxeAnnuelleController controller = (TaxeAnnuelleController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "taxeAnnuelleController");
            return controller.getTaxeAnnuelle(getKey(value));
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
            if (object instanceof TaxeAnnuelle) {
                TaxeAnnuelle o = (TaxeAnnuelle) object;
                return getStringKey(o.getId());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), TaxeAnnuelle.class.getName()});
                return null;
            }
        }

    }

}
