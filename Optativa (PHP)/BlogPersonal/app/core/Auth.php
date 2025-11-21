<?php
namespace app\core;

// Clase para manejar la autenticación de usuarios
class Auth
{   
    // Obtiene los datos del usuario logueado desde la sesión
    public static function user()
    {
        return $_SESSION['user'] ?? null;
    }

    // Obtiene el ID del usuario logueado
    public static function id()
    {
        $u = self::user();
        return $u['id'] ?? null;
    }

    // Verifica si el usuario logueado es administrador
    public static function isAdmin()
    {
        $u = self::user();
        return !empty($u) && ($u['is_admin'] ?? 0) == 1;
    }

    // Inicia sesión guardando los datos del usuario en la sesión
    public static function loginByArray(array $user)
    {
        // Avoid storing binary avatar in session; provide URL endpoint to serve it
        $avatarUrl = null;
        if (!empty($user['id'])) {
            $avatarUrl = '/avatar.php?id=' . $user['id'];
        }

        $_SESSION['user'] = [
            'id' => $user['id'],
            'name' => $user['name'] ?? $user['username'] ?? '',
            'email' => $user['email'] ?? '',
            'is_admin' => $user['is_admin'] ?? 0,
            'avatar_url' => $avatarUrl,
            'description' => $user['description'] ?? null,
        ];
    }

    // Cierra la sesión del usuario
    public static function logout()
    {
        unset($_SESSION['user']);
    }
}
