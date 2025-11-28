-- Script para crear la base de datos y tablas
DROP DATABASE IF EXISTS blogpersonal;
CREATE DATABASE IF NOT EXISTS blogpersonal CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE blogpersonal;

CREATE TABLE IF NOT EXISTS users (
  id INT AUTO_INCREMENT PRIMARY KEY,
  name VARCHAR(150) NOT NULL,
  email VARCHAR(200) NOT NULL UNIQUE,
  -- Avatar guardado como BLOB en la base de datos (binario)
  avatar_mime VARCHAR(100) DEFAULT NULL,
  avatar MEDIUMBLOB DEFAULT NULL,
  description TEXT DEFAULT NULL,
  password VARCHAR(255) NOT NULL,
  is_admin TINYINT DEFAULT 0,
  created_at DATETIME
);

CREATE TABLE IF NOT EXISTS posts (
  id INT AUTO_INCREMENT PRIMARY KEY,
  user_id INT NOT NULL,
  title VARCHAR(255) NOT NULL,
  content TEXT NOT NULL,
  image VARCHAR(255) DEFAULT NULL,
  -- Categoría del post: General, Tecnología, Personal, Viajes, Otros
  category VARCHAR(50) DEFAULT 'General',
  -- Estado de moderación: 0 = pendiente, 1 = publicado, 2 = rechazado
  status TINYINT DEFAULT 0,
  created_at DATETIME,
  FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);

-- Si ya tienes la tabla `posts` y quieres añadir las columnas, ejecuta:
-- ALTER TABLE posts ADD COLUMN category VARCHAR(50) DEFAULT 'General' AFTER image;
-- ALTER TABLE posts ADD COLUMN status TINYINT DEFAULT 0 AFTER category;

-- LAS CONTRASEÑAS SON 123 (MUY SEGURAS)
-- Los usuarios se crean con el avatar predeterminado
INSERT INTO users (name, email, avatar_mime, avatar, description, password, is_admin, created_at) 
VALUES 
('Admin', 'admin@admin.com', 'image/png', LOAD_FILE('C:/Users/jacob/Desktop/Jacobo-DAM/Optativa (PHP)/BlogPersonal/public/assets/images/default-avatar.png'), 'Soy el admin.', '$2y$10$MByqc18BUFfvWudpHW5Oq.ghvn61.HKA8oRnoo5mBY37G41vBRP52', 1, now()),
('Jacobo', 'usuario@email.com', 'image/png', LOAD_FILE('C:/Users/jacob/Desktop/Jacobo-DAM/Optativa (PHP)/BlogPersonal/public/assets/images/default-avatar.png'), 'Soy un estudiante', '$2y$10$MByqc18BUFfvWudpHW5Oq.ghvn61.HKA8oRnoo5mBY37G41vBRP52', 0, NOW());



