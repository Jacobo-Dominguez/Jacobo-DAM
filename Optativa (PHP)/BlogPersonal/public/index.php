<?php
// public/index.php

session_start();

// Cargar configuración general
$config = require __DIR__ . '/../config/config.php';

// Cargar helpers
//require_once __DIR__ . '/../app/Helpers/Helpers.php';


//require_once __DIR__ . '/../vendor/autoload.php';
require_once __DIR__ . '/../app/Core/Router.php';

// Autoload simple (para clases bajo app/)
spl_autoload_register(function ($class) {
    $base = __DIR__ . '/../app/';
    $path = $base . str_replace('\\', '/', $class) . '.php';
    if (file_exists($path)) require $path;
});

use Core\Router;

$url = $_GET['url'] ?? '';
Router::route($url);


// Obtener la ruta actual
$uri = parse_url($_SERVER['REQUEST_URI'], PHP_URL_PATH);
$uri = rtrim($uri, '/');

// Enrutamiento básico
if ($uri === '' || $uri === '/') {
    (new Controllers\PostController())->index();
    exit;
}

$segments = explode('/', trim($uri, '/'));

// /login
if ($segments[0] === 'login') {
    (new Controllers\AuthController())->login();
    exit;
}

// /register
if ($segments[0] === 'register') {
    (new Controllers\AuthController())->register();
    exit;
}

// /post/mi-slug
if ($segments[0] === 'post' && isset($segments[1])) {
    (new Controllers\PostController())->show($segments[1]);
    exit;
}

// Si no se encuentra la ruta
http_response_code(404);
echo "<h1>404 - Página no encontrada</h1>";

