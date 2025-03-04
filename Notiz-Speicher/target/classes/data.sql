-- sample data for unit testing
INSERT INTO users(id, username, email, password) 
VALUES(1,'thowl', 'prog3@th-owl.de', 'start') ;
INSERT INTO users(id, username, email, password) 
VALUES(2,'proghx', 'prog3@th-owl.de', 'sonne') ;
INSERT INTO users(id, username, email, password) 
VALUES(3,'admin', 'adm_p3@th-owl.de', 'nimda') ;

INSERT INTO notes(id, title, content, category)
VALUES(1,'Erste_Notiz', 'Ersten_notiz', 'other' ) ;

-- skip used primary keys
ALTER SEQUENCE users_seq RESTART WITH 4 ;
