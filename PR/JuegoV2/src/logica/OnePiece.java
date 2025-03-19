package logica;

import java.util.Random;
import java.util.Scanner;

public class OnePiece {
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        Random random = new Random();

        Protagonista protagonista = new Protagonista();
        Enemigos enemigos = new Enemigos();

        Personaje luffy = protagonista.obtenerLuffyBase(); // Luffy Base al inicio
        Personaje[] listaDeEnemigos = enemigos.obtenerEnemigos();
        Item carne = new Item("Carne", 50);

        boolean tieneBolaDeOro = false;
        boolean tieneBarrilDeAgua = false;

        System.out.println("¡Bienvenido a One Piece!");
        System.out.println("¿Quieres iniciar los combates? (si/no)");
        String respuesta = scanner.nextLine();

        if (!respuesta.equalsIgnoreCase("si")) {
            System.out.println("¡Tal vez la próxima vez!");
            return;
        }

        for (Personaje enemigo : listaDeEnemigos) {
            luffy.vida = enemigo.vida + 20;
            luffy.ataqueBase = enemigo.ataqueBase + 7;

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
                        int danio = luffy.atacar(ataqueElegido);
                        enemigo.recibirDanio(danio);
                        System.out.println("¡Usaste " + luffy.ataques[ataqueElegido].nombre + " y causaste " + danio
                                + " de daño!");
                    }

                    if (enemigo.estaVivo()) {
                        int ataqueEnemigo = random.nextInt(enemigo.ataques.length);
                        if (!enemigo.nombre.equals("Enel")) {
                            int danioRecibido = enemigo.atacar(ataqueEnemigo);
                            luffy.recibirDanio(danioRecibido);
                            System.out.println(enemigo.nombre + " usó " + enemigo.ataques[ataqueEnemigo].nombre
                                    + " y causó " + danioRecibido + " de daño.");
                        } else {
                            System.out.println(enemigo.nombre + " atacó, pero Luffy no recibió daño.");
                        }
                    }
                } else if (opcion == 2) {
                    // **Objetos**
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
                            protagonista.luffy.vida += carne.efecto;
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
                }

            }

            if (!luffy.estaVivo()) {
                System.out.println("¡Has sido derrotado! Fin del juego.");
                return;
            }

            System.out.println("\n¡Has derrotado a " + enemigo.nombre + "!");

            if (luffy.nombre.equals("Luffy (Base)") && enemigo.nombre.equals("Blueno")) {
                luffy = protagonista.obtenerLuffyGear2();
                System.out.println("\n¡Luffy ha activado el Gear 2!");
            } else if (luffy.nombre.equals("Luffy (Gear 2)") && enemigo.nombre.equals("Doflamingo")) {
                luffy = protagonista.obtenerLuffyGear4();
                System.out.println("\n¡Luffy ha activado el Gear 4!");
            }
        }
    }
}
