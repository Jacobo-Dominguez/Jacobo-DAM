<?php
// Este archivo define el controlador de autenticación (login, registro, cierre de sesión)
namespace app\controller;

// Importamos las clases que vamos a usar:
use app\core\Helpers;
use app\core\Auth;
use app\model\User;

class AuthController
{   
    // Método para manejar el inicio de sesión.
    public function login()
    {   
        // Verificamos si la petición es de tipo POST (el usuario envia el formulario).
        if ($_SERVER['REQUEST_METHOD'] === 'POST') {
            $email = $_POST['email'] ?? '';
            $password = $_POST['password'] ?? '';
            // Buscamos al usuario en la base de datos por su email.
            $user = User::findByEmail($email);
            // Verificamos que el usuario exista y que la contraseña ingresada coincida con la almacenada
            if ($user && password_verify($password, $user['password'])) {
                Auth::loginByArray($user);
                Helpers::redirect('?route=home');
            } else {
                // Mensaje de error si las credenciales son inválidas
                $error = 'Credenciales inválidas';
                Helpers::view('auth/login.php', ['error' => $error]);
            }
        } else {
            Helpers::view('auth/login.php');
        }
    }

     // Método para manejar el registro de nuevos usuarios.
    public function register()
    {
        if ($_SERVER['REQUEST_METHOD'] === 'POST') {
            $name = $_POST['name'] ?? '';
            $email = $_POST['email'] ?? '';
            $password = $_POST['password'] ?? '';
            // Validación básica: aseguramos que ningún campo esté vacío.
            if (empty($name) || empty($email) || empty($password)) {
                $error = 'Completa todos los campos';
                Helpers::view('auth/register.php', ['error' => $error]);
                return;
            }
            // Verificamos que el email no esté ya registrado.
            if (User::findByEmail($email)) {
                $error = 'Email ya registrado';
                Helpers::view('auth/register.php', ['error' => $error]);
                return;
            }
            $id = User::create($name, $email, $password);
            $user = User::findById($id);
            Auth::loginByArray($user);
            Helpers::redirect('?route=home');
        } else {
            Helpers::view('auth/register.php');
        }
    }   

    // Método para cerrar sesión.
    public function logout()
    {
        Auth::logout();
        Helpers::redirect('?route=login');
    }
}
