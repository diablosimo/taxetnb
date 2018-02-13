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
import javax.persistence.OneToOne;
import javax.persistence.Temporal;

/**
 *
 * @author simob
 */
@Entity
public class TauxRetard implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date dateApplication;
    @OneToMany(mappedBy = "tauxRetard")
    private List<TauxRetardItem> tauxRetardItems;
    @OneToOne
    private Utilisateur utilisateur;
    @OneToMany(mappedBy = "tauxRetard")
    private List<TauxTaxe> tauxTaxes;

    public TauxRetard() {
    }

    public TauxRetard(Date dateApplication) {
        this.dateApplication = dateApplication;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getDateApplication() {
        if (dateApplication == null) {
            dateApplication = new Date();
        }
        return dateApplication;
    }

    public void setDateApplication(Date dateApplication) {
        this.dateApplication = dateApplication;
    }

    public List<TauxRetardItem> getTauxRetardItems() {
        if (tauxRetardItems == null) {
            tauxRetardItems = new ArrayList();
        }
        return tauxRetardItems;
    }

    public void setTauxRetardItems(List<TauxRetardItem> tauxRetardItems) {
        this.tauxRetardItems = tauxRetardItems;
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
        int hash = 5;
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
        final TauxRetard other = (TauxRetard) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "TauxRetard{" + "id=" + id + ", dateApplication=" + dateApplication + '}';
    }

}
