/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

/**
 *
 * @author simob
 */
@Entity
public class CategorieTerrain implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String nom;
    @OneToOne
    private TauxTaxeItem tauxTaxeItem;
    @OneToMany(mappedBy = "categorieTerrain")
    private List<Terrain> terrains;
    @OneToOne
    private Utilisateur utilisateur;

    public CategorieTerrain() {
    }

    public CategorieTerrain(Long id) {
        this.id = id;
    }

    public CategorieTerrain(String nom) {
        this.nom = nom;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public List<Terrain> getTerrains() {
        if (terrains == null) {
            terrains = new ArrayList();
        }
        return terrains;
    }

    public void setTerrains(List<Terrain> terrains) {
        this.terrains = terrains;
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
        hash = 41 * hash + Objects.hashCode(this.id);
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
        final CategorieTerrain other = (CategorieTerrain) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "CategorieTerrain{" + "id=" + id + ", nom=" + nom + '}';
    }

}
