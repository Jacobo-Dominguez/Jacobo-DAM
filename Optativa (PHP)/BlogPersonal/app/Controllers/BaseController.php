<?php
// app/Controllers/BaseController.php

namespace Controllers;

class BaseController
{
    protected object $config;

    public function __construct()
    {
        // Cargamos la configuración global
        $this->config = require __DIR__ . '/../../config/config.php';
    }

    /**
     * Renderiza una vista con layout incluido.
     * @param string $view  Ruta relativa dentro de Views (sin .php)
     * @param array $data   Datos a pasar a la vista
     */
    public function view(string $view, array $data = []): void
    {
        // Extrae variables para que estén disponibles en la vista
        extract($data);

        // Rutas a los layouts y vistas
        $viewPath = __DIR__ . '/../Views/' . $view . '.php';
        $header = __DIR__ . '/../Views/layout/header.php';
        $footer = __DIR__ . '/../Views/layout/footer.php';

        // Renderizado ordenado
        if (file_exists($viewPath)) {
            require $header;
            require $viewPath;
            require $footer;
        } else {
            http_response_code(404);
            echo "<h1>Vista no encontrada: {$view}</h1>";
        }
    }

    /**
     * Redirige a una URL relativa o absoluta.
     */
    protected function redirect(string $path): void
    {
        header('Location: ' . $this->config->base_url . ltrim($path, '/'));
        exit;
    }

    
}
