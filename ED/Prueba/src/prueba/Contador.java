package prueba;

public class Contador{
    private int valor;
    private int incremento;

    public Contador(){

    }

    public Contador(int incremento){
        this.incremento = incremento;
    }

    public Contador(int valor, int incremento){
        this.valor = valor;
        this.incremento = incremento;
    }

    public void incrementaCuenta(){
        this.valor += this.incremento;
    }

    public void iniciaCuenta(int valor){
        this.valor = valor;
    }

    public int obtenerCuenta(){
        return valor;
    }
}