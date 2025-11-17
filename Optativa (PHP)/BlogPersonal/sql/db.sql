-- Script para crear la base de datos y tablas
DROP DATABASE IF EXISTS blogpersonal;
CREATE DATABASE IF NOT EXISTS blogpersonal CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE blogpersonal;

CREATE TABLE IF NOT EXISTS users (
  id INT AUTO_INCREMENT PRIMARY KEY,
  name VARCHAR(150) NOT NULL,
  email VARCHAR(200) NOT NULL UNIQUE,
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
  created_at DATETIME,
  FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);


