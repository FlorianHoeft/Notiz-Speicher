-- sample data for unit testing
INSERT INTO users(id, username, email, password) 
VALUES(1,'thowl', 'prog3@th-owl.de', 'start') ;
INSERT INTO users(id, username, email, password) 
VALUES(2,'proghx', 'prog3@th-owl.de', 'sonne') ;
INSERT INTO users(id, username, email, password) 
VALUES(3,'admin', 'adm_p3@th-owl.de', 'nimda') ;
INSERT INTO users(id, username, email, password)
VALUES (4, 'email', 'email', '$2a$10$.ogZJlP4vMV/9uiH0S32VecOR6Znrlcy3djxOL3lHfdZIbc9pLl7G');


-- skip used primary keys
ALTER SEQUENCE users_seq RESTART WITH 4 ;
