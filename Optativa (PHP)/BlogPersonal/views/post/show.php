<section class="card">
    <h2><?php echo htmlspecialchars($post['title']); ?></h2>
    <p class="meta">por <?php echo htmlspecialchars($post['author']); ?> - <?php echo $post['created_at']; ?></p>
    <?php if ($post['image']): ?>
        <img class="full-image" src="/public/uploads/<?php echo htmlspecialchars($post['image']); ?>" alt="">
    <?php endif; ?>
    <div class="content"><?php echo nl2br(htmlspecialchars($post['content'])); ?></div>
    <p><a class="btn" href="?route=home">Volver</a></p>
</section>
