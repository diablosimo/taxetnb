/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bean;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;

/**
 *
 * @author simob
 */
@Entity
public class Terrain implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    private Long numeroLot;
    private BigDecimal surface;
    private int anneeDernierPaiement;
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date dateAchat;
    @ManyToOne
    private Redevable redevable;
    @ManyToOne
    private Rue rue;
    @ManyToOne
    private CategorieTerrain categorieTerrain;
    @OneToMany(mappedBy = "terrain")
    private List<TaxeAnnuelle> taxeAnnuelles;
    @OneToMany(mappedBy = "terrain")
    private List<Notification> notifications;
    @OneToOne
    private Utilisateur utilisateur;

    public Terrain() {
    }

    public Terrain(Long numeroLot) {
        this.numeroLot = numeroLot;
    }

    public Terrain(Long numeroLot, BigDecimal surface, int lastYPaid) {
        this.numeroLot = numeroLot;
        this.surface = surface;
        this.anneeDernierPaiement = lastYPaid;
    }

    public Long getNumeroLot() {
        return numeroLot;
    }

    public void setNumeroLot(Long numeroLot) {
        this.numeroLot = numeroLot;
    }

    public BigDecimal getSurface() {
        return surface;
    }

    public void setSurface(BigDecimal surface) {
        this.surface = surface;
    }

    public Date getDateAchat() {
        if (dateAchat == null) {
            dateAchat = new Date();
        }
        return dateAchat;
    }

    public void setDateAchat(Date dateAchat) {
        this.dateAchat = dateAchat;
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

    public Redevable getRedevable() {
        if (redevable == null) {
            redevable = new Redevable();
        }
        return redevable;
    }

    public void setRedevable(Redevable redevable) {
        this.redevable = redevable;
    }

    public List<TaxeAnnuelle> getTaxeAnnuelles() {
        if (taxeAnnuelles == null) {
            taxeAnnuelles = new ArrayList<>();
        }
        return taxeAnnuelles;
    }

    public void setTaxeAnnuelles(List<TaxeAnnuelle> taxeAnnuelles) {
        this.taxeAnnuelles = taxeAnnuelles;
    }

    public int getAnneeDernierPaiement() {
        return anneeDernierPaiement;
    }

    public void setAnneeDernierPaiement(int anneeDernierPaiement) {
        this.anneeDernierPaiement = anneeDernierPaiement;
    }

    public List<Notification> getNotifications() {
        if (notifications == null) {
            notifications = new ArrayList();
        }
        return notifications;
    }

    public void setNotifications(List<Notification> notifications) {
        this.notifications = notifications;
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

    public Rue getRue() {
        if (rue == null) {
            rue = new Rue();
        }
        return rue;
    }

    public void setRue(Rue rue) {
        this.rue = rue;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 37 * hash + Objects.hashCode(this.numeroLot);
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
        final Terrain other = (Terrain) obj;
        if (!Objects.equals(this.numeroLot, other.numeroLot)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Terrain{" + "numeroLot=" + numeroLot + ", surface=" + surface + ", lastYPaid=" + anneeDernierPaiement + '}';
    }

}
