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

/**
 *
 * @author simob
 */
@Entity
public class Redevable implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String cin;//code d'identité nationale
    private String nom;
    private String prenom;
    private String sexe;
    private String nif;//numero d'identité fiscale
    private String adresse;
    private String email;
    private String motDePasse;
    private String numTel;
    @OneToMany(mappedBy = "redevable")
    private List<Terrain> terrains;

    public Redevable() {
    }

    public Redevable(Long id) {
        this.id = id;
    }

    public Redevable(String nom, String prenom) {
        this.nom = nom;
        this.prenom = prenom;
    }

    public Redevable(String cin, String nom, String prenom, String sexe, String adresse, String numTel) {
        this.cin = cin;
        this.nom = nom;
        this.prenom = prenom;
        this.sexe = sexe;
        this.adresse = adresse;
        this.numTel = numTel;
    }

    public Redevable(String nom, String nif, String adresse, String numTel) {
        this.nom = nom;
        this.nif = nif;
        this.adresse = adresse;
        this.numTel = numTel;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getSexe() {
        return sexe;
    }

    public void setSexe(String sexe) {

        this.sexe = sexe;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public String getNumTel() {
        return numTel;
    }

    public void setNumTel(String numTel) {
        this.numTel = numTel;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    
    public List<Terrain> getTerrains() {
        if (terrains == null) {
            terrains = new ArrayList<>();
        }
        return terrains;
    }

    public void setTerrains(List<Terrain> terrains) {
        this.terrains = terrains;
    }

    public String getMotDePasse() {
        return motDePasse;
    }

    public void setMotDePasse(String motDePasse) {
        this.motDePasse = motDePasse;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 37 * hash + Objects.hashCode(this.id);
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
        final Redevable other = (Redevable) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Redevable{" + "cin=" + cin + ", nom=" + nom + ", prenom=" + prenom + ", sexe=" + sexe + ", nif=" + nif + ", adresse=" + adresse + ", email=" + email + ", numTel=" + numTel + '}';
    }

    
}
