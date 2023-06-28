INSERT INTO users (username, password, status, name, surname, email) VALUES ('pepeUser', '$2a$10$WI8SZ/sow7sq9YG3jCgllucwWYwCuJNe9/69kB1apXE5HUA8nvKSG', 1, 'pepe', 'pepon', 'pepe@gmail');
INSERT INTO users (username, password, status, name, surname, email) VALUES ('admin', '$2a$10$WXAfOeVo6qUMblMqRmAbhOf3OkolHdYx6L32sRQnEv2lvZJpB6uwa', 1, 'admin', 'admin', 'admin@gmail');

INSERT INTO roles (name) VALUES ('ROLE_ADMIN');
INSERT INTO roles (name) VALUES ('ROLE_USER');

INSERT INTO users_roles (user_id, role_id) VALUES (1, 2);
INSERT INTO users_roles (user_id, role_id) VALUES (2, 1);
INSERT INTO users_roles (user_id, role_id) VALUES (2, 2);
