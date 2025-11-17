<?php
namespace app\core;

// Clase para funciones auxiliares
class Helpers
{   
    // Redirige a una ruta específica
    public static function redirect($path)
    {
        header('Location: ' . $path);
        exit;
    }

    // Renderiza una vista con datos opcionales
    public static function view($path, $data = [])
    {
        extract($data);
        require __DIR__ . '/../../views/layout/header.php';
        require __DIR__ . '/../../views/' . $path;
        require __DIR__ . '/../../views/layout/footer.php';
    }
}
