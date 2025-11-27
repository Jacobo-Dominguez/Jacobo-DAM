<?php
require __DIR__ . '/config.php';

use app\controller\AuthController;
use app\controller\PostController;

$route = $_GET['route'] ?? 'login';

// Rutas simples: login, register, logout, home, post/create, post/edit, post/delete, post/show
if ($route === 'login') {
    (new AuthController())->login();
} elseif ($route === 'register') {
    (new AuthController())->register();
} elseif ($route === 'logout') {
    (new AuthController())->logout();
} elseif ($route === 'profile') {
    (new AuthController())->profile();
} elseif ($route === 'profile/edit') {
    (new AuthController())->editProfile();
} elseif (str_starts_with($route, 'post/')) {
    $action = substr($route, 5);
    $pc = new PostController();
    if ($action === 'create') $pc->create();
    elseif ($action === 'store') $pc->store();
    elseif ($action === 'edit') $pc->edit();
    elseif ($action === 'update') $pc->update();
    elseif ($action === 'delete') $pc->delete();
    elseif ($action === 'approve') $pc->approve();
    elseif ($action === 'reject') $pc->reject();
    elseif ($action === 'moderate') $pc->moderate();
    elseif ($action === 'show') $pc->show();
    else $pc->index();
} else {
    (new PostController())->index();
}
