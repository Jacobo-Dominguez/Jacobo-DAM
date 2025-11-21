<?php
namespace app\core;

// Clase para manejar la conexión a la base de datos
use PDO;
use PDOException;

class DB
{
    private static $pdo = null; // Instancia de PDO

    // Obtiene la conexión PDO a la base de datos
    public static function connection()
    {   
        // Crear la conexión si no existe
        if (self::$pdo === null) {
            $dsn = 'mysql:host=' . DB_HOST . ';dbname=' . DB_NAME . ';charset=utf8mb4';
            try {
                self::$pdo = new PDO($dsn, DB_USER, DB_PASS, [
                    PDO::ATTR_ERRMODE => PDO::ERRMODE_EXCEPTION, // Modo de errores
                    PDO::ATTR_DEFAULT_FETCH_MODE => PDO::FETCH_ASSOC, // Modo de obtención por defecto
                    PDO::ATTR_EMULATE_PREPARES => false, // Desactivar emulación para LOBs
                ]);
            } catch (PDOException $e) {
                die('DB Connection failed: ' . $e->getMessage()); // Manejo básico de errores
            }
        }
        return self::$pdo;
    }
}
