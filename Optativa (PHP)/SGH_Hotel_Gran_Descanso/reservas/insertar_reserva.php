<?php
require '../conexion.php';

if ($_SERVER['REQUEST_METHOD'] === 'POST') {
    $id_huesped = $_POST['id_huesped'];
    $id_habitacion = $_POST['id_habitacion'];
    $fecha_llegada = $_POST['fecha_llegada'];
    $fecha_salida = $_POST['fecha_salida'];

    try {
        $mantenimiento = $pdo->prepare("
            SELECT * FROM mantenimientos
            WHERE id_habitacion = ? 
              AND (
                    (fecha_inicio <= ? AND fecha_fin >= ?) OR 
                    (fecha_inicio <= ? AND fecha_fin >= ?) OR
                    (fecha_inicio >= ? AND fecha_fin <= ?)
                  )
        ");
        $mantenimiento->execute([$id_habitacion, $fecha_llegada, $fecha_llegada, $fecha_salida, $fecha_salida, $fecha_llegada, $fecha_salida]);
        if ($mantenimiento->rowCount() > 0) {
            die("<script>alert('La habitación tiene una tarea de mantenimiento activa en esas fechas.'); window.history.back();</script>");
        }

        $reserva = $pdo->prepare("
            SELECT * FROM reservas
            WHERE id_habitacion = ? AND estado = 'Confirmada'
            AND (
                (fecha_llegada <= ? AND fecha_salida >= ?) OR
                (fecha_llegada <= ? AND fecha_salida >= ?) OR
                (fecha_llegada >= ? AND fecha_salida <= ?)
            )
        ");
        $reserva->execute([$id_habitacion, $fecha_llegada, $fecha_llegada, $fecha_salida, $fecha_salida, $fecha_llegada, $fecha_salida]);
        if ($reserva->rowCount() > 0) {
            die("<script>alert('La habitación ya está reservada en esas fechas.'); window.history.back();</script>");
        }

        $dias = (strtotime($fecha_salida) - strtotime($fecha_llegada)) / 86400;
        if ($dias <= 0) {
            die("<script>alert('La fecha de salida debe ser posterior a la de llegada.'); window.history.back();</script>");
        }

        $precioStmt = $pdo->prepare("SELECT precio_base FROM habitaciones WHERE id = ?");
        $precioStmt->execute([$id_habitacion]);
        $precio_base = $precioStmt->fetchColumn();
        $precio_total = $precio_base * $dias;

        $stmt = $pdo->prepare("
            INSERT INTO reservas (id_huesped, id_habitacion, fecha_llegada, fecha_salida, precio_total, estado, fecha_reserva)
            VALUES (?, ?, ?, ?, ?, 'Confirmada', NOW())
        ");
        $stmt->execute([$id_huesped, $id_habitacion, $fecha_llegada, $fecha_salida, $precio_total]);

        header("Location: listar_reservas.php");
    } catch (PDOException $e) {
        die("Error al insertar reserva: " . $e->getMessage());
    }
}
