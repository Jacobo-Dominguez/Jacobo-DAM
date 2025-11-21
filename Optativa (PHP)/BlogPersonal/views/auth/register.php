<section class="card">
    <h2>Registrarse</h2>
    <?php if (!empty($error)): ?>
        <div class="error"><?php echo htmlspecialchars($error); ?></div>
    <?php endif; ?>
    <form method="post" action="?route=register" enctype="multipart/form-data">
        <label>Nombre</label>
        <input type="text" name="name" required>
        <label>Email</label>
        <input type="email" name="email" required>
        <label>Contraseña</label>
        <input type="password" name="password" required>
        <label>Avatar (opcional)</label>
        <input type="file" name="avatar" accept="image/png,image/jpeg">
            <label>Descripción (opcional)</label>
            <textarea name="description" id="description" class="form-control" rows="3" placeholder="Habla brevemente sobre ti"></textarea>
        <button class="primary" type="submit">Crear cuenta</button>
    </form>
</section>
