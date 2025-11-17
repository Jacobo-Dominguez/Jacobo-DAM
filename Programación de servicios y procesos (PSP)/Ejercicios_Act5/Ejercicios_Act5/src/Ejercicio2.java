import java.io.File;

public class Ejercicio2 {
    public static void main(String[] args) {
        boolean windows = System.getProperty("os.name").toLowerCase().contains("win");
        String tempDir = windows ? "C:/temp" : "/tmp";

        String comando = windows ? "cmd" : "ls";
        ProcessBuilder pb;

        if (windows)
            pb = new ProcessBuilder("cmd", "/c", "dir");
        else
            pb = new ProcessBuilder("ls");

        // 1. Después de crear el ProcessBuilder
        System.out.println("1) directory() inicial: " + pb.directory());

        // 2. Cambiar user.dir
        System.setProperty("user.dir", tempDir);
        System.out.println("2) user.dir cambiado a: " + System.getProperty("user.dir"));

        // 3. Cambiar directorio del ProcessBuilder
        pb.directory(new File(tempDir));
        System.out.println("3) directory() tras cambio: " + pb.directory());
    }
}
