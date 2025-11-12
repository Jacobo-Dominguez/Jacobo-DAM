<?php
// app/Controllers/AuthController.php

namespace Controllers;

use Models\User;

class AuthController extends BaseController
{
    private User $userModel;

    public function __construct()
    {
        parent::__construct();
        $this->userModel = new User();
        session_start();
    }

    /**
     * Mostrar formulario de login
     */
    public function login(): void
    {
        // Si ya está logueado, redirige al inicio
        if (isset($_SESSION['user'])) {
            $this->redirect('');
        }

        $this->view('auth/login', ['title' => 'Iniciar Sesión']);
    }

    /**
     * Procesar login
     */
    public function loginProcess(): void
    {
        $email = $_POST['email'] ?? '';
        $password = $_POST['password'] ?? '';

        $user = $this->userModel->login($email, $password);

        if ($user) {
            $_SESSION['user'] = [
                'id' => $user['id'],
                'username' => $user['username'],
                'email' => $user['email']
            ];
            $this->redirect('');
        } else {
            $this->view('auth/login', [
                'title' => 'Iniciar Sesión',
                'error' => 'Correo o contraseña incorrectos.'
            ]);
        }

        $_SESSION['user'] = [
            'id' => $user['id'],
            'username' => $user['username'],
            'email' => $user['email'],
            'role' => $user['role']
        ];
    }

    /**
     * Mostrar formulario de registro
     */
    public function register(): void
    {
        $this->view('auth/register', ['title' => 'Registro de Usuario']);
    }

    /**
     * Procesar registro
     */
    public function registerProcess(): void
    {
        $username = trim($_POST['username'] ?? '');
        $email = trim($_POST['email'] ?? '');
        $password = $_POST['password'] ?? '';
        $confirm = $_POST['confirm'] ?? '';

        if (empty($username) || empty($email) || empty($password)) {
            $this->view('auth/register', [
                'title' => 'Registro de Usuario',
                'error' => 'Todos los campos son obligatorios.'
            ]);
            return;
        }

        if ($password !== $confirm) {
            $this->view('auth/register', [
                'title' => 'Registro de Usuario',
                'error' => 'Las contraseñas no coinciden.'
            ]);
            return;
        }

        if ($this->userModel->exists($email)) {
            $this->view('auth/register', [
                'title' => 'Registro de Usuario',
                'error' => 'El correo ya está registrado.'
            ]);
            return;
        }

        $this->userModel->register($username, $email, $password);

        $this->redirect('login');
    }

    /**
     * Cerrar sesión
     */
    public function logout(): void
    {
        session_destroy();
        $this->redirect('');
    }
}
