-- MySQL Script generated by MySQL Workbench
-- Sun Aug 18 20:01:34 2024
-- Model: New Model    Version: 1.0
-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema restaurantdb
-- -----------------------------------------------------
DROP SCHEMA IF EXISTS `restaurantdb` ;

-- -----------------------------------------------------
-- Schema restaurantdb
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `restaurantdb` DEFAULT CHARACTER SET utf8 ;
USE `restaurantdb` ;

-- -----------------------------------------------------
-- Table `restaurantdb`.`restaurant`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `restaurantdb`.`restaurant` ;

CREATE TABLE IF NOT EXISTS `restaurantdb`.`restaurant` (
  `id_restaurant` CHAR(36) NOT NULL DEFAULT (UUID()),
  `name` VARCHAR(45) NULL,
  `location` VARCHAR(45) NULL,
  `id_hotel` CHAR(36) NULL,
  PRIMARY KEY (`id_restaurant`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `restaurantdb`.`dish`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `restaurantdb`.`dish` ;

CREATE TABLE IF NOT EXISTS `restaurantdb`.`dish` (
  `id_dish` CHAR(36) NOT NULL DEFAULT (UUID()),
  `name` VARCHAR(45) NULL,
  `price` DOUBLE NULL,
  `description` VARCHAR(200) NULL,
  `restaurant_id_restaurant` CHAR(36) NOT NULL DEFAULT (UUID()),
  PRIMARY KEY (`id_dish`),
  INDEX `fk_dish_restaurant_idx` (`restaurant_id_restaurant` ASC) VISIBLE,
  CONSTRAINT `fk_dish_restaurant`
    FOREIGN KEY (`restaurant_id_restaurant`)
    REFERENCES `restaurantdb`.`restaurant` (`id_restaurant`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;



SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;


INSERT INTO `restaurantdb`.`restaurant` (`id_restaurant`, `name`, `location`)
VALUES 
    ('011febee-b7a4-4691-b5a0-29fdcd4618fa', 'Restaurant Example 1', 'Location 6'),
    ('022febee-b7a4-4691-b5a0-29fdcd4618fa', 'Restaurant Example 2', 'Location 7'),
    ('033febee-b7a4-4691-b5a0-29fdcd4618fa', 'Restaurant Example 3', 'Location 8'),
    ('044febee-b7a4-4691-b5a0-29fdcd4618fa', 'Restaurant Example 4', 'Location 9'),
    ('055febee-b7a4-4691-b5a0-29fdcd4618fa', 'Restaurant Example 5', 'Location 1');


INSERT INTO `restaurantdb`.`dish` (`id_dish`, `name`, `price`, `description`, `restaurant_id_restaurant`)
VALUES 
    ('001febee-b7a4-4691-b5a0-29fdcd4618fa', 'Spaghetti Carbonara', 12.50, 'Classic Italian pasta with creamy sauce', '011febee-b7a4-4691-b5a0-29fdcd4618fa'),
    ('002febee-b7a4-4691-b5a0-29fdcd4618fa', 'Margherita Pizza', 10.00, 'Traditional pizza with tomato and mozzarella', '022febee-b7a4-4691-b5a0-29fdcd4618fa'),
    ('003febee-b7a4-4691-b5a0-29fdcd4618fa', 'Caesar Salad', 8.75, 'Fresh salad with Caesar dressing and croutons', '033febee-b7a4-4691-b5a0-29fdcd4618fa'),
    ('004febee-b7a4-4691-b5a0-29fdcd4618fa', 'Beef Burger', 15.00, 'Juicy beef burger with lettuce and tomato', '044febee-b7a4-4691-b5a0-29fdcd4618fa'),
    ('005febee-b7a4-4691-b5a0-29fdcd4618fa', 'Grilled Salmon', 18.00, 'Salmon fillet grilled to perfection', '055febee-b7a4-4691-b5a0-29fdcd4618fa');







