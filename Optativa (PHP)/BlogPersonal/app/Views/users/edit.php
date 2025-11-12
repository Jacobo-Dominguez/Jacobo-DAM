<div class="max-w-md mx-auto mt-6 animate__animated animate__fadeIn">
    <h1 class="text-2xl font-bold text-blue-600 mb-4">Editar Perfil</h1>

    <?php if (!empty($error)): ?>
        <div class="bg-red-100 text-red-600 p-2 rounded mb-4"><?= htmlspecialchars($error) ?></div>
    <?php endif; ?>

    <form method="POST">
        <div class="mb-4">
            <label class="block mb-1">Usuario</label>
            <input type="text" name="username" value="<?= htmlspecialchars($user['username']) ?>"
                class="w-full border border-gray-300 rounded px-3 py-2" required>
        </div>
        <div class="mb-4">
            <label class="block mb-1">Email</label>
            <input type="email" name="email" value="<?= htmlspecialchars($user['email']) ?>"
                class="w-full border border-gray-300 rounded px-3 py-2" required>
        </div>
        <div class="mb-4">
            <label class="block mb-1">Nueva contraseña (opcional)</label>
            <input type="password" name="password" placeholder="********"
                class="w-full border border-gray-300 rounded px-3 py-2">
        </div>
        <button type="submit" class="bg-blue-600 text-white px-4 py-2 rounded hover:bg-blue-700 transition">Actualizar</button>
    </form>
</div>