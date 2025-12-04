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

    // Crea un nuevo usuario (con avatar binario y descripción opcionales)
    // Esto de avatares lo he tenido que implementar completamente con IA (lo siento)
    // $avatar: binary data (string) or null
    // $avatar_mime: mime type string (e.g. 'image/png') or null
    public static function create($name, $email, $password, $avatar = null, $avatar_mime = null, $description = null)
    {
        $db = DB::connection();
        $hash = password_hash($password, PASSWORD_DEFAULT);
        
        // Si no se proporciona avatar, usar el avatar predeterminado
        if ($avatar === null) {
            $defaultAvatarPath = __DIR__ . '/../../public/assets/images/default-avatar.png';
            if (file_exists($defaultAvatarPath)) {
                $avatar = file_get_contents($defaultAvatarPath);
                $avatar_mime = 'image/png';
            }
        }
        
        try {
            $stmt = $db->prepare('INSERT INTO users (name,email,password,avatar_mime,avatar,description,is_admin,created_at) VALUES (?,?,?,?,?,?,0,NOW())');
            // Bind parameters to support BLOB using a memory stream for the blob
            $stmt->bindValue(1, $name);
            $stmt->bindValue(2, $email);
            $stmt->bindValue(3, $hash);
            $stmt->bindValue(4, $avatar_mime);
            if ($avatar !== null) {
                $fp = fopen('php://memory', 'r+');
                fwrite($fp, $avatar);
                rewind($fp);
                $stmt->bindParam(5, $fp, \PDO::PARAM_LOB);
            } else {
                $stmt->bindValue(5, null);
            }
            $stmt->bindValue(6, $description);
            $stmt->execute();
            if (!empty($fp) && is_resource($fp)) fclose($fp);
            return $db->lastInsertId();
        } catch (\PDOException $e) {
            // Si la columna avatar/description no existe en la tabla, hacemos la inserción básica
            if ($e->getCode() === '42S22') {
                $stmt = $db->prepare('INSERT INTO users (name,email,password,is_admin,created_at) VALUES (?,?,?,0,NOW())');
                $stmt->execute([$name, $email, $hash]);
                return $db->lastInsertId();
            }
            throw $e;
        }
    }

    // Actualiza los datos del usuario
    public static function update($id, $name, $email, $avatar = null, $avatar_mime = null, $description = null)
    {
        $db = DB::connection();
        try {
            if ($avatar !== null) {
                $stmt = $db->prepare('UPDATE users SET name = ?, email = ?, avatar_mime = ?, avatar = ?, description = ? WHERE id = ?');
                $stmt->bindValue(1, $name);
                $stmt->bindValue(2, $email);
                $stmt->bindValue(3, $avatar_mime);
                // Use memory stream for blob
                $fp = fopen('php://memory', 'r+');
                fwrite($fp, $avatar);
                rewind($fp);
                $stmt->bindParam(4, $fp, \PDO::PARAM_LOB);
                $stmt->bindValue(5, $description);
                $stmt->bindValue(6, $id);
                $res = $stmt->execute();
                if (!empty($fp) && is_resource($fp)) fclose($fp);
                return $res;
            } else {
                $stmt = $db->prepare('UPDATE users SET name = ?, email = ?, description = ? WHERE id = ?');
                return $stmt->execute([$name, $email, $description, $id]);
            }
        } catch (\PDOException $e) {
            // Fallback si las columnas avatar/description no existen
            if ($e->getCode() === '42S22') {
                $stmt = $db->prepare('UPDATE users SET name = ?, email = ? WHERE id = ?');
                return $stmt->execute([$name, $email, $id]);
            }
            throw $e;
        }
    }

    // Obtiene todos los usuarios
    public static function all()
    {
        $db = DB::connection();
        $stmt = $db->prepare('SELECT id, name, email, is_admin, created_at FROM users ORDER BY created_at DESC');
        $stmt->execute();
        return $stmt->fetchAll();
    }

    // Elimina un usuario por su ID
    public static function delete($id)
    {
        $db = DB::connection();
        $stmt = $db->prepare('DELETE FROM users WHERE id = ?');
        return $stmt->execute([$id]);
    }
}
