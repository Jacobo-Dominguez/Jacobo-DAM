<div class="max-w-6xl mx-auto mt-6 animate__animated animate__fadeIn">
    <div class="flex justify-between items-center mb-6">
        <h1 class="text-3xl font-bold text-blue-600">Dashboard</h1>
        <?php if ($_SESSION['user']['role'] === 'admin'): ?>
            <a href="<?= $this->config->base_url ?>posts/create"
                class="bg-green-600 text-white px-4 py-2 rounded-lg hover:bg-green-700 transition">
                Crear Nuevo Post
            </a>
        <?php endif; ?>

    </div>

    <table class="w-full table-auto border-collapse shadow-lg rounded-lg overflow-hidden">
        <thead class="bg-blue-600 text-white">
            <tr>
                <th class="px-4 py-2 text-left">#</th>
                <th class="px-4 py-2 text-left">Título</th>
                <th class="px-4 py-2 text-left">Slug</th>
                <th class="px-4 py-2 text-left">Creado</th>
                <th class="px-4 py-2 text-center">Acciones</th>
            </tr>
        </thead>
        <tbody>
            <?php foreach ($posts as $post): ?>
                <tr class="border-b hover:bg-gray-100 transition">
                    <td class="px-4 py-2"><?= $post['id'] ?></td>
                    <td class="px-4 py-2"><?= htmlspecialchars($post['title']) ?></td>
                    <td class="px-4 py-2"><?= htmlspecialchars($post['slug']) ?></td>
                    <td class="px-4 py-2"><?= $post['created_at'] ?></td>
                    <td class="px-4 py-2 text-center space-x-2">
                        <?php if ($_SESSION['user']['role'] === 'admin'): ?>
                            <a href="<?= $this->config->base_url ?>posts/edit/<?= $post['id'] ?>"
                                class="bg-yellow-400 text-white px-2 py-1 rounded hover:bg-yellow-500 transition">
                                Editar
                            </a>
                            <a href="#" data-id="<?= $post['id'] ?>" class="delete-post bg-red-600 text-white px-2 py-1 rounded hover:bg-red-700 transition">
                                Eliminar
                            </a>
                        <?php endif; ?>
                    </td>
                </tr>
            <?php endforeach; ?>
        </tbody>
    </table>
</div>

<script>
    document.querySelectorAll('.delete-post').forEach(button => {
        button.addEventListener('click', function(e) {
            e.preventDefault();
            const id = this.dataset.id;
            const row = this.closest('tr');

            if (confirm('¿Seguro que quieres eliminar este post?')) {
                fetch(`<?= $this->config->base_url ?>posts/delete/${id}`, {
                        method: 'GET'
                    })
                    .then(res => {
                        // Animación fade out
                        row.style.transition = "opacity 0.5s ease, transform 0.5s ease";
                        row.style.opacity = 0;
                        row.style.transform = "translateX(-50px)";
                        setTimeout(() => row.remove(), 500);
                    })
                    .catch(err => alert('Error al eliminar el post.'));
            }
        });
    });
</script>