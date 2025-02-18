package juego;

import java.util.Scanner;
import java.util.Random;

public class OnePiece {
    public static void main(String[] args) {
        /* TODO
        AÑADIR POSIBILIDAD DE EVITAR ATAQUES
        ARREGLAR LAS ESTADISTICAS DE LUFFY (MEJORAR DAÑO LUFFY)
         */
        Scanner scanner = new Scanner(System.in);
        Random random = new Random();

        // Crear ataques de Luffy
        Ataque[] ataquesLuffy = { new Ataque("Gomu Gomu no Pistol", 15), new Ataque("Gomu Gomu no Bazooka", 20),
                new Ataque("Gomu Gomu no Gatling", 25), new Ataque("Red Hawk", 30) };

        // Crear enemigos con 4 ataques cada uno
        Personaje[] enemigos = {
                new Personaje("Alvida", 50, 5, 2,
                        new Ataque[] { new Ataque("Club Swing", 10), new Ataque("Iron Mace", 15),
                                new Ataque("Counter", 12), new Ataque("Slam", 8) }),
                new Personaje("Buggy", 70, 6, 3,
                        new Ataque[] { new Ataque("Bara Bara Cannon", 12), new Ataque("Bara Bara Punch", 14),
                                new Ataque("Split Assault", 10), new Ataque("Bara Bara Chop", 8) }),
                new Personaje("Arlong", 90, 10, 5,
                        new Ataque[] { new Ataque("Shark On Darts", 15), new Ataque("Water Cannon", 18),
                                new Ataque("Saw Slash", 20), new Ataque("Bite", 22) }),
                new Personaje("Crocodile", 120, 12, 8,
                        new Ataque[] { new Ataque("Desert Spada", 20), new Ataque("Sables", 18),
                                new Ataque("Desert Girasole", 22), new Ataque("Ground Death", 25) }),
                new Personaje("Enel", 150, 14, 6,
                        new Ataque[] { new Ataque("El Thor", 25), new Ataque("Lightning Beast", 22),
                                new Ataque("200,000,000 Volt Amaru", 28), new Ataque("Thunder Dragon", 30) }),
                new Personaje("Rob Lucci", 180, 16, 10,
                        new Ataque[] { new Ataque("Rokuogan", 30), new Ataque("Shigan", 20), new Ataque("Rankyaku", 25),
                                new Ataque("Tekkai Kenpo", 18) }),
                new Personaje("Moria", 200, 12, 12,
                        new Ataque[] { new Ataque("Shadow's Asgard", 18), new Ataque("Brick Bat", 20),
                                new Ataque("Doppelman", 15), new Ataque("Scissors", 22) }),
                new Personaje("Hody", 220, 18, 14,
                        new Ataque[] { new Ataque("Water Shot", 25), new Ataque("Yabusame", 22),
                                new Ataque("Shark Bite", 28), new Ataque("Water Heart", 20) }),
                new Personaje("Doflamingo", 250, 20, 18,
                        new Ataque[] { new Ataque("Bird Cage", 30), new Ataque("Parasite", 25),
                                new Ataque("Overheat", 28), new Ataque("Five Color Strings", 32) }),
                new Personaje("Katakuri", 300, 22, 20,
                        new Ataque[] { new Ataque("Mochi Thrust", 35), new Ataque("Power Mochi", 30),
                                new Ataque("Mochi Rain", 28), new Ataque("Buzz Cut Mochi", 40) }),
                new Personaje("Kaido", 400, 25, 25,
                        new Ataque[] { new Ataque("Bolo Breath", 40), new Ataque("Thunder Bagua", 38),
                                new Ataque("Ragnarok", 45), new Ataque("Conqueror's Roar", 50) }) };

        // Crear Luffy
        Personaje luffy = new Personaje("Luffy", 0, 0, 0, ataquesLuffy);

        // Crear objeto Carne
        Item carne = new Item("Carne", 50);
        /*
         Item bolaDeOro = new Item("Bola de Oro", 0);
         Item barrilDeAgua = new Item("Barril de Agua", 0);
        */

        boolean tieneBolaDeOro = false;
        boolean tieneBarrilDeAgua = false;

        System.out.println("¡Bienvenido a la One Piece!");
        System.out.println("¿Quieres iniciar los combates? (si/no)");
        String respuesta = scanner.nextLine();

        if (!respuesta.equalsIgnoreCase("si")) {
            System.out.println("¡Tal vez la próxima vez!");
            return;
        }

        for (Personaje enemigo : enemigos) {
            luffy.vida = enemigo.vida + 10; // 10 mas de vida por cada combate
            luffy.ataqueBase = enemigo.ataqueBase + 3; // 3 mas de vida por cada combate
            luffy.defensa = enemigo.defensa + 3; // 3 mas de defensa por cada combate

            System.out.println("¡Te enfrentas a " + enemigo.nombre + "!");

            while (enemigo.estaVivo() && luffy.estaVivo()) {
                System.out.println("\nTu vida: " + luffy.vida + " | Vida de " + enemigo.nombre + ": " + enemigo.vida);
                System.out.println("1. Atacar");
                System.out.println("2. Objetos");
                System.out.println("3. Huir");
                System.out.println("4. Ver Estadísticas de Ataques");
                int opcion = scanner.nextInt();

                if (opcion == 1) {
                    System.out.println("Elige tu ataque:");
                    for (int i = 0; i < luffy.ataques.length; i++) {
                        System.out.println((i + 1) + ". " + luffy.ataques[i].nombre);
                    }
                    int ataqueElegido = scanner.nextInt() - 1;

                    boolean ataqueRealizado = true;

                    if (enemigo.nombre.equals("Enel") && !tieneBolaDeOro) {
                        System.out.println("¡El ataque no afecta al rival!");
                        ataqueRealizado = false;

                    }

                    if (enemigo.nombre.equals("Crocodile") && !tieneBarrilDeAgua) {
                        System.out.println("¡El ataque no afecta al rival!");
                        ataqueRealizado = false;

                    }

                    if (ataqueRealizado) {
                        int danio = luffy.atacar(ataqueElegido, enemigo.defensa);
                        enemigo.recibirDanio(danio);
                        System.out.println("¡Usaste " + luffy.ataques[ataqueElegido].nombre + " y causaste " + danio
                                + " de daño!");
                    }

                    // Turno del enemigo, incluso si el ataque de Luffy no fue efectivo
                    if (enemigo.estaVivo()) {
                        int ataqueEnemigo = random.nextInt(enemigo.ataques.length);

                        // Si el enemigo es Enel, no se aplica daño a Luffy
                        if (!enemigo.nombre.equals("Enel")) {
                            int danioRecibido = enemigo.atacar(ataqueEnemigo, luffy.defensa);
                            luffy.recibirDanio(danioRecibido);
                            System.out.println(enemigo.nombre + " usó " + enemigo.ataques[ataqueEnemigo].nombre
                                    + " y causó " + danioRecibido + " de daño.");
                        } else {
                            // Mantener la vida de Luffy al máximo cuando enfrenta a Enel
                            System.out.println(enemigo.nombre + " atacó, pero Luffy no recibió daño.");
                        }
                    }

                } else if (opcion == 2) {
                    while (true) {
                        System.out.println("Elige un objeto:");
                        System.out.println("1. Carne (+50 vida)");
                        if (enemigo.nombre.equals("Enel"))
                            System.out.println("2. Bola de Oro");
                        if (enemigo.nombre.equals("Crocodile"))
                            System.out.println("2. Barril de Agua");
                        System.out.println("3. Volver al menú principal");

                        int objetoElegido = scanner.nextInt();

                        if (objetoElegido == 1) {
                            luffy.vida += carne.efecto;
                            System.out.println(
                                    "¡Usaste " + carne.nombre + "! Recuperaste " + carne.efecto + " puntos de vida.");
                            break;
                        } else if (objetoElegido == 2) {
                            if (enemigo.nombre.equals("Enel")) {
                                tieneBolaDeOro = true;
                                System.out.println("¡Usaste la Bola de Oro! Ahora puedes dañar a Enel.");
                            } else if (enemigo.nombre.equals("Crocodile")) {
                                tieneBarrilDeAgua = true;
                                System.out.println("¡Usaste el Barril de Agua! Ahora puedes dañar a Crocodile.");
                            }
                            break;
                        } else if (objetoElegido == 3) {
                            System.out.println("Volviendo al menú principal...");
                            break;
                        } else {
                            System.out.println("Opción inválida, intenta nuevamente.");
                        }
                    }
                } else if (opcion == 3) {
                    System.out.println("¡Huiste del combate! Fin del juego.");
                    return;
                } else if (opcion == 4) {
                    System.out.println("\nEstadísticas de Ataques:");
                    for (Ataque ataque : luffy.ataques) {
                        System.out.println(ataque.nombre + " - Daño base: " + ataque.potencia);
                    }
                } else {
                    System.out.println("Opción inválida, intenta nuevamente.");
                }
            }

            if (!luffy.estaVivo()) {
                System.out.println("¡Has sido derrotado! Fin del juego.");
                return;
            }

            System.out.println("\n¡Has derrotado a " + enemigo.nombre + "!");

            System.out.println("Tus estadísticas han mejorado:");

            System.out.println("\n¿Quieres seguir avanzando? (si/no)");
            scanner.nextLine(); // Limpiar buffer del scanner
            String continuar = scanner.nextLine();

            while (!continuar.equalsIgnoreCase("si")) {
                if (continuar.equalsIgnoreCase("no")) {
                    System.out.println("¡Gracias por jugar! Fin del juego.");
                    return;
                }
                System.out.println("Respuesta inválida. ¿Quieres seguir avanzando? (si/no)");
                continuar = scanner.nextLine();
            }
        }

        System.out.println("¡Felicitaciones! ¡Has derrotado a todos los enemigos de la Liga One Piece!");
    }
}
