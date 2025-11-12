<?php
// config/config.php

// Configuración general de la aplicación
return (object) [

    // Configuración de la base de datos
    'db' => [
        'dsn' => 'mysql:host=127.0.0.1;dbname=blog_mvc;charset=utf8mb4',
        'user' => 'root',
        'pass' => '',

        // Opciones recomendadas de PDO
        'options' => [
            PDO::ATTR_ERRMODE => PDO::ERRMODE_EXCEPTION,
            PDO::ATTR_DEFAULT_FETCH_MODE => PDO::FETCH_ASSOC,
            PDO::ATTR_EMULATE_PREPARES => false
        ]
    ],

    // URL base del proyecto (ajusta si está en subcarpeta)
    'base_url' => '/',

    // Ruta física donde se guardarán las imágenes subidas
    'uploads_dir' => __DIR__ . '/../public/assets/uploads',

    // Configuraciones opcionales del sitio
    'site_name' => 'Mi Blog MVC',
    'debug' => true
];
