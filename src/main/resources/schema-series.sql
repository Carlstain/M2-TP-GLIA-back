CREATE TABLE IF NOT EXISTS USERS (ID BIGINT PRIMARY KEY AUTO_INCREMENT, USERNAME VARCHAR(30), PASSWORD VARCHAR (255));

CREATE TABLE IF NOT EXISTS SERIES(ID BIGINT PRIMARY KEY AUTO_INCREMENT,
                  TITLE VARCHAR(30) NOT NULL, DESCRIPTION VARCHAR (255), USERID BIGINT,
                  FOREIGN KEY (USERID) REFERENCES USERS(ID) ON DELETE CASCADE ON UPDATE CASCADE);

CREATE TABLE IF NOT EXISTS EVENTS(ID BIGINT PRIMARY KEY AUTO_INCREMENT, MOMENT TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
                  RECORD DOUBLE NOT NULL, COMMENT VARCHAR(255), SERIEID BIGINT,
                  FOREIGN KEY (SERIEID) REFERENCES SERIES(ID) ON DELETE CASCADE ON UPDATE CASCADE);

CREATE TABLE IF NOT EXISTS TAGS(ID BIGINT PRIMARY KEY AUTO_INCREMENT, VAL VARCHAR(255) NOT NULL , EVENTID BIGINT,
                    FOREIGN KEY (EVENTID) REFERENCES EVENTS(ID) ON DELETE CASCADE ON UPDATE CASCADE);