<?php
require '../conexion.php';

if (!isset($_GET['id'])) {
    die("Error: falta el ID de la reserva");
}

$id = $_GET['id'];

try {
    $stmt = $pdo->prepare("DELETE FROM reservas WHERE id = ?");
    $stmt->execute([$id]);
    header("Location: listar_reservas.php");
} catch (PDOException $e) {
    die("Error al eliminar reserva: " . $e->getMessage());
}
