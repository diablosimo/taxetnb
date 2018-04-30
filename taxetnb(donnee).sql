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

--
-- Contenu de la table `categorieterrain`
--

INSERT INTO `categorieterrain` (`ID`, `NOM`) VALUES
(1, 'Zone immeubles'),
(2, 'Zone villas'),
(3, 'Zone habitat individuel'),
(4, 'autres');

--
-- Contenu de la table `connexionlog`
--

INSERT INTO `connexionlog` (`ID`, `ACTIONDATE`, `TYPE`, `UTILISATEUR_MATRICULE`) VALUES
(551, '2018-04-30', 1, '13089122'),
(552, '2018-04-30', 2, '13089122'),
(553, '2018-04-30', 1, 'EE18'),
(556, '2018-04-30', 1, 'EE18'),
(601, '2018-04-30', 1, '13089122'),
(651, '2018-04-30', 1, '13089122'),
(701, '2018-04-30', 1, '13089122'),
(702, '2018-04-30', 2, '13089122'),
(751, '2018-04-30', 1, '13089122'),
(752, '2018-04-30', 2, '13089122'),
(753, '2018-04-30', 1, '13089122'),
(754, '2018-04-30', 2, '13089122'),
(755, '2018-04-30', 2, NULL),
(756, '2018-04-30', 1, '13089122'),
(757, '2018-04-30', 2, '13089122'),
(758, '2018-04-30', 1, 'EE18'),
(759, '2018-04-30', 1, 'EE18'),
(760, '2018-04-30', 1, '13089122'),
(801, '2018-04-30', 1, 'EE18'),
(802, '2018-04-30', 2, 'EE18'),
(803, '2018-04-30', 1, '13089122'),
(804, '2018-04-30', 2, '13089122'),
(805, '2018-04-30', 1, '13089122'),
(806, '2018-04-30', 2, '13089122'),
(807, '2018-04-30', 1, 'EE1997'),
(812, '2018-04-30', 1, '13089122'),
(813, '2018-04-30', 2, NULL),
(851, '2018-04-30', 1, '13089122'),
(901, '2018-04-30', 1, '13089122');

--
-- Contenu de la table `historique`
--

INSERT INTO `historique` (`ID`, `ACTIONDATE`, `ANCIENVALEUR`, `NOMBEAN`, `NOUVELLEVALEUR`, `TYPE`, `UTILISATEUR_MATRICULE`) VALUES
(37, '2018-04-01', 'azert', 'zerty', 'rtyui', 1, '13089122'),
(127, '2018-04-16', 'id=1234 nom=Abdekarim Khatabi4', 'Rue', NULL, 3, 'EE1997'),
(137, '2018-04-16', 'id=1234 nom=Abdekarim Khatabi4', 'Rue', NULL, 3, 'EE1997'),
(145, '2018-01-08', 'id=127 dateApplication=2012-01-01', 'Taux taxe', NULL, 1, 'EE1997'),
(147, '2018-04-16', 'id=1234 nom=Abdekarim Khatabi', 'Rue', NULL, 3, 'EE1997'),
(680, '2018-04-14', 'ID=465 ACTIONDATE=2012-10-08', 'Taux Taxe', NULL, 1, 'EE1997'),
(690, '2018-04-08', 'ID=465 ACTIONDATE=2018-04-08', 'Taux Taxe', NULL, 1, 'EE1997'),
(691, '2018-04-08', 'ID=465 ACTIONDATE=2018-04-08', 'Taux Taxe', NULL, 1, 'EE1997'),
(692, '2018-04-09', 'ID=465 ACTIONDATE=2017-20-08', 'Taux Taxe', NULL, 1, 'EE1997'),
(693, '2018-04-10', 'ID=465 ACTIONDATE=2017-04-08', 'Taux Taxe', NULL, 1, 'EE1997'),
(694, '2018-04-11', 'ID=465 ACTIONDATE=2015-03-08', 'Taux Taxe', NULL, 1, 'EE1997'),
(695, '2018-04-12', 'ID=465 ACTIONDATE=2014-06-08', 'Taux Taxe', NULL, 1, 'EE1997'),
(696, '2018-04-13', 'ID=465 ACTIONDATE=2013-19-08', 'Taux Taxe', NULL, 1, 'EE1997'),
(1234, '2018-04-16', 'Nom=Sara Cin=EE1256', 'Redevable', 'Nom=Sarae Cin=EE1256', 2, 'EE1997'),
(1235, '2018-04-16', 'Nom=Sara Cin=EE1257', 'Redevable', 'Nom=Hasna  Cin=EE1257', 2, 'EE1997'),
(1236, '2017-04-16', 'Nom=Sara Cin=EE1258', 'Redevable', 'Nom=Souh Cin=EE1258', 2, 'EE1997'),
(1237, '2016-04-16', 'Nom=Sara Cin=EE1258', 'Redevable', 'Nom=Souhail Cin=EE1258', 2, 'EE1997'),
(1238, '2015-04-16', 'Nom=Sara Cin=EE1259', 'Redevable', 'Nom=Sarami Cin=EE1259', 2, 'EE1997'),
(1239, '2014-04-16', 'Nom=Sara Cin=EE1252', 'Redevable', 'Nom=lolita Cin=EE1252', 2, 'EE1997'),
(1240, '2015-04-16', 'Nom=Sara Cin=EE1253', 'Redevable', 'Nom=sosita  Cin=EE1253', 2, 'EE1997'),
(1241, '2016-04-16', 'Nom=Sara Cin=EE1254', 'Redevable', 'Nom=soumia Cin=EE1254', 2, 'EE1997'),
(1297, '2018-04-16', 'id=1234 nom=Abdekarim Khatabi3', 'Rue', NULL, 3, 'EE1997'),
(1470, '2018-04-16', 'id=1234 nom=Abdekarim Khatabi 2', 'Rue', NULL, 3, 'EE1997');

--
-- Contenu de la table `quartier`
--

INSERT INTO `quartier` (`ID`, `NOM`, `SECTEUR_CODEPOSTAL`) VALUES
(5, 'Massira1', 40140),
(6, 'Massira2', 40140),
(7, 'Massira3', 40140),
(8, 'Semlalia', 40000),
(9, 'La liberté', 40000),
(10, 'Souria', 40000),
(15, 'nakhil', 42000),
(16, 'les oranges', 42000),
(21, 'E''rafiq', 43000),
(22, 'E''charif', 43000);

--
-- Contenu de la table `redevable`
--

INSERT INTO `redevable` (`ID`, `ADRESSE`, `CIN`, `EMAIL`, `MOTDEPASSE`, `NIF`, `NOM`, `NUMTEL`, `PRENOM`, `SEXE`) VALUES
(51, 'Imm elbaraka 659 appt 21 Massira1 ', 'A33639', 'med.benmansour1997@gmail.com', '123456789', NULL, 'BENMANSOUR', '0612345678', 'MOHAMMED', 'Masculin'),
(231, 'none', 'EE793372', 'benmansour@hotmail.com', '2f4f8f758e9fb6d64b2a8bd80e1619c44795959c14127bff238e0d32a14deb1f', '', 'simo', '0659691266', 'simo', 'Masculin'),
(232, 'chtouka ait baha', 'EE527254', 'RTYUI@gmail.com', '34cbe3e208ab876b50393345253db3255f3a33539f61c834688cec4406e36466', NULL, 'zarbag', '0612345678', 'mehdi', NULL),
(233, 'bni mellal', 'EE65778', 'benmansour.demon@hotmail.com', 'ffd3c18a64c589321fe688192d2d173f1c3e45cdf303fe7b8bcbcd5a4ff12386', NULL, 'Bahlouli', '0612345678', 'Yassir', NULL),
(234, 'xxxxxxxxxxxxxxx', 'EE2323433', 'farah@gmail.com', 'fe6d604b24ad3e88837f59e854d1d7430c39d08fab7ca669eb323170745b6a5e', NULL, 'elHajoui', '0654353453454"("', 'farah', NULL),
(235, 'Niger', 'A533553', 'laouliadaousmane@gmail.com', '0ddfff23ff0c3f743f54afb7928ee4c2fc7e97a1234f4728e368973f771093a1', NULL, 'ADA AYA', '0612345678', 'Laouli', NULL),
(236, 'Av Abbdelkrim el khttabi fst ', 'EE666', 'zouani.younnes@gmail.com', '90156e85726038f00e421088ce2803c3afafd58376e535a32173f5e81d2281e7', NULL, 'Zouani', '0612345654', 'Youness', NULL),
(237, 'hay enakhil  villa nr 36 el kella', 'Y451698', 'happy01bird@gmail.com', 'e623c3c3367f4b889a75107b9f548f21ab86117ee71538473a5798d466a11be6', NULL, 'ELHAJOUI', '0673813483', 'FARAH', NULL),
(238, 'sidimkhlouf', 'EE34567', 'chaimaahletouate@gmail.com', 'c530031ad1a54a78a006e00bd62efa7339dc1d220c25450db8c64d38f86f53ae', NULL, 'ahletouate', '0612345678', 'chaima', NULL);

--
-- Contenu de la table `rue`
--

INSERT INTO `rue` (`ID`, `NOM`, `QUARTIER_ID`) VALUES
(11, 'saada', 5),
(12, 'Ibn Aouam', 5),
(13, 'abdelkrim elkhtabi', 21),
(17, 'andalouss', 22),
(18, 'ahltouate', 5),
(19, 'ghrnata', 21),
(25, 'octobre', 21),
(30, 'decembre', 21),
(35, 'novembre', 15);

--
-- Contenu de la table `secteur`
--

INSERT INTO `secteur` (`CODEPOSTAL`, `NOM`) VALUES
(40000, 'Gueliz'),
(40140, 'Massira'),
(42000, 'daoudiate'),
(43000, 'sidi youssef');

--
-- Contenu de la table `sequence`
--

INSERT INTO `sequence` (`SEQ_NAME`, `SEQ_COUNT`) VALUES
('SEQ_GEN', '950');

--
-- Contenu de la table `tauxretard`
--

INSERT INTO `tauxretard` (`ID`, `DATEAPPLICATION`) VALUES
(57, '2018-03-01'),
(58, '2018-04-29');

--
-- Contenu de la table `tauxretarditem`
--

INSERT INTO `tauxretarditem` (`ID`, `TAUXAUTREMOIS`, `TAUXPREMIERMOIS`, `TAUXRETARD_ID`, `CATEGORIETERRAIN_ID`) VALUES
(58, '0.050', '0.100', 57, 1),
(59, '0.000', '0.000', 57, 2),
(60, '0.000', '0.000', 57, 3),
(61, '0.000', '0.000', 57, 4),
(451, '0.050', '0.500', 58, 1),
(452, '0.050', '0.500', 58, 2),
(453, '0.050', '0.500', 58, 3),
(454, '0.050', '0.500', 58, 4);

--
-- Contenu de la table `tauxtaxe`
--

INSERT INTO `tauxtaxe` (`ID`, `DATEAPPLICATION`) VALUES
(52, '2018-03-01'),
(53, '2018-04-29'),
(54, '2018-10-20');

--
-- Contenu de la table `tauxtaxeitem`
--

INSERT INTO `tauxtaxeitem` (`ID`, `TAUX`, `TAUXTAXE_ID`, `CATEGORIETERRAIN_ID`) VALUES
(53, '15', 52, 1),
(54, '10', 52, 2),
(55, '10', 52, 3),
(56, '10', 52, 4),
(501, '12', 53, 1),
(502, '14', 53, 2),
(503, '17', 53, 3),
(504, '12', 53, 4),
(808, '12', 54, 1),
(809, '12', 54, 2),
(810, '12', 54, 3),
(811, '12', 54, 4);

--
-- Contenu de la table `taxeannuelle`
--

INSERT INTO `taxeannuelle` (`ID`, `ANNEE`, `AUTREMOISRETARD`, `DATEPRESENTAION`, `DATETAXE`, `MONTANT`, `MONTANTRETARD`, `MONTANTTOTAL`, `NBRMOISRETARD`, `PREMIERMOISRETARD`, `TAUXRETARDITEM_ID`, `TAUXTAXEITEM_ID`, `TERRAIN_NUMEROLOT`, `UTILISATEUR_MATRICULE`) VALUES
(0, 2014, '213', '2014-07-05', '2014-04-01', '21323', '231', '12313123', 5, '2132', 58, 54, 121344, '13089122'),
(201, 2015, '3750', '2015-05-19', '2015-04-01', '7500', '4500', '12000', 11, '750', 58, 53, 121344, NULL),
(235, 2016, '214', '2016-07-01', '2016-04-01', '21323', '231', '12313123', 3, '2132', 58, 54, 121344, '13089122'),
(351, 2017, '0', '2017-04-03', '2017-04-01', '7500', '0', '7500', 0, '0', 58, 53, 121344, NULL),
(353, 2018, '0', '2018-04-30', '2018-01-01', '75000', '0', '75000', 0, '0', 451, 53, 543, 'EE18'),
(354, 2018, '0', '2018-04-30', '2018-01-01', '3000', '0', '3000', 0, '0', 453, 55, 121352, NULL),
(355, 2018, '0', '2018-04-30', '2018-01-01', '5000', '0', '5000', 0, '0', 453, 55, 121351, 'EE18'),
(356, 2018, '0', '2018-04-30', '2018-01-01', '8640', '0', '8640', 0, '0', 454, 56, 121350, 'EE18'),
(357, 2018, '0', '2018-04-30', '2018-01-01', '2740', '0', '2740', 0, '0', 452, 54, 121347, 'EE18'),
(358, 2018, '0', '2018-04-30', '2018-01-01', '4640', '0', '4640', 0, '0', 452, 54, 121348, 'EE18'),
(359, 2018, '0', '2018-04-30', '2018-01-01', '8650', '0', '8650', 0, '0', 452, 54, 121349, 'EE18'),
(554, 0, '0', '2018-04-30', NULL, '0', '0', '0', 0, '0', NULL, NULL, NULL, NULL),
(555, 0, '0', '2018-04-30', NULL, '0', '0', '0', 0, '0', NULL, NULL, NULL, NULL),
(556, 2018, '0', '2018-04-30', '2018-01-01', '12000', '0', '12000', 0, '0', 454, 56, 121354, 'EE18'),
(557, 2018, '0', '2018-04-30', '2018-01-01', '8000', '0', '8000', 0, '0', 452, 54, 121353, '13089122');

--
-- Contenu de la table `terrain`
--

INSERT INTO `terrain` (`NUMEROLOT`, `DATEDERNIERNOTIFICATION`, `CPADRESSE`, `DATEACHAT`, `DATEDECLARATION`, `SURFACE`, `TYPEDERNIERNOTIFICATION`, `CATEGORIETERRAIN_ID`, `REDEVABLE_ID`, `RUE_ID`, `DERNIERPAIEMENT_ID`) VALUES
(543, '2018-04-01', 'azertyuijhgfd', '2018-04-30', '2018-02-04', '5000', 0, 1, 51, 12, 353),
(121344, '2018-01-18', '13', '2018-02-28', '2018-02-28', '500', 0, 1, 51, 12, 351),
(121345, NULL, 'bloc B', '2018-04-30', '2018-04-30', '650', 0, NULL, 231, NULL, NULL),
(121346, NULL, '', '2018-04-30', '2018-04-30', '700', 0, 3, 51, NULL, NULL),
(121347, NULL, 'bloc c', '2018-04-30', '2018-04-30', '274', 0, 2, 236, 13, 357),
(121348, NULL, 'bloc D', '2018-04-30', '2018-04-30', '464', 0, 2, 236, 35, 358),
(121349, NULL, 'bloc E', '2018-04-30', '2018-04-30', '865', 0, 2, 236, 17, 359),
(121350, NULL, 'bloc F', '2018-04-30', '2018-04-30', '864', 0, 4, 237, 17, 356),
(121351, NULL, 'bloc F', '2018-04-30', '2018-04-30', '500', 0, 3, 237, 25, 355),
(121352, NULL, 'bloc G', '2018-04-30', '2018-04-30', '300', 0, 3, 237, 35, 354),
(121353, NULL, '17', '2018-04-30', '2018-04-30', '800', 0, 2, 238, 35, 557),
(121354, NULL, '18', '2018-04-30', '2018-04-30', '1200', 0, 4, 238, 18, 556),
(121355, NULL, 'DHDHD', '2018-04-30', '2018-04-30', '5656', 0, 1, 51, 11, NULL);

--
-- Contenu de la table `utilisateur`
--

INSERT INTO `utilisateur` (`MATRICULE`, `EMAIL`, `MOTDEPASSE`, `NOM`, `PRENOM`, `TYPE`, `SECTEUR_CODEPOSTAL`) VALUES
('13089122', 'benmansour.demon@hotmail.com', '13089122', 'BENMANSOUR', 'MOHAMMED', 3, 40140),
('EE18', 'sarabouhamdane72@gmail.com', 'ytyD578', 'bouhamdane', 'sara', 2, 40140),
('EE1997', 'aniela@gmail.com', '13089122', 'ahletouate', 'chaima', 1, 40140),
('EE2344', 'zouani.younes@gmail.com', 'YUUYG64764', 'a', 'zouani', 1, 40000);
SET FOREIGN_KEY_CHECKS=1;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
