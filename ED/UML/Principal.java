package UML;

public class Principal {
    public static void main(String[] args) {
        // Crear algunos computadores
        Computador comp1 = new Computador("Acer", 50, "Intel Core i7", "Windows", 18500000);
        Computador comp2 = new Computador("Toshiba", 80, "Intel Core i5", "Windows", 15500000);
        Computador comp3 = new Computador("Apple", 100, "Intel Core i7", "macOS", 2500000);

        // Crear una tienda con capacidad para 5 computadores
        Tienda tienda = new Tienda("Tienda Cuantica", "Pepito Pérez", 123456, 5);

        // Añadir computadores a la tienda
        tienda.añadir(comp1);
        tienda.añadir(comp2);
        tienda.añadir(comp3);

        // Buscar un computador por marca
        int indice = tienda.buscar("Acer");
        if (indice != -1) {
            System.out.println("Computador encontrado en la posición: " + indice);
        } else {
            System.out.println("Computador no encontrado.");
        }

        // Imprimir toda la información de la tienda y los computadores
        System.out.println("Información completa de la tienda:");
        System.out.println(tienda.imprimir());

        // Imprimir solo la información de los computadores
        System.out.println("\nLista de computadores en la tienda:");
        for (int i = 0; i < tienda.getNumeroComputadores(); i++) {
            System.out.println(tienda.getComputadores()[i]);
        }
    }

}
