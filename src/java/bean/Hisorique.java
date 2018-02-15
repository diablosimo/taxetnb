/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bean;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.xml.registry.infomodel.User;

/**
 *
 * @author Aniela
 */
@Entity
public class Hisorique implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date dateAction;
    private int type;   //1 delete 2 upate 3 create
    private String ancienValeur;
    private String nouvelleValeur;
    private String beanName;
    @ManyToOne
    private Utilisateur user;

    public Hisorique() {
    }

    public Hisorique(Date dateAction, int type, String beanName, Utilisateur user) {
        this.dateAction = dateAction;
        this.type = type;
        this.beanName = beanName;
        this.user = user;
    }

    public Hisorique(Date dateAction, int type, String ancienValeur, String nouvelleValeur, String beanName, Utilisateur user) {
        this.dateAction = dateAction;
        this.type = type;
        this.ancienValeur = ancienValeur;
        this.nouvelleValeur = nouvelleValeur;
        this.beanName = beanName;
        this.user = user;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getDateAction() {
        return dateAction;
    }

    public void setDateAction(Date dateAction) {
        this.dateAction = dateAction;
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

    public String getBeanName() {
        return beanName;
    }

    public void setBeanName(String beanName) {
        this.beanName = beanName;
    }

    public Utilisateur getUser() {
        return user;
    }

    public void setUser(Utilisateur user) {
        this.user = user;
    }

    @Override
    public int hashCode() {
        int hash = 7;
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
        final Hisorique other = (Hisorique) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Hisorique{" + "dateAction=" + dateAction + ", type=" + type + ", ancienValeur=" + ancienValeur + ", nouvelleValeur=" + nouvelleValeur + ", beanName=" + beanName + '}';
    }
    

}
