<?php
require '../conexion.php';

if (!isset($_GET['id'])) {
    die("Falta el ID del mantenimiento.");
}

$id = intval($_GET['id']);

try {
    $stmt = $pdo->prepare("DELETE FROM mantenimientos WHERE id = ?");
    $stmt->execute([$id]);
    header("Location: listar_mantenimientos.php");
    exit;
} catch (PDOException $e) {
    die("No se pudo eliminar el mantenimiento: " . htmlspecialchars($e->getMessage()));
}
