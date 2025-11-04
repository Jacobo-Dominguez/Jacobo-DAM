package org.example.calculadorafx;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.event.ActionEvent;
import javafx.scene.control.TextField;
import modelo.Calculadora;

public class CalculadoraController {

    @FXML
    private TextField pantalla;
    private Calculadora calculadora = new Calculadora();
    private boolean nuevoNumero = true;

    @FXML
    private void manejarNumero(ActionEvent event) {
        String valor = ((Button) event.getSource()).getText();
        if (nuevoNumero) {
            pantalla.setText(valor);
            nuevoNumero = false;
        } else {
            pantalla.setText(pantalla.getText() + valor);
        }
    }

    @FXML
    private void manejarOperacion(ActionEvent event) {
        double valorActual = Double.parseDouble(pantalla.getText());
        calculadora.aplicarOperacion(valorActual);
        calculadora.setOperador(((Button) event.getSource()).getText());
        pantalla.setText(String.valueOf(calculadora.getResultado()));
        nuevoNumero = true;
    }

    @FXML
    private void manejarIgual(ActionEvent event) {
        double valorActual = Double.parseDouble(pantalla.getText());
        calculadora.aplicarOperacion(valorActual);
        pantalla.setText(String.valueOf(calculadora.getResultado()));
        nuevoNumero = true;
    }

    @FXML
    private void manejarLimpiar(ActionEvent event) {
        calculadora.reiniciar();
        pantalla.setText("0");
        nuevoNumero = true;
    }

}
