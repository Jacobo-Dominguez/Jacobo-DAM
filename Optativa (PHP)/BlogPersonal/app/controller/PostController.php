<?php
// Este archivo maneja todas las acciones relacionadas con los "posts"
namespace app\controller;

// Importamos las clases que vamos a usar:
use app\core\Helpers;
use app\core\Auth;
use app\model\Post;

// Controlador principal para gestionar publicaciones (crear, leer, editar, eliminar).
class PostController
{   
    // Método que muestra la página principal con la lista de posts.
    public function index()
    {
        $user = Auth::user();  // Cogemos los datos del usuario actual desde la sesión con Auth.
        if (!$user) {
            Helpers::redirect('?route=login');
        }

        // Sistema de "Cargar más" - 4 posts por carga
        $postsPerPage = 4;
        $page = isset($_GET['page']) ? max(1, (int)$_GET['page']) : 1;
        $offset = ($page - 1) * $postsPerPage;
        
        // Mostrar solo posts publicados en el home (para todos los usuarios)
        // Los posts pendientes se gestionan en la página de moderación
        $posts = Post::allPublished($postsPerPage, $offset);
        $totalPosts = Post::countPublished();
        $hasMore = ($offset + $postsPerPage) < $totalPosts;

        Helpers::view('post/home.php', [
            'posts' => $posts, 
            'user' => $user,
            'currentPage' => $page,
            'hasMore' => $hasMore
        ]);
    }

    // Muestra el formulario para crear un nuevo post.
    public function create()
    {
        $user = Auth::user();
        if (!$user) Helpers::redirect('?route=login');
        Helpers::view('post/post.php');
    }

     // Procesa el envío del formulario de creación de posts.
    public function store()
    {
        $user = Auth::user();
        if (!$user) Helpers::redirect('?route=login');

        $title = $_POST['title'] ?? '';
        $content = $_POST['content'] ?? '';
        $category = $_POST['category'] ?? 'General';
        $imageName = null;
        if (!empty($_FILES['image']['name'])) {
            // Obtenemos la ruta temporal del archivo subido.
            $tmp = $_FILES['image']['tmp_name'];
            // Creamos un nombre único para evitar sobrescrituras
            $imageName = time() . '_' . basename($_FILES['image']['name']);
            move_uploaded_file($tmp, UPLOADS_DIR . '/' . $imageName);
        }
        // Guardamos el post en estado pendiente (status = 0)
        Post::create($user['id'], $title, $content, $imageName, $category);
        Helpers::redirect('?route=home');
    }

    // Aprobar un post (solo admin)
    public function approve()
    {
        $user = Auth::user();
        if (!$user || !Auth::isAdmin()) Helpers::redirect('?route=login');
        $id = $_GET['id'] ?? null;
        if ($id) {
            Post::setStatus($id, 1);
        }
        Helpers::redirect('?route=home');
    }

    // Rechazar un post (solo admin) — lo marcamos como rechazado (2)
    public function reject()
    {
        $user = Auth::user();
        if (!$user || !Auth::isAdmin()) Helpers::redirect('?route=login');
        $id = $_GET['id'] ?? null;
        if ($id) {
            Post::setStatus($id, 2);
        }
        Helpers::redirect('?route=home');
    }

    // Página de moderación (solo admin)
    public function moderate()
    {
        $user = Auth::user();
        if (!$user || !Auth::isAdmin()) Helpers::redirect('?route=login');
        
        // Sistema de "Cargar más" - 4 posts por carga
        $postsPerPage = 4;
        $page = isset($_GET['page']) ? max(1, (int)$_GET['page']) : 1;
        $offset = ($page - 1) * $postsPerPage;
        
        // Obtener todos los posts pendientes
        $posts = Post::allPending($postsPerPage, $offset);
        $totalPosts = Post::countPending();
        $hasMore = ($offset + $postsPerPage) < $totalPosts;
        
        Helpers::view('post/moderate.php', [
            'posts' => $posts, 
            'user' => $user,
            'currentPage' => $page,
            'hasMore' => $hasMore
        ]);
    }

    // Muestra un post individual (en detalle)
    public function show()
    {
        $user = Auth::user();
        if (!$user) Helpers::redirect('?route=login');
        $id = $_GET['id'] ?? null; // Obtenemos el ID del post desde la URL
        $post = Post::find($id); // Buscamos el post por su ID.
        if (!$post) Helpers::redirect('?route=home');
        $status = $post['status'] ?? 0;
        if ($status != 1 && !Auth::isAdmin() && ($post['user_id'] ?? null) != Auth::id()) {
            Helpers::redirect('?route=home');
        }
        Helpers::view('post/show.php', ['post' => $post]);// Mostramos la vista de detalle con los datos del post.
    }

    // Muestra el formulario para editar un post.
    public function edit()
    {
        $user = Auth::user();
        if (!$user) Helpers::redirect('?route=login');
        $id = $_GET['id'] ?? null;
        $post = Post::find($id);
        // Solo el autor o admin pueden editar
        if (!$post) Helpers::redirect('?route=home');
        if (!Auth::isAdmin() && ($post['user_id'] ?? null) != Auth::id()) {
            Helpers::redirect('?route=home');
        }
        Helpers::view('post/edit_post.php', ['post' => $post]);
    }

    // Procesa la actualización de un post.
    public function update()
    {
        $user = Auth::user();
        if (!$user) Helpers::redirect('?route=login');
        $id = $_POST['id'] ?? null;
        $post = Post::find($id);
        if (!$post) Helpers::redirect('?route=home');
        if (!Auth::isAdmin() && ($post['user_id'] ?? null) != Auth::id()) Helpers::redirect('?route=home');
        $title = $_POST['title'] ?? '';
        $content = $_POST['content'] ?? '';
        $category = $_POST['category'] ?? 'General';
        $imageName = null;
        if (!empty($_FILES['image']['name'])) {
            $tmp = $_FILES['image']['tmp_name'];
            $imageName = time() . '_' . basename($_FILES['image']['name']);
            move_uploaded_file($tmp, UPLOADS_DIR . '/' . $imageName);
        }
        Post::update($id, $title, $content, $imageName, $category);
        Helpers::redirect('?route=home');
    }

    // Elimina un post.
    public function delete()
    {
        $user = Auth::user();
        if (!$user) Helpers::redirect('?route=login');
        $id = $_GET['id'] ?? null;
        $post = Post::find($id);
        if ($post && (Auth::isAdmin() || ($post['user_id'] ?? null) == Auth::id())) {
            Post::delete($id);
        }
        Helpers::redirect('?route=home');
    }

    // Buscar posts
    public function search()
    {
        $user = Auth::user();
        if (!$user) Helpers::redirect('?route=login');
        
        $query = $_GET['q'] ?? '';
        $posts = [];
        $hasMore = false;
        $currentPage = 1;
        
        if (!empty($query)) {
            // Sistema de "Cargar más" - 4 posts por carga
            $postsPerPage = 4;
            $page = isset($_GET['page']) ? max(1, (int)$_GET['page']) : 1;
            $offset = ($page - 1) * $postsPerPage;
            
            $posts = Post::search($query, $postsPerPage, $offset);
            $totalPosts = Post::countSearch($query);
            $hasMore = ($offset + $postsPerPage) < $totalPosts;
            $currentPage = $page;
        }
        
        Helpers::view('post/home.php', [
            'posts' => $posts, 
            'user' => $user, 
            'searchQuery' => $query,
            'currentPage' => $currentPage,
            'hasMore' => $hasMore
        ]);
    }
}
