-- phpMyAdmin SQL Dump
-- version 4.1.14
-- http://www.phpmyadmin.net
--
-- Client :  127.0.0.1
-- Généré le :  Lun 30 Avril 2018 à 19:56
-- Version du serveur :  5.6.17
-- Version de PHP :  7.2.4

SET FOREIGN_KEY_CHECKS=0;
SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Base de données :  `taxetnb`
--

-- --------------------------------------------------------

--
-- Structure de la table `categorieterrain`
--

CREATE TABLE IF NOT EXISTS `categorieterrain` (
  `ID` bigint(20) NOT NULL,
  `NOM` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Structure de la table `connexionlog`
--

CREATE TABLE IF NOT EXISTS `connexionlog` (
  `ID` bigint(20) NOT NULL,
  `ACTIONDATE` date DEFAULT NULL,
  `TYPE` int(11) DEFAULT NULL,
  `UTILISATEUR_MATRICULE` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `FK_CONNEXIONLOG_UTILISATEUR_MATRICULE` (`UTILISATEUR_MATRICULE`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Structure de la table `historique`
--

CREATE TABLE IF NOT EXISTS `historique` (
  `ID` bigint(20) NOT NULL,
  `ACTIONDATE` date DEFAULT NULL,
  `ANCIENVALEUR` varchar(255) DEFAULT NULL,
  `NOMBEAN` varchar(255) DEFAULT NULL,
  `NOUVELLEVALEUR` varchar(255) DEFAULT NULL,
  `TYPE` int(11) DEFAULT NULL,
  `UTILISATEUR_MATRICULE` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `FK_HISTORIQUE_UTILISATEUR_MATRICULE` (`UTILISATEUR_MATRICULE`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Structure de la table `notification`
--

CREATE TABLE IF NOT EXISTS `notification` (
  `ID` bigint(20) NOT NULL,
  `ANNEE` int(11) DEFAULT NULL,
  `DATEENVOI` date DEFAULT NULL,
  `DATERECEPTION` date DEFAULT NULL,
  `MONTANTESTIME` decimal(38,0) DEFAULT NULL,
  `NOMBREMOISRETARD` int(11) DEFAULT NULL,
  `TYPE` int(11) DEFAULT NULL,
  `TERRAIN_NUMEROLOT` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `FK_NOTIFICATION_TERRAIN_NUMEROLOT` (`TERRAIN_NUMEROLOT`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Structure de la table `quartier`
--

CREATE TABLE IF NOT EXISTS `quartier` (
  `ID` bigint(20) NOT NULL,
  `NOM` varchar(255) DEFAULT NULL,
  `SECTEUR_CODEPOSTAL` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `FK_QUARTIER_SECTEUR_CODEPOSTAL` (`SECTEUR_CODEPOSTAL`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Structure de la table `redevable`
--

CREATE TABLE IF NOT EXISTS `redevable` (
  `ID` bigint(20) NOT NULL,
  `ADRESSE` varchar(255) DEFAULT NULL,
  `CIN` varchar(255) DEFAULT NULL,
  `EMAIL` varchar(255) DEFAULT NULL,
  `MOTDEPASSE` varchar(255) DEFAULT NULL,
  `NIF` varchar(255) DEFAULT NULL,
  `NOM` varchar(255) DEFAULT NULL,
  `NUMTEL` varchar(255) DEFAULT NULL,
  `PRENOM` varchar(255) DEFAULT NULL,
  `SEXE` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Structure de la table `rue`
--

CREATE TABLE IF NOT EXISTS `rue` (
  `ID` bigint(20) NOT NULL,
  `NOM` varchar(255) DEFAULT NULL,
  `QUARTIER_ID` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `FK_RUE_QUARTIER_ID` (`QUARTIER_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Structure de la table `secteur`
--

CREATE TABLE IF NOT EXISTS `secteur` (
  `CODEPOSTAL` bigint(20) NOT NULL,
  `NOM` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`CODEPOSTAL`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Structure de la table `sequence`
--

CREATE TABLE IF NOT EXISTS `sequence` (
  `SEQ_NAME` varchar(50) NOT NULL,
  `SEQ_COUNT` decimal(38,0) DEFAULT NULL,
  PRIMARY KEY (`SEQ_NAME`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Structure de la table `tauxretard`
--

CREATE TABLE IF NOT EXISTS `tauxretard` (
  `ID` bigint(20) NOT NULL,
  `DATEAPPLICATION` date DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Structure de la table `tauxretarditem`
--

CREATE TABLE IF NOT EXISTS `tauxretarditem` (
  `ID` bigint(20) NOT NULL,
  `TAUXAUTREMOIS` decimal(38,3) DEFAULT NULL,
  `TAUXPREMIERMOIS` decimal(38,3) DEFAULT NULL,
  `TAUXRETARD_ID` bigint(20) DEFAULT NULL,
  `CATEGORIETERRAIN_ID` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `FK_TAUXRETARDITEM_CATEGORIETERRAIN_ID` (`CATEGORIETERRAIN_ID`),
  KEY `FK_TAUXRETARDITEM_TAUXRETARD_ID` (`TAUXRETARD_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Structure de la table `tauxtaxe`
--

CREATE TABLE IF NOT EXISTS `tauxtaxe` (
  `ID` bigint(20) NOT NULL,
  `DATEAPPLICATION` date DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Structure de la table `tauxtaxeitem`
--

CREATE TABLE IF NOT EXISTS `tauxtaxeitem` (
  `ID` bigint(20) NOT NULL,
  `TAUX` decimal(38,0) DEFAULT NULL,
  `TAUXTAXE_ID` bigint(20) DEFAULT NULL,
  `CATEGORIETERRAIN_ID` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `FK_TAUXTAXEITEM_TAUXTAXE_ID` (`TAUXTAXE_ID`),
  KEY `FK_TAUXTAXEITEM_CATEGORIETERRAIN_ID` (`CATEGORIETERRAIN_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Structure de la table `taxeannuelle`
--

CREATE TABLE IF NOT EXISTS `taxeannuelle` (
  `ID` bigint(20) NOT NULL,
  `ANNEE` int(11) DEFAULT NULL,
  `AUTREMOISRETARD` decimal(38,0) DEFAULT NULL,
  `DATEPRESENTAION` date DEFAULT NULL,
  `DATETAXE` date DEFAULT NULL,
  `MONTANT` decimal(38,0) DEFAULT NULL,
  `MONTANTRETARD` decimal(38,0) DEFAULT NULL,
  `MONTANTTOTAL` decimal(38,0) DEFAULT NULL,
  `NBRMOISRETARD` int(11) DEFAULT NULL,
  `PREMIERMOISRETARD` decimal(38,0) DEFAULT NULL,
  `TAUXRETARDITEM_ID` bigint(20) DEFAULT NULL,
  `TAUXTAXEITEM_ID` bigint(20) DEFAULT NULL,
  `TERRAIN_NUMEROLOT` bigint(20) DEFAULT NULL,
  `UTILISATEUR_MATRICULE` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `FK_TAXEANNUELLE_TAUXTAXEITEM_ID` (`TAUXTAXEITEM_ID`),
  KEY `FK_TAXEANNUELLE_TAUXRETARDITEM_ID` (`TAUXRETARDITEM_ID`),
  KEY `FK_TAXEANNUELLE_UTILISATEUR_MATRICULE` (`UTILISATEUR_MATRICULE`),
  KEY `FK_TAXEANNUELLE_TERRAIN_NUMEROLOT` (`TERRAIN_NUMEROLOT`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Structure de la table `terrain`
--

CREATE TABLE IF NOT EXISTS `terrain` (
  `NUMEROLOT` bigint(20) NOT NULL,
  `DATEDERNIERNOTIFICATION` date DEFAULT NULL,
  `CPADRESSE` varchar(255) DEFAULT NULL,
  `DATEACHAT` date DEFAULT NULL,
  `DATEDECLARATION` date DEFAULT NULL,
  `SURFACE` decimal(38,0) DEFAULT NULL,
  `TYPEDERNIERNOTIFICATION` int(11) DEFAULT NULL,
  `CATEGORIETERRAIN_ID` bigint(20) DEFAULT NULL,
  `REDEVABLE_ID` bigint(20) DEFAULT NULL,
  `RUE_ID` bigint(20) DEFAULT NULL,
  `DERNIERPAIEMENT_ID` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`NUMEROLOT`),
  KEY `FK_TERRAIN_CATEGORIETERRAIN_ID` (`CATEGORIETERRAIN_ID`),
  KEY `FK_TERRAIN_REDEVABLE_ID` (`REDEVABLE_ID`),
  KEY `FK_TERRAIN_RUE_ID` (`RUE_ID`),
  KEY `FK_TERRAIN_DERNIERPAIEMENT_ID` (`DERNIERPAIEMENT_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Structure de la table `utilisateur`
--

CREATE TABLE IF NOT EXISTS `utilisateur` (
  `MATRICULE` varchar(255) NOT NULL,
  `EMAIL` varchar(255) DEFAULT NULL,
  `MOTDEPASSE` varchar(255) DEFAULT NULL,
  `NOM` varchar(255) DEFAULT NULL,
  `PRENOM` varchar(255) DEFAULT NULL,
  `TYPE` int(11) DEFAULT NULL,
  `SECTEUR_CODEPOSTAL` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`MATRICULE`),
  KEY `FK_UTILISATEUR_SECTEUR_CODEPOSTAL` (`SECTEUR_CODEPOSTAL`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Contraintes pour les tables exportées
--

--
-- Contraintes pour la table `connexionlog`
--
ALTER TABLE `connexionlog`
  ADD CONSTRAINT `FK_CONNEXIONLOG_UTILISATEUR_MATRICULE` FOREIGN KEY (`UTILISATEUR_MATRICULE`) REFERENCES `utilisateur` (`MATRICULE`);

--
-- Contraintes pour la table `historique`
--
ALTER TABLE `historique`
  ADD CONSTRAINT `FK_HISTORIQUE_UTILISATEUR_MATRICULE` FOREIGN KEY (`UTILISATEUR_MATRICULE`) REFERENCES `utilisateur` (`MATRICULE`);

--
-- Contraintes pour la table `notification`
--
ALTER TABLE `notification`
  ADD CONSTRAINT `FK_NOTIFICATION_TERRAIN_NUMEROLOT` FOREIGN KEY (`TERRAIN_NUMEROLOT`) REFERENCES `terrain` (`NUMEROLOT`);

--
-- Contraintes pour la table `quartier`
--
ALTER TABLE `quartier`
  ADD CONSTRAINT `FK_QUARTIER_SECTEUR_CODEPOSTAL` FOREIGN KEY (`SECTEUR_CODEPOSTAL`) REFERENCES `secteur` (`CODEPOSTAL`);

--
-- Contraintes pour la table `rue`
--
ALTER TABLE `rue`
  ADD CONSTRAINT `FK_RUE_QUARTIER_ID` FOREIGN KEY (`QUARTIER_ID`) REFERENCES `quartier` (`ID`);

--
-- Contraintes pour la table `tauxretarditem`
--
ALTER TABLE `tauxretarditem`
  ADD CONSTRAINT `FK_TAUXRETARDITEM_CATEGORIETERRAIN_ID` FOREIGN KEY (`CATEGORIETERRAIN_ID`) REFERENCES `categorieterrain` (`ID`),
  ADD CONSTRAINT `FK_TAUXRETARDITEM_TAUXRETARD_ID` FOREIGN KEY (`TAUXRETARD_ID`) REFERENCES `tauxretard` (`ID`);

--
-- Contraintes pour la table `tauxtaxeitem`
--
ALTER TABLE `tauxtaxeitem`
  ADD CONSTRAINT `FK_TAUXTAXEITEM_CATEGORIETERRAIN_ID` FOREIGN KEY (`CATEGORIETERRAIN_ID`) REFERENCES `categorieterrain` (`ID`),
  ADD CONSTRAINT `FK_TAUXTAXEITEM_TAUXTAXE_ID` FOREIGN KEY (`TAUXTAXE_ID`) REFERENCES `tauxtaxe` (`ID`);

--
-- Contraintes pour la table `taxeannuelle`
--
ALTER TABLE `taxeannuelle`
  ADD CONSTRAINT `FK_TAXEANNUELLE_TAUXRETARDITEM_ID` FOREIGN KEY (`TAUXRETARDITEM_ID`) REFERENCES `tauxretarditem` (`ID`),
  ADD CONSTRAINT `FK_TAXEANNUELLE_TAUXTAXEITEM_ID` FOREIGN KEY (`TAUXTAXEITEM_ID`) REFERENCES `tauxtaxeitem` (`ID`),
  ADD CONSTRAINT `FK_TAXEANNUELLE_TERRAIN_NUMEROLOT` FOREIGN KEY (`TERRAIN_NUMEROLOT`) REFERENCES `terrain` (`NUMEROLOT`),
  ADD CONSTRAINT `FK_TAXEANNUELLE_UTILISATEUR_MATRICULE` FOREIGN KEY (`UTILISATEUR_MATRICULE`) REFERENCES `utilisateur` (`MATRICULE`);

--
-- Contraintes pour la table `terrain`
--
ALTER TABLE `terrain`
  ADD CONSTRAINT `FK_TERRAIN_CATEGORIETERRAIN_ID` FOREIGN KEY (`CATEGORIETERRAIN_ID`) REFERENCES `categorieterrain` (`ID`),
  ADD CONSTRAINT `FK_TERRAIN_DERNIERPAIEMENT_ID` FOREIGN KEY (`DERNIERPAIEMENT_ID`) REFERENCES `taxeannuelle` (`ID`),
  ADD CONSTRAINT `FK_TERRAIN_REDEVABLE_ID` FOREIGN KEY (`REDEVABLE_ID`) REFERENCES `redevable` (`ID`),
  ADD CONSTRAINT `FK_TERRAIN_RUE_ID` FOREIGN KEY (`RUE_ID`) REFERENCES `rue` (`ID`);

--
-- Contraintes pour la table `utilisateur`
--
ALTER TABLE `utilisateur`
  ADD CONSTRAINT `FK_UTILISATEUR_SECTEUR_CODEPOSTAL` FOREIGN KEY (`SECTEUR_CODEPOSTAL`) REFERENCES `secteur` (`CODEPOSTAL`);
SET FOREIGN_KEY_CHECKS=1;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
