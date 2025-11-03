<?php
require '../conexion.php';

if (!isset($_GET['id'])) {
    die("Falta el ID de la habitación.");
}

$id = intval($_GET['id']);

try {
    $stmt = $pdo->prepare("DELETE FROM habitaciones WHERE id = ?");
    $stmt->execute([$id]);
    header("Location: /habitaciones/listar_habitaciones.php");
    exit;
} catch (PDOException $e) {
    die("No se pudo eliminar la habitación: " . htmlspecialchars($e->getMessage()));
}
