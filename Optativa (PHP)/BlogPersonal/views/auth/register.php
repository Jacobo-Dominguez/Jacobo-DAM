<section class="card">
    <h2>Registrarse</h2>
    <?php if (!empty($error)): ?>
        <div class="error"><?php echo htmlspecialchars($error); ?></div>
    <?php endif; ?>
    <form method="post" action="?route=register">
        <label>Nombre</label>
        <input type="text" name="name" required>
        <label>Email</label>
        <input type="email" name="email" required>
        <label>Contraseña</label>
        <input type="password" name="password" required>
        <button class="primary" type="submit">Crear cuenta</button>
    </form>
</section>
