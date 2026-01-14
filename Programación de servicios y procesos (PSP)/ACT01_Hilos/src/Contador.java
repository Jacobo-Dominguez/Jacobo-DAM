public class Contador implements Runnable {

    private String nombre;
    private int limite;

    public Contador(String nombre, int limite) {
        this.nombre = nombre;
        this.limite = limite;
    }

    @Override
    public void run() {
        for (int i = 1; i <= limite; i++) {
            System.out.println(nombre  + " -> " + i);
            try{
                Thread.sleep(500); // Uso de sleep que pausa el hilo durante 500ms

            } catch (InterruptedException e) {
                System.out.println("El " + nombre + " fue interrumpido");
            }
        }
        System.out.println("El " + nombre + " ha terminado\n");
    }


}
