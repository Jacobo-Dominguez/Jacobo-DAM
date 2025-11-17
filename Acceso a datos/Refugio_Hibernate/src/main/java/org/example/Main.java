
package org.example;

import org.example.dao.AnimalDAO;
import org.example.dao.AnimalDAOImpl;
import org.example.dao.ClasificacionDAO;
import org.example.dao.ClasificacionDAOImpl;
import org.example.entities.Animal;
import org.example.entities.Clasificacion;
import org.example.entities.TipoAlimento;
import org.example.entities.TipoAnimal;
import org.example.Util.HibernateUtil;
import org.hibernate.Session;

import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        // Probar conexión rápida
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            System.out.println("Conexión correcta con Hibernate / MySQL");
        } catch (Exception e) {
            System.out.println("Error al conectar con la BD");
            e.printStackTrace();
            return;
        }

        Scanner sc = new Scanner(System.in);
        AnimalDAO animalDAO = new AnimalDAOImpl();
        ClasificacionDAO clasificacionDAO = new ClasificacionDAOImpl();

        int opcion;
        do {
            System.out.println("\nREFUGIO DE ANIMALES");
            System.out.println("1. Registrar nuevo animal");
            System.out.println("2. Buscar animales por especie");
            System.out.println("3. Actualizar estado de un animal");
            System.out.println("4. Listar todos los animales");
            System.out.println("0. Salir");
            System.out.print("Opción: ");

            opcion = leerEntero(sc);

            switch (opcion) {
                case 1 -> registrarAnimal(sc, animalDAO, clasificacionDAO);
                case 2 -> buscarPorEspecie(sc, animalDAO);
                case 3 -> actualizarEstado(sc, animalDAO);
                case 4 -> listarAnimales(animalDAO);
                case 0 -> System.out.println("Saliendo...");
                default -> System.out.println("Opción no válida.");
            }

        } while (opcion != 0);

        sc.close();
        HibernateUtil.getSessionFactory().close();
    }

    // OPCIONES MENÚ

    private static void registrarAnimal(Scanner sc, AnimalDAO animalDAO, ClasificacionDAO clasificacionDAO) {
        System.out.println("\n--- Registrar nuevo animal ---");

        System.out.print("Nombre: ");
        String nombre = sc.nextLine();

        System.out.print("Especie: ");
        String especie = sc.nextLine();

        System.out.print("Descripción de cómo se perdió: ");
        String descripcion = sc.nextLine();

        System.out.print("Estado (recién abandonado / tiempo en refugio / próximamente en acogida): ");
        String estado = sc.nextLine();

        // Tipo de alimento (enum)
        System.out.println("Tipo de alimento (elige número): ");
        System.out.println("1. CARNIVORO");
        System.out.println("2. HERBIVORO");
        System.out.println("3. OMNIVORO");
        System.out.print("Opción: ");
        int opcAlimento = leerEntero(sc);

        TipoAlimento tipoAlimento = switch (opcAlimento) {
            case 1 -> TipoAlimento.CARNIVORO;
            case 2 -> TipoAlimento.HERBIVORO;
            case 3 -> TipoAlimento.OMNIVORO;
            default -> {
                System.out.println("Opción no válida, se pone OMNIVORO por defecto.");
                yield TipoAlimento.OMNIVORO;
            }
        };

        // Tipo de animal (enum, se usará en Clasificacion ManyToMany)
        System.out.println("Tipo de animal (elige número): ");
        System.out.println("1. MAMIFERO");
        System.out.println("2. REPTIL");
        System.out.println("3. PEZ");
        System.out.println("4. AVE");
        System.out.println("5. ANFIBIO");
        System.out.print("Opción: ");
        int opcTipo = leerEntero(sc);

        TipoAnimal tipoAnimal = switch (opcTipo) {
            case 1 -> TipoAnimal.MAMIFERO;
            case 2 -> TipoAnimal.REPTIL;
            case 3 -> TipoAnimal.PEZ;
            case 4 -> TipoAnimal.AVE;
            case 5 -> TipoAnimal.ANFIBIO;
            default -> {
                System.out.println("Opción no válida, se pone MAMIFERO por defecto.");
                yield TipoAnimal.MAMIFERO;
            }
        };

        // Creamos el animal
        Animal animal = new Animal(nombre, especie, descripcion, estado, tipoAlimento);

        // Buscamos o creamos la Clasificacion correspondiente al tipoAnimal
        Clasificacion clasificacion = clasificacionDAO.findByTipoAnimal(tipoAnimal);
        if (clasificacion == null) {
            clasificacion = new Clasificacion(tipoAnimal, tipoAnimal.name());
            clasificacionDAO.create(clasificacion);
        }
        animal.getClasificaciones().add(clasificacion);

        // Guardamos el animal en la BD
        animalDAO.create(animal);

        System.out.println("✔ Animal registrado correctamente.");
    }

    private static void buscarPorEspecie(Scanner sc, AnimalDAO animalDAO) {
        System.out.print("Especie a buscar: ");
        String especie = sc.nextLine();

        List<Animal> animales = animalDAO.findByEspecie(especie);
        if (animales.isEmpty()) {
            System.out.println("No hay animales de esa especie.");
        } else {
            System.out.println("\nAnimales encontrados:");
            for (Animal a : animales) {
                System.out.println(a.getId() + " - " + a.getNombre() +
                        " (" + a.getEspecie() + ") - Estado: " + a.getEstado());
            }
        }
    }

    private static void actualizarEstado(Scanner sc, AnimalDAO animalDAO) {
        System.out.print("ID del animal: ");
        int id = leerEntero(sc);

        System.out.print("Nuevo estado: ");
        String nuevoEstado = sc.nextLine();

        boolean ok = animalDAO.updateEstado(id, nuevoEstado);
        if (ok) {
            System.out.println("Estado actualizado.");
        } else {
            System.out.println("No se ha encontrado un animal con ese ID.");
        }
    }

    private static void listarAnimales(AnimalDAO animalDAO) {
        List<Animal> animales = animalDAO.findAll();
        if (animales.isEmpty()) {
            System.out.println("No hay animales registrados.");
        } else {
            System.out.println("\n Animales en el refugio");
            for (Animal a : animales) {
                System.out.println(a.getId() + " - " + a.getNombre() +
                        " (" + a.getEspecie() + ") - Estado: " + a.getEstado() +
                        " - Tipo alimento: " + a.getTipoAlimento());
            }
        }
    }

    private static int leerEntero(Scanner sc) {
        while (true) {
            try {
                return Integer.parseInt(sc.nextLine());
            } catch (NumberFormatException e) {
                System.out.print("Valor no válido, introduce un número: ");
            }
        }
    }
}
