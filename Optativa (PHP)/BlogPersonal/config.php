<?php
// Configuración global
define('DB_HOST', '127.0.0.1');
define('DB_NAME', 'blogpersonal');
define('DB_USER', 'root');
define('DB_PASS', '');

define('BASE_URL', '/'); 

// Carpeta de subida
define('UPLOADS_DIR', __DIR__ . '/public/uploads');

// Crear uploads si no existe
if (!is_dir(UPLOADS_DIR)) {
    mkdir(UPLOADS_DIR, 0755, true);
}

session_start();

// Autocarga simple
spl_autoload_register(function($class){
    // Convierte el namespace en ruta de archivo. Ej: app\controller\PostController -> app/controller/PostController.php
    $path = __DIR__ . '/' . str_replace('\\', '/', $class) . '.php';
    if (file_exists($path)) require $path;
});
