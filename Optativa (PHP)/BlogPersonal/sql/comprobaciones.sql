-- Uso de la base para hacer pruebas
USE blogpersonal; 

-- Comprobacion de que existen los avatares
SHOW COLUMNS FROM users LIKE 'avatar%';

-- Usuarios
SELECT * FROM users;

-- Comprobar que se guardan los avatares
SELECT id, email, LENGTH(avatar) AS avatar_size, avatar_mime
FROM users
WHERE id = 1;

SELECT * FROM posts;



