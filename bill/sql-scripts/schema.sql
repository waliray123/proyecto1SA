-- Eliminar el esquema si existe
DROP SCHEMA IF EXISTS `billingdb`;

-- Crear el esquema de la base de datos
CREATE DATABASE IF NOT EXISTS `billingdb` DEFAULT CHARACTER SET utf8;
USE `billingdb`;

-- Eliminar la tabla `bill` si existe
DROP TABLE IF EXISTS `	`.`bill`;

-- Crear la tabla `bill`
CREATE TABLE IF NOT EXISTS `billingdb`.`bill` (
    `id` CHAR(36) NOT NULL DEFAULT (UUID()),  -- Usar CHAR(36) para UUID
    `type` VARCHAR(50) NOT NULL,
    `id_location` CHAR(36) NOT NULL,  -- Eliminar el DEFAULT UUID(), se debe establecer al insertar
    `start_date` DATE NOT NULL,
    `end_date` DATE NOT NULL,
    `total` DECIMAL(10, 2) NOT NULL,
    `user_id` CHAR(36) NOT NULL,  -- Eliminar el DEFAULT UUID(), se debe establecer al insertar
    `reservation_id` CHAR(36) NULL,
    PRIMARY KEY (`id`)
) ENGINE=InnoDB;

-- Eliminar la tabla `bill_description` si existe
DROP TABLE IF EXISTS `billingdb`.`bill_description`;

-- Crear la tabla `bill_description`
CREATE TABLE IF NOT EXISTS `billingdb`.`bill_description` (
    `id` CHAR(36) NOT NULL DEFAULT (UUID()),  -- Usar CHAR(36) para UUID
    `bill_id` CHAR(36) NOT NULL,
    `type` VARCHAR(50) NOT NULL,
    `id_product` CHAR(36) NOT NULL,  -- Eliminar el DEFAULT UUID(), se debe establecer al insertar
    `unit_price` DECIMAL(10, 2) NOT NULL,
    `quantity` INT NOT NULL,
    PRIMARY KEY (`id`),
    INDEX `fk_bill_description_bill_idx` (`bill_id` ASC) VISIBLE,
    CONSTRAINT `fk_bill_description_bill`
        FOREIGN KEY (`bill_id`)
        REFERENCES `billingdb`.`bill` (`id`)
        ON DELETE NO ACTION
        ON UPDATE NO ACTION
) ENGINE=InnoDB;

insert into bill (id, type, id_location, start_date, end_date, total, user_id) values ('a0f88518-d987-4f2e-8520-08847f8654b8', 'RESTAURANT', '011febee-b7a4-4691-b5a0-29fdcd4618fa', '2024-09-09', '2024-09-09', 12.5,'1dc06405-e451-4095-b76b-fcccaeaaac96');
insert into bill (id, type, id_location, start_date, end_date, total, user_id) values ('a1f88518-d987-4f2e-8520-08847f8654b8', 'RESTAURANT', '011febee-b7a4-4691-b5a0-29fdcd4618fa', '2024-09-08', '2024-09-08', 12.5,'1dc06405-e451-4095-b76b-fcccaeaaac96');
insert into bill (id, type, id_location, start_date, end_date, total, user_id) values ('a2f88518-d987-4f2e-8520-08847f8654b8', 'RESTAURANT', '011febee-b7a4-4691-b5a0-29fdcd4618fa', '2024-09-07', '2024-09-07', 12.5,'2dc06405-e451-4095-b76b-fcccaeaaac96');
insert into bill (id, type, id_location, start_date, end_date, total, user_id) values ('a3f88518-d987-4f2e-8520-08847f8654b8', 'RESTAURANT', '011febee-b7a4-4691-b5a0-29fdcd4618fa', '2024-09-06', '2024-09-06', 12.5,'1dc06405-e451-4095-b76b-fcccaeaaac96');
insert into bill (id, type, id_location, start_date, end_date, total, user_id) values ('a4f88518-d987-4f2e-8520-08847f8654b8', 'RESTAURANT', '011febee-b7a4-4691-b5a0-29fdcd4618fa', '2024-09-05', '2024-09-05', 12.5,'2dc06405-e451-4095-b76b-fcccaeaaac96');
insert into bill (id, type, id_location, start_date, end_date, total, user_id) values ('a5f88518-d987-4f2e-8520-08847f8654b8', 'RESTAURANT', '011febee-b7a4-4691-b5a0-29fdcd4618fa', '2024-09-04', '2024-09-04', 12.5,'1dc06405-e451-4095-b76b-fcccaeaaac96');
insert into bill (id, type, id_location, start_date, end_date, total, user_id) values ('a6f88518-d987-4f2e-8520-08847f8654b8', 'RESTAURANT', '011febee-b7a4-4691-b5a0-29fdcd4618fa', '2024-09-03', '2024-09-03', 12.5,'3dc06405-e451-4095-b76b-fcccaeaaac96');
insert into bill (id, type, id_location, start_date, end_date, total, user_id) values ('a7f88518-d987-4f2e-8520-08847f8654b8', 'RESTAURANT', '011febee-b7a4-4691-b5a0-29fdcd4618fa', '2024-09-02', '2024-09-02', 12.5,'1dc06405-e451-4095-b76b-fcccaeaaac96');
insert into bill (id, type, id_location, start_date, end_date, total, user_id) values ('a8f88518-d987-4f2e-8520-08847f8654b8', 'RESTAURANT', '011febee-b7a4-4691-b5a0-29fdcd4618fa', '2024-09-01', '2024-09-01', 12.5,'3dc06405-e451-4095-b76b-fcccaeaaac96');
insert into bill (id, type, id_location, start_date, end_date, total, user_id) values ('a9f88518-d987-4f2e-8520-08847f8654b8', 'RESTAURANT', '011febee-b7a4-4691-b5a0-29fdcd4618fa', '2024-09-09', '2024-09-09', 12.5,'4dc06405-e451-4095-b76b-fcccaeaaac96');



insert into bill_description (id, bill_id, type, id_product, unit_price, quantity) values (UUID(), 'a0f88518-d987-4f2e-8520-08847f8654b8', 'dish', '001febee-b7a4-4691-b5a0-29fdcd4618fa', 12.5, 1);
insert into bill_description (id, bill_id, type, id_product, unit_price, quantity) values (UUID(), 'a1f88518-d987-4f2e-8520-08847f8654b8', 'dish', '001febee-b7a4-4691-b5a0-29fdcd4618fa', 12.5, 1);
insert into bill_description (id, bill_id, type, id_product, unit_price, quantity) values (UUID(), 'a2f88518-d987-4f2e-8520-08847f8654b8', 'dish', '001febee-b7a4-4691-b5a0-29fdcd4618fa', 12.5, 1);
insert into bill_description (id, bill_id, type, id_product, unit_price, quantity) values (UUID(), 'a3f88518-d987-4f2e-8520-08847f8654b8', 'dish', '001febee-b7a4-4691-b5a0-29fdcd4618fa', 12.5, 1);
insert into bill_description (id, bill_id, type, id_product, unit_price, quantity) values (UUID(), 'a4f88518-d987-4f2e-8520-08847f8654b8', 'dish', '001febee-b7a4-4691-b5a0-29fdcd4618fa', 12.5, 1);
insert into bill_description (id, bill_id, type, id_product, unit_price, quantity) values (UUID(), 'a5f88518-d987-4f2e-8520-08847f8654b8', 'dish', '001febee-b7a4-4691-b5a0-29fdcd4618fa', 12.5, 1);
insert into bill_description (id, bill_id, type, id_product, unit_price, quantity) values (UUID(), 'a6f88518-d987-4f2e-8520-08847f8654b8', 'dish', '001febee-b7a4-4691-b5a0-29fdcd4618fa', 12.5, 1);
insert into bill_description (id, bill_id, type, id_product, unit_price, quantity) values (UUID(), 'a7f88518-d987-4f2e-8520-08847f8654b8', 'dish', '001febee-b7a4-4691-b5a0-29fdcd4618fa', 12.5, 1);
insert into bill_description (id, bill_id, type, id_product, unit_price, quantity) values (UUID(), 'a8f88518-d987-4f2e-8520-08847f8654b8', 'dish', '001febee-b7a4-4691-b5a0-29fdcd4618fa', 12.5, 1);
insert into bill_description (id, bill_id, type, id_product, unit_price, quantity) values (UUID(), 'a9f88518-d987-4f2e-8520-08847f8654b8', 'dish', '001febee-b7a4-4691-b5a0-29fdcd4618fa', 12.5, 1);


insert into bill (id, type, id_location, start_date, end_date, total, user_id) values ('c1f88518-d987-4f2e-8520-08847f8654b8', 'HOTEL', '111febee-b7a4-4691-b5a0-29fdcd4618fa', '2024-09-07', '2024-09-08', 100,'1dc06405-e451-4095-b76b-fcccaeaaac96');
insert into bill_description (id, bill_id, type, id_product, unit_price, quantity) values (UUID(), 'c1f88518-d987-4f2e-8520-08847f8654b8', 'room', '111febee-b7a4-4691-b5a0-29fdcd4618fb', 100, 1);

insert into bill (id, type, id_location, start_date, end_date, total, user_id) values ('c2f88518-d987-4f2e-8520-08847f8654b8', 'HOTEL', '222febee-b7a4-4691-b5a0-29fdcd4618fa', '2024-09-07', '2024-09-08', 100,'2dc06405-e451-4095-b76b-fcccaeaaac96');
insert into bill_description (id, bill_id, type, id_product, unit_price, quantity) values (UUID(), 'c2f88518-d987-4f2e-8520-08847f8654b8', 'room', '222febee-b7a4-4691-b5a0-29fdcd4618fb', 100, 1);

insert into bill (id, type, id_location, start_date, end_date, total, user_id) values ('c3f88518-d987-4f2e-8520-08847f8654b8', 'HOTEL', '333febee-b7a4-4691-b5a0-29fdcd4618fa', '2024-09-07', '2024-09-08', 100,'3dc06405-e451-4095-b76b-fcccaeaaac96');
insert into bill_description (id, bill_id, type, id_product, unit_price, quantity) values (UUID(), 'c3f88518-d987-4f2e-8520-08847f8654b8', 'room', '333febee-b7a4-4691-b5a0-29fdcd4618fb', 100, 1);

insert into bill (id, type, id_location, start_date, end_date, total, user_id) values ('c4f88518-d987-4f2e-8520-08847f8654b8', 'HOTEL', '444febee-b7a4-4691-b5a0-29fdcd4618fa', '2024-09-07', '2024-09-08', 100,'4dc06405-e451-4095-b76b-fcccaeaaac96');
insert into bill_description (id, bill_id, type, id_product, unit_price, quantity) values (UUID(), 'c4f88518-d987-4f2e-8520-08847f8654b8', 'room', '444febee-b7a4-4691-b5a0-29fdcd4618fb', 100, 1);

insert into bill (id, type, id_location, start_date, end_date, total, user_id) values ('c5f88518-d987-4f2e-8520-08847f8654b8', 'HOTEL', '555febee-b7a4-4691-b5a0-29fdcd4618fa', '2024-09-07', '2024-09-08', 100,'5dc06405-e451-4095-b76b-fcccaeaaac96');
insert into bill_description (id, bill_id, type, id_product, unit_price, quantity) values (UUID(), 'c5f88518-d987-4f2e-8520-08847f8654b8', 'room', '555febee-b7a4-4691-b5a0-29fdcd4618fb', 100, 1);













