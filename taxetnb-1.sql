-- phpMyAdmin SQL Dump
-- version 4.1.14
-- http://www.phpmyadmin.net
--
-- Client :  127.0.0.1
-- Généré le :  Jeu 15 Mars 2018 à 11:11
-- Version du serveur :  5.6.17
-- Version de PHP :  5.5.12

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

--
-- Contenu de la table `categorieterrain`
--

INSERT INTO `categorieterrain` (`ID`, `NOM`) VALUES
(1, 'Zone immeubles'),
(2, 'Zone villas'),
(3, 'Zone habitat individuel'),
(4, 'autres');

--
-- Contenu de la table `quartier`
--

INSERT INTO `quartier` (`ID`, `NOM`, `SECTEUR_CODEPOSTAL`) VALUES
(5, 'Massira1', 40140),
(6, 'Massira2', 40140),
(7, 'Massira3', 40140),
(8, 'Semlalia', 40000),
(9, 'La liberté', 40000),
(10, 'Souria', 40000);

--
-- Contenu de la table `redevable`
--

INSERT INTO `redevable` (`ID`, `ADRESSE`, `CIN`, `EMAIL`, `MOTDEPASSE`, `NIF`, `NOM`, `NUMTEL`, `PRENOM`, `SEXE`) VALUES
(51, 'Imm elbaraka 659 appt 21 Massira1 ', 'A33639', 'benmansour@hotmail.com', '123456789', '', 'BENMANSOUR', '0612345678', 'MOHAMMED', 'Masculin');

--
-- Contenu de la table `rue`
--

INSERT INTO `rue` (`ID`, `NOM`, `QUARTIER_ID`) VALUES
(11, 'saada', 5),
(12, 'Ibn Aouam', 5);

--
-- Contenu de la table `secteur`
--

INSERT INTO `secteur` (`CODEPOSTAL`, `NOM`) VALUES
(40000, 'Gueliz'),
(40140, 'Massira');

--
-- Contenu de la table `sequence`
--

INSERT INTO `sequence` (`SEQ_NAME`, `SEQ_COUNT`) VALUES
('SEQ_GEN', '200');

--
-- Contenu de la table `tauxretard`
--

INSERT INTO `tauxretard` (`ID`, `DATEAPPLICATION`) VALUES
(57, '2018-03-01');

--
-- Contenu de la table `tauxretarditem`
--

INSERT INTO `tauxretarditem` (`ID`, `TAUXAUTREMOIS`, `TAUXPREMIERMOIS`, `TAUXRETARD_ID`, `CATEGORIETERRAIN_ID`) VALUES
(58, '0', '0', 57, 1),
(59, '0', '0', 57, 2),
(60, '0', '0', 57, 3),
(61, '0', '0', 57, 4);

--
-- Contenu de la table `tauxtaxe`
--

INSERT INTO `tauxtaxe` (`ID`, `DATEAPPLICATION`) VALUES
(52, '2018-03-01');

--
-- Contenu de la table `tauxtaxeitem`
--

INSERT INTO `tauxtaxeitem` (`ID`, `TAUX`, `TAUXTAXE_ID`, `CATEGORIETERRAIN_ID`) VALUES
(53, '15', 52, 1),
(54, '10', 52, 2),
(55, '10', 52, 3),
(56, '10', 52, 4);

--
-- Contenu de la table `taxeannuelle`
--

INSERT INTO `taxeannuelle` (`ID`, `ANNEE`, `AUTREMOISRETARD`, `DATEPRESENTAION`, `DATETAXE`, `MONTANT`, `MONTANTRETARD`, `MONTANTTOTAL`, `NBRMOISRETARD`, `PREMIERMOISRETARD`, `TAUXRETARDITEM_ID`, `TAUXTAXEITEM_ID`, `TERRAIN_NUMEROLOT`, `UTILISATEUR_MATRICULE`) VALUES
(0, 2017, '213', '2017-07-05', '2017-04-01', '21323', '231', '12313123', 5, '2132', 58, 54, 121344, '13089122'),
(151, 2018, '0', '2018-03-15', '2018-01-01', '7500', '0', '7500', 0, '0', 58, 53, 121344, NULL),
(152, 2018, '0', '2018-03-15', '2018-01-01', '7500', '0', '7500', 0, '0', 58, 53, 121344, NULL),
(234, 2015, '213', '2015-07-05', '2015-01-01', '21323', '231', '12313123', 5, '2132', 58, 54, 121344, '13089122'),
(235, 2016, '214', '2017-07-01', '2016-04-01', '21323', '231', '12313123', 5, '2132', 58, 54, 121344, '13089122');

--
-- Contenu de la table `terrain`
--

INSERT INTO `terrain` (`NUMEROLOT`, `DATEDERNIERNOTIFICATION`, `CPADRESSE`, `DATEACHAT`, `DATEDECLARATION`, `SURFACE`, `TYPEDERNIERNOTIFICATION`, `CATEGORIETERRAIN_ID`, `REDEVABLE_ID`, `RUE_ID`, `DERNIERPAIEMENT_ID`) VALUES
(121344, NULL, '13', '2018-02-28', '2018-02-28', '500', 0, 1, 51, 12, 152);

--
-- Contenu de la table `utilisateur`
--

INSERT INTO `utilisateur` (`MATRICULE`, `EMAIL`, `MOTDEPASSE`, `NOM`, `PRENOM`, `TYPE`, `SECTEUR_CODEPOSTAL`) VALUES
('13089122', 'benmansour.demon@hotmail.com', '13089122', 'BENMANSOUR', 'MOHAMMED', 1, 40140);
SET FOREIGN_KEY_CHECKS=1;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
