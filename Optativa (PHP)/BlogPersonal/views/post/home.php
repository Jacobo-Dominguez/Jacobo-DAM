<section>
    <h1>Mis posts</h1>
    <?php if (isset($searchQuery) && !empty($searchQuery)): ?>
        <p class="muted">Resultados de búsqueda para: "<strong><?= htmlspecialchars($searchQuery) ?></strong>"</p>
    <?php endif; ?>
    <?php if (empty($posts)): ?>
        <p class="muted">No hay posts todavía.</p>
    <?php else: ?>
        <div class="posts-grid" id="posts-container">
            <?php foreach ($posts as $p): ?>
                <article class="post-card">
                    <?php if ($p['image']): ?>
                        <img src="/public/uploads/<?php echo htmlspecialchars($p['image']); ?>" alt=""/>
                    <?php endif; ?>
                    <h3><?php echo htmlspecialchars($p['title']); ?></h3>
                    <?php if (!empty($p['category'])): ?>
                        <span class="category-badge">
                            <?php echo htmlspecialchars($p['category']); ?>
                        </span>
                    <?php endif; ?>
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
        
        <?php if (isset($hasMore) && $hasMore): ?>
            <!-- Sistema de "Cargar más" - Solo se muestra si hay más posts disponibles -->
            <div class="load-more-container">
                <!-- Botón principal que el usuario clickea para cargar más posts -->
                <button id="load-more-btn" class="btn primary">
                    Cargar más posts
                </button>
                <!-- Texto de carga que aparece mientras se obtienen los posts -->
                <p id="loading-text">Cargando...</p>
            </div>
            
            <script>
            // Variable que mantiene el número de página actual
            let currentPage = <?= $currentPage ?? 1 ?>;
            
            // Referencias a los elementos del DOM
            const loadMoreBtn = document.getElementById('load-more-btn');
            const loadingText = document.getElementById('loading-text');
            const postsContainer = document.getElementById('posts-container');
            
            // Evento que se ejecuta cuando el usuario hace click en "Cargar más"
            loadMoreBtn.addEventListener('click', function() {
                // 1. Ocultar el botón y mostrar el texto "Cargando..."
                loadMoreBtn.style.display = 'none';
                loadingText.style.display = 'block';
                
                // 2. Incrementar el número de página para obtener el siguiente bloque
                currentPage++;
                
                // 3. Construir la URL dependiendo si estamos en búsqueda o en home normal
                const baseUrl = <?= isset($searchQuery) ? "'?route=post/search&q=" . urlencode($searchQuery) . "'" : "'?route=home'" ?>;
                const url = baseUrl + '&page=' + currentPage;
                
                // 4. Hacer petición AJAX para obtener la siguiente página
                fetch(url)
                    .then(response => response.text())  // Convertir respuesta a texto HTML
                    .then(html => {
                        // 5. Parsear el HTML recibido para extraer los posts
                        const parser = new DOMParser();
                        const doc = parser.parseFromString(html, 'text/html');
                        const newPosts = doc.querySelectorAll('.post-card');
                        
                        // 6. Añadir cada nuevo post al contenedor existente
                        newPosts.forEach(post => {
                            postsContainer.appendChild(post);
                        });
                        
                        // 7. Verificar si hay más posts disponibles
                        // Si la respuesta contiene el botón "Cargar más", significa que hay más posts
                        const hasMorePosts = doc.querySelector('#load-more-btn');
                        if (hasMorePosts) {
                            loadMoreBtn.style.display = 'inline-block';  // Mostrar botón de nuevo
                        }
                        // Si no hay más posts, el botón permanece oculto
                        
                        // 8. Ocultar el texto de carga
                        loadingText.style.display = 'none';
                    })
                    .catch(error => {
                        // Manejo de errores en caso de que falle la petición
                        console.error('Error:', error);
                        loadingText.textContent = 'Error al cargar posts';
                    });
            });
            </script>
        <?php endif; ?>
    <?php endif; ?>
</section>
