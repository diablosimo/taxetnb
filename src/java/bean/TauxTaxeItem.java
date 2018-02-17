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
public class TauxTaxeItem implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private BigDecimal taux;
    @ManyToOne
    private TauxTaxe tauxTaxe;
    @OneToOne
    private CategorieTerrain categorieTerrain;

    public TauxTaxeItem() {
    }

    public TauxTaxeItem(BigDecimal taux) {
        this.taux = taux;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getTaux() {
        if (taux == null) {
            taux = new BigDecimal("0.00");
        }
        return taux;
    }

    public void setTaux(BigDecimal taux) {
        this.taux = taux;
    }

    public TauxTaxe getTauxTaxe() {
        if (tauxTaxe == null) {
            tauxTaxe = new TauxTaxe();
        }
        return tauxTaxe;
    }

    public void setTauxTaxe(TauxTaxe tauxTaxe) {
        this.tauxTaxe = tauxTaxe;
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

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TauxTaxeItem)) {
            return false;
        }
        TauxTaxeItem other = (TauxTaxeItem) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "TauxTaxeItem{" + "id=" + id + ", taux=" + taux + '}';
    }
}
