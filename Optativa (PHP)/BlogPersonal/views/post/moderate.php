<section>
    <h1>Moderación de Posts</h1>
    <p class="muted">Aquí puedes aprobar o rechazar los posts pendientes de moderación.</p>
    
    <?php if (empty($posts)): ?>
        <p class="muted">No hay posts pendientes de moderación.</p>
    <?php else: ?>
        <div class="posts-grid">
            <?php foreach ($posts as $p): ?>
                <article class="post-card">
                    <?php if ($p['image']): ?>
                        <img src="/public/uploads/<?php echo htmlspecialchars($p['image']); ?>" alt=""/>
                    <?php endif; ?>
                    <h3><?php echo htmlspecialchars($p['title']); ?></h3>
                    <p class="meta">por <?php echo htmlspecialchars($p['author']); ?> - <?php echo $p['created_at']; ?></p>
                    <p class="status">Estado: <strong style="color: orange;">pendiente</strong></p>
                    <p><?php echo nl2br(htmlspecialchars(substr($p['content'],0,200))); ?>...</p>
                    <div class="actions">
                        <a class="btn" href="?route=post/show&id=<?php echo $p['id']; ?>">Ver completo</a>
                        <a class="btn" style="background: #28a745; color: white;" href="?route=post/approve&id=<?php echo $p['id']; ?>">✓ Aprobar</a>
                        <a class="btn danger" href="?route=post/reject&id=<?php echo $p['id']; ?>" onclick="return confirm('¿Rechazar este post?')">✗ Rechazar</a>
                    </div>
                </article>
            <?php endforeach; ?>
        </div>
    <?php endif; ?>
</section>
