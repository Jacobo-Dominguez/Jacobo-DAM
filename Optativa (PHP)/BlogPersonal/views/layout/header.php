<!doctype html>
<html lang="es">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width,initial-scale=1">
    <title>Blog Personal</title>
    <link rel="stylesheet" href="/public/assets/css/style.css">
    <link rel="stylesheet" href="/public/assets/css/inline-styles.css">
    <script src="/public/assets/js/menu.js" defer></script>
</head>
<body>
<header class="site-header">
    <div class="container">
        <div class="brand"><a href="?route=home" class="unstyled-link">BlogPersonal</a></div>
        <nav class="nav">
            <?php if (!empty($_SESSION['user'])): ?>
                <a class="profile-link" href="?route=profile" title="Ver perfil">
                    <?php if (!empty($_SESSION['user']['avatar_url'])): ?>
                        <img src="<?= htmlspecialchars($_SESSION['user']['avatar_url']) ?>" alt="avatar" class="header-avatar">
                    <?php endif; ?>
                    <span class="who"><?= htmlspecialchars($_SESSION['user']['name']) ?></span>
                </a>
                <form method="get" action="?route=post/search" class="search-form">
                    <input type="hidden" name="route" value="post/search">
                    <input type="text" name="q" placeholder="Buscar posts..." class="search-input">
                    <button type="submit" class="btn search-btn">🔍</button>
                </form>
                <div class="hamburger-menu">
                    <button class="hamburger-btn" aria-label="Menú">
                        <div class="hamburger-icon">
                            <span></span>
                            <span></span>
                            <span></span>
                        </div>
                        <span class="menu-text">Menú</span>
                    </button>
                    <div class="dropdown-menu">
                        <a href="?route=post/create">Crear post</a>
                        <?php if (!empty($_SESSION['user']['is_admin'])): ?>
                            <a href="?route=post/moderate">Moderar posts</a>
                        <?php endif; ?>
                        <a href="?route=profile">Perfil</a>
                        <?php if (!empty($_SESSION['user']['is_admin'])): ?>
                            <a href="?route=user/manage">Gestión de usuarios</a>
                        <?php endif; ?>
                        <a href="?route=logout">Cerrar sesión</a>
                    </div>
                </div>
            <?php else: ?>
                <a class="btn" href="?route=login">Iniciar sesión</a>
                <a class="btn" href="?route=register">Registrarse</a>
            <?php endif; ?>
            <button id="theme-toggle" class="btn" title="Cambiar tema">🌙</button>
        </nav>
    </div>
</header>
<main class="container">
