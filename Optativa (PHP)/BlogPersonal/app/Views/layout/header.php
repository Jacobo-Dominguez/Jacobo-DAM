<?php
// app/Views/layout/header.php
?>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title><?= $data['title'] ?? 'Mi Blog MVC' ?></title>

    <!-- Tailwind CSS -->
    <script src="https://cdn.tailwindcss.com"></script>

    <!-- Animate.css para animaciones -->
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/animate.css/4.1.1/animate.min.css"/>

    <!-- Fuente moderna -->
    <link href="https://fonts.googleapis.com/css2?family=Inter:wght@400;600;700&display=swap" rel="stylesheet">

    <!-- Iconos -->
    <script src="https://unpkg.com/lucide@latest"></script>

    <!-- Estilos personalizados -->
    <style>
        body {
            font-family: 'Inter', sans-serif;
            background-color: #f9fafb;
            color: #1f2937;
        }
        .nav-link:hover {
            color: #2563eb;
        }
        .fade-in {
            animation: fadeIn 0.8s ease-in-out;
        }
        @keyframes fadeIn {
            from { opacity: 0; transform: translateY(10px); }
            to { opacity: 1; transform: translateY(0); }
        }
    </style>
</head>
<body class="min-h-screen flex flex-col">

    <!-- 🔝 NAVBAR -->
    <nav class="bg-white shadow-md fixed top-0 left-0 right-0 z-50">
        <div class="max-w-6xl mx-auto px-4">
            <div class="flex justify-between items-center h-16">
                <a href="<?= $this->config->base_url ?>" class="text-xl font-bold text-blue-600 hover:text-blue-700 transition">
                    Mi Blog MVC
                </a>
                <div class="space-x-6">
                    <a href="<?= $this->config->base_url ?>posts" class="nav-link hover:underline">Publicaciones</a>
                    <a href="<?= $this->config->base_url ?>login" class="nav-link hover:underline">Login</a>
                    <a href="<?= $this->config->base_url ?>register" class="nav-link hover:underline">Registro</a>
                </div>
            </div>
        </div>
    </nav>

    <!-- Espaciado para la navbar fija -->
    <div class="h-16"></div>

    <!-- 📦 CONTENIDO PRINCIPAL -->
    <main class="flex-grow container mx-auto px-4 fade-in">
