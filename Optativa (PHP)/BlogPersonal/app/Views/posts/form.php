<div class="max-w-2xl mx-auto mt-6 animate__animated animate__fadeIn">
    <h1 class="text-2xl font-bold text-blue-600 mb-6">
        <?= $post ? 'Editar Publicación' : 'Crear Publicación' ?>
    </h1>

    <?php if (!empty($error)): ?>
        <div class="bg-red-100 text-red-600 p-3 rounded-lg text-sm text-center mb-4">
            <?= htmlspecialchars($error) ?>
        </div>
    <?php endif; ?>

    <form method="POST" enctype="multipart/form-data"
        action="<?= $post
                    ? $this->config->base_url . 'posts/update/' . $post['id']
                    : $this->config->base_url . 'posts/store' ?>">

        <div class="mb-4">
            <label class="block text-sm font-medium mb-1">Título</label>
            <input type="text" name="title" required
                value="<?= $post['title'] ?? '' ?>"
                class="w-full border border-gray-300 rounded-lg px-3 py-2 focus:outline-none focus:ring-2 focus:ring-blue-500" />
        </div>

        <div class="mb-4">
            <label class="block text-sm font-medium mb-1">Contenido</label>
            <textarea name="content" rows="6" required
                class="w-full border border-gray-300 rounded-lg px-3 py-2 focus:outline-none focus:ring-2 focus:ring-blue-500"><?= $post['content'] ?? '' ?></textarea>
        </div>

        <div class="mb-4">
            <label class="block text-sm font-medium mb-1">Imagen</label>
            <input type="file" name="image"
                class="w-full border border-gray-300 rounded-lg px-3 py-2 focus:outline-none focus:ring-2 focus:ring-blue-500" />
            <?php if (!empty($post['image'])): ?>
                <img src="<?= $this->config->base_url ?>assets/uploads/<?= $post['image'] ?>"
                    class="mt-2 w-32 h-32 object-cover rounded-lg" alt="Imagen actual">
            <?php endif; ?>
        </div>

        <button type="submit"
            class="bg-blue-600 text-white px-4 py-2 rounded-lg hover:bg-blue-700 transition">
            <?= $post ? 'Actualizar' : 'Crear' ?>
        </button>
    </form>

    <a href="<?= $this->config->base_url ?>posts" class="inline-block mt-4 text-blue-600 hover:underline">&larr; Volver</a>
</div>

<div class="mb-4">
    <label class="block text-sm font-medium mb-1">Imagen</label>
    <input type="file" id="imageInput" name="image"
        class="w-full border border-gray-300 rounded-lg px-3 py-2 focus:outline-none focus:ring-2 focus:ring-blue-500" />
    <div class="mt-2">
        <img id="imagePreview"
            src="<?= !empty($post['image']) ? $this->config->base_url . 'assets/uploads/' . $post['image'] : '' ?>"
            class="w-32 h-32 object-cover rounded-lg <?= empty($post['image']) ? 'hidden' : '' ?>"
            alt="Preview de la imagen">
    </div>
</div>

<script>
    const imageInput = document.getElementById('imageInput');
    const imagePreview = document.getElementById('imagePreview');

    imageInput.addEventListener('change', function() {
        const file = this.files[0];
        if (file) {
            const reader = new FileReader();

            reader.addEventListener('load', function() {
                imagePreview.src = this.result;
                imagePreview.classList.remove('hidden');
            });

            reader.readAsDataURL(file);
        } else {
            imagePreview.src = '';
            imagePreview.classList.add('hidden');
        }
    });
</script>

<script>
    const postForm = document.querySelector('form');
    postForm.addEventListener('submit', function(e) {
        let valid = true;

        // Validar título
        const title = postForm.querySelector('input[name="title"]');
        if (title.value.trim().length < 3) {
            valid = false;
            title.classList.add('border-red-500', 'animate__animated', 'animate__shakeX');
            setTimeout(() => title.classList.remove('animate__animated', 'animate__shakeX'), 1000);
        } else {
            title.classList.remove('border-red-500');
        }

        // Validar contenido
        const content = postForm.querySelector('textarea[name="content"]');
        if (content.value.trim().length < 10) {
            valid = false;
            content.classList.add('border-red-500', 'animate__animated', 'animate__shakeX');
            setTimeout(() => content.classList.remove('animate__animated', 'animate__shakeX'), 1000);
        } else {
            content.classList.remove('border-red-500');
        }

        if (!valid) e.preventDefault();
    });
</script>