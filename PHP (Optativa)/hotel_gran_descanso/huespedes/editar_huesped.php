<?php
include '../header.php';
require '../conexion.php';

if (!isset($_GET['id'])) {
    die("Falta el ID del huésped.");
}

$id = intval($_GET['id']);

$stmt = $pdo->prepare("SELECT * FROM huespedes WHERE id = ?");
$stmt->execute([$id]);
$h = $stmt->fetch();

if (!$h) {
    die("Huésped no encontrado.");
}

if ($_SERVER['REQUEST_METHOD'] === 'POST') {
    $nombre = trim($_POST['nombre']);
    $email = trim($_POST['email']);
    $documento = trim($_POST['documento_identidad']);

    if ($nombre === '' || $email === '' || $documento === '') {
        echo "<div class='alert alert-danger'>Todos los campos son obligatorios.</div>";
    } else {
        $chk = $pdo->prepare("SELECT COUNT(*) FROM huespedes WHERE email = ? AND id != ?");
        $chk->execute([$email, $id]);
        if ($chk->fetchColumn() > 0) {
            echo "<div class='alert alert-danger'>El email ya está en uso por otro huésped.</div>";
        } else {
            try {
                $upd = $pdo->prepare("UPDATE huespedes SET nombre = ?, email = ?, documento_identidad = ? WHERE id = ?");
                $upd->execute([$nombre, $email, $documento, $id]);
                header("Location: listar_huespedes.php");
                exit;
            } catch (PDOException $e) {
                echo "<div class='alert alert-danger'>Error al actualizar: " . htmlspecialchars($e->getMessage()) . "</div>";
            }
        }
    }
}
?>

<h2>Editar Huésped</h2>
<form method="post" class="mt-3">
    <div class="mb-3">
        <label class="form-label">Nombre</label>
        <input class="form-control" name="nombre" required value="<?= htmlspecialchars($h['nombre']) ?>">
    </div>
    <div class="mb-3">
        <label class="form-label">Email</label>
        <input class="form-control" type="email" name="email" required value="<?= htmlspecialchars($h['email']) ?>">
    </div>
    <div class="mb-3">
        <label class="form-label">Documento Identidad</label>
        <input class="form-control" name="documento_identidad" required value="<?= htmlspecialchars($h['documento_identidad']) ?>">
    </div>
    <button class="btn btn-primary" type="submit">Guardar cambios</button>
    <a class="btn btn-secondary" href="listar_huespedes.php">Cancelar</a>
</form>

<?php include '../footer.php'; ?>