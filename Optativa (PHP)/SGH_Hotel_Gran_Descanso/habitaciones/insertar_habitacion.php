<?php
require '../conexion.php';

if ($_SERVER['REQUEST_METHOD'] === 'POST') {
    $numero = $_POST['numero'];
    $tipo = $_POST['tipo'];
    $precio = $_POST['precio_base'];
    $estado = $_POST['estado_limpieza'];

    try {
        $stmt = $pdo->prepare("INSERT INTO habitaciones (numero, tipo, precio_base, estado_limpieza) VALUES (?, ?, ?, ?)");
        $stmt->execute([$numero, $tipo, $precio, $estado]);
        header("Location: listar_habitaciones.php");
    } catch (PDOException $e) {
        die("Error al insertar habitación: " . $e->getMessage());
    }
}
