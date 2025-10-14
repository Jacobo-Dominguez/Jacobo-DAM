<?php include '../header.php'; ?>
<h2>Registrar nueva habitación</h2>
<form action="insertar_habitacion.php" method="POST" class="mt-3">
    <div class="mb-3">
        <label class="form-label">Número:</label>
        <input type="number" name="numero" class="form-control" required>
    </div>
    <div class="mb-3">
        <label class="form-label">Tipo:</label>
        <select name="tipo" class="form-select" required>
            <option value="Sencilla">Sencilla</option>
            <option value="Doble">Doble</option>
            <option value="Suite">Suite</option>
        </select>
    </div>
    <div class="mb-3">
        <label class="form-label">Precio base por noche (€):</label>
        <input type="number" step="0.01" name="precio_base" class="form-control" required>
    </div>
    <div class="mb-3">
        <label class="form-label">Estado de limpieza:</label>
        <select name="estado_limpieza" class="form-select">
            <option value="Limpia">Limpia</option>
            <option value="Sucia">Sucia</option>
            <option value="En Limpieza">En Limpieza</option>
        </select>
    </div>
    <button type="submit" class="btn btn-primary">Guardar</button>
    <a href="listar_habitaciones.php" class="btn btn-secondary">Cancelar</a>
</form>
<?php include '../footer.php'; ?>