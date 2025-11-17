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

        // Si es admin mostrar todos los posts, si no, solo los del usuario logueado
        if (Auth::isAdmin()) {
            $posts = Post::all();
        } else {
            $posts = Post::findByUser(Auth::id());
        }

        Helpers::view('post/home.php', ['posts' => $posts, 'user' => $user]);
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
        $imageName = null;
        if (!empty($_FILES['image']['name'])) {
            // Obtenemos la ruta temporal del archivo subido.
            $tmp = $_FILES['image']['tmp_name'];
            // Creamos un nombre único para evitar sobrescrituras
            $imageName = time() . '_' . basename($_FILES['image']['name']);
            move_uploaded_file($tmp, UPLOADS_DIR . '/' . $imageName);
        }
        Post::create($user['id'], $title, $content, $imageName); // Guardamos el post en la base de datos, asociado al ID
        Helpers::redirect('?route=home');
    }

    // Muestra un post individual (en detalle)
    public function show()
    {
        $user = Auth::user();
        if (!$user) Helpers::redirect('?route=login');
        $id = $_GET['id'] ?? null; // Obtenemos el ID del post desde la URL
        $post = Post::find($id); // Buscamos el post por su ID.
        Helpers::view('post/show.php', ['post' => $post]); // Mostramos la vista de detalle con los datos del post.
    }

    // Muestra el formulario para editar un post.
    public function edit()
    {
        $user = Auth::user();
        if (!$user) Helpers::redirect('?route=login');
        $id = $_GET['id'] ?? null;
        $post = Post::find($id);
        Helpers::view('post/edit_post.php', ['post' => $post]);
    }

    // Procesa la actualización de un post.
    public function update()
    {
        $user = Auth::user();
        if (!$user) Helpers::redirect('?route=login');
        $id = $_POST['id'] ?? null;
        $title = $_POST['title'] ?? '';
        $content = $_POST['content'] ?? '';
        $imageName = null;
        if (!empty($_FILES['image']['name'])) {
            $tmp = $_FILES['image']['tmp_name'];
            $imageName = time() . '_' . basename($_FILES['image']['name']);
            move_uploaded_file($tmp, UPLOADS_DIR . '/' . $imageName);
        }
        Post::update($id, $title, $content, $imageName);
        Helpers::redirect('?route=home');
    }

    // Elimina un post.
    public function delete()
    {
        $user = Auth::user();
        if (!$user) Helpers::redirect('?route=login');
        $id = $_GET['id'] ?? null;
        Post::delete($id);
        Helpers::redirect('?route=home');
    }
}
