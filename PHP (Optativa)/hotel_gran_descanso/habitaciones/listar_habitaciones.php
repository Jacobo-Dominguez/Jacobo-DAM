<?php include '../header.php';
require '../conexion.php'; ?>

<div class="d-flex justify-content-between align-items-center mb-3">
    <h2>Lista de Habitaciones</h2>
    <a href="form_habitacion.php" class="btn btn-success">+ Nueva Habitación</a>
</div>

<table class="table table-hover">
    <thead>
        <tr>
            <th>ID</th>
            <th>Número</th>
            <th>Tipo</th>
            <th>Precio Base</th>
            <th>Estado Limpieza</th>
            <th>Acciones</th>
        </tr>
    </thead>
    <tbody>
        <?php
        $stmt = $pdo->query("SELECT * FROM habitaciones ORDER BY id DESC");
        foreach ($stmt as $fila) {
            echo "<tr>
                <td>{$fila['id']}</td>
                <td>{$fila['numero']}</td>
                <td>{$fila['tipo']}</td>
                <td>{$fila['precio_base']} €</td>
                <td>{$fila['estado_limpieza']}</td>
                <td>
                  <a href='editar_habitacion.php?id={$fila['id']}' class='btn btn-edit btn-sm'>Editar</a>
                  <a href='eliminar_habitacion.php?id={$fila['id']}' class='btn btn-delete btn-sm' onclick='return confirm(\"¿Eliminar habitación?\")'>Eliminar</a>
                </td>
              </tr>";
        }
        ?>
    </tbody>
</table>

<?php include '../footer.php'; ?>