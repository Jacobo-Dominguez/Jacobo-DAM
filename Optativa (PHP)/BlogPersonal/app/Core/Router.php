<?php
// app/Core/Router.php

namespace Core;

use Controllers\BaseController;
use Controllers\AuthController;
use Controllers\PostController;
use Controllers\DashboardController;
use Controllers\UserController;


class Router
{
    public static function route(string $url)
    {
        $auth = new AuthController();
        $post = new PostController();

        // Eliminar barras al inicio y fin
        $url = trim($url, '/');

        // Manejo de URLs amigables para posts
        $parts = explode('/', $url);

        // Rutas básicas
        switch ($parts[0] ?? '') {
            case '':
                (new BaseController())->view('home', ['title' => 'Inicio']);
                break;

            case 'login':
                $_SERVER['REQUEST_METHOD'] === 'POST'
                    ? $auth->loginProcess()
                    : $auth->login();
                break;

            case 'register':
                $_SERVER['REQUEST_METHOD'] === 'POST'
                    ? $auth->registerProcess()
                    : $auth->register();
                break;

            case 'logout':
                $auth->logout();
                break;

            case 'posts':
                // posts/create
                if ($parts[1] ?? '' === 'create') {
                    $_SERVER['REQUEST_METHOD'] === 'POST'
                        ? $post->store()
                        : $post->create();
                    break;
                }

                // posts/edit/{id}
                if (($parts[1] ?? '') === 'edit' && isset($parts[2])) {
                    $_SERVER['REQUEST_METHOD'] === 'POST'
                        ? $post->update((int)$parts[2])
                        : $post->edit((int)$parts[2]);
                    break;
                }

                // posts/delete/{id}
                if (($parts[1] ?? '') === 'delete' && isset($parts[2])) {
                    $post->delete((int)$parts[2]);
                    break;
                }

                // posts/{slug} → ver post individual
                if (isset($parts[1]) && !is_numeric($parts[1])) {
                    $post->show($parts[1]);
                    break;
                }

                // posts → listado
                $post->index();
                break;

            case 'dashboard':
                (new \Controllers\DashboardController())->index();
                break;

            case 'profile':
                $controller = new \Controllers\UserController();
                $_SERVER['REQUEST_METHOD'] === 'POST'
                    ? $controller->update()
                    : $controller->edit();
                break;


            default:
                http_response_code(404);
                echo "<h1>404 - Página no encontrada</h1>";
                break;
        }
    }
}
