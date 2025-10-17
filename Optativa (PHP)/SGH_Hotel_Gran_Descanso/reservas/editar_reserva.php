<?php
include '../header.php';
require '../conexion.php';

if (!isset($_GET['id'])) {
    die("Error: falta el ID de la reserva");
}

$id = $_GET['id'];

if ($_SERVER['REQUEST_METHOD'] === 'POST') {
    $id_huesped = $_POST['id_huesped'];
    $id_habitacion = $_POST['id_habitacion'];
    $fecha_llegada = $_POST['fecha_llegada'];
    $fecha_salida = $_POST['fecha_salida'];
    $estado = $_POST['estado'];

    try {
        $dias = (strtotime($fecha_salida) - strtotime($fecha_llegada)) / 86400;
        if ($dias <= 0) {
            die("<script>alert('La fecha de salida debe ser posterior a la de llegada.'); window.history.back();</script>");
        }

        $precioStmt = $pdo->prepare("SELECT precio_base FROM habitaciones WHERE id = ?");
        $precioStmt->execute([$id_habitacion]);
        $precio_base = $precioStmt->fetchColumn();
        $precio_total = $precio_base * $dias;

        $stmt = $pdo->prepare("
            UPDATE reservas 
            SET id_huesped=?, id_habitacion=?, fecha_llegada=?, fecha_salida=?, precio_total=?, estado=? 
            WHERE id=?
        ");
        $stmt->execute([$id_huesped, $id_habitacion, $fecha_llegada, $fecha_salida, $precio_total, $estado, $id]);

        header("Location: listar_reservas.php");
    } catch (PDOException $e) {
        die("Error al actualizar reserva: " . $e->getMessage());
    }
}

// Script para obtener los datos de las reservas
$stmt = $pdo->prepare("SELECT * FROM reservas WHERE id = ?");
$stmt->execute([$id]);
$reserva = $stmt->fetch();

if (!$reserva) {
    die("Reserva no encontrada");
}
?>

<h2>Editar reserva</h2>
<form method="POST" class="mt-3">
    <div class="mb-3">
        <label>Huésped:</label>
        <select name="id_huesped" class="form-select" required>
            <?php
            $huespedes = $pdo->query("SELECT id, nombre FROM huespedes");
            foreach ($huespedes as $h) {
                $sel = ($h['id'] == $reserva['id_huesped']) ? "selected" : "";
                echo "<option value='{$h['id']}' $sel>{$h['nombre']}</option>";
            }
            ?>
        </select>
    </div>
    <div class="mb-3">
        <label>Habitación:</label>
        <select name="id_habitacion" class="form-select" required>
            <?php
            $hab = $pdo->query("SELECT id, numero FROM habitaciones");
            foreach ($hab as $h) {
                $sel = ($h['id'] == $reserva['id_habitacion']) ? "selected" : "";
                echo "<option value='{$h['id']}' $sel>Habitación {$h['numero']}</option>";
            }
            ?>
        </select>
    </div>
    <div class="mb-3">
        <label>Fecha llegada:</label>
        <input type="date" name="fecha_llegada" value="<?= $reserva['fecha_llegada'] ?>" class="form-control" required>
    </div>
    <div class="mb-3">
        <label>Fecha salida:</label>
        <input type="date" name="fecha_salida" value="<?= $reserva['fecha_salida'] ?>" class="form-control" required>
    </div>
    <div class="mb-3">
        <label>Estado:</label>
        <select name="estado" class="form-select">
            <option <?= $reserva['estado'] === 'Pendiente' ? 'selected' : '' ?>>Pendiente</option>
            <option <?= $reserva['estado'] === 'Confirmada' ? 'selected' : '' ?>>Confirmada</option>
            <option <?= $reserva['estado'] === 'Cancelada' ? 'selected' : '' ?>>Cancelada</option>
        </select>
    </div>
    <button type="submit" class="btn btn-primary">Guardar cambios</button>
    <a href="listar_reservas.php" class="btn btn-secondary">Cancelar</a>
</form>

<?php include '../footer.php'; ?>