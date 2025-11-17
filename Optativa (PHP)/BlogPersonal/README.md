# BlogPersonal — CMS básico en PHP

Proyecto mínimo de gestión de contenidos (CMS) para un blog personal, hecho en PHP sin frameworks, pensado para uso local y aprendizaje.

**Resumen rápido**
- Login / Registro (usuarios normales). Solo un admin podrá ver todos los posts.
- CRUD de posts (crear, ver, editar, eliminar). Subida de imagen opcional por post.
- Autenticación por sesión PHP; la columna `is_admin` en la tabla `users` determina si un usuario es admin.

**Estructura principal**
```
BLOGPERSONAL/
├── app/                          # Contiene toda la lógica de la aplicación (MVC)
│   ├── controller/               # Controladores: manejan las rutas y la lógica de negocio
│   │   ├── AuthController.php    # Gestiona login, registro, logout
│   │   └── PostController.php    # Gestiona posts: crear, leer, editar, eliminar
│   │
│   ├── core/                     # Clases base y utilidades globales
│   │   ├── Auth.php              # Maneja autenticación y sesión
│   │   ├── DB.php                # Conexión a la base de datos
│   │   └── Helpers.php           # Funciones auxiliares (redirección, carga de vistas, etc.)
│   │
│   └── model/                    # Modelos: interactúan con la base de datos
│       ├── Post.php              # Operaciones CRUD sobre los posts
│       └── User.php              # Operaciones CRUD sobre los usuarios
│
├── public/                       # Carpeta pública accesible desde el navegador
│   ├── assets/                   # Recursos estáticos (CSS, JS, imágenes)
│   │   ├── css/
│   │   │   └── style.css         # Hoja de estilos principal
│   │   └── js/
│   │       └── theme.js          # Script para cambiar entre modo claro/oscuro
│   │
│   └── uploads/                  # Imágenes subidas por los usuarios (publicadas en los posts)
│
├── sql/                          # Archivos SQL para la base de datos
│   └── db.sql                    # Script para crear tablas y estructura inicial
│
├── views/                        # Vistas HTML (plantillas que se muestran al usuario)
│   ├── auth/                     # Vistas relacionadas con autenticación
│   │   ├── login.php             # Formulario de inicio de sesión
│   │   └── register.php          # Formulario de registro
│   │
│   ├── layout/                   # Plantillas compartidas (header, footer)
│   │   ├── header.php            # Encabezado común (con menú de navegación)
│   │   └── footer.php            # Pie de página común
│   │
│   └── post/                     # Vistas relacionadas con posts
│       ├── edit_post.php         # Formulario de edición de post
│       ├── home.php              # Lista de posts (página principal)
│       ├── post.php              # Formulario de creación de post
│       └── show.php              # Vista detallada de un post
│
├── .htaccess                     # Configuración del servidor Apache (para reescritura de URLs)
├── config.php                    # Archivo de configuración global (ej: constantes, conexión BD)
├── index.php                     # Punto de entrada único (front controller)
└── README.md                     # Documentación del proyecto (instrucciones, descripción, etc.)
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

Autenticación y privilegios
- La tabla `users` tiene la columna `is_admin` (TINYINT). Si su valor es `1`, el usuario es tratado como admin.
- Al iniciar sesión, `AuthController` extrae el usuario de la BD y `Auth::loginByArray()` guarda en `$_SESSION['user']` los campos: `id`, `name`, `email`, `is_admin`.
- `PostController::index()` usa `Auth::isAdmin()` para decidir si mostrar `Post::all()` (admin) o `Post::findByUser(Auth::id())` (usuario normal).

Cómo convertir a un usuario en admin
1. Tras registrar un usuario desde la interfaz, abre MySQL y ejecuta:

```sql
UPDATE blogpersonal.users SET is_admin = 1 WHERE email = 'tu-email@ejemplo.com';
```

O desde PowerShell (si `mysql` está en PATH):
```powershell
mysql -u root -p -e "UPDATE blogpersonal.users SET is_admin = 1 WHERE email = 'tu-email@ejemplo.com';"
```

Notas de seguridad importantes (no para producción)
- No hay protección CSRF en formularios. Añade tokens CSRF en `register`, `login`, `post/store`, `post/update`, etc.
- La subida de archivos no valida exhaustivamente el tipo de fichero ni el tamaño. Añade validación de mime type y límites de tamaño.
- Validación de inputs mínima. Se recomienda sanitizar/validar todos los datos recibidos.
- No hay control de permisos en `delete`/`edit` más allá de la vista: considera validar en controlador que el usuario que edita/elimina sea el autor o admin.

Mejoras sugeridas
- Añadir sistema de roles y panel de administración para gestionar usuarios/posts.
- Añadir paginación, búsqueda y etiquetas para posts.
- Reemplazar el enrutador simple por un router PSR-7/PSR-15 o un micro-framework si el proyecto crece.
- Añadir tests unitarios y de integración.

Debug y logs
- Para depuración temporal puedes volcar `$_SESSION['user']` en `views/layout/header.php` o escribir a un archivo de log.

Archivos clave a revisar
- `config.php` — configuración y autoloader
- `app/core/DB.php` — conexión PDO
- `app/core/Auth.php` — helpers de sesión
- `app/controller/AuthController.php` — lógica de login/registro
- `app/controller/PostController.php` — lógica de posts
- `app/model/*` — interacción con la base de datos

Contacto
Si quieres, puedo:
- Añadir un script `create_admin.php` para crear un admin desde CLI.
- Habilitar CSRF y validación de uploads automáticamente.
- Implementar un panel de usuarios para gestionar roles.

— Fin
