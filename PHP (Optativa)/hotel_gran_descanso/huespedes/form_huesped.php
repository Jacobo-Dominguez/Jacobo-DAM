<?php include '../header.php'; ?>
<h2>Registrar nuevo huésped</h2>
<form action="insertar_huesped.php" method="POST" class="mt-3">
    <div class="mb-3">
        <label class="form-label">Nombre:</label>
        <input type="text" name="nombre" class="form-control" required>
    </div>
    <div class="mb-3">
        <label class="form-label">Email:</label>
        <input type="email" name="email" class="form-control" required>
    </div>
    <div class="mb-3">
        <label class="form-label">Documento Identidad:</label>
        <input type="text" name="documento_identidad" class="form-control" required>
    </div>
    <button type="submit" class="btn btn-primary">Guardar</button>
    <a href="listar_huespedes.php" class="btn btn-secondary">Cancelar</a>
</form>
<?php include '../footer.php'; ?>