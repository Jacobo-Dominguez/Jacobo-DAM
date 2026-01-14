public class Main {
    public static void main(String[] args) {

        // Creamos los contadores
        Contador c1 = new Contador("Contador 1", 5);
        Contador c2 = new Contador("Contador 2", 5);
        Contador c3 = new Contador("Contador 3", 5);
        Contador c4 = new Contador("Contador 4", 5);

        // Creamos los hilos
        Thread t1 = new Thread(c1);
        Thread t2 = new Thread(c2);
        Thread t3 = new Thread(c3);
        Thread t4 = new Thread(c4);

        try{
            t1.start(); // Inicia el hilo
            t1.join(); // Espera a que termine t1

            t2.start();
            t2.join(); // Espera a t2

            t3.start();
            t3.join(); // Espera a t3

            t4.start();
            t4.join(); // Espera a t4

        } catch (InterruptedException e) {
            System.out.println("El hilo principal ha sido interrumpido");
        }

        System.out.println("Todos los contadores han finalizado");
    }
}