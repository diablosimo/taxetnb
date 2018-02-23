/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bean;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

/**
 *
 * @author simob
 */
@Entity
public class Utilisateur implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    private String matricule;//login
    private String motDepasse;
    private String nom;
    private String prenom;
    private int type; //1:admin//2:superAdmin//3:utilisateur
    @ManyToOne
    private Secteur secteur;

    public Utilisateur() {
    }

    public Utilisateur(String matricule) {
        this.matricule = matricule;
    }

    public Utilisateur(String matricule, String motDepasse, String nom, String prenom) {
        this.matricule = matricule;
        this.motDepasse = motDepasse;
        this.nom = nom;
        this.prenom = prenom;
    }

    public Utilisateur(String matricule, String motDepasse) {
        this.matricule = matricule;
        this.motDepasse = motDepasse;
    }

    public String getMatricule() {
        return matricule;
    }

    public void setMatricule(String matricule) {
        this.matricule = matricule;
    }

    public String getMotDepasse() {
        return motDepasse;
    }

    public void setMotDepasse(String motDepasse) {
        this.motDepasse = motDepasse;
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

    public Secteur getSecteur() {
        return secteur;
    }

    public void setSecteur(Secteur secteur) {
        this.secteur = secteur;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 97 * hash + Objects.hashCode(this.matricule);
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
        final Utilisateur other = (Utilisateur) obj;
        if (!Objects.equals(this.matricule, other.matricule)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Utilisateur{" + "matricule=" + matricule + ", motDepasse=" + motDepasse + ", nom=" + nom + ", prenom=" + prenom + ", type=" + type + '}';
    }
}
