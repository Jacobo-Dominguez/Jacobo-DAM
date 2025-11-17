<section>
    <h1>Mis posts</h1>
    <?php if (empty($posts)): ?>
        <p class="muted">No hay posts todavía.</p>
    <?php else: ?>
        <div class="posts-grid">
            <?php foreach ($posts as $p): ?>
                <article class="post-card">
                    <?php if ($p['image']): ?>
                        <img src="/public/uploads/<?php echo htmlspecialchars($p['image']); ?>" alt=""/>
                    <?php endif; ?>
                    <h3><?php echo htmlspecialchars($p['title']); ?></h3>
                    <p class="meta">por <?php echo htmlspecialchars($p['author']); ?> - <?php echo $p['created_at']; ?></p>
                    <p><?php echo nl2br(htmlspecialchars(substr($p['content'],0,200))); ?>...</p>
                    <div class="actions">
                        <a class="btn" href="?route=post/show&id=<?php echo $p['id']; ?>">Ver</a>
                        <a class="btn" href="?route=post/edit&id=<?php echo $p['id']; ?>">Editar</a>
                        <a class="btn danger" href="?route=post/delete&id=<?php echo $p['id']; ?>" onclick="return confirm('Eliminar post?')">Eliminar</a>
                    </div>
                </article>
            <?php endforeach; ?>
        </div>
    <?php endif; ?>
</section>
