<?php
require '../conexion.php';

if ($_SERVER['REQUEST_METHOD'] === 'POST') {
    $nombre = $_POST['nombre'];
    $email = $_POST['email'];
    $documento = $_POST['documento_identidad'];

    try {
        $stmt = $pdo->prepare("INSERT INTO huespedes (nombre, email, documento_identidad) VALUES (?, ?, ?)");
        $stmt->execute([$nombre, $email, $documento]);
        header("Location: listar_huespedes.php");
    } catch (PDOException $e) {
        die("Error al insertar: " . $e->getMessage());
    }
}
