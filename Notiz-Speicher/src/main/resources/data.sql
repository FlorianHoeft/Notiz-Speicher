-- sample data for unit testing
INSERT INTO users(id, username, email, password) 
VALUES(1,'thowl', 'prog3@th-owl.de', 'start') ;
INSERT INTO users(id, username, email, password) 
VALUES(2,'proghx', 'prog3@th-owl.de', 'sonne') ;
INSERT INTO users(id, username, email, password) 
VALUES(3,'admin', 'adm_p3@th-owl.de', 'nimda') ;

INSERT INTO notes(id, title, content, category, favorite)
VALUES(1,'Erste_Notiz', 'Erste notiz, diese notiz sollte favorisiert sein und am anfang oder ende einer liste stehen', 'other' ,true) ;
INSERT INTO notes(id, title, content, category, favorite)
VALUES(2,'Notiz Nr2', 'Notiz2 favo', 'other' ,true) ;
INSERT INTO notes(id, title, content, category, favorite)
VALUES(3,'Notiz Nr3', 'Notiz3 notfavo', 'other' ,false) ;
INSERT INTO notes(id, title, content, category, favorite)
VALUES(4,'Notiz Nr4', 'Notiz4 notfavo', 'other' ,false) ;
INSERT INTO notes(id, title, content, category, favorite)
VALUES(5,'Notiz Nr5', 'Notiz5 favo', 'other' ,true) ;
INSERT INTO notes(id, title, content, category, favorite)
VALUES(6,'Notiz Nr6', 'Notiz notfavo', 'other' ,false) ;

-- skip used primary keys
ALTER SEQUENCE users_seq RESTART WITH 4 ;
