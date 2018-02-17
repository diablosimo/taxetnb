/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;

/**
 *
 * @author simob
 */
@Entity
public class Secteur implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    private Long codePostal;
    private String nom;
    @OneToMany(mappedBy = "secteur")
    private List<Quartier> quartiers;
    @OneToMany(mappedBy = "secteur")
    private List<Utilisateur> utilisateurs;

    public Long getCodePostal() {
        return codePostal;
    }

    public void setCodePostal(Long codePostal) {
        this.codePostal = codePostal;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public List<Quartier> getQuartiers() {
        if (quartiers == null) {
            quartiers = new ArrayList();
        }
        return quartiers;
    }

    public void setQuartiers(List<Quartier> quartiers) {
        this.quartiers = quartiers;
    }

    public List<Utilisateur> getUtilisateurs() {
        if (utilisateurs == null) {
            utilisateurs = new ArrayList();
        }
        return utilisateurs;
    }

    public void setUtilisateurs(List<Utilisateur> utilisateurs) {
        this.utilisateurs = utilisateurs;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (codePostal != null ? codePostal.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the codePostal fields are not set
        if (!(object instanceof Secteur)) {
            return false;
        }
        Secteur other = (Secteur) object;
        if ((this.codePostal == null && other.codePostal != null) || (this.codePostal != null && !this.codePostal.equals(other.codePostal))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Secteur{" + "codePostal=" + codePostal + ", nom=" + nom + '}';
    }

}
