<?php
namespace app\model;

use app\core\DB;

// Clase para manejar los usuarios
class User
{   
    // Obtiene un usuario por su email
    public static function findByEmail($email)
    {
        $db = DB::connection();
        $stmt = $db->prepare('SELECT * FROM users WHERE email = ? LIMIT 1');
        $stmt->execute([$email]);
        return $stmt->fetch();
    }

    // Obtiene un usuario por su ID
    public static function findById($id)
    {
        $db = DB::connection();
        $stmt = $db->prepare('SELECT * FROM users WHERE id = ? LIMIT 1');
        $stmt->execute([$id]);
        return $stmt->fetch();
    }

    // Crea un nuevo usuario
    public static function create($name, $email, $password)
    {
        $db = DB::connection();
        $hash = password_hash($password, PASSWORD_DEFAULT);
        $stmt = $db->prepare('INSERT INTO users (name,email,password,is_admin,created_at) VALUES (?,?,?,0,NOW())');
        $stmt->execute([$name, $email, $hash]);
        return $db->lastInsertId();
    }
}
