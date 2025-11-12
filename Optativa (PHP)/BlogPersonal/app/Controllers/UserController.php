<?php

namespace Controllers;

use Models\User;

class UserController extends BaseController
{
    private User $userModel;

    public function __construct()
    {
        parent::__construct();
        $this->userModel = new User();
        session_start();
        $this->ensureLogged();
    }

    private function ensureLogged(): void
    {
        if (!isset($_SESSION['user'])) {
            $this->redirect('login');
        }
    }

    // Formulario editar perfil
    public function edit(): void
    {
        $user = $_SESSION['user'];
        $this->view('users/edit', ['user' => $user, 'title' => 'Editar Perfil']);
    }

    // Procesar actualización
    public function update(): void
    {
        $id = $_SESSION['user']['id'];
        $username = $_POST['username'] ?? '';
        $email = $_POST['email'] ?? '';
        $password = $_POST['password'] ?? '';

        $success = $this->userModel->update($id, $username, $email, $password);

        if ($success) {
            $_SESSION['user']['username'] = $username;
            $_SESSION['user']['email'] = $email;
            $this->redirect('profile');
        } else {
            $this->view('users/edit', ['user' => $_SESSION['user'], 'error' => 'Error al actualizar', 'title' => 'Editar Perfil']);
        }
    }
}
