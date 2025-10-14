<?php
include '../header.php';
require '../conexion.php';

if (!isset($_GET['id'])) {
    die("Falta el ID del mantenimiento.");
}

$id = intval($_GET['id']);

$stmt = $pdo->prepare("SELECT * FROM mantenimientos WHERE id = ?");
$stmt->execute([$id]);
$m = $stmt->fetch();

if (!$m) {
    die("Registro de mantenimiento no encontrado.");
}

$habStmt = $pdo->query("SELECT id, numero FROM habitaciones ORDER BY numero");
$habitaciones = $habStmt->fetchAll();

if ($_SERVER['REQUEST_METHOD'] === 'POST') {
    $id_habitacion = intval($_POST['id_habitacion']);
    $descripcion = trim($_POST['descripcion']);
    $fecha_inicio = $_POST['fecha_inicio'];
    $fecha_fin = $_POST['fecha_fin'] ?: null;

    if ($descripcion === '' || $fecha_inicio === '') {
        echo "<div class='alert alert-danger'>Descripción y fecha de inicio obligatorias.</div>";
    } else {
        if ($fecha_fin && $fecha_inicio > $fecha_fin) {
            echo "<div class='alert alert-danger'>La fecha de inicio no puede ser posterior a la fecha fin.</div>";
        } else {
            try {
                $upd = $pdo->prepare("UPDATE mantenimientos SET id_habitacion = ?, descripcion = ?, fecha_inicio = ?, fecha_fin = ? WHERE id = ?");
                $upd->execute([$id_habitacion, $descripcion, $fecha_inicio, $fecha_fin, $id]);
                header("Location: listar_mantenimientos.php");
                exit;
            } catch (PDOException $e) {
                echo "<div class='alert alert-danger'>Error al actualizar: " . htmlspecialchars($e->getMessage()) . "</div>";
            }
        }
    }
}
?>

<h2>Editar Mantenimiento</h2>
<form method="post" class="mt-3">
    <div class="mb-3">
        <label class="form-label">Habitación</label>
        <select name="id_habitacion" class="form-select" required>
            <?php foreach ($habitaciones as $hh): ?>
                <option value="<?= $hh['id'] ?>" <?= $hh['id'] == $m['id_habitacion'] ? 'selected' : '' ?>>Habitación <?= htmlspecialchars($hh['numero']) ?></option>
            <?php endforeach; ?>
        </select>
    </div>
    <div class="mb-3">
        <label class="form-label">Descripción</label>
        <textarea name="descripcion" class="form-control" rows="4" required><?= htmlspecialchars($m['descripcion']) ?></textarea>
    </div>
    <div class="mb-3">
        <label class="form-label">Fecha inicio</label>
        <input type="date" name="fecha_inicio" class="form-control" required value="<?= $m['fecha_inicio'] ?>">
    </div>
    <div class="mb-3">
        <label class="form-label">Fecha fin (opcional)</label>
        <input type="date" name="fecha_fin" class="form-control" value="<?= $m['fecha_fin'] ?>">
    </div>

    <button class="btn btn-primary" type="submit">Guardar cambios</button>
    <a class="btn btn-secondary" href="listar_mantenimientos.php">Cancelar</a>
</form>

<?php include '../footer.php'; ?>