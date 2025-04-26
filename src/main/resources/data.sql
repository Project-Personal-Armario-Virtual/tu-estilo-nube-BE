-- Limpiar tablas por si acaso
DELETE FROM users_roles;
DELETE FROM images;
DELETE FROM roles;
DELETE FROM users;
DELETE FROM profile;
DELETE FROM category;

-- Insertar roles básicos
INSERT INTO roles (name) VALUES ('ROLE_USER'), ('ROLE_ADMIN');

-- Insertar usuario de prueba
INSERT INTO users (id, username, email, password, enabled) 
VALUES (1, 'testuser', 'testuser@example.com', '$2a$10$skypvVLksYHUuYh5s0fOruFvzxLhU85uyIRhrMFBw8sJ5G7b/4Ona', true);
-- La contraseña es "password" en BCrypt. Si quieres otra, me dices y la cambiamos 🔥

-- Asociar rol ROLE_USER al usuario
INSERT INTO users_roles (user_id, role_id)
SELECT 1, r.id FROM roles r WHERE r.name = 'ROLE_USER';

-- Insertar perfil de prueba
INSERT INTO profile (id, user_id, display_name, bio)
VALUES (1, 1, 'Test User', 'Perfil de prueba');

-- Insertar categoría básica
INSERT INTO category (id, name)
VALUES (1, 'Uncategorized');

-- Opcional: Si quieres también insertar una imagen de prueba podríamos hacerlo, pero normalmente no se inserta directamente porque `data` es bytea y es complejo.
