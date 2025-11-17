<?php
namespace app\model;

use app\core\DB;

// Clase para manejar los posts del blog
class Post
{   
    // Obtiene todos los posts con el nombre del autor
    public static function all()
    {
        $db = DB::connection();
        $stmt = $db->query('SELECT p.*, u.name as author FROM posts p LEFT JOIN users u ON u.id = p.user_id 
        ORDER BY p.created_at DESC');
        return $stmt->fetchAll();
    }

    // Obtiene todos los posts de un usuario específico
    public static function findByUser($user_id)
    {
        $db = DB::connection();
        $user_id = (int)$user_id;
        $stmt = $db->prepare('SELECT p.*, u.name as author FROM posts p LEFT JOIN users u ON u.id = p.user_id 
        WHERE p.user_id = ? ORDER BY p.created_at DESC');
        $stmt->execute([$user_id]);
        return $stmt->fetchAll();
    }

    // Obtiene un post por su ID, incluyendo el nombre del autor
    public static function find($id)
    {
        $db = DB::connection();
        $stmt = $db->prepare('SELECT p.*, u.name as author FROM posts p LEFT JOIN users u ON u.id = p.user_id 
        WHERE p.id = ? LIMIT 1');
        $stmt->execute([$id]);
        return $stmt->fetch();
    }

    // Crea un nuevo post
    public static function create($user_id, $title, $content, $image = null)
    {
        $db = DB::connection();
        $stmt = $db->prepare('INSERT INTO posts (user_id,title,content,image,created_at) VALUES (?,?,?,?,NOW())');
        $stmt->execute([$user_id, $title, $content, $image]);
        return $db->lastInsertId();
    }

    // Actualiza un post existente
    public static function update($id, $title, $content, $image = null)
    {
        $db = DB::connection();
        if ($image === null) {
            $stmt = $db->prepare('UPDATE posts SET title = ?, content = ? WHERE id = ?');
            $stmt->execute([$title, $content, $id]);
        } else {
            $stmt = $db->prepare('UPDATE posts SET title = ?, content = ?, image = ? WHERE id = ?');
            $stmt->execute([$title, $content, $image, $id]);
        }
        return true;
    }

    // Elimina un post por su ID
    public static function delete($id)
    {
        $db = DB::connection();
        $stmt = $db->prepare('DELETE FROM posts WHERE id = ?');
        return $stmt->execute([$id]);
    }
}
