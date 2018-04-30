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
import javax.persistence.Id;
import javax.persistence.ManyToOne;
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
    private String cpAdresse;
    @ManyToOne
    private Rue rue;
    @ManyToOne
    private CategorieTerrain categorieTerrain;
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date dateDeclaration=new Date();
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date dateAchat;
    @ManyToOne
    private Redevable redevable;
    @OneToOne
    private TaxeAnnuelle dernierPaiement=null;
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date DatedernierNotification=null;
    private int typeDernierNotification=0;
    
    public Terrain() {
    }

    public Terrain(Long numeroLot) {
        this.numeroLot = numeroLot;
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

    public TaxeAnnuelle getDernierPaiement() {
        if(dernierPaiement==null)
            dernierPaiement=new TaxeAnnuelle();
        return dernierPaiement;
    }

    public void setDernierPaiement(TaxeAnnuelle dernierPaiement) {
        this.dernierPaiement = dernierPaiement;
    }

    public Date getDateDeclaration() {
        return dateDeclaration;
    }

    public void setDateDeclaration(Date dateDeclaration) {
        this.dateDeclaration = dateDeclaration;
    }

    public Date getDatedernierNotification() {
        return DatedernierNotification;
    }

    public void setDatedernierNotification(Date DatedernierNotification) {
        this.DatedernierNotification = DatedernierNotification;
    }

    public int getTypeDernierNotification() {
        return typeDernierNotification;
    }

    public void setTypeDernierNotification(int typeDernierNotification) {
        this.typeDernierNotification = typeDernierNotification;
    }

    public String getCpAdresse() {
        return cpAdresse;
    }

    public void setCpAdresse(String cpAdresse) {
        this.cpAdresse = cpAdresse;
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

    public String toString2(){
                return numeroLot+", "+cpAdresse+", "+rue.getNom()+", "+rue.getQuartier().getNom()+", "+rue.getQuartier().getSecteur().getNom();

    }
    
    @Override
    public String toString() {
        // return "Terrain{" + "numeroLot=" + numeroLot + '}';
        return numeroLot+", "+cpAdresse+", "+rue.getNom()+", "+rue.getQuartier().getNom()+", "+rue.getQuartier().getSecteur().getNom();
    }

    
}
