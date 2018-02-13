/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bean;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

/**
 *
 * @author simob
 */
@Entity
public class TauxRetardItem implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private BigDecimal penalite;
    private BigDecimal premierMois;
    private BigDecimal autreMois;
    @ManyToOne
    private TauxRetard tauxRetard;
    @OneToMany(mappedBy = "tauxRetardItem")
    private List<TaxeAnnuelle> taxeAnnuelles;
    @OneToOne
    private Utilisateur utilisateur;

    public TauxRetardItem() {
    }

    public TauxRetardItem(BigDecimal premierMois, BigDecimal autreMois) {
        this.premierMois = premierMois;
        this.autreMois = autreMois;
    }

    public TauxRetardItem(BigDecimal penalite, BigDecimal premierMois, BigDecimal autreMois) {
        this.penalite = penalite;
        this.premierMois = premierMois;
        this.autreMois = autreMois;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<TaxeAnnuelle> getTaxeAnnuelles() {
        if (taxeAnnuelles == null) {
            taxeAnnuelles = new ArrayList();
        }
        return taxeAnnuelles;
    }

    public void setTaxeAnnuelles(List<TaxeAnnuelle> taxeAnnuelles) {
        this.taxeAnnuelles = taxeAnnuelles;
    }

    public BigDecimal getPenalite() {
        if (penalite == null) {
            penalite = new BigDecimal("0.00");
        }
        return penalite;
    }

    public void setPenalite(BigDecimal penalite) {
        this.penalite = penalite;
    }

    public BigDecimal getPremierMois() {
        if (premierMois == null) {
            premierMois = new BigDecimal("0.00");
        }
        return premierMois;
    }

    public void setPremierMois(BigDecimal premierMois) {
        this.premierMois = premierMois;
    }

    public BigDecimal getAutreMois() {
        if (autreMois == null) {
            autreMois = new BigDecimal("0.00");
        }
        return autreMois;
    }

    public void setAutreMois(BigDecimal autreMois) {
        this.autreMois = autreMois;
    }

    public TauxRetard getTauxRetard() {
        if (tauxRetard == null) {
            tauxRetard = new TauxRetard();
        }
        return tauxRetard;
    }

    public void setTauxRetard(TauxRetard tauxRetard) {
        this.tauxRetard = tauxRetard;
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
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TauxRetardItem)) {
            return false;
        }
        TauxRetardItem other = (TauxRetardItem) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "TauxRetardItem{" + "id=" + id + ", penalite=" + penalite + ", premierMois=" + premierMois + ", autreMois=" + autreMois + '}';
    }

}
