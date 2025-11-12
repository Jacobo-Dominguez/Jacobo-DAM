<?php

namespace Controllers;

use Models\Comment;

class CommentController extends BaseController
{
    private Comment $commentModel;

    public function __construct()
    {
        parent::__construct();
        $this->commentModel = new Comment();
        session_start();
        $this->ensureLogged();
    }

    private function ensureLogged(): void
    {
        if (!isset($_SESSION['user'])) {
            $this->redirect('login');
        }
    }

    // Crear comentario
    public function store(int $post_id): void
    {
        $content = $_POST['content'] ?? '';
        if (!empty(trim($content))) {
            $this->commentModel->create($post_id, $_SESSION['user']['id'], $content);
        }
        $this->redirect('posts/' . $_POST['slug']);
    }

    // Borrar comentario
    public function delete(int $id, int $post_id): void
    {
        $this->commentModel->delete($id, $_SESSION['user']['id']);
        $this->redirect('posts/' . $_POST['slug']);
    }
}
