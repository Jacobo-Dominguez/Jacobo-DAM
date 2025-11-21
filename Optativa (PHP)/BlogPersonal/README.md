# BlogPersonal — CMS básico en PHP

Proyecto mínimo de gestión de contenidos (CMS) para un blog personal, hecho en PHP sin frameworks, pensado para uso local y aprendizaje.

**Resumen rápido**
- Login / Registro (usuarios normales). Solo un admin podrá ver todos los posts.
- CRUD de posts (crear, ver, editar, eliminar). Subida de imagen opcional por post.
- Autenticación por sesión PHP; la columna `is_admin` en la tabla `users` determina si un usuario es admin.

**Estructura principal**
```
BLOGPERSONAL/
│
├── app/                          # Lógica de negocio (MVC)
│   ├── controller/               # Controladores
│   │   ├── AuthController.php    # Login, registro, logout, perfil
│   │   └── PostController.php    # Gestión de posts
│   │
│   ├── core/                     # Clases base y utilidades
│   │   ├── Auth.php              # Autenticación y sesión
│   │   ├── DB.php                # Conexión a base de datos
│   │   └── Helpers.php           # Funciones auxiliares (redirección, vista, etc.)
│   │
│   └── model/                    # Modelos de datos
│       ├── Post.php              # Gestión de posts
│       └── User.php              # Gestión de usuarios (avatar, descripción, etc.)
│
├── public/                       # Archivos accesibles desde el navegador
│   ├── assets/                   # Recursos estáticos
│   │   ├── css/                  # Hojas de estilo
│   │   │   └── style.css         # Estilos globales (tema oscuro/claro, botones, cards)
│   │   │
│   │   └── js/                   # Scripts JavaScript
│   │       └── theme.js          # Cambio de tema claro/oscuro
│   │
│   └── uploads/                  # Subidas de archivos (opcional, si cambias a guardar en disco)
│
├── sql/                          # Scripts SQL para la base de datos
│   ├── db.sql                    # Creación de tablas (users, posts)
│   └── comprobaciones.sql        # Datos de prueba o inserciones iniciales
│
├── views/                        # Plantillas HTML (vistas)
│   ├── auth/                     # Vistas de autenticación
│   │   ├── login.php             # Formulario de login
│   │   └── register.php          # Formulario de registro
│   │
│   ├── layout/                   # Plantillas compartidas
│   │   ├── header.php            # Cabecera con menú y avatar
│   │   └── footer.php            # Pie de página + script de tema
│   │
│   └── post/                     # Vistas de posts y perfil
│       ├── home.php              # Lista de posts
│       ├── post.php              # Formulario de creación de post
│       ├── show.php              # Detalle de un post
│       └── edit_post.php         # Edición de post
│       
├── profile_edit.php              # Edición del perfil
├── profile.php                   # Perfil del usuario
├── avatar.php                    # Endpoint para servir avatares desde BD
├── config.php                    # Configuración global (DB, autoloader)
├── index.php                     # Punto de entrada principal (router)
├── .htaccess                     # Reglas de reescritura (si usas Apache)
└── README.md                     # Documentación del proyecto
```

Requisitos
- PHP 7.4+ (recomendado PHP 8)
- MySQL o MariaDB
- Extensión PDO para MySQL

Instalación y puesta en marcha (local)
1. Clona o copia este proyecto en una carpeta, por ejemplo `C:\Users\tu\Desktop\BlogPersonal`.
2. Crea la base de datos e importa el esquema:

```powershell
# Ejecutar desde PowerShell (ajusta usuario/contraseña)
mysql -u root -p < .\sql\db.sql
```

3. Ajusta la conexión en `config.php` si tu usuario/contraseña/host son distintos:

```php
define('DB_HOST', '127.0.0.1');
define('DB_NAME', 'blogpersonal');
define('DB_USER', 'root');
define('DB_PASS', '');
```

4. Inicia el servidor PHP embebido para pruebas:

```powershell
php -S localhost:8000 -t C:\Users\tu\Desktop\BlogPersonal
```

5. Abre en el navegador: `http://localhost:8000` (la app usa rutas por `?route=...`).

Rutas principales
- `?route=login` — Iniciar sesión
- `?route=register` — Registrarse (usuarios normales)
- `?route=logout` — Cerrar sesión
- `?route=home` — Página principal (lista de posts) — ésta es la ruta por defecto
- `?route=post/create` — Formulario para crear post
- `?route=post/store` — Acción para guardar post (POST)
- `?route=post/edit&id={id}` — Formulario para editar post
- `?route=post/update` — Acción para actualizar post (POST)
- `?route=post/delete&id={id}` — Eliminar post
- `?route=post/show&id={id}` — Ver post completo
- `?route=profile` — Ver pefil de usuario
- `?route=profile/edit` — Editar perfil de usuario

Autenticación y privilegios
- La tabla `users` tiene la columna `is_admin` (TINYINT). Si su valor es `1`, el usuario es tratado como admin.
- Al iniciar sesión, `AuthController` extrae el usuario de la BD y `Auth::loginByArray()` guarda en `$_SESSION['user']` los campos: `id`, `name`, `email`, `is_admin`.
- `PostController::index()` usa `Auth::isAdmin()` para decidir si mostrar `Post::all()` (admin) o `Post::findByUser(Auth::id())` (usuario normal).

Cómo convertir a un usuario en admin
1. Tras registrar un usuario desde la interfaz, abre MySQL y ejecuta:

```sql
USE blogpersonal
UPDATE users SET is_admin = 1 WHERE email = 'tu-email@ejemplo.com';
```

