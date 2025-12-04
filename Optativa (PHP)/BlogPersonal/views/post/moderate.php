<section>
    <h1>Moderación de Posts</h1>
    <p class="muted">Aquí puedes aprobar o rechazar los posts pendientes de moderación.</p>
    
    <?php if (empty($posts)): ?>
        <p class="muted">No hay posts pendientes de moderación.</p>
    <?php else: ?>
        <div class="posts-grid" id="posts-container">
            <?php foreach ($posts as $p): ?>
                <article class="post-card">
                    <?php if ($p['image']): ?>
                        <img src="/public/uploads/<?php echo htmlspecialchars($p['image']); ?>" alt=""/>
                    <?php endif; ?>
                    <h3><?php echo htmlspecialchars($p['title']); ?></h3>
                    <p class="meta">por <?php echo htmlspecialchars($p['author']); ?> - <?php echo $p['created_at']; ?></p>
                    <p class="status">Estado: <strong class="status-pending">pendiente</strong></p>
                    <p><?php echo nl2br(htmlspecialchars(substr($p['content'],0,200))); ?>...</p>
                    <div class="actions">
                        <a class="btn" href="?route=post/show&id=<?php echo $p['id']; ?>&from=moderate">Ver completo</a>
                        <a class="btn btn-approve" href="?route=post/approve&id=<?php echo $p['id']; ?>">Aprobar</a>
                        <a class="btn danger" href="?route=post/reject&id=<?php echo $p['id']; ?>" onclick="return confirm('¿Rechazar este post?')">Rechazar</a>
                    </div>
                </article>
            <?php endforeach; ?>
        </div>
        
        <?php if (isset($hasMore) && $hasMore): ?>
            <!-- Sistema de "Cargar más" para posts pendientes de moderación -->
            <div class="load-more-container">
                <!-- Botón para cargar el siguiente bloque de posts pendientes -->
                <button id="load-more-btn" class="btn primary">
                    Cargar más posts
                </button>
                <!-- Indicador de carga -->
                <p id="loading-text">Cargando...</p>
            </div>
            
            <script>
            // Página actual (comienza en 1, se incrementa con cada carga)
            let currentPage = <?= $currentPage ?? 1 ?>;
            
            // Obtener referencias a los elementos HTML
            const loadMoreBtn = document.getElementById('load-more-btn');
            const loadingText = document.getElementById('loading-text');
            const postsContainer = document.getElementById('posts-container');
            
            // Agregar evento click al botón "Cargar más"
            loadMoreBtn.addEventListener('click', function() {
                // Paso 1: Ocultar botón y mostrar indicador de carga
                loadMoreBtn.style.display = 'none';
                loadingText.style.display = 'block';
                
                // Paso 2: Incrementar contador de página
                currentPage++;
                
                // Paso 3: Construir URL para la siguiente página de posts pendientes
                const url = '?route=post/moderate&page=' + currentPage;
                
                // Paso 4: Realizar petición AJAX con Fetch API
                fetch(url)
                    .then(response => response.text())  // Obtener HTML como texto
                    .then(html => {
                        // Paso 5: Parsear el HTML recibido
                        const parser = new DOMParser();
                        const doc = parser.parseFromString(html, 'text/html');
                        
                        // Paso 6: Extraer solo las tarjetas de posts (.post-card)
                        const newPosts = doc.querySelectorAll('.post-card');
                        
                        // Paso 7: Agregar cada post nuevo al final del contenedor
                        newPosts.forEach(post => {
                            postsContainer.appendChild(post);
                        });
                        
                        // Paso 8: Verificar si existen más posts por cargar
                        // Si la respuesta incluye el botón, hay más posts disponibles
                        const hasMorePosts = doc.querySelector('#load-more-btn');
                        if (hasMorePosts) {
                            loadMoreBtn.style.display = 'inline-block';  // Volver a mostrar el botón
                        }
                        // Si no hay botón en la respuesta, no hay más posts (botón queda oculto)
                        
                        // Paso 9: Ocultar indicador de carga
                        loadingText.style.display = 'none';
                    })
                    .catch(error => {
                        // Manejo de errores si la petición falla
                        console.error('Error:', error);
                        loadingText.textContent = 'Error al cargar posts';
                    });
            });
            </script>
        <?php endif; ?>
    <?php endif; ?>
</section>
