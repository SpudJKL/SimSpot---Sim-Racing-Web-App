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

USE `Sim-Spot-ecommerce` ;

INSERT INTO product ( name, description, price, image_url, status, date_created, date_updated, category_id, location, delivery_method, orginalboxes)
VALUES ('Fanatec DD w/o Pedals', 'Second hand DD - No pedals included. Comes with 8nm boost kit. Barely used.', 450, 'assets/images/products/placeholder.png', 1, NOW(), NOW(), 1, 'Ireland', 1,0);

INSERT INTO product ( name, description, price, image_url, status, date_created, date_updated, category_id, location, delivery_method, orginalboxes)
VALUES ('Thrustmaster TX racing with pedals', 'Bought for me as a christmas present, and I have since upgrade my setup. Like new', 515, 'assets/images/products/txracing.png', 1, NOW(), NOW(), 4, 'Ireland', 1,1);

INSERT INTO product ( name, description, price, image_url, status, date_created, date_updated, category_id, location, delivery_method, orginalboxes)
VALUES ('Sim Cube Pro 2 NEW', 'Just bought. Brand new. Selling as I need the cash.', 1474, 'assets/images/products/simcubepro.jpg', 1, NOW(), NOW(), 5, 'UK', 1,1);

INSERT INTO product ( name, description, price, image_url, status, date_created, date_updated, category_id, location, delivery_method, orginalboxes)
VALUES ('Moza R5 Bundle', '3 month old bundle, upgraded to a Fanatec DD. Torque wasnt enough for me but great starter wheel. Comes with original boxes.', 600, 'assets/images/products/mozabundle.jpg', 1, NOW(), NOW(), 6, 'Nothern Ireland', 1,1);

INSERT INTO roles(name) VALUES('ROLE_USER');
INSERT INTO roles(name) VALUES('ROLE_MODERATOR');
INSERT INTO roles(name) VALUES('ROLE_ADMIN');

ALTER TABLE customer_role ADD CONSTRAINT fk_customer_id FOREIGN KEY (customer_id) REFERENCES (customer);

SET FOREIGN_KEY_CHECKS=1;

truncate customer;
truncate orders;
truncate order_item;
truncate address;

ALTER TABLE customer ADD UNIQUE(email);

CREATE TABLE IF NOT EXISTS `Sim-Spot-ecommerce`.`users` (
  `id` BIGINT(20) NOT NULL AUTO_INCREMENT,
  `email` VARCHAR(50) DEFAULT NULL,
  `password` VARCHAR(120) DEFAULT NULL,
  `username` VARCHAR(20) DEFAULT NULL,
  PRIMARY KEY (`id`))

ENGINE=InnoDB
AUTO_INCREMENT = 1;

CREATE TABLE IF NOT EXISTS `Sim-Spot-ecommerce`.`user_roles` (
  `user_id` BIGINT(20) NOT NULL,
  `role_id` int(11)NOT NULL,
  PRIMARY KEY (`user_id`, `role_id`))


