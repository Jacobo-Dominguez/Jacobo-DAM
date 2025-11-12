<?php
// app/Controllers/DashboardController.php

namespace Controllers;

use Models\Post;

class DashboardController extends BaseController
{
    private Post $postModel;

    public function __construct()
    {
        parent::__construct();
        $this->postModel = new Post();
        session_start();
        $this->ensureAdmin();
    }

    /**
     * Mostrar listado de posts en dashboard
     */
    public function index(): void
    {
        $posts = $this->postModel->getAll();
        $this->view('dashboard/index', [
            'title' => 'Dashboard',
            'posts' => $posts,
            'user' => $_SESSION['user'] ?? null
        ]);
    }

    /**
     * Control de acceso: solo usuarios logueados
     */
    private function ensureAdmin(): void
    {
        if (!isset($_SESSION['user']) || $_SESSION['user']['role'] !== 'admin') {
            $this->redirect('login');
        }
    }
}
