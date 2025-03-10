package UML_Parte2;

import java.util.ArrayList;
import java.util.Scanner;

public class Curso {
    private ArrayList<Estudiantes> estudiantes;

    public Curso() {

        this.estudiantes = new ArrayList<>();
    }

    public void agregarEstudiante(Estudiantes estudiante) {
        for (Estudiantes est : estudiantes) {
            if (est.getCodigo().equals(estudiante.getCodigo())) {
                System.out.println("Error: El código ya existe.");
                return;
            }
        }
        estudiantes.add(estudiante);
        System.out.println("Estudiante agregado exitosamente.");
    }

    public void buscarEstudiante(String codigo) {
        for (Estudiantes estudiante : estudiantes) {
            if (estudiante.getCodigo().equals(codigo)) {
                System.out.println(estudiante.mostrarInfo());
                return;
            }
        }
        System.out.println("Estudiante no encontrado.");
    }

    public void eliminarEstudiante(String codigo) {
        for (Estudiantes estudiante : estudiantes) {
            if (estudiante.getCodigo().equals(codigo)) {
                Scanner scanner = new Scanner(System.in);
                System.out.print("¿Desea eliminar a " + estudiante.mostrarInfo() + "? (s/n): ");
                String confirmacion = scanner.nextLine();
                if (confirmacion.equalsIgnoreCase("s")) {
                    estudiantes.remove(estudiante);
                    System.out.println("Estudiante eliminado.");
                }
                return;
            }
        }
        System.out.println("Estudiante no encontrado.");
    }

    public void calcularPromedio() {
        if (estudiantes.isEmpty()) {
            System.out.println("No hay estudiantes en el curso.");
            return;
        }
        double sumaNotas = 0;
        for (Estudiantes est : estudiantes) {
            sumaNotas += est.getNotaFinal();
        }
        double promedio = sumaNotas / estudiantes.size();
        System.out.printf("El promedio del curso es: %.2f\n", promedio);
    }

    public void cantidadAprobados() {
        int aprobados = 0;
        for (Estudiantes est : estudiantes) {
            if (est.getNotaFinal() >= 3.0) {
                aprobados++;
            }
        }
        double porcentaje = (estudiantes.isEmpty()) ? 0 : (aprobados * 100.0 / estudiantes.size());
        System.out.printf("Cantidad de aprobados: %d, Porcentaje: %.2f%%\n", aprobados, porcentaje);
    }
}
