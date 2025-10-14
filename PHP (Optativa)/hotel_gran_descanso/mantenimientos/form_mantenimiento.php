<?php include '../header.php';
require '../conexion.php'; ?>
<h2>Registrar tarea de mantenimiento</h2>
<form action="insertar_mantenimiento.php" method="POST" class="mt-3">
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
        <label>Descripción:</label>
        <textarea name="descripcion" class="form-control" required></textarea>
    </div>
    <div class="mb-3">
        <label>Fecha inicio:</label>
        <input type="date" name="fecha_inicio" class="form-control" required>
    </div>
    <div class="mb-3">
        <label>Fecha fin (estimada):</label>
        <input type="date" name="fecha_fin" class="form-control">
    </div>
    <button type="submit" class="btn btn-primary">Guardar</button>
    <a href="listar_mantenimientos.php" class="btn btn-secondary">Cancelar</a>
</form>
<?php include '../footer.php'; ?>