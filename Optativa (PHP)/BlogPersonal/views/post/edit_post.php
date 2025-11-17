<section class="card">
    <h2>Editar post</h2>
    <form method="post" action="?route=post/update" enctype="multipart/form-data">
        <input type="hidden" name="id" value="<?php echo $post['id']; ?>">
        <label>Título</label>
        <input type="text" name="title" value="<?php echo htmlspecialchars($post['title']); ?>" required>
        <label>Contenido</label>
        <textarea name="content" rows="8" required><?php echo htmlspecialchars($post['content']); ?></textarea>
        <label>Imagen (subir para reemplazar)</label>
        <input type="file" name="image" accept="image/*">
        <button class="primary" type="submit">Actualizar</button>
        <button class="btn danger" type="cancel">Cancelar</button>
    </form>
</section>
