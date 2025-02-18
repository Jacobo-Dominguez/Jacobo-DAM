import prueba.*;

public class App {
    public static void main(String[] args) throws Exception {
        Contador contador = new Contador(7,2);
        System.out.println(contador.obtenerCuenta());
        contador.incrementaCuenta();
        System.out.println(contador.obtenerCuenta());
        contador.incrementaCuenta();
        System.out.println(contador.obtenerCuenta());
        
    }

}
