<?php
// app/Models/Database.php

namespace Models;

use PDO;
use PDOException;

class Database
{
    private static ?PDO $connection = null;

    /**
     * Devuelve una única instancia de la conexión PDO (Singleton)
     */
    public static function getConnection(): PDO
    {
        if (self::$connection === null) {
            $config = require __DIR__ . '/../../config/config.php';

            try {
                self::$connection = new PDO(
                    $config->db['dsn'],
                    $config->db['user'],
                    $config->db['password'],
                    [
                        PDO::ATTR_ERRMODE => PDO::ERRMODE_EXCEPTION,
                        PDO::ATTR_DEFAULT_FETCH_MODE => PDO::FETCH_ASSOC,
                        PDO::ATTR_EMULATE_PREPARES => false,
                    ]
                );
            } catch (PDOException $e) {
                // Mostrar error de conexión bonito
                die("
                    <div style='
                        background:#fee2e2;
                        color:#b91c1c;
                        font-family:Arial, sans-serif;
                        padding:20px;
                        margin:20px;
                        border-radius:8px;
                        text-align:center;'>
                        <h2>Error de conexión a la base de datos 😥</h2>
                        <p>{$e->getMessage()}</p>
                    </div>
                ");
            }
        }

        return self::$connection;
    }

    /**
     * Cierra la conexión (opcional, útil para testing)
     */
    public static function closeConnection(): void
    {
        self::$connection = null;
    }
}
