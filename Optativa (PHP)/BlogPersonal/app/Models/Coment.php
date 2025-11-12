<?php

namespace Models;

use PDO;
use Models\Database;

class Comment
{
    private PDO $db;

    public function __construct()
    {
        $this->db = Database::getConnection();
    }

    // Obtener comentarios de un post
    public function getByPostId(int $post_id): array
    {
        $sql = "SELECT c.*, u.username 
                FROM comments c 
                JOIN users u ON c.user_id = u.id
                WHERE c.post_id = :post_id
                ORDER BY c.created_at ASC";
        $stmt = $this->db->prepare($sql);
        $stmt->execute([':post_id' => $post_id]);
        return $stmt->fetchAll();
    }

    // Crear comentario
    public function create(int $post_id, int $user_id, string $content): bool
    {
        $sql = "INSERT INTO comments (post_id, user_id, content) VALUES (:post_id, :user_id, :content)";
        $stmt = $this->db->prepare($sql);
        return $stmt->execute([
            ':post_id' => $post_id,
            ':user_id' => $user_id,
            ':content' => trim($content)
        ]);
    }

    // Borrar comentario
    public function delete(int $id, int $user_id): bool
    {
        $sql = "DELETE FROM comments WHERE id = :id AND user_id = :user_id";
        $stmt = $this->db->prepare($sql);
        return $stmt->execute([':id' => $id, ':user_id' => $user_id]);
    }
}
