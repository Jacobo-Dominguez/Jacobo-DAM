<?php
if (session_status() == PHP_SESSION_NONE) {
    session_start();
}

if (!isset($_SESSION['usuario_id'])) {
    header("Location: /auth/login.php");
    exit;
}

if ($_SESSION['usuario_rol'] !== 'admin') {
    header("Location: /reservas/insertar_reserva.php");
    exit;
}
