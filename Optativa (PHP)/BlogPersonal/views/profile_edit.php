<div class="container mt-4" style="max-width:700px;">
    <h2>Editar perfil</h2>

    <?php if (!empty($error)): ?>
        <div class="alert alert-danger"><?= htmlspecialchars($error) ?></div>
    <?php endif; ?>

    <div class="card p-3">
        <form action="?route=profile/edit" method="POST" enctype="multipart/form-data">
            <div class="mb-3">
                <label>Nombre</label>
                <input type="text" name="name" class="form-control" value="<?= htmlspecialchars($user['name']) ?>" required>
            </div>
            <div class="mb-3">
                <label>Email</label>
                <input type="email" name="email" class="form-control" value="<?= htmlspecialchars($user['email']) ?>" required>
            </div>
            <div class="mb-3">
                <label>Descripción</label>
                <textarea name="description" class="form-control" rows="4"><?= htmlspecialchars($user['description'] ?? '') ?></textarea>
            </div>
            <?php if (!empty($user['id'])): ?>
                <div class="mb-3">
                    <label>Avatar actual</label>
                    <div><img src="/avatar.php?id=<?= $user['id'] ?>" style="max-width:120px;border-radius:50%;object-fit:cover;"></div>
                </div>
            <?php else: ?>
                <div class="mb-3">
                    <label>Avatar actual</label>
                    <div style="width:120px;height:120px;background:#eee;border-radius:50%;"></div>
                </div>
            <?php endif; ?>
            <div class="mb-3">
                <label>Cambiar avatar (opcional)</label>
                <input type="file" name="avatar" accept="image/png,image/jpeg" class="form-control">
                <small class="text-muted">JPG/PNG, máx 2MB</small>
            </div>

            <button class="primary">Guardar</button>
        </form>
    </div>
</div>

