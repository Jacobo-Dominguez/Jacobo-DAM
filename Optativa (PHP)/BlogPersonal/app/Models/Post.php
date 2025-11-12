<?php
// app/Models/Post.php

namespace Models;

use PDO;
use Models\Database;

class Post
{
    private PDO $db;

    public function __construct()
    {
        $this->db = Database::getConnection();
    }

    /**
     * Listar todas las publicaciones (más recientes primero)
     */
    public function getAll(): array
    {
        $sql = "SELECT * FROM posts ORDER BY created_at DESC";
        return $this->db->query($sql)->fetchAll();
    }

    /**
     * Obtener una publicación por su slug
     */
    public function getBySlug(string $slug): ?array
    {
        $sql = "SELECT * FROM posts WHERE slug = :slug LIMIT 1";
        $stmt = $this->db->prepare($sql);
        $stmt->execute([':slug' => $slug]);
        return $stmt->fetch() ?: null;
    }

    /**
     * Crear una nueva publicación
     */
    public function create(string $title, string $content, ?string $image = null): bool
    {
        $slug = $this->generateSlug($title);

        $sql = "INSERT INTO posts (title, slug, content, image) VALUES (:title, :slug, :content, :image)";
        $stmt = $this->db->prepare($sql);
        return $stmt->execute([
            ':title' => trim($title),
            ':slug' => $slug,
            ':content' => trim($content),
            ':image' => $image
        ]);
    }

    /**
     * Actualizar publicación por ID
     */
    public function update(int $id, string $title, string $content, ?string $image = null): bool
    {
        $slug = $this->generateSlug($title);

        $sql = "UPDATE posts SET title = :title, slug = :slug, content = :content, image = :image WHERE id = :id";
        $stmt = $this->db->prepare($sql);
        return $stmt->execute([
            ':title' => trim($title),
            ':slug' => $slug,
            ':content' => trim($content),
            ':image' => $image,
            ':id' => $id
        ]);
    }

    /**
     * Borrar publicación por ID
     */
    public function delete(int $id): bool
    {
        $sql = "DELETE FROM posts WHERE id = :id";
        $stmt = $this->db->prepare($sql);
        return $stmt->execute([':id' => $id]);
    }

    /**
     * Genera un slug amigable a partir del título
     */
    private function generateSlug(string $title): string
    {
        $slug = strtolower(trim($title));
        $slug = preg_replace('/[^a-z0-9]+/i', '-', $slug);
        $slug = trim($slug, '-');
        return $slug;
    }

    public function getById(int $id): ?array
    {
        $sql = "SELECT * FROM posts WHERE id = :id LIMIT 1";
        $stmt = $this->db->prepare($sql);
        $stmt->execute([':id' => $id]);
        return $stmt->fetch() ?: null;
    }
}
