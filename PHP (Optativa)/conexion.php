<?php
// conexion.php
$host = 'localhost';
$db   = 'hotel_sgh';
$user = 'root';
$pass = '';
$charset = 'utf8mb4';

$dsn = "mysql:host=$host;dbname=$db;charset=$charset";

$opciones = [
    PDO::ATTR_ERRMODE => PDO::ERRMODE_EXCEPTION,
    PDO::ATTR_DEFAULT_FETCH_MODE => PDO::FETCH_ASSOC,
];

try {
    $pdo = new PDO($dsn, $user, $pass, $opciones);
} catch (PDOException $e) {
    // Mensaje claro para desarrollo; en producción no exponer detalles
    die("Error de conexión a la base de datos: " . $e->getMessage());
}
