<?php
$nombre_cookie = "modo_color";
$color_actual = "claro";

if (isset($_POST['color_elegido'])) {
    $color_elegido = htmlspecialchars($_POST['color_elegido']);

    $expiracion = time() + (86400 * 30);
    setcookie($nombre_cookie, $color_elegido, $expiracion, "/");

    $color_actual = $color_elegido;

} else if (isset($_COOKIE[$nombre_cookie])) {
    $color_actual = htmlspecialchars($_COOKIE[$nombre_cookie]);
}

$clase_body = ($color_actual === "oscuro") ? "modo-oscuro" : "modo-claro";
?>
