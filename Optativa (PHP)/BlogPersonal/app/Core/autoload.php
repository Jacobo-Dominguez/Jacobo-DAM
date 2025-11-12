<?php
spl_autoload_register(function($class) {
    // Reemplaza "\" por "/" en el namespace
    $path = __DIR__ . '/' . str_replace('\\', '/', $class) . '.php';
    // Cambiamos para que busque desde "app"
    $path = str_replace('Core/', '', $path); // Ajusta según tu estructura
    $path = __DIR__ . '/' . $path;

    if(file_exists($path)) {
        require_once $path;
    }
});
