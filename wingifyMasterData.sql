/*
Created		12-05-2016
Modified		13-05-2016
Project		
Model		
Company		
Author		
Version		
Database		mySQL 5 
*/



drop table IF EXISTS users;
drop table IF EXISTS Attributes;
drop table IF EXISTS Categories;
drop table IF EXISTS Products;

--
-- Table structure for table `users`
--
CREATE TABLE IF NOT EXISTS `users` (
  `user_id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(30) NOT NULL,
  `password` varchar(30) NOT NULL,
  `role` varchar(15) NOT NULL,
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=5 ;
--
-- Dumping data for table `users`
--
INSERT INTO `users` (`user_id`, `username`, `password`, `role`) VALUES
(1, 'nitish', 'nitish', 'Administrator');

--
-- Table structure for table `categories`
--
Create table Categories (
	category_id Int NOT NULL AUTO_INCREMENT,
	name Varchar(100),
	description Varchar(200),
	parent_id Int DEFAULT 0,
 Primary Key (category_id)) ENGINE = InnoDB;
--
-- Dumping data for table `categories`
--
INSERT INTO `categories` (`category_id`, `name`, `description`, `parent_id`) VALUES
(1, 'Electronics', 'Sample Electronics Description', -1),
(2, 'Books', 'Sample Books Description', -1),
(3, 'Home and Kitchen', 'Sample Home and Kitchen Description', -1),
(4, 'Music', 'Sample Music Description', -1),
(5, 'Mobiles', 'Mobiles Description', 1),
(6, 'Computers', 'Sample Computers Description', 1),
(7, 'Spiritual', 'Sample Spiritual Books Description', 2),
(8, 'Home Furnishing', 'Sample Home Furnishing Description', 3),
(9, 'Home Appliances', 'Sample Home Appliances Description', 3),
(10, 'Kitchen Appliances', 'Sample Kitchen Appliances Description', 3),
(11, 'Apparels', 'Sample Apparels Description', -1);

--
-- Table structure for table `attributes`
--
Create table Attributes (
	attribute_id Int NOT NULL AUTO_INCREMENT,
	attribute_name Varchar(200),
 Primary Key (attribute_id)) ENGINE = InnoDB;
--
-- Dumping data for table `attributes`
--
INSERT INTO `attributes` (`attribute_id`, `attribute_name`) VALUES
(1, 'size'),
(2, 'color'),
(3, 'book_type');

--
-- Table structure for table `products`
--
Create table Products (
	product_id Int NOT NULL AUTO_INCREMENT,
	name Varchar(200),
	description Varchar(300),
	quantity Int,
	availability Varchar(50),
	price Decimal(10,2),
	categories Varchar(100),
  attributes Varchar(100),
	created_at Timestamp,
	last_updated_at Timestamp,
 Primary Key (product_id)) ENGINE = InnoDB;
--
-- Dumping data for table `products`
--
INSERT INTO `products` (`product_id`, `name`, `description`, `quantity`,`availability`, `price`, `categories`,`attributes`,`created_at`,`last_updated_at`) VALUES
(1, 'Dell Inspiron Laptop', 'Sample Dell Inspiron Laptop Description', 20,'in_stock',31000,'1,6','0','2015-07-25 12:19:33','2015-07-29 18:19:33'),
(2, 'HP Laptop', 'Sample HP Laptop Description', 0,'not_available',32000,'1,6','0','2015-04-25 12:19:33','2015-07-29 18:19:33'),
(3, 'Mossad', 'Sample Mossad book Description', 120,'in_stock',450,'2','3','2015-08-15 12:19:33','2015-08-29 18:19:33'),
(4, 'Himalayan Blunder', 'Sample Himalayan Blunder 1962 Description', 57,'in_stock',680,'2','3','2015-07-25 12:19:33','2015-07-29 18:19:33'),
(5, 'BlackBerry Z10', 'Sample BlackBerry Z10 Mobile Description', 5,'in_stock',22000,'1,5','2','2015-07-25 12:19:33','2015-07-29 18:19:33'),
(6, 'Mi 4i', 'Sample Mi 4i Description', 0,'not_available',11999,'1,5','2','2015-07-25 12:19:33','2015-07-29 18:19:33'),
(7, 'Levis Jeans', 'Sample Levis Jeans Description',200,'in_stock',1299,'11','1,2','2015-07-25 12:19:33','2015-07-29 18:19:33'),
(8, 'Denim Shirt', 'Denim Shirt Description',145,'in_stock',895,'11','1,2','2015-07-25 12:19:33','2015-07-29 18:19:33'),
(9, 'Leather Jacket', 'Sample Leather Jacket Description',20,'in_stock',1599,'11','1,2','2015-07-25 12:19:33','2015-07-29 18:19:33'),
(10, 'Induction cooker', 'Sample Induction Cooker Description',45,'in_stock',2400,'3,9','0','2015-07-25 12:19:33','2015-07-29 18:19:33');

--
-- Table structure for table `variants`
--
Create table Variants (
	variant_id Int NOT NULL AUTO_INCREMENT,
	product_id Int,
	variant_sku Varchar(100),
	quantity Int,
	price Decimal(10,2),
	attribute_id int,
  attribute_value Varchar(200),
 Primary Key (variant_id)) ENGINE = InnoDB;
--
-- Dumping data for table `variants`
--
INSERT INTO `variants` (`variant_id`, `product_id`,`variant_sku`,`quantity`,`price`,`attribute_id`,`attribute_value`) VALUES
(1, 3,'PPR-2015/3',120,450,3,'PAPERBACK'),
(2, 4,'HRD-2015/4',57,680,3,'HARDCOVER'),
(3, 6,'MI4I-2015/6/5',0,11999,2,'RED'),
(4, 6,'MI4I-2015/6/8',0,12999,2,'BLACK'),
(5, 9,'APRL-2015/9/S1',50,1299,1,'30'),
(6, 9,'APRL-2015/9/S2',50,1299,1,'32'),
(7, 9,'APRL-2015/9/S3',50,1299,2,'BLUE'),
(8, 9,'APRL-2015/9/S4',50,1299,2,'BLACK');
/* Users permissions */
