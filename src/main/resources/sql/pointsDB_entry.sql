CREATE DATABASE points;
USE points;

DROP TABLE entry;
CREATE TABLE entry (id  int auto_increment PRIMARY KEY, 
					longitude DOUBLE NOT NULL, 
                    latitude DOUBLE NOT NULL, 
                    entryType VARCHAR(12),
                    entryName VARCHAR(20),
                    description VARCHAR(100), 
 					username VARCHAR(20),                    
                    rating INT, 
                    datecreated DATETIME);


DELIMITER $$
DROP TRIGGER checkRating; $$
CREATE TRIGGER checkRating
	BEFORE INSERT ON entry FOR EACH ROW
	BEGIN
			IF NEW.rating < -1   OR NEW.rating > 1 THEN
				SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'Error, Invalid value for rating';
			END IF;
	END $$

