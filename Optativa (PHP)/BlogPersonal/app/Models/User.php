<?php
// app/Models/User.php

namespace Models;

use PDO;
use Models\Database;

class User
{
    private PDO $db;

    public function __construct()
    {
        $this->db = Database::getConnection();
    }

    /**
     * Crea un nuevo usuario
     */
    public function register(string $username, string $email, string $password, string $role = 'user'): bool
    {
        $hash = password_hash($password, PASSWORD_DEFAULT);
        $sql = "INSERT INTO users (username, email, password, role) VALUES (:username, :email, :password, :role)";
        $stmt = $this->db->prepare($sql);
        return $stmt->execute([
            ':username' => $username,
            ':email' => $email,
            ':password' => $hash,
            ':role' => $role
        ]);
    }


    /**
     * Verifica el login de un usuario
     */
    public function login(string $email, string $password): ?array
    {
        $sql = "SELECT * FROM users WHERE email = :email LIMIT 1";
        $stmt = $this->db->prepare($sql);
        $stmt->execute([':email' => trim($email)]);
        $user = $stmt->fetch();

        if ($user && password_verify($password, $user['password'])) {
            return $user;
        }

        return null;
    }

    /**
     * Comprueba si ya existe un usuario con ese email
     */
    public function exists(string $email): bool
    {
        $sql = "SELECT id FROM users WHERE email = :email";
        $stmt = $this->db->prepare($sql);
        $stmt->execute([':email' => trim($email)]);
        return $stmt->fetch() ? true : false;
    }

    /**
     * Obtiene usuario por ID
     */
    public function getById(int $id): ?array
    {
        $sql = "SELECT id, username, email, created_at FROM users WHERE id = :id";
        $stmt = $this->db->prepare($sql);
        $stmt->execute([':id' => $id]);
        return $stmt->fetch() ?: null;
    }

    public function isAdmin(array $user): bool
    {
        return isset($user['role']) && $user['role'] === 'admin';
    }

    public function update(int $id, string $username, string $email, string $password = ''): bool
    {
        $params = [':id' => $id, ':username' => $username, ':email' => $email];
        $sql = "UPDATE users SET username=:username, email=:email";

        if (!empty($password)) {
            $sql .= ", password=:password";
            $params[':password'] = password_hash($password, PASSWORD_DEFAULT);
        }

        $sql .= " WHERE id=:id";
        $stmt = $this->db->prepare($sql);
        return $stmt->execute($params);
    }
}
