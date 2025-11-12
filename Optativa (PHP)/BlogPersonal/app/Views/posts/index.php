<div class="max-w-6xl mx-auto mt-6 animate__animated animate__fadeIn">
    <div class="flex justify-between items-center mb-6">
        <h1 class="text-3xl font-bold text-blue-600">Publicaciones</h1>
        <?php if(isset($_SESSION['user'])): ?>
            <a href="<?= $this->config->base_url ?>posts/create"
               class="bg-blue-600 text-white px-4 py-2 rounded-lg hover:bg-blue-700 transition">
                Crear Nuevo
            </a>
        <?php endif; ?>
    </div>

    <div class="grid md:grid-cols-2 lg:grid-cols-3 gap-6">
        <?php foreach($posts as $post): ?>
            <div class="bg-white shadow rounded-lg overflow-hidden hover:shadow-lg transition">
                <?php if($post['image']): ?>
                    <img src="<?= $this->config->base_url ?>assets/uploads/<?= htmlspecialchars($post['image']) ?>"
                         class="w-full h-48 object-cover" alt="<?= htmlspecialchars($post['title']) ?>">
                <?php endif; ?>
                <div class="p-4">
                    <h2 class="text-xl font-semibold text-gray-800"><?= htmlspecialchars($post['title']) ?></h2>
                    <p class="text-gray-600 text-sm mt-2"><?= substr(strip_tags($post['content']),0,100) ?>...</p>
                    <a href="<?= $this->config->base_url ?>posts/<?= htmlspecialchars($post['slug']) ?>"
                       class="text-blue-600 hover:underline mt-2 inline-block">Leer más</a>
                </div>
            </div>
        <?php endforeach; ?>
    </div>
</div>
