INSERT INTO users (username, password, status, name, surname, email) VALUES ('pepeUser', '$2a$10$fa46X7KIyy4zJpvOGNLzyeTishC8bSVbfhtl3jE/R57cbKc5H4p4C', 1, 'pepe', 'pepon', 'pepe@gmail');
INSERT INTO users (username, password, status, name, surname, email) VALUES ('admin', '$2a$10$EQYNd4yFn3ZSQmsgSUdlru9ZGgdD/8yatvb3mXO.W2Rd4u37xlrxa', 1, 'admin', 'admin', 'admin@gmail');

INSERT INTO roles (name) VALUES ('ROLE_ADMIN');
INSERT INTO roles (name) VALUES ('ROLE_USER');

INSERT INTO users_roles (user_id, role_id) VALUES (1, 1);
INSERT INTO users_roles (user_id, role_id) VALUES (2, 2);
INSERT INTO users_roles (user_id, role_id) VALUES (2, 1);