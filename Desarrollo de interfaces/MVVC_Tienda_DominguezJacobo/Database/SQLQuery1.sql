-- TRABAJO REALIZADO POR JACOBO LUIS DOMÍNGUEZ MORALES
------------------------------------------------------------
-- CREACIÓN DE LA BASE DE DATOS Y TABLAS
------------------------------------------------------------

-- Si la base de datos no existe, la creamos
IF NOT EXISTS (SELECT * FROM sys.databases WHERE name = 'TiendaMVVC')
BEGIN 
	CREATE DATABASE TiendaMVVC;
	PRINT 'Base de datos creada.';
END 
ELSE
    PRINT 'La base de datos ya existe.';
GO

-- Indicamos que vamos a usar la base de datos
USE TiendaMVVC;
GO

-- Creo la tabla Productos solo si no existe
IF NOT EXISTS (SELECT * FROM sysobjects WHERE name='Productos' AND xtype='U')
BEGIN
    CREATE TABLE Productos (
        Id INT IDENTITY(1,1) PRIMARY KEY,
        Nombre NVARCHAR(100) NOT NULL,
        Categoria NVARCHAR(50),
        Precio DECIMAL(10,2) NOT NULL,
        Stock INT,
        Imagen NVARCHAR(255),
        Descripcion NVARCHAR(255)
    );
    PRINT 'Tabla Productos creada.';
END
ELSE
    PRINT 'La tabla Productos ya existe.';
GO


-- Inserto solo si no hay registros
IF NOT EXISTS (SELECT 1 FROM Productos)
BEGIN
    INSERT INTO Productos (Nombre, Categoria, Precio, Stock, Imagen, Descripcion)
    VALUES
    ('MSI GeForce RTX 5090', 'Graficas', 1209.99, 10, 'pack://application:,,,/Resources/producto.png', 'Tarjeta grafica de ultima generacion con DLSS4 y FrameGeneration.'),
    ('Logitech G203', 'Perifericos', 19.99, 10, 'pack://application:,,,/Resources/producto1.png', 'Raton inalambrico óptico de Logitech.'),
    ('Teclado Mecanico', 'Perifericos', 34.99, 8, 'pack://application:,,,/Resources/producto2.png', 'Teclado mecanico con materiales duraderos de Mars Gaming.'),
    ('Alfombrilla de raton XXL', 'Perifericos', 9.99, 6, 'pack://application:,,,/Resources/producto3.png', 'Alfombrilla de raton amplia de material antideslizante.'),
    ('Monitor Gaming 23', 'Pantallas', 129.99, 6, 'pack://application:,,,/Resources/producto4.png', 'Monitor gaming de 23'' con panel IPS 144Hz.'),
    ('Auriculares Bluetooth Pro', 'Audio', 59.99, 6, 'pack://application:,,,/Resources/producto5.png', 'Auriculares inalámbricos con cancelación de ruido y batería de larga duración.'),
    ('Ryzen 7 7800X 3D', 'Procesadores', 369.99, 12, 'pack://application:,,,/Resources/producto6.png', 'El procesador para juegos que domina el mundo de la mano de la tecnología AMD 3D V-Cache.');
    PRINT 'Productos insertados correctamente.';
END
ELSE
    PRINT 'Los productos ya existen en la tabla.';
GO


-- Creo la tabla Pedidos
IF NOT EXISTS (SELECT * FROM sysobjects WHERE name='Pedidos' AND xtype='U')
BEGIN
    CREATE TABLE Pedidos (
        Id INT IDENTITY(1,1) PRIMARY KEY,
        Producto NVARCHAR(100),
        Fecha DATETIME DEFAULT GETDATE()
    );
    PRINT 'Tabla Pedidos creada.';
END
ELSE
    PRINT 'La tabla Pedidos ya existe.';
GO

-- Creo la tabla Usuarios
IF NOT EXISTS (SELECT * FROM sysobjects WHERE name='Usuarios' AND xtype='U')
BEGIN
    CREATE TABLE Usuarios (
        Id INT IDENTITY(1,1) PRIMARY KEY,
        Nombre NVARCHAR(100),
        Apellidos NVARCHAR(100),
        Email NVARCHAR(100),
        Contrasena NVARCHAR(50),
        Rol NVARCHAR(50)
    );
    PRINT 'Tabla Usuarios creada.';
END
ELSE
    PRINT 'La tabla Usuarios ya existe.';
GO



-- Verificar
SELECT * FROM Productos;
SELECT * FROM Pedidos;
SELECT * FROM Usuarios;
