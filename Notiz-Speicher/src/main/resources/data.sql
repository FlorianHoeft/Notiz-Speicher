-- sample data for unit testing

INSERT INTO users(id, username, email, password)
VALUES (1, 'email', 'email', '$2a$10$.ogZJlP4vMV/9uiH0S32VecOR6Znrlcy3djxOL3lHfdZIbc9pLl7G');


-- skip used primary keys
ALTER SEQUENCE users_seq RESTART WITH 4 ;
