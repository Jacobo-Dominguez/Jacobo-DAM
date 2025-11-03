<!DOCTYPE html>
<html lang="es">

<head>
  <meta charset="UTF-8">
  <title>Hotel El Gran Descanso</title>
  <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css">
  <link rel="stylesheet" href="/css/style.css">
</head>

<body>
  <?php
  session_start(); // muy importante al inicio del header.php
  ?>
  <nav class="navbar navbar-expand-lg navbar-dark bg-dark shadow">
    <div class="container-fluid">
      <a class="navbar-brand" href="/index.php">El Gran Descanso</a>
      <div class="collapse navbar-collapse">
        <ul class="navbar-nav me-auto">
          <?php if (isset($_SESSION['usuario_id'])): ?>
            <?php if ($_SESSION['usuario_rol'] === 'admin'): ?>
              <li class="nav-item"><a class="nav-link" href="/huespedes/listar_huespedes.php">Huéspedes</a></li>
              <li class="nav-item"><a class="nav-link" href="/habitaciones/listar_habitaciones.php">Habitaciones</a></li>
              <li class="nav-item"><a class="nav-link" href="/reservas/listar_reservas.php">Reservas</a></li>
              <li class="nav-item"><a class="nav-link" href="/mantenimientos/listar_mantenimientos.php">Mantenimientos</a></li>
            <?php else: ?>
              <li class="nav-item"><a class="nav-link" href="/reservas/insertar_reserva.php">Crear Reserva</a></li>
            <?php endif; ?>
          <?php endif; ?>
        </ul>

        <ul class="navbar-nav ms-auto">
          <?php if (isset($_SESSION['usuario_nombre'])): ?>
            <li class="nav-item">
              <span class="nav-link text-white">👋 <?php echo htmlspecialchars($_SESSION['usuario_nombre']); ?></span>
            </li>
            <li class="nav-item">
              <a class="nav-link" href="/auth/logout.php">Cerrar sesión</a>
            </li>
          <?php else: ?>
            <li class="nav-item">
              <a class="nav-link" href="/auth/login.php">Iniciar sesión</a>
            </li>
          <?php endif; ?>
        </ul>
      </div>
    </div>
  </nav>

  <div class="container py-4">