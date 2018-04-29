/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;

/**
 *
 * @author simob
 */
@Entity
public class TauxTaxe implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date dateApplication;
    @OneToMany(mappedBy = "tauxTaxe")
    private List<TauxTaxeItem> tauxTaxeItems;

    
    public TauxTaxe() {
    }

    public TauxTaxe(Long id, Date dateApplication) {
        this.id = id;
        this.dateApplication = dateApplication;
    }

    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getDateApplication() {
        return dateApplication;
    }

    public void setDateApplication(Date dateApplication) {
        this.dateApplication = dateApplication;
    }

    public List<TauxTaxeItem> getTauxTaxeItems() {
        if (tauxTaxeItems == null) {
            tauxTaxeItems = new ArrayList();
        }
        return tauxTaxeItems;
    }

    public void setTauxTaxeItems(List<TauxTaxeItem> tauxTaxeItems) {
        this.tauxTaxeItems = tauxTaxeItems;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 67 * hash + Objects.hashCode(this.id);
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
        final TauxTaxe other = (TauxTaxe) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "TauxTaxe{" + "id=" + id + ", dateApplication=" + dateApplication + '}';
    }
}
