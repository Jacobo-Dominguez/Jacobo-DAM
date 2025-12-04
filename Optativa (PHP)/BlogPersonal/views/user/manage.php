<div class="card">
    <h1>Gestión de Usuarios</h1>
    <p class="muted">Administración de todos los usuarios del sistema</p>
    
    <div class="users-table-container">
        <table class="users-table">
            <thead>
                <tr>
                    <th>ID</th>
                    <th>Nombre</th>
                    <th>Email</th>
                    <th>Rol</th>
                    <th>Fecha de Creación</th>
                    <th>Acciones</th>
                </tr>
            </thead>
            <tbody>
                <?php if (!empty($users)): ?>
                    <?php foreach ($users as $u): ?>
                        <tr>
                            <td><?= htmlspecialchars($u['id']) ?></td>
                            <td><?= htmlspecialchars($u['name']) ?></td>
                            <td><?= htmlspecialchars($u['email']) ?></td>
                            <td>
                                <?php if ($u['is_admin']): ?>
                                    <span class="role-badge admin">Admin</span>
                                <?php else: ?>
                                    <span class="role-badge user">Usuario</span>
                                <?php endif; ?>
                            </td>
                            <td><?= date('d/m/Y H:i', strtotime($u['created_at'])) ?></td>
                            <td>
                                <a href="?route=user/delete&id=<?= $u['id'] ?>" 
                                   class="btn danger" 
                                   onclick="return confirm('¿Estás seguro de que quieres eliminar este usuario?')">
                                    Eliminar
                                </a>
                            </td>
                        </tr>
                    <?php endforeach; ?>
                <?php else: ?>
                    <tr>
                        <td colspan="6" style="text-align: center; padding: 20px;">
                            No hay usuarios registrados
                        </td>
                    </tr>
                <?php endif; ?>
            </tbody>
        </table>
    </div>
    
    <p>
        <a class="btn" href="?route=home">Volver al inicio</a>
    </p>
</div>
