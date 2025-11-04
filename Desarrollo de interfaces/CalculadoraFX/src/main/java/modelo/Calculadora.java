package modelo;

public class Calculadora {
    private double resultado = 0;
    private String operador = "";
    private boolean nuevoNumero = true;

    public double getResultado() {
        return resultado;
    }

    public void aplicarOperacion(double valor){
        switch (operador){
            case  "+":
                resultado = resultado + valor;
                break;
            case "-":
                resultado = resultado - valor;
                break;
            case "*":
                resultado = resultado * valor;
                break;
            case "/":
                if (valor != 0) {
                    resultado = resultado / valor;
                } else {
                    throw new ArithmeticException("División por cero");
                }
                break;
            default:
                resultado = valor;
                break;
        }
    }

    public void setOperador(String operador) {
        this.operador = operador;
    }

    public void reiniciar() {
        resultado = 0;
        operador = "";
        nuevoNumero = true;
    }
}
