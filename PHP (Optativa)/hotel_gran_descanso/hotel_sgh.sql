DROP DATABASE IF EXISTS hotel_sgh ;
CREATE DATABASE IF NOT EXISTS hotel_sgh;
USE hotel_sgh;

-- Tabla de habitaciones
CREATE TABLE habitaciones(
	id INT AUTO_INCREMENT PRIMARY KEY,
    numero VARCHAR(20) UNIQUE NOT NULL,
    tipo ENUM('Sencilla', 'Doble', 'Suite') NOT NULL,
    precio_base DECIMAL(10,2) NOT NULL, 
    estado_limpieza ENUM('Limpia', 'Sucia', 'En limpieza') DEFAULT 'Limpia'
);

-- Tabla de huespedes
CREATE TABLE huespedes(
	id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL,
    documento_identidad VARCHAR(50) NOT NULL
);

-- Tabla de reservas
CREATE TABLE reservas(
	id INT AUTO_INCREMENT PRIMARY KEY,
    id_huesped INT NOT NULL,
    id_habitacion INT NOT NULL,
    fecha_llegada DATE NOT NULL,
    fecha_salida DATE NOT NULL,
    precio_total DECIMAL(10,2) NOT NULL,
    estado ENUM('Pendiente', 'Confirmada', 'Cancelada') DEFAULT 'Pendiente',
    fecha_reserva TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (id_huesped) REFERENCES huespedes(id),
    FOREIGN KEY (id_habitacion) REFERENCES habitaciones(id)
);

-- Tabla de mantenimientos
CREATE TABLE mantenimientos(
	id INT AUTO_INCREMENT PRIMARY KEY,
    id_habitacion INT NOT NULL,
    descripcion TEXT NOT NULL,
    fecha_inicio DATE NOT NULL,
    fecha_fin DATE,
    FOREIGN KEY (id_habitacion) REFERENCES habitaciones(id)
);

INSERT INTO habitaciones (numero, tipo, precio_base, estado_limpieza)
VALUES
('101', 'Sencilla', 50.00, 'Limpia'),
('102', 'Doble', 75.00, 'Limpia'),
('201', 'Suite', 120.00, 'Sucia');

INSERT INTO huespedes (nombre, email, documento_identidad)
VALUES
('Juan Pérez', 'juan.perez@ejemplo.com', '12345678'),
('María López', 'maria.lopez@ejemplo.com', '98765432');


