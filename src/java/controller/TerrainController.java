package controller;

import bean.Quartier;
import bean.Redevable;
import bean.Rue;
import bean.Secteur;
import bean.Terrain;
import controller.util.DateUtil;
import controller.util.JsfUtil;
import controller.util.JsfUtil.PersistAction;
import service.TerrainFacade;
import service.SecteurFacade;
import java.io.Serializable;
import static java.lang.System.exit;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import static javafx.application.Platform.exit;
import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.faces.event.AjaxBehaviorEvent;
import service.RedevableFacade;

@Named("terrainController")
@SessionScoped
public class TerrainController implements Serializable {

    @EJB
    private service.TerrainFacade ejbFacade;
    @EJB
    private RedevableFacade redevableFacade;
   
    private String message = "";

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public RedevableFacade getRedevableFacade() {
        return redevableFacade;
    }

    public void setRedevableFacade(RedevableFacade redevableFacade) {
        this.redevableFacade = redevableFacade;
    }
    private List<Terrain> items;
    private Terrain selected;
    private Secteur secteur;
    private Redevable redevable;
    private List<Redevable> redevables = null;
    private List<Terrain> terrains = null;

    public List<Terrain> getTerrains() {
        if (terrains == null) {
            terrains = new ArrayList<>();
        }
        return terrains;
    }

    public void setTerrains(List<Terrain> terrains) {
        this.terrains = terrains;
    }

    public Redevable getRedevable() {
        return redevable;
    }

    public List<Redevable> getRedevables() {
        if (redevables == null) {
            redevables = new ArrayList<>();
        }
        return redevables;
    }

    public void setRedevables(List<Redevable> redevables) {
        this.redevables = redevables;
    }

    public void setRedevable(Redevable redevable) {
        this.redevable = redevable;
    }

    public Secteur getSecteur() {
        return secteur;
    }

    public void setSecteur(Secteur secteur) {
        this.secteur = secteur;
    }
    private String resultString = "";
    private int result = 0;
    private int valeurDaffichage = 0;
    private List<Terrain> itemsSearch = null;
    private List<Terrain> itemsSearchDate = null;
    private String achat;
    private String declaration;
    private List<Object[]> NbTerrainParCategorie = new ArrayList();
    @EJB
    private SecteurFacade secteurFacade;

    private List<Secteur> secteurItems = null;
    private Quartier quartier;
    private List<Quartier> quartierItems = null;
    private List<Rue> ruesItems = null;
    private Rue rue;
    private List<Quartier> quartiers = null;

    public List<Quartier> getQuartierItems() {
        return quartierItems;
    }

    public List<Secteur> getSecteurItems() {
        if (secteurItems == null) {
            secteurItems = secteurFacade.findAll();
        }
        return secteurItems;
    }

    public void setSecteurItems(List<Secteur> secteurItems) {
        this.secteurItems = secteurItems;
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

    public Rue getRue() {
        return rue;
    }

    public void setRue(Rue rue) {
        this.rue = rue;
    }

    public int getValeurDaffichage() {
        return valeurDaffichage;
    }

    public void setValeurDaffichage(int valeurDaffichage) {
        this.valeurDaffichage = valeurDaffichage;
    }

    public String getResultString() {
        return resultString;
    }

    public void setResultString(String resultString) {
        this.resultString = resultString;
    }

    public SecteurFacade getSecteurFacade() {
        return secteurFacade;
    }

    public void setSecteurFacade(SecteurFacade secteurFacade) {
        this.secteurFacade = secteurFacade;
    }

    public List<Object[]> getNbTerrainParCategorie() {
        return NbTerrainParCategorie;
    }

    public void setNbTerrainParCategorie(List<Object[]> NbTerrainParCategorie) {
        this.NbTerrainParCategorie = NbTerrainParCategorie;
    }

    public List<Quartier> getQuartiers() {
        return quartiers;
    }

    public void setQuartiers(List<Quartier> quartiers) {
        this.quartiers = quartiers;
    }

    public Quartier getQuartier() {
        return quartier;
    }

    public void setQuartier(Quartier quartier) {
        this.quartier = quartier;
    }

    ////////////Zarbag controller don't touch /////////
    public void findYourVenteDetails() {
        findYourRedevableByIds();
        findYourTerrain();

    }

    public void findYourRedevable() {
        redevables = new ArrayList<>();
        redevable = redevableFacade.find(selected.getRedevable().getId());
        if (redevable != null) {
            getRedevables().add(redevable);
        }
    }

    public void findYourRedevableByIds() {
        redevables = new ArrayList<>();
        redevable = redevableFacade.findByIdSecondaire(selected.getRedevable().getCin(), selected.getRedevable().getNif());
        if (redevable != null) {
            getRedevables().add(redevable);
        }
    }

    public void findYourTerrain() {
        terrains = new ArrayList<>();
        selected = ejbFacade.find(selected.getNumeroLot());
        if (selected != null) {
            getTerrains().add(selected);
        } else if (selected == null) {

        }

    }

    public void calculNbTerrainParCategorie() {
        valeurDaffichage = 1;
       
        NbTerrainParCategorie = ejbFacade.CalculNbTerrainParCategorie(quartier.getId());
        controller.util.SessionUtil.setAttribute("NbTerrainParCategorie", NbTerrainParCategorie);
    }

    public void findQuartiersBySecteur() {
        System.out.println("wa  hani dkhlt");
        quartierItems = secteurFacade.findQuartiersBySecteur(secteur.getCodePostal());
        System.out.println("wa hani khrjt");
        ruesItems = null;
    }

    public void findRuesBySecteur(final AjaxBehaviorEvent event) {

        ruesItems = secteurFacade.findRuesByQuartier(quartier.getId());

    }

    public void setRuesItemsToNull(final AjaxBehaviorEvent event) {

        ruesItems = null;

    }

    public void changeQuartiers(final AjaxBehaviorEvent event) {
        quartiers = secteurFacade.findQuartiersBySecteur(secteur.getCodePostal());
    }

    public void setAttributesToNull(final AjaxBehaviorEvent event) {
        selected.setRedevable(null);
    }

    public String getAchat() {
        return achat;
    }

    public void setAchat(String achat) {
        this.achat = achat;
    }

    public String getDeclaration() {
        return declaration;
    }

    public void setDeclaration(String declaration) {
        this.declaration = declaration;
    }

    public List<Terrain> getItemsSearchDate() {
        return itemsSearchDate;
    }

    public void setItemsSearchDate(List<Terrain> itemsSearchDate) {
        this.itemsSearchDate = itemsSearchDate;
    }

    public List<Terrain> getItemsSearch() {
        if (itemsSearch == null) {
            itemsSearch = new ArrayList();
        }
        return itemsSearch;
    }

    public void setItemsSearch(List<Terrain> itemsSearch) {
        this.itemsSearch = itemsSearch;
    }

    public TerrainFacade getEjbFacade() {
        return ejbFacade;
    }

    public void setEjbFacade(TerrainFacade ejbFacade) {
        this.ejbFacade = ejbFacade;
    }

    public int getResult() {
        return result;
    }

    public void setResult(int result) {
        this.result = result;
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

    public void quitancer() {
        findYourTerrain();

        int year = Calendar.getInstance().get(Calendar.YEAR);
        result = ejbFacade.quitancer(selected.getNumeroLot());
        if (result == -2) {
            resultString = "le terrain " + selected.getNumeroLot() + " n'existe pas dans notre systeme";
        }
        if (result == year) {
            resultString = " le terrain " + selected.getNumeroLot() + " n'a pas de taxe a payer ";
        } else {
            List<Integer> myList = new ArrayList<>();
            for (int i = 0; i <= result; i++) {
                myList.add(year - i);
                resultString = " le terrain " + selected.getNumeroLot() + " a de taxes a payer pour les année " + myList + " ";
            }
        }
    }

    public void vendre() {
        result = ejbFacade.changerProprietaire(selected.getNumeroLot(), selected.getRedevable().getId());
        if (result == 1) {
            resultString = "le terrain " + selected.getNumeroLot() + " a été vendu a " + redevable.getNom() + "" + redevable.getId() + "";
        } else {
            resultString = " le terrain " + selected.getNumeroLot() + " ne peut pas etre vendu veulliez quittancer ";

        }
    }

    public void search1() {
        itemsSearch = ejbFacade.findByCriteria(selected.getSurface(), selected.getRedevable().getId(), selected.getCategorieTerrain().getId());
    }

    public void searchParDate() {
        itemsSearch = ejbFacade.findByDate(selected.getDateDeclaration(), selected.getDateAchat(), selected.getDernierPaiement().getAnnee());

    }

    public void searchParLocalisation() {
        itemsSearch = ejbFacade.findByLocalisation(selected.getRue().getId());

    }

    public void create() {
        terrains = new ArrayList<>();
        ejbFacade.creerTerrain(selected);
        Boolean result = persist(PersistAction.CREATE, ResourceBundle.getBundle("/Bundle").getString("TerrainCreated"));
        if (result == true) {
            terrains.add(selected);
        }
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
        }
        selected = null;
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
            items = ejbFacade.findAll();
        }
        return items;
    }

    private Boolean persist(PersistAction persistAction, String successMessage) {
        boolean result = false;
        if (selected != null) {
            setEmbeddableKeys();
            try {
                if (persistAction != PersistAction.DELETE) {
                    getFacade().edit(selected);
                    result = true;
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
        return result;
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
