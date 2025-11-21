import java.io.IOException;

public class Main {
    public static void main(String[] args) {

        // Lista de comandos a probar (al menos 6)
        String[][] comandos = {
                {"notepad"},
                {"calc"},
                {"cmd", "/c", "dir"},
                {"cmd", "/c", "ping localhost -n 1"},
                {"aplicacion_que_no_existe"},
                {"cmd", "/c", "dir /parametro_incorrecto"}
        };

        for (String[] comando : comandos) {
            ejecutarComando(comando);
        }

        System.exit(10);
    }

    private static void ejecutarComando(String[] comando) {
        try {
            System.out.println("\nEjecutando: " + String.join(" ", comando));

            // Lanzar proceso
            Process proceso = new ProcessBuilder(comando).start();

            // Esperar a que termine
            int exitValue = proceso.waitFor();

            System.out.println("Finalizado con exit code: " + exitValue);

        } catch (IOException e) {
            System.out.println("ERROR: El comando no existe o no se pudo ejecutar");
        } catch (InterruptedException e) {
            System.out.println("ERROR: La ejecución fue interrumpida");
        }
    }
}