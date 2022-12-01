-- -----------------------------------------------------
-- Schema full-stack-ecommerce
-- -----------------------------------------------------
DROP SCHEMA IF EXISTS `Sim-Spot-ecommerce`;

CREATE SCHEMA `Sim-Spot-ecommerce`;
USE `Sim-Spot-ecommerce` ;

-- -----------------------------------------------------
-- Table `Sim-Spot-ecommerce`.`product_category`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `Sim-Spot-ecommerce`.`product_category` (
  `id` BIGINT(20) NOT NULL AUTO_INCREMENT,
  `category_name` VARCHAR(255) NULL DEFAULT NULL,
  PRIMARY KEY (`id`))
ENGINE=InnoDB
AUTO_INCREMENT = 1;

-- -----------------------------------------------------
-- Table `Sim-Spot-ecommerce`.`product`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `Sim-Spot-ecommerce`.`product` (
  `product_id` BIGINT(20) NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(255) DEFAULT NULL,
  `description` VARCHAR(255) DEFAULT NULL,
  `price` DECIMAL(13,2) DEFAULT NULL,
  `image_url` VARCHAR(255) DEFAULT NULL,
  `status` BIT DEFAULT 1,
   `date_created` DATETIME(6) DEFAULT NULL,
  `date_updated` DATETIME(6) DEFAULT NULL,
  `category_id` BIGINT(20) NOT NULL,
  `location` VARCHAR(255) DEFAULT NULL,
  `delivery_method` BIT DEFAULT 1,
  `orginalboxes` BIT DEFAULT 1,
  PRIMARY KEY (`product_id`),
  KEY `fk_category` (`category_id`),
  CONSTRAINT `fk_category_name` FOREIGN KEY (`category_id`) REFERENCES `product_category` (`id`)
) 
ENGINE=InnoDB
AUTO_INCREMENT = 1;



INSERT INTO product_category(category_name) VALUES ('Fanatec');
INSERT INTO product_category(category_name) VALUES ('Logitech');
INSERT INTO product_category(category_name) VALUES ('Thrustmaster');
INSERT INTO product_category(category_name) VALUES ('Sim Cube');
INSERT INTO product_category(category_name) VALUES ('Moza');

INSERT INTO product ( name, description, location, image_url, status,
price, category_id, date_created,delivery_method)
VALUES ('G29', 'Second hand G29 - No shifter included', 'Ireland',
'assets/images/products/placeholder.png'
,1,150,4, NOW(), 1);


