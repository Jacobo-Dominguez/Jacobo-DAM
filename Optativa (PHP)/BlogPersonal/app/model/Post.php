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

    // Obtiene todos los posts publicados
    public static function allPublished($limit = null, $offset = 0)
    {
        $db = DB::connection();
        $sql = 'SELECT p.*, u.name as author FROM posts p LEFT JOIN users u ON u.id = p.user_id WHERE p.status = 1 ORDER BY p.created_at DESC';
        if ($limit !== null) {
            $sql .= ' LIMIT ' . (int)$limit . ' OFFSET ' . (int)$offset;
        }
        $stmt = $db->prepare($sql);
        $stmt->execute();
        return $stmt->fetchAll();
    }

    // Obtiene todos los posts pendientes de moderación
    public static function allPending($limit = null, $offset = 0)
    {
        $db = DB::connection();
        $sql = 'SELECT p.*, u.name as author FROM posts p LEFT JOIN users u ON u.id = p.user_id WHERE p.status = 0 ORDER BY p.created_at DESC';
        if ($limit !== null) {
            $sql .= ' LIMIT ' . (int)$limit . ' OFFSET ' . (int)$offset;
        }
        $stmt = $db->prepare($sql);
        $stmt->execute();
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
    public static function create($user_id, $title, $content, $image = null, $category = 'General')
    {
        $db = DB::connection();
        // status: 0 = pending, 1 = published, 2 = rejected
        $stmt = $db->prepare('INSERT INTO posts (user_id,title,content,image,category,status,created_at) VALUES (?,?,?,?,?,?,NOW())');
        $stmt->execute([$user_id, $title, $content, $image, $category, 0]);
        return $db->lastInsertId();
    }

    // Actualiza un post existente
    public static function update($id, $title, $content, $image = null, $category = 'General')
    {
        $db = DB::connection();
        if ($image === null) {
            $stmt = $db->prepare('UPDATE posts SET title = ?, content = ?, category = ? WHERE id = ?');
            $stmt->execute([$title, $content, $category, $id]);
        } else {
            $stmt = $db->prepare('UPDATE posts SET title = ?, content = ?, image = ?, category = ? WHERE id = ?');
            $stmt->execute([$title, $content, $image, $category, $id]);
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

    // Cambia el estado de un post: 0=pending,1=published,2=rejected
    public static function setStatus($id, $status)
    {
        $db = DB::connection();
        $stmt = $db->prepare('UPDATE posts SET status = ? WHERE id = ?');
        return $stmt->execute([(int)$status, (int)$id]);
    }

    // Obtiene las categorías disponibles
    public static function getCategories()
    {
        return ['General', 'Tecnología', 'Personal', 'Viajes', 'Otros'];
    }

    // Filtra posts publicados por categoría
    public static function findByCategory($category, $limit = null, $offset = 0)
    {
        $db = DB::connection();
        $sql = 'SELECT p.*, u.name as author FROM posts p LEFT JOIN users u ON u.id = p.user_id WHERE p.status = 1 AND p.category = ? ORDER BY p.created_at DESC';
        if ($limit !== null) {
            $sql .= ' LIMIT ' . (int)$limit . ' OFFSET ' . (int)$offset;
        }
        $stmt = $db->prepare($sql);
        $stmt->execute([$category]);
        return $stmt->fetchAll();
    }

    // Busca posts publicados por título o contenido
    public static function search($query, $limit = null, $offset = 0)
    {
        $db = DB::connection();
        $searchTerm = '%' . $query . '%';
        $sql = 'SELECT p.*, u.name as author FROM posts p LEFT JOIN users u ON u.id = p.user_id WHERE p.status = 1 AND (p.title LIKE ? OR p.content LIKE ?) ORDER BY p.created_at DESC';
        if ($limit !== null) {
            $sql .= ' LIMIT ' . (int)$limit . ' OFFSET ' . (int)$offset;
        }
        $stmt = $db->prepare($sql);
        $stmt->execute([$searchTerm, $searchTerm]);
        return $stmt->fetchAll();
    }

    // Cuenta el total de posts publicados
    public static function countPublished()
    {
        $db = DB::connection();
        $stmt = $db->query('SELECT COUNT(*) as total FROM posts WHERE status = 1');
        $result = $stmt->fetch();
        return $result['total'] ?? 0;
    }

    // Cuenta el total de posts pendientes
    public static function countPending()
    {
        $db = DB::connection();
        $stmt = $db->query('SELECT COUNT(*) as total FROM posts WHERE status = 0');
        $result = $stmt->fetch();
        return $result['total'] ?? 0;
    }

    // Cuenta resultados de búsqueda
    public static function countSearch($query)
    {
        $db = DB::connection();
        $searchTerm = '%' . $query . '%';
        $stmt = $db->prepare('SELECT COUNT(*) as total FROM posts WHERE status = 1 AND (title LIKE ? OR content LIKE ?)');
        $stmt->execute([$searchTerm, $searchTerm]);
        $result = $stmt->fetch();
        return $result['total'] ?? 0;
    }

    // Cuenta posts por categoría
    public static function countByCategory($category)
    {
        $db = DB::connection();
        $stmt = $db->prepare('SELECT COUNT(*) as total FROM posts WHERE status = 1 AND category = ?');
        $stmt->execute([$category]);
        $result = $stmt->fetch();
        return $result['total'] ?? 0;
    }
}
