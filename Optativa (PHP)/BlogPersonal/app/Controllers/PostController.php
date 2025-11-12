<?php
// app/Controllers/PostController.php

namespace Controllers;

use Models\Post;
use Models\Comment;

class PostController extends BaseController
{
    private Post $postModel;

    public function __construct()
    {
        parent::__construct();
        $this->postModel = new Post();
        session_start();
    }

    /**
     * Lista todas las publicaciones
     */
    public function index(): void
    {
        $posts = $this->postModel->getAll();
        $this->view('posts/index', [
            'title' => 'Publicaciones',
            'posts' => $posts
        ]);
    }

    /**
     * Muestra un post individual por slug
     */
    

    public function show(string $slug): void
    {
        $post = $this->postModel->getBySlug($slug);
        if (!$post) {
            http_response_code(404);
            echo "<h1>404 - Publicación no encontrada</h1>";
            return;
        }

        $commentModel = new Comment();
        $comments = $commentModel->getByPostId($post['id']);

        $this->view('posts/show', [
            'title' => $post['title'],
            'post' => $post,
            'comments' => $comments
        ]);
    }


    /**
     * Mostrar formulario de creación de post
     */
    public function create(): void
    {
        $this->ensureAdmin();
        $this->view('posts/form', [
            'title' => 'Crear Publicación',
            'post' => null
        ]);
    }

    /**
     * Guardar nuevo post
     */
    public function store(): void
    {
        $this->ensureAdmin();

        $title = $_POST['title'] ?? '';
        $content = $_POST['content'] ?? '';
        $image = $this->handleImageUpload($_FILES['image'] ?? null);

        $this->postModel->create($title, $content, $image);
        $this->redirect('posts');
    }

    /**
     * Mostrar formulario de edición
     */
    public function edit(int $id): void
    {
        $this->ensureAdmin();
        $post = $this->postModel->getById($id);

        if (!$post) {
            http_response_code(404);
            echo "<h1>404 - Publicación no encontrada</h1>";
            return;
        }

        $this->view('posts/form', [
            'title' => 'Editar Publicación',
            'post' => $post
        ]);
    }

    /**
     * Actualizar post existente
     */
    public function update(int $id): void
    {
        $this->ensureAdmin();

        $title = $_POST['title'] ?? '';
        $content = $_POST['content'] ?? '';
        $image = $this->handleImageUpload($_FILES['image'] ?? null);

        $this->postModel->update($id, $title, $content, $image);
        $this->redirect('posts');
    }

    /**
     * Eliminar post
     */
    public function delete(int $id): void
    {
        $this->ensureAdmin();
        $this->postModel->delete($id);
        $this->redirect('posts');
    }

    /**
     * Subida de imágenes segura
     */
    private function handleImageUpload(?array $file): ?string
    {
        if (!$file || $file['error'] !== UPLOAD_ERR_OK) return null;

        $allowed = ['image/jpeg', 'image/png', 'image/gif'];
        if (!in_array($file['type'], $allowed)) return null;

        $ext = pathinfo($file['name'], PATHINFO_EXTENSION);
        $filename = uniqid() . '.' . $ext;

        $uploadDir = $this->config->uploads_dir;
        if (!is_dir($uploadDir)) mkdir($uploadDir, 0777, true);

        move_uploaded_file($file['tmp_name'], $uploadDir . '/' . $filename);

        return $filename;
    }

    /**
     * Verifica que el usuario esté logueado (simple control de acceso)
     */
    private function ensureAdmin(): void
    {
        if (!isset($_SESSION['user']) || $_SESSION['user']['role'] !== 'admin') {
            $this->redirect('login');
        }
    }
}
