-- MySQL Script to Create Reservation and Reservation_Description Tables

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema reservationdb
-- -----------------------------------------------------
DROP SCHEMA IF EXISTS `reservationdb` ;

CREATE SCHEMA IF NOT EXISTS `reservationdb` DEFAULT CHARACTER SET utf8 ;
USE `reservationdb` ;

-- -----------------------------------------------------
-- Table `reservationdb`.`Reservation`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `reservationdb`.`reservation` ;

CREATE TABLE IF NOT EXISTS `reservationdb`.`reservation` (
  `id` CHAR(36) NOT NULL DEFAULT (UUID()),
  `id_location` CHAR(36) NOT NULL DEFAULT (UUID()),
  `date_start` DATE NOT NULL,
  `date_end` DATE NOT NULL,
  `total` DOUBLE NOT NULL,
  `status` ENUM('PENDING', 'CONFIRMED', 'CANCELLED') NOT NULL,
  `user` CHAR(36) NOT NULL DEFAULT (UUID()),
  PRIMARY KEY (`id`))
ENGINE = InnoDB;

-- -----------------------------------------------------
-- Table `reservationdb`.`Reservation_Description`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `reservationdb`.`reservation_description` ;

CREATE TABLE IF NOT EXISTS `reservationdb`.`reservation_description` (
  `id` CHAR(36) NOT NULL DEFAULT (UUID()),
  `id_product` CHAR(36) NOT NULL DEFAULT (UUID()),
  `unit_price` DOUBLE NOT NULL,
  `quantity` INT NOT NULL,
  `reservation_id` CHAR(36) NOT NULL DEFAULT (UUID()),
  PRIMARY KEY (`id`),
  INDEX `fk_reservation_description_reservation_idx` (`reservation_id` ASC) VISIBLE,
  CONSTRAINT `fk_reservation_description_reservation`
    FOREIGN KEY (`reservation_id`)
    REFERENCES `reservationdb`.`reservation` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

-- Reset Foreign Key Checks
SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;

