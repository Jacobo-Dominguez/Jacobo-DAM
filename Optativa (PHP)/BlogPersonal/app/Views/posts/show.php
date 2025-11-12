<div class="max-w-4xl mx-auto mt-6 animate__animated animate__fadeIn">
    <h1 class="text-3xl font-bold text-blue-600 mb-4"><?= htmlspecialchars($post['title']) ?></h1>
    <?php if ($post['image']): ?>
        <img src="<?= $this->config->base_url ?>assets/uploads/<?= htmlspecialchars($post['image']) ?>"
            class="w-full h-64 object-cover rounded-lg mb-4" alt="<?= htmlspecialchars($post['title']) ?>">
    <?php endif; ?>
    <div class="text-gray-700 leading-relaxed whitespace-pre-line">
        <?= nl2br(htmlspecialchars($post['content'])) ?>
    </div>
    <a href="<?= $this->config->base_url ?>posts"
        class="inline-block mt-6 text-blue-600 hover:underline">&larr; Volver a publicaciones</a>
</div>

<h2 class="text-xl font-semibold mt-6 mb-2">Comentarios</h2>

<div class="space-y-4">
    <?php foreach ($comments as $comment): ?>
        <div id="comment-<?= $comment['id'] ?>"
            class="bg-gray-100 p-3 rounded-lg animate__animated animate__fadeInUp">
            <p class="text-gray-800"><?= htmlspecialchars($comment['content']) ?></p>
            <p class="text-sm text-gray-500 mt-1">Por <?= htmlspecialchars($comment['username']) ?> el <?= $comment['created_at'] ?></p>
            <?php if (isset($_SESSION['user']) && $_SESSION['user']['id'] === $comment['user_id']): ?>
                <button class="delete-comment mt-1 text-red-600 hover:underline text-sm"
                    data-id="<?= $comment['id'] ?>" data-slug="<?= $post['slug'] ?>">
                    Eliminar
                </button>
            <?php endif; ?>
        </div>
    <?php endforeach; ?>
</div>


<?php if (isset($_SESSION['user'])): ?>
    <form method="POST" action="<?= $this->config->base_url ?>comments/store/<?= $post['id'] ?>">
        <input type="hidden" name="slug" value="<?= $post['slug'] ?>">
        <textarea name="content" rows="3" required
            class="w-full border border-gray-300 rounded-lg px-3 py-2 mt-2 focus:outline-none focus:ring-2 focus:ring-blue-500"
            placeholder="Escribe un comentario..."></textarea>
        <button type="submit" class="mt-2 bg-blue-600 text-white px-4 py-2 rounded hover:bg-blue-700 transition">Comentar</button>
    </form>
<?php else: ?>
    <p class="text-gray-500 mt-2">Debes <a href="<?= $this->config->base_url ?>login" class="text-blue-600 hover:underline">iniciar sesión</a> para comentar.</p>
<?php endif; ?>


<script>
    document.querySelectorAll('.delete-comment').forEach(button => {
        button.addEventListener('click', function() {
            const id = this.dataset.id;
            const slug = this.dataset.slug;
            const commentDiv = document.getElementById('comment-' + id);

            if (confirm('¿Seguro que quieres eliminar este comentario?')) {
                fetch(`<?= $this->config->base_url ?>comments/delete/${id}`, {
                        method: 'POST',
                        headers: {
                            'Content-Type': 'application/x-www-form-urlencoded'
                        },
                        body: `slug=${slug}`
                    })
                    .then(() => {
                        // Animación fade out
                        commentDiv.style.transition = "opacity 0.5s ease, transform 0.5s ease";
                        commentDiv.style.opacity = 0;
                        commentDiv.style.transform = "translateX(50px)";
                        setTimeout(() => commentDiv.remove(), 500);
                    })
                    .catch(() => alert('Error al eliminar el comentario.'));
            }
        });
    });
</script>

<script>
    const commentForm = document.querySelector('form');
    commentForm.addEventListener('submit', function(e) {
        const content = commentForm.querySelector('textarea[name="content"]');
        if (content.value.trim().length < 3) {
            e.preventDefault();
            content.classList.add('border-red-500', 'animate__animated', 'animate__shakeX');
            setTimeout(() => content.classList.remove('animate__animated', 'animate__shakeX'), 1000);
        } else {
            content.classList.remove('border-red-500');
        }
    });
</script>