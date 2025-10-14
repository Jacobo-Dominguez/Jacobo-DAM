<?php include '../header.php';
require '../conexion.php'; ?>

<div class="d-flex justify-content-between align-items-center mb-3">
    <h2>Tareas de Mantenimiento</h2>
    <a href="form_mantenimiento.php" class="btn btn-success">+ Nuevo Mantenimiento</a>
</div>

<table class="table table-hover">
    <thead>
        <tr>
            <th>ID</th>
            <th>Habitación</th>
            <th>Descripción</th>
            <th>Inicio</th>
            <th>Fin</th>
            <th>Acciones</th>
        </tr>
    </thead>
    <tbody>
        <?php
        $stmt = $pdo->query("SELECT m.*, h.numero AS num FROM mantenimientos m JOIN habitaciones h ON m.id_habitacion = h.id ORDER BY m.id DESC");
        foreach ($stmt as $fila) {
            echo "<tr>
                <td>{$fila['id']}</td>
                <td>{$fila['num']}</td>
                <td>{$fila['descripcion']}</td>
                <td>{$fila['fecha_inicio']}</td>
                <td>{$fila['fecha_fin']}</td>
                <td>
                  <a href='editar_mantenimiento.php?id={$fila['id']}' class='btn btn-edit btn-sm'>Editar</a>
                  <a href='eliminar_mantenimiento.php?id={$fila['id']}' class='btn btn-delete btn-sm' onclick='return confirm(\"¿Eliminar tarea?\")'>Eliminar</a>
                </td>
              </tr>";
        }
        ?>
    </tbody>
</table>

<?php include '../footer.php'; ?>