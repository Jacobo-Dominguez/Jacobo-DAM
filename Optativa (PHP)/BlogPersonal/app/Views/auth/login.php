<div class="flex justify-center items-center mt-16">
  <div class="bg-white shadow-lg rounded-2xl p-8 w-full max-w-md animate__animated animate__fadeIn">
    <h2 class="text-2xl font-bold text-center text-blue-600 mb-6">Iniciar Sesión</h2>

    <?php if (!empty($error)): ?>
      <div class="bg-red-100 text-red-600 p-3 rounded-lg text-sm text-center mb-4">
        <?= htmlspecialchars($error) ?>
      </div>
    <?php endif; ?>

    <form method="POST" action="<?= $this->config->base_url ?>login">
      <div class="mb-4">
        <label for="email" class="block text-sm font-medium mb-1">Correo electrónico</label>
        <input type="email" id="email" name="email" required
          class="w-full border border-gray-300 rounded-lg px-3 py-2 focus:outline-none focus:ring-2 focus:ring-blue-500" />
      </div>

      <div class="mb-4">
        <label for="password" class="block text-sm font-medium mb-1">Contraseña</label>
        <input type="password" id="password" name="password" required
          class="w-full border border-gray-300 rounded-lg px-3 py-2 focus:outline-none focus:ring-2 focus:ring-blue-500" />
      </div>

      <button type="submit"
        class="w-full bg-blue-600 text-white py-2 rounded-lg hover:bg-blue-700 transition-colors">
        Entrar
      </button>
    </form>

    <p class="text-sm text-center mt-4 text-gray-600">
      ¿No tienes cuenta?
      <a href="<?= $this->config->base_url ?>register" class="text-blue-600 hover:underline">Regístrate</a>
    </p>
  </div>
</div>
