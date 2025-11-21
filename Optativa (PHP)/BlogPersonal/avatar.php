<?php
// avatar.php - sirve el avatar de un usuario por id (desde la BD)
require __DIR__ . '/config.php';

// Autoload
spl_autoload_register(function($class){
    $path = __DIR__ . '/' . str_replace('\\', '/', $class) . '.php';
    if (file_exists($path)) require $path;
});

use app\core\DB;

$id = isset($_GET['id']) ? (int) $_GET['id'] : 0;
if (!$id) {
    http_response_code(404);
    exit;
}

$db = DB::connection();
$stmt = $db->prepare('SELECT avatar, avatar_mime FROM users WHERE id = ? LIMIT 1');
$stmt->execute([$id]);
$row = $stmt->fetch();

if (!$row || empty($row['avatar'])) {
    http_response_code(404);
    exit;
}

$mime = $row['avatar_mime'] ?: 'application/octet-stream';
$data = $row['avatar'];

// Set headers
header('Content-Type: ' . $mime);
if (is_string($data)) {
    header('Content-Length: ' . strlen($data));
    // Cache a short time
    header('Cache-Control: public, max-age=3600');
    echo $data;
} elseif (is_resource($data)) {
    // stream resource
    header('Cache-Control: public, max-age=3600');
    fseek($data, 0);
    fpassthru($data);
} else {
    // fallback
    echo $data;
}
exit;
