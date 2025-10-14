<?php
include '../header.php';
require '../conexion.php';

if (!isset($_GET['id'])) {
    die("Falta el ID de la habitación.");
}

$id = intval($_GET['id']);

$stmt = $pdo->prepare("SELECT * FROM habitaciones WHERE id = ?");
$stmt->execute([$id]);
$h = $stmt->fetch();

if (!$h) {
    die("Habitación no encontrada.");
}

if ($_SERVER['REQUEST_METHOD'] === 'POST') {
    $numero = trim($_POST['numero']);
    $tipo = $_POST['tipo'];
    $precio_base = $_POST['precio_base'];
    $estado_limpieza = $_POST['estado_limpieza'];

    if ($numero === '' || $precio_base === '') {
        echo "<div class='alert alert-danger'>Número y precio son obligatorios.</div>";
    } else {
        $chk = $pdo->prepare("SELECT COUNT(*) FROM habitaciones WHERE numero = ? AND id != ?");
        $chk->execute([$numero, $id]);
        if ($chk->fetchColumn() > 0) {
            echo "<div class='alert alert-danger'>Ya existe otra habitación con ese número.</div>";
        } else {
            try {
                $upd = $pdo->prepare("UPDATE habitaciones SET numero = ?, tipo = ?, precio_base = ?, estado_limpieza = ? WHERE id = ?");
                $upd->execute([$numero, $tipo, $precio_base, $estado_limpieza, $id]);
                header("Location: listar_habitaciones.php");
                exit;
            } catch (PDOException $e) {
                echo "<div class='alert alert-danger'>Error al actualizar: " . htmlspecialchars($e->getMessage()) . "</div>";
            }
        }
    }
}
?>

<h2>Editar Habitación</h2>
<form method="post" class="mt-3">
    <div class="mb-3">
        <label class="form-label">Número</label>
        <input class="form-control" name="numero" required value="<?= htmlspecialchars($h['numero']) ?>">
    </div>
    <div class="mb-3">
        <label class="form-label">Tipo</label>
        <select class="form-select" name="tipo">
            <option value="Sencilla" <?= $h['tipo'] === 'Sencilla' ? 'selected' : '' ?>>Sencilla</option>
            <option value="Doble" <?= $h['tipo'] === 'Doble' ? 'selected' : '' ?>>Doble</option>
            <option value="Suite" <?= $h['tipo'] === 'Suite' ? 'selected' : '' ?>>Suite</option>
        </select>
    </div>
    <div class="mb-3">
        <label class="form-label">Precio base (€)</label>
        <input class="form-control" type="number" step="0.01" name="precio_base" required value="<?= htmlspecialchars($h['precio_base']) ?>">
    </div>
    <div class="mb-3">
        <label class="form-label">Estado limpieza</label>
        <select class="form-select" name="estado_limpieza">
            <option value="Limpia" <?= $h['estado_limpieza'] === 'Limpia' ? 'selected' : '' ?>>Limpia</option>
            <option value="Sucia" <?= $h['estado_limpieza'] === 'Sucia' ? 'selected' : '' ?>>Sucia</option>
            <option value="En Limpieza" <?= $h['estado_limpieza'] === 'En Limpieza' ? 'selected' : '' ?>>En Limpieza</option>
        </select>
    </div>

    <button class="btn btn-primary" type="submit">Guardar cambios</button>
    <a class="btn btn-secondary" href="listar_habitaciones.php">Cancelar</a>
</form>

<?php include '../footer.php'; ?>