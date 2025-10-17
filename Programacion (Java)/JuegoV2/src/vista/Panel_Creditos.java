package vista;

import javax.swing.*;
import java.awt.*;

public class Panel_Creditos extends JPanel {
    public Panel_Creditos(JFrame_General frame) {
        setLayout(new BorderLayout()); // Configurar el Layout (por ejemplo, FlowLayout para que el botón esté en el centro)
        JButton boton = new JButton("Volver al Inicio"); // Crear el botón
        // Añadir un ActionListener para manejar el clic del botón
        boton.addActionListener(e -> {
            frame.cambiarAPanelCombate("PORTADA"); // Ir al panel de portada
        });
        // Añadir el botón al panel (en la parte inferior)
        this.add(boton, BorderLayout.SOUTH); // Puedes cambiar la posición si lo deseas
    }

    //El primer parámetro sera nuestro objeto de tipo ImageIcon, contiene la imagen que vamos a agregar.
    //El segundo y tercer parámetro serán dos valores enteros que equivalen a la posición en el eje x,y.
    //El cuarto y quinto parámetro serán las dimensiones que tendrá nuestra imagen, para este ejemplo las dimensiones sera iguales a las dimensiones de nuestro JPanel de esta manera garantizamos que la imagen tenga el mismo tamaño de nuestra ventana JFrame.
    //El ultimo parámetro solamente pasamos un valor null

    @Override
    public void paint(Graphics g){
        Dimension dimension = this.getSize();
        ImageIcon icon = new ImageIcon(getClass().getResource("/resources/imagenes/the end.jpg"));
        g.drawImage(icon.getImage(), 0, 0, dimension.width, dimension.height, null);
        setOpaque(false);
        super.paintChildren(g);
    }

}
