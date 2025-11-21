<?php
// Este archivo define el controlador de autenticación (login, registro, cierre de sesión)
namespace app\controller;

// Importamos de las clases que vamos a usar:
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
            $description = $_POST['description'] ?? null;
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
            // Subida de avatar (opcional) -> guardamos en BD como BLOB
            $avatarBinary = null;
            $avatarMime = null;
            if (!empty($_FILES['avatar']['tmp_name'])) {
                $file = $_FILES['avatar'];
                $allowed = ['image/jpeg', 'image/png'];
                if (!in_array($file['type'], $allowed) || $file['size'] > 2 * 1024 * 1024) {
                    $error = 'Archivo no válido';
                    Helpers::view('auth/register.php', ['error' => $error]);
                    return;
                }
                $avatarBinary = file_get_contents($file['tmp_name']);
                $avatarMime = $file['type'];
            }

            $id = User::create($name, $email, $password, $avatarBinary, $avatarMime, $description);
            $user = User::findById($id);
            Auth::loginByArray($user);
            Helpers::redirect('?route=home');
        } else {
            Helpers::view('auth/register.php');
        }
    }   

    // Mostrar perfil
    public function profile()
    {
        $user = Auth::user();
        if (empty($user)) {
            Helpers::redirect('?route=login');
        }
        $u = User::findById($user['id']);
        Helpers::view('profile.php', ['user' => $u]);
    }

    // Editar perfil
    public function editProfile()
    {
        $user = Auth::user();
        if (empty($user)) {
            Helpers::redirect('?route=login');
        }

        if ($_SERVER['REQUEST_METHOD'] === 'POST') {
            $name = $_POST['name'] ?? '';
            $email = $_POST['email'] ?? '';
            $description = $_POST['description'] ?? null;

            $avatarBinary = null;
            $avatarMime = null;
            if (!empty($_FILES['avatar']['tmp_name'])) {
                $file = $_FILES['avatar'];
                $allowed = ['image/jpeg', 'image/png'];
                if (!in_array($file['type'], $allowed) || $file['size'] > 2 * 1024 * 1024) {
                    $error = 'Archivo no válido';
                    Helpers::view('profile_edit.php', ['error' => $error]);
                    return;
                }
                $avatarBinary = file_get_contents($file['tmp_name']);
                $avatarMime = $file['type'];
            }

            User::update($user['id'], $name, $email, $avatarBinary, $avatarMime, $description);
            $u = User::findById($user['id']);
            Auth::loginByArray($u); // refresh session
            Helpers::redirect('?route=profile');
        } else {
            $u = User::findById($user['id']);
            Helpers::view('profile_edit.php', ['user' => $u]);
        }
    }

    // Método para cerrar sesión.
    public function logout()
    {
        Auth::logout();
        Helpers::redirect('?route=login');
    }
}
