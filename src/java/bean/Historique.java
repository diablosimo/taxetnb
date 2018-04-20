/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bean;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;

/**
 *
 * @author aniela
 */
@Entity
public class Historique implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private int type; //1:create//2:delete//3:update
    private String ancienValeur;
    private String nouvelleValeur;
    private String nomBean;
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date actionDate;
    @ManyToOne
    private Utilisateur utilisateur;

    public Historique() {
    }

    public Historique(int type, String ancienValeur, String nouvelleValeur, String nomBean) {
        this.type = type;
        this.ancienValeur = ancienValeur;
        this.nouvelleValeur = nouvelleValeur;
        this.nomBean = nomBean;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getAncienValeur() {
        return ancienValeur;
    }

    public void setAncienValeur(String ancienValeur) {
        this.ancienValeur = ancienValeur;
    }

    public String getNouvelleValeur() {
        return nouvelleValeur;
    }

    public void setNouvelleValeur(String nouvelleValeur) {
        this.nouvelleValeur = nouvelleValeur;
    }

    public String getNomBean() {
        return nomBean;
    }

    public void setNomBean(String nomBean) {
        this.nomBean = nomBean;
    }

    public Date getActionDate() {
        return actionDate;
    }

    public void setActionDate(Date actionDate) {
        this.actionDate = actionDate;
    }

    public Utilisateur getUtilisateur() {
        return utilisateur;
    }

    public void setUtilisateur(Utilisateur utilisateur) {
        this.utilisateur = utilisateur;
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
        if (!(object instanceof Historique)) {
            return false;
        }
        Historique other = (Historique) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Historique{" + "type=" + type + ", ancienValeur=" + ancienValeur + ", nouvelleValeur=" + nouvelleValeur + ", nomBean=" + nomBean + '}';
    }

}
