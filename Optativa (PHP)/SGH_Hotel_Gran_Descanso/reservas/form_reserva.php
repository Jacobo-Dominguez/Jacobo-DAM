<?php include '../header.php';
require '../conexion.php'; ?>
<h2>Registrar nueva reserva</h2>
<form action="insertar_reserva.php" method="POST" class="mt-3">
    <div class="mb-3">
        <label>Huésped:</label>
        <select name="id_huesped" class="form-select" required>
            <?php
            $stmt = $pdo->query("SELECT id, nombre FROM huespedes");
            foreach ($stmt as $fila) {
                echo "<option value='{$fila['id']}'>{$fila['nombre']}</option>";
            }
            ?>
        </select>
    </div>
    <div class="mb-3">
        <label>Habitación:</label>
        <select name="id_habitacion" class="form-select" required>
            <?php
            $stmt = $pdo->query("SELECT id, numero FROM habitaciones");
            foreach ($stmt as $fila) {
                echo "<option value='{$fila['id']}'>Habitación {$fila['numero']}</option>";
            }
            ?>
        </select>
    </div>
    <div class="mb-3">
        <label>Fecha llegada:</label>
        <input type="date" name="fecha_llegada" class="form-control" required>
    </div>
    <div class="mb-3">
        <label>Fecha salida:</label>
        <input type="date" name="fecha_salida" class="form-control" required>
    </div>
    <button type="submit" class="btn btn-primary">Guardar</button>
    <a href="listar_reservas.php" class="btn btn-secondary">Cancelar</a>
</form>
<?php include '../footer.php'; ?>