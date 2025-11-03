<?php include '../header.php';
require '../conexion.php'; 
require_once __DIR__ . '/../includes/verificar_sesion.php';

if ($_SESSION['usuario_rol'] !== 'admin') {
    echo "<div class='alert alert-danger m-3'>Acceso denegado: solo el administrador puede acceder a esta sección.</div>";
    exit;
}?>

<div class="d-flex justify-content-between align-items-center mb-3">
    <h2>Lista de Huéspedes</h2>
    <a href="/huespedes/form_huesped.php" class="btn btn-success">+ Nuevo Huésped</a>
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
                  <a href='/huespedes/editar_huesped.php?id={$fila['id']}' class='btn btn-edit btn-sm'>Editar</a>
                  <a href='/huespedes/eliminar_huesped.php?id={$fila['id']}' class='btn btn-delete btn-sm' onclick='return confirm(\"¿Eliminar este huésped?\")'>Eliminar</a>
                </td>
              </tr>";
        }
        ?>
    </tbody>
</table>

<?php include '../footer.php'; ?>