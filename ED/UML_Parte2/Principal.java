package UML_Parte2;

public class Principal {
    public static void main(String[] args) {
        Curso curso = new Curso();
        curso.agregarEstudiante(new Estudiantes("Juan", "Pérez", "2023001", 3, 4.5));
        curso.agregarEstudiante(new Estudiantes("María", "Gómez", "2023002", 2, 2.8));
        curso.agregarEstudiante(new Estudiantes("Luis", "Martínez", "2023003", 4, 3.2));
        curso.buscarEstudiante("2023001");
        curso.eliminarEstudiante("2023002");
        curso.calcularPromedio();
        curso.cantidadAprobados();
    }
}
