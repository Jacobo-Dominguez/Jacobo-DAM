<div class="container mt-4" style="max-width:700px;">
    <h2>Perfil de usuario</h2>

    <div class="card p-3">
        <div class="d-flex align-items-center mb-3">
            <?php if (!empty($user['id'])): ?>
                <img src="/avatar.php?id=<?= $user['id'] ?>" alt="avatar" style="width:96px;height:96px;border-radius:50%;margin-right:12px;object-fit:cover;">
            <?php else: ?>
                <div style="width:96px;height:96px;border-radius:50%;background:#eee;display:inline-block;margin-right:12px;"></div>
            <?php endif; ?>
            <div>
                <h4><?= htmlspecialchars($user['name']) ?></h4>
                <p class="text-muted"><?= htmlspecialchars($user['email']) ?></p>
            </div>
        </div>

        <h5>Descripción</h5>
        <p><?= nl2br(htmlspecialchars($user['description'] ?? 'Sin descripción')) ?></p>
        <button class="primary" onclick="window.location.href='?route=profile/edit'">Editar perfil</button>
    </div>
</div>



