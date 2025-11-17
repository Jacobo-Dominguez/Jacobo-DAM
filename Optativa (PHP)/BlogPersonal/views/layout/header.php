<!doctype html>
<html lang="es">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width,initial-scale=1">
    <title>Blog Personal</title>
    <link rel="stylesheet" href="/public/assets/css/style.css">
</head>
<body>
<header class="site-header">
    <div class="container">
        <div class="brand">BlogPersonal</div>
        <nav class="nav">
            <?php if (!empty($_SESSION['user'])): ?>
                <span class="who">Conectado: <?php echo htmlspecialchars($_SESSION['user']['name']); ?></span>
                <a class="btn" href="?route=post/create">Crear post</a>
                <a class="btn" href="?route=logout">Cerrar sesión</a>
            <?php else: ?>
                <a class="btn" href="?route=login">Iniciar sesión</a>
                <a class="btn" href="?route=register">Registrarse</a>
            <?php endif; ?>
            <button id="theme-toggle" class="btn" title="Cambiar tema">🌙</button>
        </nav>
    </div>
</header>
<main class="container">
