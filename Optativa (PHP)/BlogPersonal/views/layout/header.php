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
        <div class="brand"><a href="?route=home" style="color:inherit; text-decoration:none;">BlogPersonal</a></div>
        <nav class="nav">
            <?php if (!empty($_SESSION['user'])): ?>
                <a class="profile-link" href="?route=profile" title="Ver perfil">
                    <?php if (!empty($_SESSION['user']['avatar_url'])): ?>
                        <img src="<?= htmlspecialchars($_SESSION['user']['avatar_url']) ?>" alt="avatar" style="width:28px;height:28px;border-radius:50%;vertical-align:middle;margin-right:6px;">
                    <?php endif; ?>
                    <span class="who"><?= htmlspecialchars($_SESSION['user']['name']) ?></span>
                </a>
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
