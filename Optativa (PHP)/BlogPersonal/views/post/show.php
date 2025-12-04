<section class="card">
    <h2><?php echo htmlspecialchars($post['title']); ?></h2>
    <p class="meta">por <?php echo htmlspecialchars($post['author']); ?> - <?php echo $post['created_at']; ?></p>
    <?php if ($post['image']): ?>
        <img class="full-image" src="/public/uploads/<?php echo htmlspecialchars($post['image']); ?>" alt="">
    <?php endif; ?>
    <div class="content"><?php echo nl2br(htmlspecialchars($post['content'])); ?></div>
    <p>
        <?php 
        $backUrl = '?route=home';
        if (isset($_GET['from']) && $_GET['from'] === 'moderate') {
            $backUrl = '?route=post/moderate';
        }
        ?>
        <a class="btn" href="<?= $backUrl ?>">Volver</a>
    </p>
</section>
