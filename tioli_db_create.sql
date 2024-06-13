
-- -----------------------------------------------------
-- Schema TIOLI
-- -----------------------------------------------------
DROP SCHEMA IF EXISTS `tioli` ;

-- -----------------------------------------------------
-- Schema tioli
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `tioli` DEFAULT CHARACTER SET utf8 ;
USE `tioli` ;

-- -----------------------------------------------------
-- Table `tioli`.`player`
-- -----------------------------------------------------

CREATE TABLE IF NOT EXISTS `tioli`.`player` (
  `player_id` VARCHAR(8) NOT NULL,
  `name` VARCHAR(45) NOT NULL,
  `bank` INT(10) NOT NULL DEFAULT 1000,
  PRIMARY KEY (`player_id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `tioli`.`hands`
-- -----------------------------------------------------

CREATE TABLE IF NOT EXISTS `tioli`.`hands` (
  `hand_id` VARCHAR(45) NOT NULL,
  `card_num` INT NOT NULL,
  `player_id` VARCHAR(8) NOT NULL,
  `face` VARCHAR(10) NOT NULL,
  `suit` VARCHAR(10) NOT NULL,
  PRIMARY KEY (`hand_id`, `card_num`),
  INDEX `fk_hands_player1_idx` (`player_id` ASC),
  CONSTRAINT `fk_hands_player1`
    FOREIGN KEY (`player_id`)
    REFERENCES `tioli`.`player` (`player_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `tioli`.`game_results`
-- -----------------------------------------------------

CREATE TABLE IF NOT EXISTS `tioli`.`game_results` (
  `game_id` VARCHAR(45) NOT NULL,
  `player_id` VARCHAR(8) NOT NULL,
  `hand_descr` VARCHAR(45) NOT NULL,
  `amount_won` INT(10) NOT NULL,
  `player_bank` INT(10) NOT NULL,
  PRIMARY KEY (`game_id`),
  INDEX `fk_game_results_player1_idx` (`player_id` ASC),
  CONSTRAINT `fk_game_results_player1`
    FOREIGN KEY (`player_id`)
    REFERENCES `tioli`.`player` (`player_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;



#Now add data
USE tioli;

INSERT INTO player
VALUES 
    ("1432567", "LuckBeALady", 1200),
    ("2435768", "JimBob", 500),
    ("3476981", "AcesHigh", 4430),
    ("4369876", "FullHouse", 2300),
	("5564832", "TheGambler", 1000),
    ("6987637", "FoldEm!", 900),
    ("7098352", "LuckyLou", 3500),
    ("8143876", "BettyBetter", 2150),
    ("9765467", "FastFreddy", 1450),
    ("0768542", "RaisingRita", 2000),
    ("9867423", "HitMe!", 1600),
    ("2435573", "OneEyedJack", 7500),
    ("3499981", "Maurice", 44430),
    ("4936876", "Grandma", 2100),
	("5584326", "Mickie", 2200),
    ("6987987", "Minnie", 8900),
    ("7098255", "Donald", 5500),
    ("8143988", "Pluto", 9150),
    ("9765122", "Wilma", 1850),
    ("9745129", "Dino", 1850),
    ("0768311", "Barney", 2430);
