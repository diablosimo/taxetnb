/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bean;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Objects;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;

/**
 *
 * @author simob
 */
@Entity
public class TaxeAnnuelle implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private int annee;
    private BigDecimal montant;//taux*surface
    private int nbrMoisRetard;
    private BigDecimal premierMoisRetard;   //tauxPremierMois*montant
    private BigDecimal autreMoisRetard;     //tauxAutreMois*nbrMoisRetard
    private BigDecimal montantRetard;       //premierMoisRetard+autreMoisRetard
    private BigDecimal montantTotal;        //montant+premierMoisRetard+autreMoisRetard
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date datePresentaion;
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date dateTaxe;
    @ManyToOne
    private Terrain terrain;
    @ManyToOne
    private Utilisateur utilisateur;

    // fchekk
    @ManyToOne
    private TauxTaxeItem tauxTaxeItem;
    @ManyToOne
    private TauxRetardItem tauxRetardItem;

    public TaxeAnnuelle() {
    }

    public TaxeAnnuelle(Long id) {
        this.id = id;
    }

    public TaxeAnnuelle(int annee) {
        this.annee = annee;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getAnnee() {
        return annee;
    }

    public void setAnnee(int annee) {
        this.annee = annee;
    }

    public Date getDatePresentaion() {
        return datePresentaion;
    }

    public void setDatePresentaion(Date datePresentaion) {
        this.datePresentaion = datePresentaion;
    }

    public Terrain getTerrain() {
        if (terrain == null) {
            terrain = new Terrain();
        }
        return terrain;
    }

    public int getNbrMoisRetard() {
        return nbrMoisRetard;
    }

    public void setNbrMoisRetard(int nbrMoisRetard) {
        this.nbrMoisRetard = nbrMoisRetard;
    }

    public BigDecimal getPremierMoisRetard() {
        return premierMoisRetard;
    }

    public void setPremierMoisRetard(BigDecimal premierMoisRetard) {
        this.premierMoisRetard = premierMoisRetard;
    }

    public BigDecimal getAutreMoisRetard() {
        return autreMoisRetard;
    }

    public void setAutreMoisRetard(BigDecimal autreMoisRetard) {
        this.autreMoisRetard = autreMoisRetard;
    }

    public BigDecimal getMontantRetard() {
        return montantRetard;
    }

    public void setMontantRetard(BigDecimal montantRetard) {
        this.montantRetard = montantRetard;
    }

    public BigDecimal getMontantTotal() {
        return montantTotal;
    }

    public void setMontantTotal(BigDecimal montantTotal) {
        this.montantTotal = montantTotal;
    }

    public void setTerrain(Terrain terrain) {
        this.terrain = terrain;
    }

    public Date getDateTaxe() {
        if (dateTaxe == null) {
            Date date = new Date();
        }
        return dateTaxe;
    }

    public void setDateTaxe(Date dateTaxe) {
        this.dateTaxe = dateTaxe;
    }

    public TauxTaxeItem getTauxTaxeItem() {
        if (tauxTaxeItem == null) {
            tauxTaxeItem = new TauxTaxeItem();
        }
        return tauxTaxeItem;
    }

    public void setTauxTaxeItem(TauxTaxeItem tauxTaxeItem) {
        this.tauxTaxeItem = tauxTaxeItem;
    }

    public TauxRetardItem getTauxRetardItem() {
        if (tauxRetardItem == null) {
            tauxRetardItem = new TauxRetardItem();
        }
        return tauxRetardItem;
    }

    public void setTauxRetardItem(TauxRetardItem tauxRetardItem) {
        this.tauxRetardItem = tauxRetardItem;
    }

    public BigDecimal getMontant() {
        return montant;
    }

    public void setMontant(BigDecimal montant) {
        this.montant = montant;
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

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 71 * hash + Objects.hashCode(this.id);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final TaxeAnnuelle other = (TaxeAnnuelle) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "TaxeAnnuelle{" + "id=" + id + ", annee=" + annee + ", montant=" + montant + '}';
    }
}
