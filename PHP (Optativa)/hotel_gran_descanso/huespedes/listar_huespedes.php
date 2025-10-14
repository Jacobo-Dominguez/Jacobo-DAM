<?php include '../header.php';
require '../conexion.php'; ?>

<div class="d-flex justify-content-between align-items-center mb-3">
    <h2>Lista de Huéspedes</h2>
    <a href="form_huesped.php" class="btn btn-success">+ Nuevo Huésped</a>
</div>

<table class="table table-hover">
    <thead>
        <tr>
            <th>ID</th>
            <th>Nombre</th>
            <th>Email</th>
            <th>Documento</th>
            <th>Acciones</th>
        </tr>
    </thead>
    <tbody>
        <?php
        $stmt = $pdo->query("SELECT * FROM huespedes ORDER BY id DESC");
        foreach ($stmt as $fila) {
            echo "<tr>
                <td>{$fila['id']}</td>
                <td>{$fila['nombre']}</td>
                <td>{$fila['email']}</td>
                <td>{$fila['documento_identidad']}</td>
                <td>
                  <a href='editar_huesped.php?id={$fila['id']}' class='btn btn-edit btn-sm'>Editar</a>
                  <a href='eliminar_huesped.php?id={$fila['id']}' class='btn btn-delete btn-sm' onclick='return confirm(\"¿Eliminar este huésped?\")'>Eliminar</a>
                </td>
              </tr>";
        }
        ?>
    </tbody>
</table>

<?php include '../footer.php'; ?>