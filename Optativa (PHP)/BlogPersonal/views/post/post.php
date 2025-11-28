<section class="card">
    <h2>Crear nuevo post</h2>
    <form method="post" action="?route=post/store" enctype="multipart/form-data">
        <label>Título</label>
        <input type="text" name="title" required>
        <label>Contenido</label>
        <textarea name="content" rows="8" required></textarea>
        <label>Categoría</label>
        <select name="category">
            <option value="General">General</option>
            <option value="Tecnología">Tecnología</option>
            <option value="Personal">Personal</option>
            <option value="Viajes">Viajes</option>
            <option value="Otros">Otros</option>
        </select>
        <label>Imagen (opcional)</label>
        <input type="file" name="image" accept="image/*">
        <button class="primary" type="submit">Publicar</button>
    </form>
</section>
