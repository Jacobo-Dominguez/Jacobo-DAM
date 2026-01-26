import javax.crypto.SecretKey;
import java.security.KeyPair;
import java.util.Scanner;

public class Main {

    // Claves simétricas
    static SecretKey claveAES;
    static SecretKey claveDES;

    // Claves asimétricas
    static KeyPair clavesRSA;

    // Últimos textos cifrados
    static String cifradoAES;
    static String cifradoDES;
    static String cifradoRSA;

    public static void main(String[] args) throws Exception {

        Scanner sc = new Scanner(System.in);
        int opcionPrincipal;

        do {
            // MENÚ PRINCIPAL
            System.out.println("\n===== MENÚ PRINCIPAL (Hecho por Jacobo) =====");
            System.out.println("1. Cifrar");
            System.out.println("2. Descifrar");
            System.out.println("3. Salir");
            System.out.print("Opción: ");
            opcionPrincipal = sc.nextInt();
            sc.nextLine();
            switch (opcionPrincipal) {
                case 1:
                    // MENÚ CIFRAR
                    int opcionCifrar;
                    do {
                        System.out.println("\n--- Cifrado ---");
                        System.out.println("1. AES");
                        System.out.println("2. DES");
                        System.out.println("3. Volver");
                        System.out.print("Opción: ");
                        opcionCifrar = sc.nextInt();
                        sc.nextLine();
                        switch (opcionCifrar) {
                            case 1:
                                // AES
                                if (claveAES == null) {
                                    claveAES = Simetrico.generarClave("AES");
                                    System.out.println("Clave AES generada automáticamente");
                                }
                                System.out.print("Texto a cifrar (AES): ");
                                String textoAES = sc.nextLine();
                                cifradoAES = Simetrico.cifrar(textoAES, claveAES, "AES");
                                System.out.println("Texto cifrado AES (Base64): " + cifradoAES);
                                break;
                            case 2:
                                // DES
                                if (claveDES == null) {
                                    claveDES = Simetrico.generarClave("DES");
                                    System.out.println("Clave DES generada automáticamente");
                                }
                                System.out.print("Texto a cifrar (DES): ");
                                String textoDES = sc.nextLine();
                                cifradoDES = Simetrico.cifrar(textoDES, claveDES, "DES");
                                System.out.println("Texto cifrado DES (Base64): " + cifradoDES);
                                break;
                            case 3:
                                System.out.println("Volviendo al menú principal...");
                                break;
                            default:
                                System.out.println("Opción no válida");
                        }
                    } while (opcionCifrar != 3);
                    break;
                case 2:
                    // MENÚ DESCIFRAR
                    int opcionDescifrar;
                    do {
                        System.out.println("\n--- Descifrado ---");
                        System.out.println("1. RSA");
                        System.out.println("2. Volver");
                        System.out.print("Opción: ");
                        opcionDescifrar = sc.nextInt();
                        sc.nextLine();
                        switch (opcionDescifrar) {
                            case 1:
                                // RSA
                                if (clavesRSA == null) {
                                    clavesRSA = Asimetrico.generarClaves();
                                    System.out.println("Claves RSA generadas automáticamente");
                                }
                                System.out.print("Texto a cifrar (RSA): ");
                                String textoRSA = sc.nextLine();
                                cifradoRSA = Asimetrico.cifrar(textoRSA, clavesRSA);
                                System.out.println("Texto cifrado RSA (Base64): " + cifradoRSA);
                                break;
                            case 2:
                                System.out.println("Volviendo al menú principal...");
                                break;
                            default:
                                System.out.println("Opción no válida");
                        }
                    } while (opcionDescifrar != 2);
                    break;
                case 3:
                    System.out.println("Saliendo del programa...");
                    break;
                default:
                    System.out.println("Opción no válida");
            }
        } while (opcionPrincipal != 3);
        sc.close();
    }
}
