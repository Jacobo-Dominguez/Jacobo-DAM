# Refugio de Animales (Hibernate)

Proyecto Java que implementa un refugio de animales usando Hibernate (JPA) y MySQL.

**Descripción del enunciado**
- **Parte 1:** Registrar animales (nombre, especie, edad, estado, descripción de pérdida). Funciones para:
  - Registrar nuevos animales.
  - Buscar animales por especie.
  - Actualizar el estado de un animal ("recién abandonado", "tiempo en el refugio", "próximamente en acogida").
- **Parte 2:** Modelar personas (dueños) que pueden tener varios animales, guardando `DNI`, `nombre`, `email`. Además, modelar clasificaciones que pueden aplicarse a animales (por ejemplo: "mamífero", "carnívoro") identificadas con un código y nombre.

**Resumen de la implementación**
- Lenguaje: Java 21
- Persistencia: Hibernate ORM 6.x (Jakarta Persistence)
- Base de datos: MySQL (configuración en `src/main/resources/hibernate.cfg.xml`)

**Entidades principales y mapeos**
- `Animal` (`org.example.entities.Animal`)
  - Campos: `id` (auto), `nombre`, `especie`, `descripcionPerdida`, `estado`, `tipoAlimento` (enum `TipoAlimento`).
  - Relaciones:
    - ManyToMany con `Persona` (tabla join `persona_animal`).
    - ManyToMany con `Clasificacion` (tabla join `animal_clasificacion`).
- `Persona` (`org.example.entities.Persona`)
  - Campos: `dni` (PK String), `nombre`, `email`.
  - Relaciones:
    - ManyToMany con `Animal` (tabla join `persona_animal`).
- `Clasificacion` (`org.example.entities.Clasificacion`)
  - Campos: `id` (auto), `tipoAnimal` (enum `TipoAnimal`), `nombre`.
  - Relaciones:
    - ManyToMany con `Animal` (tabla join `animal_clasificacion`).
- Enumerados:
  - `TipoAlimento` (CARNIVORO, HERBIVORO, OMNIVORO)
  - `TipoAnimal` (MAMIFERO, REPTIL, PEZ, AVE, ANFIBIO)

**DAO y operaciones implementadas**
- `AnimalDAO` / `AnimalDAOImpl`:
  - `findAll()`, `findById(id)`, `findByEspecie(especie)`, `create(animal)`, `update(animal)`, `deleteById(id)`, `updateEstado(id, nuevoEstado)`.
- `ClasificacionDAO` / `ClasificacionDAOImpl`:
  - `findByTipoAnimal(tipo)` y `create(clasificacion)` (útil para asociar clasificaciones por `TipoAnimal`).

**Archivo principal**
- `Main` (`org.example.Main`) proporciona una sencilla interfaz por consola con un menú para:
  1. Registrar nuevo animal (solicita nombre, especie, descripción de pérdida, estado, tipo de alimento y tipo animal; crea/clasifica y persiste).
  2. Buscar animales por especie.
  3. Actualizar estado de un animal por `id`.
  4. Listar todos los animales.

**Configuración de base de datos**
- Fichero: `src/main/resources/hibernate.cfg.xml`
  - URL por defecto: `jdbc:mysql://localhost:3306/refugioAnimales`
  - Usuario por defecto: `root`
  - `hbm2ddl.auto = update` (actualiza tablas automáticamente). Ajustar en producción.

Ejemplo de creación de base de datos (MySQL):

```sql
CREATE DATABASE refugioAnimales CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
-- Crear usuario/ajustar permisos si es necesario:
-- CREATE USER 'miusuario'@'localhost' IDENTIFIED BY 'mipassword';
-- GRANT ALL PRIVILEGES ON refugioAnimales.* TO 'miusuario'@'localhost';
```

Modificar la conexión en `src/main/resources/hibernate.cfg.xml` para poner el usuario/contraseña correctos.

**Estructura relevante del proyecto**
- `pom.xml` — configuraciones y dependencias (Hibernate, MySQL Connector).
- `src/main/java/org/example/Main.java` — interfaz de consola y flujo principal.
- `src/main/java/org/example/Util/HibernateUtil.java` — crea `SessionFactory` con `Configuration.configure()`.
- `src/main/java/org/example/entities/*` — entidades y enums.
- `src/main/java/org/example/dao/*` — interfaces y DAO implementations.
- `src/main/resources/hibernate.cfg.xml` — configuración Hibernate y mappings.

