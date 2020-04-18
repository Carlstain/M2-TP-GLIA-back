CREATE TABLE IF NOT EXISTS USERS (ID BIGINT PRIMARY KEY AUTO_INCREMENT, USERNAME VARCHAR(30), PASSWORD VARCHAR (255));

CREATE TABLE IF NOT EXISTS SHARED(ID BIGINT PRIMARY KEY AUTO_INCREMENT,
SERIEID BIGINT NOT NULL ,
USERID BIGINT NOT NULL ,
PERMISSION VARCHAR(30) NOT NULL,
CONSTRAINT chk_PERMISSION CHECK (PERMISSION IN ('Read', 'ReadWrite')),
FOREIGN KEY (USERID) REFERENCES USERS(ID));