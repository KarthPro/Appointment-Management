-- phpMyAdmin SQL Dump
-- version 4.8.5
-- https://www.phpmyadmin.net/
--
-- Hôte : 127.0.0.1:3306
-- Généré le :  sam. 18 jan. 2020 à 17:18
-- Version du serveur :  5.7.26
-- Version de PHP :  7.2.18

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de données :  `bdd2_projet_test_1`
--

-- --------------------------------------------------------

--
-- Structure de la table `bilan_postseance`
--

DROP TABLE IF EXISTS `bilan_postseance`;
CREATE TABLE IF NOT EXISTS `bilan_postseance` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `expression` varchar(50) NOT NULL,
  `posture` varchar(50) NOT NULL,
  `comportement` varchar(50) NOT NULL,
  `id_seance` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `Bilan_PostSeance_seance_AK` (`id_seance`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Structure de la table `categorieage`
--

DROP TABLE IF EXISTS `categorieage`;
CREATE TABLE IF NOT EXISTS `categorieage` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `nomCategorie` varchar(50) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Structure de la table `moyenconnu`
--

DROP TABLE IF EXISTS `moyenconnu`;
CREATE TABLE IF NOT EXISTS `moyenconnu` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `intitule` varchar(50) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Structure de la table `personne`
--

DROP TABLE IF EXISTS `personne`;
CREATE TABLE IF NOT EXISTS `personne` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `nom` varchar(50) NOT NULL,
  `prenom` varchar(50) NOT NULL,
  `email` varchar(50) NOT NULL,
  `mdp` varchar(50) NOT NULL,
  `isAdmin` tinyint(1) NOT NULL,
  `id_CategorieAge` int(11) NOT NULL,
  `id_MoyenConnu` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `Personne_CategorieAge_FK` (`id_CategorieAge`),
  KEY `Personne_MoyenConnu0_FK` (`id_MoyenConnu`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Structure de la table `profession`
--

DROP TABLE IF EXISTS `profession`;
CREATE TABLE IF NOT EXISTS `profession` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `nomProfession` varchar(50) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Structure de la table `seance`
--

DROP TABLE IF EXISTS `seance`;
CREATE TABLE IF NOT EXISTS `seance` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `date` date NOT NULL,
  `prix` float NOT NULL,
  `retard` tinyint(1) NOT NULL,
  `id_Personne` int(11) NOT NULL,
  `id_TypePaiement` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `seance_Personne_FK` (`id_Personne`),
  KEY `seance_TypePaiement0_FK` (`id_TypePaiement`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Structure de la table `travaille`
--

DROP TABLE IF EXISTS `travaille`;
CREATE TABLE IF NOT EXISTS `travaille` (
  `id` int(11) NOT NULL,
  `id_Profession` int(11) NOT NULL,
  `date` date NOT NULL,
  PRIMARY KEY (`id`,`id_Profession`),
  KEY `travaille_Profession0_FK` (`id_Profession`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Structure de la table `typepaiement`
--

DROP TABLE IF EXISTS `typepaiement`;
CREATE TABLE IF NOT EXISTS `typepaiement` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `type` varchar(50) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Contraintes pour les tables déchargées
--

--
-- Contraintes pour la table `bilan_postseance`
--
ALTER TABLE `bilan_postseance`
  ADD CONSTRAINT `Bilan_PostSeance_seance_FK` FOREIGN KEY (`id_seance`) REFERENCES `seance` (`id`);

--
-- Contraintes pour la table `personne`
--
ALTER TABLE `personne`
  ADD CONSTRAINT `Personne_CategorieAge_FK` FOREIGN KEY (`id_CategorieAge`) REFERENCES `categorieage` (`id`),
  ADD CONSTRAINT `Personne_MoyenConnu0_FK` FOREIGN KEY (`id_MoyenConnu`) REFERENCES `moyenconnu` (`id`);

--
-- Contraintes pour la table `seance`
--
ALTER TABLE `seance`
  ADD CONSTRAINT `seance_Personne_FK` FOREIGN KEY (`id_Personne`) REFERENCES `personne` (`id`),
  ADD CONSTRAINT `seance_TypePaiement0_FK` FOREIGN KEY (`id_TypePaiement`) REFERENCES `typepaiement` (`id`);

--
-- Contraintes pour la table `travaille`
--
ALTER TABLE `travaille`
  ADD CONSTRAINT `travaille_Personne_FK` FOREIGN KEY (`id`) REFERENCES `personne` (`id`),
  ADD CONSTRAINT `travaille_Profession0_FK` FOREIGN KEY (`id_Profession`) REFERENCES `profession` (`id`);
COMMIT;

---
---Trigger un patient ne peux avoir plus de 3 rendez vous par jour / La psy ne peux pas travailler plus de 10h par jour
---

CREATE TRIGGER before_insert_seance BEFORE INSERT ON seance FOR EACH ROW 

DECLARE 
nb_seance_psy INT DEFAULT 
nb_seance_patient INT DEFAULT

BEGIN

SELECT SUM(*) INTO nb_seance_psy  FROM seance WHERE ( ( date BETWEEN TRUNC(NEW.date, DATE) AND TRUNC(NEW.date+1, DATE)

SELECT SUM(*) INTO nb_seance_patient  FROM seance WHERE ( ( date BETWEEN TRUNC(NEW.date, DATE) AND TRUNC(NEW.date+1, DATE) ) AND id_Personne = NEW.id_Personne

IF (nb_seance_psy >= 20) THEN
	RAISE_APPLICATION_ERROR(-20501, 'Insertion impossible : la psy travaille deja 10h ce jour ')
ELSEIF (nb_seance_patient >= 3) THEN
	RAISE_APPLICATION_ERROR(-20501, 'Insertion impossible : le patient a plus de 3 seance le même jour ')

END IF
END 

---
--- Trigger La psy ne peux pas travailler plus de 10h par jour
---

CREATE TRIGGER max_10h_psy BEFORE INSERT ON seance FOR EACH ROW
DECLARE 

nb_seance INT DEFAULT

BEGIN

SELECT SUM(*) INTO nb_seance   FROM seance WHERE ( ( date BETWEEN TRUNC(NEW.date, DATE)
AND TRUNC(NEW.date+1, DATE) ) AND id_Personne = NEW.id_Personne 

IF (nb_seance >2) THEN
	RAISE_APPLICATION_ERROR(-20501, 'Insertion impossible')
END IF
END

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
