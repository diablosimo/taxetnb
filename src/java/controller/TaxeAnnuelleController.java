package controller;

import bean.CategorieTerrain;
import bean.Quartier;
import bean.Redevable;
import bean.Rue;
import bean.Secteur;
import bean.TaxeAnnuelle;
import bean.Terrain;
import bean.Utilisateur;
import controller.util.JsfUtil;
import controller.util.JsfUtil.PersistAction;
import controller.util.SessionUtil;
import java.io.IOException;
import service.TaxeAnnuelleFacade;
import javax.mail.MessagingException;
import net.sf.jasperreports.engine.JRException;
import service.UtilisateurFacade;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import service.TerrainFacade;
///////////////////////////////////////
///// import charts //////
//////////////////////////////////////
//import javax.annotation.PostConstruct;
import java.io.Serializable;
import javax.enterprise.context.SessionScoped;
import javax.faces.event.AjaxBehaviorEvent;
import javax.inject.Named;
import service.SecteurFacade;

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
    private Date datePresentationMin;
    private Date datePresentationMax;
    private Redevable redevable;
    private CategorieTerrain categorieTerrain;
    private Rue rue;
    private Quartier quartier;
    private Secteur secteur;
    private Utilisateur utilisateur;
    private List<Terrain> terrains;
    private Object[] myObjects = new Object[]{0, null};
    private Boolean simuler = true;
    private String fileName;
    private Utilisateur connectedUser;
    @EJB
    private service.TerrainFacade terrainFacade;
    @EJB
    private service.UtilisateurFacade utilisateurFacade;
    @EJB
    private service.SecteurFacade secteurFacade;

    private BigDecimal y;
    private int valeurDaffichage;
    private List<BigDecimal> list = new ArrayList();
    private List<Object[]> listOfObjects = new ArrayList();
    private List<Object[]> resultsOfCategories = new ArrayList();

    private List<Quartier> quartiers = null;
    private List<Rue> rues=null;

/////Methodes/////////////////////// 
    public void verify() {
        myObjects = ejbFacade.verifyAndCreate(selected, selected.getAnnee());
        selected = (TaxeAnnuelle) myObjects[1];
        JsfUtil.messageForVerificationPaiement((int) myObjects[0]);
    }

    public void calcul() {
        if (myObjects[0] == (Object) 1) {
            selected = ejbFacade.calcul(selected);
        }
    }

    public void payer() {
        if (myObjects[0] == (Object) 1) {
            selected.setUtilisateur(getConnectedUser());
            selected = ejbFacade.create(selected, simuler);
        }
    }

    public void generatePdf() throws JRException, IOException, MessagingException {
        selected.setUtilisateur(getConnectedUser());
        fileName = ejbFacade.printPdf(selected);
        FacesContext.getCurrentInstance().responseComplete();
    }

    public void annuler() {
        myObjects = new Object[]{0, null};
        selected = null;
        items = null;
        terrains = null;
        cin = "";
        nif = "";
        numLot = null;
        montantMin = null;
        montantMax = null;
        annee = 0;
        anneeMin = 0;
        anneeMax = 0;
        datePresentationMin = null;
        datePresentationMax = null;
        redevable = null;
        categorieTerrain = null;
        rue = null;
        quartier = null;
        secteur = null;
        terrains = null;
        myObjects = new Object[]{0, null};
        simuler = true;
        fileName = null;
        utilisateur = null;
        utilisateur = getUtilisateur();
    }

    public void findForClient() {
        items = ejbFacade.findForClient(redevable, anneeMin, anneeMax, numLot);
        System.out.println(items);
    }

    public void findForUser() {
        items = ejbFacade.findByAllCriteria(datePresentationMin, datePresentationMax, annee, montantMin, montantMax, numLot, cin, nif, null, rue, quartier, secteur, null);
    }

    public void findForAdmin() {
        //items = ejbFacade.findByAllCriteria(datePresentationMin, datePresentationMax, annee, montantMin, montantMax, numLot, cin, nif, categorieTerrain, rue, quartier, secteur, utilisateur);
        items = ejbFacade.findByAllCriteria(datePresentationMin, datePresentationMax, annee, montantMin, montantMax, numLot, cin, nif, null, rue, quartier, secteur, utilisateur);
    }

    public void findTerrainByCinOrNif() {
        terrains = terrainFacade.findByCinOrNif(cin, nif);
    }

    public void sendEmail() throws MessagingException {
        if (fileName != null) {
            ejbFacade.sendQuitanceInEmail(selected.getTerrain().getRedevable().getEmail(), "ci-joint vous trouverez le recu du paiement du terrain:" + selected.getTerrain(), "quitance ", "C:\\Users\\simob\\Dropbox\\ARCHITECTURE REPARTIE\\Quitances\\" + fileName + ".pdf");
        }
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

    public void findQuartiersBySecteur() {
        System.out.println("wa  hani dkhlt");
        quartiers = secteurFacade.findQuartiersBySecteur(secteur.getCodePostal());
        System.out.println("wa hani khrjt");
        rues = null;
    }
    
    public void findRuesBySecteur(final AjaxBehaviorEvent event) {
        rues = secteurFacade.findRuesByQuartier(quartier.getId());
    }

    /// ZARBAG controllers DON'T touch///////////////////
    public String calculRevenuesQuartiersDansSecteur() {
        valeurDaffichage = 2;
        listOfObjects = ejbFacade.calculRevenuesQuartiersDansSecteur(selected.getTerrain().getRue().getQuartier().getSecteur(), selected.getAnnee());
        controller.util.SessionUtil.setAttribute("listOfObjects", listOfObjects);
        return "revenueChart";
    }

    public String revenuesQuartierParCategorie() {
        valeurDaffichage = 3;

        resultsOfCategories = ejbFacade.revenuesQuartierParCategorie(selected.getTerrain().getRue().getQuartier().getSecteur(), selected.getTerrain().getRue().getQuartier(), selected.getAnnee());
        controller.util.SessionUtil.setAttribute("resultsOfCategories", resultsOfCategories);
        return "revenueParCategorieChart";
    }

    public void changeSecteurs(final AjaxBehaviorEvent event) {
        quartiers = secteurFacade.findQuartiersBySecteur(selected.getTerrain().getRue().getQuartier().getSecteur().getCodePostal());
    }

    public String calculRevenuesParMois() {
        valeurDaffichage = 1;
        list = ejbFacade.calculRevenuesParMois(selected.getTerrain().getRue().getQuartier(), selected.getTerrain().getRue().getQuartier().getSecteur(), selected.getAnnee());
        controller.util.SessionUtil.setAttribute("listRevenue", list);
        return "revenueChart";
        
    }

    public void calculRevenuesAnnuelle() {
        y = ejbFacade.calculRevenuesAnnuelle(selected.getTerrain().getRue().getQuartier(), selected.getTerrain().getRue().getQuartier().getSecteur(), selected.getAnnee());
    }

////GETTERS AND SETTERS/////////////////////////////////////////////////////
    public int getValeurDaffichage() {
        return valeurDaffichage;
    }

    public void setValeurDaffichage(int valeurDaffichage) {
        this.valeurDaffichage = valeurDaffichage;
    }

    public Utilisateur getConnectedUser() {
        if (connectedUser == null) {
            connectedUser = SessionUtil.getConnectedUser();
        }
        return connectedUser;
    }

    public List<Object[]> getResultsOfCategories() {
        return resultsOfCategories;
    }

    public void setResultsOfCategories(List<Object[]> ResultsOfCategories) {
        this.resultsOfCategories = ResultsOfCategories;
    }

    public BigDecimal getY() {
        return y;
    }

    public void setY(BigDecimal y) {
        this.y = y;
    }

    public List<Quartier> getQuartiers() {
        if(quartiers==null)
            quartiers=new ArrayList();
        return quartiers;
    }

    public void setQuartiers(List<Quartier> quartiers) {
        this.quartiers = quartiers;
    }

    public List<BigDecimal> getList() {
        return list;
    }

    public List<Rue> getRues() {
        if(rues==null)
            rues=new  ArrayList<>();
        return rues;
    }

    public void setRues(List<Rue> rues) {
        this.rues = rues;
    }

    public SecteurFacade getSecteurFacade() {
        return secteurFacade;
    }

    public void setSecteurFacade(SecteurFacade secteurFacade) {
        this.secteurFacade = secteurFacade;
    }

    
    
    public void setList(List<BigDecimal> list) {
        this.list = list;
    }

    public List<Object[]> getListOfObjects() {
        return listOfObjects;
    }

    public void setListOfObjects(List<Object[]> listOfObjects) {
        this.listOfObjects = listOfObjects;
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

    public List<Terrain> getTerrains() {
        if (terrains == null) {
            terrains = new ArrayList();
        }
        return terrains;
    }

    public void setTerrains(List<Terrain> terrains) {
        this.terrains = terrains;
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

    public void setConnectedUser(Utilisateur connectedUser) {
        this.connectedUser = connectedUser;
    }

    public UtilisateurFacade getUtilisateurFacade() {
        return utilisateurFacade;
    }

    public void setUtilisateurFacade(UtilisateurFacade utilisateurFacade) {
        this.utilisateurFacade = utilisateurFacade;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public CategorieTerrain getCategorieTerrain() {
        if (categorieTerrain == null) {
            categorieTerrain = new CategorieTerrain();
        }
        return categorieTerrain;
    }

    public void setCategorieTerrain(CategorieTerrain categorieTerrain) {
        this.categorieTerrain = categorieTerrain;
    }

    public Rue getRue() {
        if (rue == null) {
            rue = new Rue();
        }
        return rue;
    }

    public void setRue(Rue rue) {
        this.rue = rue;
    }

    public Quartier getQuartier() {
        if (quartier == null) {
            quartier = new Quartier();
        }
        return quartier;
    }

    public void setQuartier(Quartier quartier) {
        this.quartier = quartier;
    }

    public Secteur getSecteur() {
        if (secteur == null) {
            secteur = new Secteur();
        }
        return secteur;
    }

    public void setSecteur(Secteur secteur) {
        this.secteur = secteur;
    }

    public Utilisateur getUtilisateur() {
        if (utilisateur == null) {
            utilisateur = new Utilisateur();
        }
        return utilisateur;
    }

    public void setUtilisateur(Utilisateur utilisateur) {
        this.utilisateur = utilisateur;
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

    public List<TaxeAnnuelle> getItems() {
        if (items == null) {
            items = new ArrayList();
        }
        return items;
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
