USE points;

DROP TABLE users;
CREATE TABLE users(id CHAR(36) PRIMARY KEY,
					username VARCHAR(20) UNIQUE,
					email VARCHAR(50) UNIQUE,
                    usertype VARCHAR(10),
                    salt VARCHAR(20),
                    pass CHAR(64));
                    

CREATE TRIGGER before_insert_users
	BEFORE INSERT ON users
	FOR EACH ROW
	SET NEW.id = UUID();
