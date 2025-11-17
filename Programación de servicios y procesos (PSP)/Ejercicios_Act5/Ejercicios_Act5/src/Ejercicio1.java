import java.util.*;

public class Ejercicio1 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.println("Seleccione acción (apagar / reiniciar / suspender): ");
        String accion = sc.nextLine().trim().toLowerCase();

        System.out.println("Indique tiempo antes de ejecutar (segundos en Windows / minutos en Linux, 0 para suspender): ");
        int tiempo = Integer.parseInt(sc.nextLine().trim());

        String os = System.getProperty("os.name").toLowerCase();
        boolean windows = os.contains("win");

        ProcessBuilder pb = null;

        switch (accion) {
            case "apagar":
                if (windows)
                    pb = new ProcessBuilder("shutdown", "/s", "/t", String.valueOf(tiempo));
                else
                    pb = new ProcessBuilder("shutdown", "-h", "+" + tiempo);
                break;

            case "reiniciar":
                if (windows)
                    pb = new ProcessBuilder("shutdown", "/r", "/t", String.valueOf(tiempo));
                else
                    pb = new ProcessBuilder("shutdown", "-r", "+" + tiempo);
                break;

            case "suspender":
                if (windows)
                    pb = new ProcessBuilder("rundll32.exe", "powrprof.dll,SetSuspendState", "Sleep");
                else
                    pb = new ProcessBuilder("systemctl", "suspend");
                break;

            default:
                System.out.println("Acción no válida.");
                System.exit(0);
        }

        System.out.println("\n--- Comando generado por ProcessBuilder ---");
        System.out.println(String.join(" ", pb.command()));
    }
}
