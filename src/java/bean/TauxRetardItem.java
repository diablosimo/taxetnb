/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bean;

import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
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
    private BigDecimal tauxPremierMois;
    private BigDecimal tauxAutreMois;
    @ManyToOne
    private TauxRetard tauxRetard;
    @OneToOne
    private CategorieTerrain categorieTerrain;

    public TauxRetardItem() {
    }

    public TauxRetardItem(BigDecimal premierMois, BigDecimal autreMois) {
        this.tauxPremierMois = premierMois;
        this.tauxAutreMois = autreMois;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getTauxPremierMois() {
        if (tauxPremierMois == null) {
            tauxPremierMois = new BigDecimal("0.00");
        }
        return tauxPremierMois;
    }

    public void setTauxPremierMois(BigDecimal tauxPremierMois) {
        this.tauxPremierMois = tauxPremierMois;
    }

    public BigDecimal getTauxAutreMois() {
        if (tauxAutreMois == null) {
            tauxAutreMois = new BigDecimal("0.00");
        }
        return tauxAutreMois;
    }

    public void setTauxAutreMois(BigDecimal tauxAutreMois) {
        this.tauxAutreMois = tauxAutreMois;
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

    public CategorieTerrain getCategorieTerrain() {
        return categorieTerrain;
    }

    public void setCategorieTerrain(CategorieTerrain categorieTerrain) {
        this.categorieTerrain = categorieTerrain;
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
        return "TauxRetardItem{" + "id=" + id + ", premierMois=" + tauxPremierMois + ", autreMois=" + tauxAutreMois + '}';
    }

}
