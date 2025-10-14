<?php
require '../conexion.php';

if (!isset($_GET['id'])) {
    die("Falta el ID del huésped.");
}

$id = intval($_GET['id']);

try {
    $stmt = $pdo->prepare("DELETE FROM huespedes WHERE id = ?");
    $stmt->execute([$id]);
    header("Location: listar_huespedes.php");
    exit;
} catch (PDOException $e) {
    die("No se pudo eliminar el huésped: " . htmlspecialchars($e->getMessage()));
}
