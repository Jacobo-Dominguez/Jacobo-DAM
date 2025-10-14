<?php include '../header.php';
require '../conexion.php'; ?>

<div class="d-flex justify-content-between align-items-center mb-3">
    <h2>Listado de Reservas</h2>
    <a href="form_reserva.php" class="btn btn-success">+ Nueva Reserva</a>
</div>

<table class="table table-hover">
    <thead>
        <tr>
            <th>ID</th>
            <th>Huésped</th>
            <th>Habitación</th>
            <th>Llegada</th>
            <th>Salida</th>
            <th>Precio Total</th>
            <th>Estado</th>
            <th>Acciones</th>
        </tr>
    </thead>
    <tbody>
        <?php
        $stmt = $pdo->query("
        SELECT r.*, h.nombre AS huesped, ha.numero AS habitacion
        FROM reservas r
        JOIN huespedes h ON r.id_huesped = h.id
        JOIN habitaciones ha ON r.id_habitacion = ha.id
        ORDER BY r.id DESC
    ");
        foreach ($stmt as $fila) {
            echo "<tr>
                <td>{$fila['id']}</td>
                <td>{$fila['huesped']}</td>
                <td>{$fila['habitacion']}</td>
                <td>{$fila['fecha_llegada']}</td>
                <td>{$fila['fecha_salida']}</td>
                <td>{$fila['precio_total']} €</td>
                <td>{$fila['estado']}</td>
                <td>
                  <a href='editar_reserva.php?id={$fila['id']}' class='btn btn-edit btn-sm'>Editar</a>
                  <a href='eliminar_reserva.php?id={$fila['id']}' class='btn btn-delete btn-sm' onclick='return confirm(\"¿Eliminar reserva?\")'>Eliminar</a>
                </td>
              </tr>";
        }
        ?>
    </tbody>
</table>

<?php include '../footer.php'; ?>