<section class="card">
    <h2>Iniciar sesión</h2>
    <?php if (!empty($error)): ?>
        <div class="error"><?php echo htmlspecialchars($error); ?></div>
    <?php endif; ?>
    <form method="post" action="?route=login">
        <label>Email</label>
        <input type="email" name="email" required>
        <label>Contraseña</label>
        <input type="password" name="password" required>
        <button class="primary" type="submit">Entrar</button>
    </form>
</section>
