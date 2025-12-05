# BlogPersonal — CMS básico en PHP

Proyecto de gestión de contenidos (CMS) para un blog personal, desarrollado en PHP nativo sin frameworks. Este proyecto ha evolucionado para incluir características modernas como carga dinámica con AJAX, sistema de moderación, categorías y búsqueda.

## ✨ Características Principales

*   **Gestión de Usuarios:**
    *   Registro y Login seguro (contraseñas hasheadas).
    *   **Roles:** Administrador y Usuario estándar.
    *   **Autenticacion** Autenticación por sesión PHP; la columna `is_admin` en la tabla `users` determina si un usuario es admin.
    *   **Perfil de Usuario:** Edición de perfil, avatar personalizado (con avatar por defecto para nuevos usuarios).
    *   **Panel de Administración:** Gestión completa de usuarios (visualización y eliminación) exclusiva para administradores.
*   **Gestión de Posts:**
    *   **CRUD Completo:** Crear, Leer, Actualizar y Eliminar posts.
    *   **Categorías:** Clasificación de posts por categorías.
    *   **Imágenes:** Soporte para subir imágenes destacadas en los posts.
*   **Sistema de Moderación:**
    *   Los posts creados por usuarios requieren aprobación de un administrador.
    *   Panel de moderación dedicado para administradores.
    *   Estados de post: `Pendiente` (0) y `Publicado` (1).
*   **Navegación Mejorada:**
    *   **Menú Hamburguesa:** Menú desplegable con acceso rápido a las funcionalidades principales.
    *   Opciones diferentes según el rol del usuario (Admin vs Usuario estándar).
*   **Experiencia de Usuario (UX):**
    *   **Carga Dinámica:** Botón "Cargar más" con AJAX para una navegación fluida sin recargas de página.
    *   **Búsqueda:** Buscador integrado en tiempo real (título y contenido).
    *   **Diseño Responsivo:** Interfaz moderna con CSS nativo, modo oscuro/claro y grid adaptativo.
    *   **Tema Personalizable:** Alternancia entre modo oscuro y claro con persistencia en localStorage.

## 📂 Estructura del Proyecto

```
BLOGPERSONAL/
│
├── app/                            # Lógica de negocio (MVC)
│   ├── controller/                 # Controladores
│   │   ├── AuthController.php      # Autenticación y perfil
│   │   ├── PostController.php      # Lógica de posts, búsqueda y moderación
│   │   └── UserController.php      # Gestión de usuarios (Admin)
│   │
│   ├── core/                       # Núcleo del framework
│   │   ├── Auth.php                # Gestión de sesión y permisos
│   │   ├── DB.php                  # Conexión Singleton a MySQL
│   │   └── Helpers.php             # Utilidades (vistas, redirecciones)
│   │
│   └── model/                      # Modelos de datos
│       ├── Post.php                # Consultas de posts (filtros, búsqueda, paginación)
│       └── User.php                # Gestión de usuarios (CRUD)
│
├── public/                         # Archivos públicos
│   ├── assets/
│   │   ├── css/
│   │   │   ├── style.css           # Estilos principales
│   │   │   └── inline-styles.css   # Clases utilitarias
│   │   ├── images/
│   │   │   └── default-avatar.png  # Avatar por defecto
│   │   └── js/
│   │       ├── theme.js            # Lógica del tema (Dark/Light)
│   │       └── menu.js             # Lógica del menú hamburguesa
│   │
│   └── uploads/                    # Directorio de imágenes subidas
│
├── sql/                            # Base de datos
│   ├── db.sql                      # Esquema completo y datos iniciales
│   └── comprobaciones.sql          # Scripts de prueba
│
├── views/                          # Vistas (HTML/PHP)
│   ├── auth/                       # Login y Registro
│   │   ├── login.php               # Formulario de inicio de sesión
│   │   └── register.php            # Formulario de registro
│   ├── layout/                     # Header y Footer compartidos
│   │   ├── header.php              # Cabecera común (nav, menú hamburguesa)
│   │   └── footer.php              # Pie de página común
│   ├── post/                       # Vistas de posts
│   │   ├── home.php                # Página principal (Grid de posts + AJAX)
│   │   ├── moderate.php            # Panel de moderación (Admin)
│   │   ├── post.php                # Crear post
│   │   ├── edit_post.php           # Editar post
│   │   └── show.php                # Ver post individual
│   ├── user/                       # Gestión de usuarios (Admin)
│   │   └── manage.php              # Panel de administración de usuarios
│   │       
│   ├── profile_edit.php            # Editar perfil
│   └── profile.php                 # Vista de perfil
│
├── .htaccess                       # Configuración Apache (reescritura URLs)
├── avatar.php                      # Script para servir imágenes de avatar
├── config.php                      # Configuración de BD y constantes
├── index.php                       # Router principal
└── README.md                       # Documentación
```

## 🚀 Instalación y Puesta en Marcha

### Requisitos
*   PHP 7.4 o superior (recomendado PHP 8.x)
*   MySQL o MariaDB
*   Para php se puede usar XAMPP que tiene todo lo necesario para ejecutar el proyecto

### Pasos
1.  **Clonar el proyecto:**
    ```bash
    git clone <url-del-repo>
    cd BlogPersonal
    ```
    
2.  **Base de Datos:**
    *   Crea una base de datos llamada `blogpersonal`.
    *   Coge el archivo `db.sql` que se encuentra en la carpeta `sql` y ejecútalo en phpMyAdmin o con MySQL Workbench.
    *   *Nota: El script `db.sql` crea usuarios por defecto: `admin@admin.com` (Admin) y `usuario@email.com` (Usuario).*
    * Las contraseñas son `123` para ambos usuarios.

3.  **Configuración:**
    *   Edita `config.php` con tus credenciales de base de datos:
    ```php
    define('DB_HOST', '127.0.0.1');
    define('DB_NAME', 'blogpersonal');
    define('DB_USER', 'root');
    define('DB_PASS', '');
    ```

4.  **Ejecutar:**
    *   Usa el servidor interno de PHP para probar localmente:
    ```powershell
    php -S localhost:8000
    ```

5.  **Acceder:**
    *   Abre tu navegador en `http://localhost:8000`.

## 🔗 Rutas y Navegación

El sistema utiliza un enrutamiento basado en parámetros query (`?route=...`).

| Ruta | Descripción | Acceso |
| :--- | :--- | :--- |
| `?route=home` | Página principal (Lista de posts publicados) | Público |
| `?route=login` | Iniciar sesión | Público |
| `?route=register` | Registro de nuevos usuarios | Público |
| `?route=logout` | Cerrar sesión | Público |
| `?route=post/create` | Crear un nuevo post | Usuarios registrados |
| `?route=post/store` | Accion para guardar post post | Usuarios registrados |
| `?route=post/moderate` | Panel de moderación de posts | **Solo Admin** |
| `?route=post/search` | Resultados de búsqueda | Público |
| `?route=profile` | Ver perfil de usuario | Usuarios registrados |
| `?route=profile/edit` | Editar perfil y avatar | Usuarios registrados |
| `?route=user/manage` | Panel de gestión de usuarios | **Solo Admin** |
| `?route=user/delete&id={id}` | Eliminar usuario | **Solo Admin** |

## 🛠️ Detalles Técnicos

### Sistema "Cargar más" (AJAX)
En lugar de la paginación tradicional, se implementó un botón "Cargar más" que solicita los siguientes posts vía AJAX (`fetch`).
*   **Frontend:** `views/post/home.php` y `moderate.php` contienen el JS que maneja el botón, el contador de páginas y la inserción de nuevos posts en el DOM.
*   **Backend:** Los controladores detectan si la petición es AJAX o normal y devuelven solo el HTML de las nuevas tarjetas de post si es necesario.

### Moderación
*   Campo `status` en la tabla `posts`: `0` (Pendiente), `1` (Publicado).
*   Los usuarios normales crean posts con `status = 0`.
*   Los administradores pueden ver posts pendientes en `?route=post/moderate` y aprobarlos (`status = 1`) o rechazarlos (eliminar).

### Menú Hamburguesa
*   **Diseño Moderno:** Menú desplegable animado con icono hamburguesa (☰) y texto "Menú" con degradado.
*   **Opciones Contextuales:**
    *   **Usuarios estándar:** Crear post, Perfil, Cerrar sesión.
    *   **Administradores:** Crear post, Moderar posts, Perfil, Gestión de usuarios, Cerrar sesión.
*   **Responsive:** Adaptado para tema oscuro y claro con transiciones suaves.

### Gestión de Usuarios (Admin)
*   **Panel Administrativo:** Tabla completa con todos los usuarios del sistema.
*   **Información Mostrada:** ID, Nombre, Email, Rol, Fecha de creación.
*   **Funcionalidades:**
    *   Visualización de todos los usuarios ordenados por fecha de registro.
    *   Eliminación de usuarios con confirmación JavaScript.
    *   Badges de rol diferenciados (Admin en rojo, Usuario en azul).
*   **Seguridad:** Todas las acciones verifican permisos de administrador en el backend.

### Estilos
*   Se ha migrado de estilos inline a clases CSS definidas en `public/assets/css/style.css` y `inline-styles.css` para mantener el código limpio y mantenible.
*   Diseño adaptable con Grid CSS (2 columnas en escritorio).
*   Paleta de colores neón/azul con soporte completo para tema oscuro y claro.
*   Animaciones y transiciones suaves para mejorar la experiencia de usuario.

## 🎨 Capturas de Pantalla


