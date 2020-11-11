CREATE SCHEMA `bookstore` DEFAULT CHARACTER SET utf8 ;

CREATE TABLE `bookstore`.`authors` (
  `idAuthor` INT NOT NULL AUTO_INCREMENT,
  `cin` INT UNSIGNED NOT NULL,
  `firstName` VARCHAR(45) NULL,
  `lastName` VARCHAR(45) NULL,
  `phoneNumber` VARCHAR(15) NULL,
  PRIMARY KEY (`idAuthor`));
  
  CREATE TABLE `bookstore`.`products` (
  `idProduct` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NOT NULL,
  `price` FLOAT NOT NULL,
  `availableQuantity` INT NULL DEFAULT 0,
  PRIMARY KEY (`idProduct`));


CREATE TABLE `bookstore`.`books` (
  `ISBN` INT NOT NULL,
  `releaseDate` DATE NULL,
  `idProduct` INT NOT NULL,
  `idAuthor` INT NOT NULL,
  PRIMARY KEY (`ISBN`),
  INDEX `fk_product_idx` (`idProduct` ASC) VISIBLE,
  INDEX `fk_author_idx` (`idAuthor` ASC) VISIBLE,
  CONSTRAINT `fk_product`
    FOREIGN KEY (`idProduct`)
    REFERENCES `bookstore`.`products` (`idProduct`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_author`
    FOREIGN KEY (`idAuthor`)
    REFERENCES `bookstore`.`authors` (`idAuthor`)
    ON DELETE CASCADE
    ON UPDATE CASCADE);
    
    
   CREATE TABLE `bookstore`.`users` (
  `idUser` INT NOT NULL AUTO_INCREMENT,
  `username` VARCHAR(45) NOT NULL,
  `password` VARCHAR(45) NOT NULL,
  `type` ENUM('admin', 'client') NOT NULL,
  PRIMARY KEY (`idUser`));
 
    
CREATE TABLE `bookstore`.`shoppingCarts` (
  `idCart` INT NOT NULL AUTO_INCREMENT,
  `idUser` INT NULL,
  PRIMARY KEY (`idCart`),
  INDEX `fk_user_idx` (`idUser` ASC) VISIBLE,
  CONSTRAINT `fk_user`
    FOREIGN KEY (`idUser`)
    REFERENCES `bookstore`.`users` (`idUser`)
    ON DELETE CASCADE
    ON UPDATE CASCADE);
    
    
   CREATE TABLE `bookstore`.`cartLines` (
  `idCartLine` INT NOT NULL AUTO_INCREMENT,
  `quantity` INT NULL,
  `idProduct` INT NULL,
  `idCart` INT NULL,
  PRIMARY KEY (`idCartLine`),
  INDEX `fk_cart_idx` (`idCart` ASC) VISIBLE,
  INDEX `fk_product_idx` (`idProduct` ASC) VISIBLE,
  CONSTRAINT `fk_cart`
    FOREIGN KEY (`idCart`)
    REFERENCES `bookstore`.`shoppingCarts` (`idCart`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_product_cartLine`
    FOREIGN KEY (`idProduct`)
    REFERENCES `bookstore`.`products` (`idProduct`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);

