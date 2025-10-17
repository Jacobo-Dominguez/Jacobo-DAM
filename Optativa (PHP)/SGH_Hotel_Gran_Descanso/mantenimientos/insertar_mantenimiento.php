<?php
require '../conexion.php';

if ($_SERVER['REQUEST_METHOD'] === 'POST') {
    $id_habitacion = $_POST['id_habitacion'];
    $descripcion = $_POST['descripcion'];
    $inicio = $_POST['fecha_inicio'];
    $fin = $_POST['fecha_fin'] ?: null;

    try {
        $stmt = $pdo->prepare("INSERT INTO mantenimientos (id_habitacion, descripcion, fecha_inicio, fecha_fin) VALUES (?, ?, ?, ?)");
        $stmt->execute([$id_habitacion, $descripcion, $inicio, $fin]);
        header("Location: listar_mantenimientos.php");
    } catch (PDOException $e) {
        die("Error: " . $e->getMessage());
    }
}
