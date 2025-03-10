package UML_Parte2;

public class Estudiantes {
    private String nombre;
    private String apellidos;
    private String codigo;
    private int semestre;
    private double notaFinal;

    public Estudiantes(String nombre, String apellidos, String codigo, int semestre, double notaFinal) {
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.codigo = codigo;
        this.semestre = semestre;
        this.notaFinal = notaFinal;
    }

    public String getCodigo() {
        return codigo;
    }

    public double getNotaFinal() {
        return notaFinal;
    }

    public String mostrarInfo() {
        return "Nombre: " + nombre + " " + apellidos + ", Código: " + codigo + ", Semestre: " + semestre + ", Nota Final: " + notaFinal;
    }
}

