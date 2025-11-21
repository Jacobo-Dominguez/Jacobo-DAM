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
                    <?php if (!empty($user) && !empty($user['is_admin'])): ?>
                        <p class="status">Estado: <?php
                            $s = $p['status'] ?? 0;
                            if ($s == 0) echo '<strong>pendiente</strong>';
                            elseif ($s == 1) echo '<strong>publicado</strong>';
                            else echo '<strong>rechazado</strong>';
                        ?></p>
                    <?php endif; ?>
                    <p><?php echo nl2br(htmlspecialchars(substr($p['content'],0,200))); ?>...</p>
                    <div class="actions">
                        <a class="btn" href="?route=post/show&id=<?php echo $p['id']; ?>">Ver</a>
                        <a class="btn" href="?route=post/edit&id=<?php echo $p['id']; ?>">Editar</a>
                        <a class="btn danger" href="?route=post/delete&id=<?php echo $p['id']; ?>" onclick="return confirm('Eliminar post?')">Eliminar</a>
                        <?php if (!empty($user) && !empty($user['is_admin'])): ?>
                            <?php if (($p['status'] ?? 0) == 0): ?>
                                <a class="btn" href="?route=post/approve&id=<?php echo $p['id']; ?>">Aprobar</a>
                                <a class="btn danger" href="?route=post/reject&id=<?php echo $p['id']; ?>" onclick="return confirm('Rechazar post?')">Rechazar</a>
                            <?php endif; ?>
                        <?php endif; ?>
                    </div>
                </article>
            <?php endforeach; ?>
        </div>
    <?php endif; ?>
</section>
