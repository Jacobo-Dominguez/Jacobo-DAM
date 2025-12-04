<?php
namespace app\controller;

use app\core\Helpers;
use app\core\Auth;
use app\model\User;

// Controlador para gestión de usuarios
class UserController
{
    // Muestra la página de gestión de usuarios (solo admin)
    public function manage()
    {
        $user = Auth::user();
        if (!$user || !Auth::isAdmin()) {
            Helpers::redirect('?route=login');
        }
        
        // Obtener todos los usuarios
        $users = User::all();
        
        Helpers::view('user/manage.php', [
            'users' => $users,
            'user' => $user
        ]);
    }

    // Elimina un usuario (solo admin)
    public function delete()
    {
        $user = Auth::user();
        if (!$user || !Auth::isAdmin()) {
            Helpers::redirect('?route=login');
        }
        
        $id = $_GET['id'] ?? null;
        if ($id) {
            User::delete($id);
        }
        
        Helpers::redirect('?route=user/manage');
    }
}
