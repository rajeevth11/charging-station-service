CREATE SCHEMA `charging_station` ;

CREATE  TABLE `charging_station`.`company` (
  `id` INT NOT NULL AUTO_INCREMENT COMMENT 'Id of the Company' ,
  `parent_company_id` INT NULL COMMENT 'Parent company id if the company is a child one. ' ,
  `name` VARCHAR(100) NOT NULL COMMENT 'Name of the Company.' ,
  PRIMARY KEY (`id`) )
COMMENT = 'Attributes of the Company entity.';

CREATE  TABLE `charging_station`.`station` (
  `id` INT NOT NULL AUTO_INCREMENT COMMENT 'Id of the station.' ,
  `name` VARCHAR(100) NOT NULL COMMENT 'Name of the station.' ,
  `longitude` DOUBLE NOT NULL COMMENT 'Longitude of the station.' ,
  `latitude` DOUBLE NOT NULL COMMENT 'Latitude of the station.' ,
  `company_id` INT NOT NULL COMMENT 'Company id the station owned.' ,
  PRIMARY KEY (`id`) ,
  INDEX `company_fk_idx` (`company_id` ASC) ,
  CONSTRAINT `company_fk`
    FOREIGN KEY (`company_id` )
    REFERENCES `charging_station`.`company` (`id` )
    ON DELETE CASCADE
    ON UPDATE CASCADE)
COMMENT = 'Attributes of the entity Station.';
