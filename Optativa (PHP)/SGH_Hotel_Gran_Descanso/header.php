<?php

if (session_status() === PHP_SESSION_NONE) {
  session_start();
}

$nombre_cookie = "modo_color";
$color_actual = "claro";


if (isset($_POST['color_elegido'])) {
  $color_elegido = htmlspecialchars($_POST['color_elegido']);
  $expiracion = time() + (86400 * 30);
  setcookie($nombre_cookie, $color_elegido, $expiracion, "/");
  $color_actual = $color_elegido;
} else if (isset($_COOKIE[$nombre_cookie])) {
  $color_actual = htmlspecialchars($_COOKIE[$nombre_cookie]);
}


$clase_body = ($color_actual === "oscuro") ? "modo-oscuro" : "modo-claro";
?>
<!DOCTYPE html>
<html lang="es">

<head>
  <meta charset="UTF-8">
  <title>Hotel El Gran Descanso</title>
  <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css">
  <link rel="stylesheet" href="/css/style.css">
</head>

<body class="<?php echo $clase_body; ?>">
  <nav class="navbar navbar-expand-lg shadow <?php echo $clase_body === 'modo-oscuro' ? 'navbar-dark bg-dark' : 'navbar-light bg-primary'; ?>">
    <div class="container-fluid">
      <a class="navbar-brand text-white fw-bold" href="/index.php">El Gran Descanso</a>
      <div class="collapse navbar-collapse">
        <ul class="navbar-nav me-auto">
          <?php if (isset($_SESSION['usuario_id'])): ?>
            <?php if ($_SESSION['usuario_rol'] === 'admin'): ?>
              <li class="nav-item"><a class="nav-link text-white" href="/huespedes/listar_huespedes.php">Huéspedes</a></li>
              <li class="nav-item"><a class="nav-link text-white" href="/habitaciones/listar_habitaciones.php">Habitaciones</a></li>
              <li class="nav-item"><a class="nav-link text-white" href="/reservas/listar_reservas.php">Reservas</a></li>
              <li class="nav-item"><a class="nav-link text-white" href="/mantenimientos/listar_mantenimientos.php">Mantenimientos</a></li>
            <?php else: ?>
              <li class="nav-item"><a class="nav-link text-white" href="/reservas/insertar_reserva.php">Crear Reserva</a></li>
            <?php endif; ?>
          <?php endif; ?>
        </ul>

        <ul class="navbar-nav ms-auto align-items-center">
          <?php if (isset($_SESSION['usuario_nombre'])): ?>
            <li class="nav-item">
              <span class="nav-link text-white"><?php echo "👋 " . htmlspecialchars($_SESSION['usuario_nombre']); ?></span>
            </li>
            <li class="nav-item">
              <a class="nav-link text-white" href="/auth/logout.php">Cerrar sesión</a>
            </li>
          <?php else: ?>
            <li class="nav-item">
              <a class="nav-link text-white" href="/auth/login.php">Iniciar sesión</a>
            </li>
          <?php endif; ?>

          <!-- Selector de modo claro/oscuro -->
          <li class="nav-item ms-3">
            <form method="POST" class="d-flex">
              <select name="color_elegido" onchange="this.form.submit()" class="form-select form-select-sm">
                <option value="claro" <?php if ($color_actual === "claro") echo "selected"; ?>>Modo Claro</option>
                <option value="oscuro" <?php if ($color_actual === "oscuro") echo "selected"; ?>>Modo Oscuro</option>
              </select>
            </form>
          </li>
        </ul>
      </div>
    </div>
  </nav>
  <div class="container py-4">
</body>