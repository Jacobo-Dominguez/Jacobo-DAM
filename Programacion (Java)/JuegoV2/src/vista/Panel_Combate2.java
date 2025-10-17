package vista;

import logica.Ataque;
import logica.Personaje;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class Panel_Combate2 extends JPanel {
    private JLabel nombreJugador, vidaJugador, nombreEnemigo, vidaEnemigo;
    private JProgressBar barraVidaJugador, barraVidaEnemigo;
    private JButton botonAtacar, botonObjeto, botonHuir, botonEstadisticas, botonSiguiente;
    private JTextArea areaTexto;
    private Personaje jugador;
    private List<Personaje> enemigos;
    private int indiceEnemigoActual;
    private Personaje enemigoActual;
    private Image fondo;  // Imagen de fondo

    private JFrame_General ventana;

    public Panel_Combate2(Personaje jugador, List<Personaje> enemigos, JFrame_General ventana) {
        this.jugador = jugador;
        this.enemigos = enemigos;
        this.indiceEnemigoActual = 0;
        this.enemigoActual = enemigos.get(indiceEnemigoActual);
        this.ventana = ventana;

        // Cargar la imagen de fondo
        fondo = new ImageIcon(getClass().getResource("/resources/imagenes/Fondo-Pelea-Buggy2.jpg")).getImage();

        setLayout(new BorderLayout());

        // Panel superior (Enemigo)
        JPanel panelEnemigo = new JPanel(new BorderLayout());
        panelEnemigo.setOpaque(false);  // Para que el fondo sea visible

        nombreEnemigo = new JLabel(enemigoActual.nombre, SwingConstants.CENTER);
        nombreEnemigo.setForeground(Color.BLACK);

        vidaEnemigo = new JLabel("Vida: " + enemigoActual.vida, SwingConstants.CENTER);
        vidaEnemigo.setForeground(Color.WHITE);

        barraVidaEnemigo = new JProgressBar(0, enemigoActual.vida);
        barraVidaEnemigo.setValue(enemigoActual.vida);
        barraVidaEnemigo.setForeground(Color.RED);

        panelEnemigo.add(nombreEnemigo, BorderLayout.NORTH);
        panelEnemigo.add(barraVidaEnemigo, BorderLayout.CENTER);
        add(panelEnemigo, BorderLayout.NORTH);

        // Panel inferior (Jugador)
        JPanel panelJugador = new JPanel(new BorderLayout());
        panelJugador.setOpaque(false);

        nombreJugador = new JLabel(jugador.nombre, SwingConstants.CENTER);
        nombreJugador.setForeground(Color.WHITE);

        vidaJugador = new JLabel("Vida: " + jugador.vida, SwingConstants.CENTER);
        vidaJugador.setForeground(Color.WHITE);

        barraVidaJugador = new JProgressBar(0, jugador.vida);
        barraVidaJugador.setValue(jugador.vida);
        barraVidaJugador.setForeground(Color.GREEN);

        panelJugador.add(barraVidaJugador, BorderLayout.CENTER);
        panelJugador.add(nombreJugador, BorderLayout.SOUTH);
        add(panelJugador, BorderLayout.SOUTH);

        // Menú de combate
        JPanel panelBotones = new JPanel(new GridLayout(2, 2));
        panelBotones.setOpaque(false);

        botonAtacar = new JButton("Atacar");
        botonObjeto = new JButton("Objeto");
        botonHuir = new JButton("Huir");
        botonEstadisticas = new JButton("Estadísticas");
        panelBotones.add(botonAtacar);
        panelBotones.add(botonObjeto);
        panelBotones.add(botonHuir);
        panelBotones.add(botonEstadisticas);
        panelJugador.add(panelBotones, BorderLayout.SOUTH);

        // Área de texto
        areaTexto = new JTextArea(5, 30);
        areaTexto.setEditable(false);
        add(new JScrollPane(areaTexto), BorderLayout.EAST);

        // Listeners
        botonAtacar.addActionListener(e -> mostrarOpcionesAtaque());
        botonObjeto.addActionListener(e -> usarObjeto());
        botonHuir.addActionListener(e -> areaTexto.setText("¡No puedo huir si quiero ser el Rey de los Piratas!"));
        botonEstadisticas.addActionListener(e -> mostrarEstadisticas());

        // Botón Siguiente
        botonSiguiente = new JButton("Siguiente");
        botonSiguiente.setVisible(false);
        botonSiguiente.addActionListener(e -> ventana.cambiarAPanelCombate("COMBATE3"));
        panelBotones.add(botonSiguiente);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (fondo != null) {
            g.drawImage(fondo, 0, 0, getWidth(), getHeight(), this);
        }
    }


    private void pasarAlSiguienteEnemigo() {
        areaTexto.setText("¡Has derrotado a " + enemigoActual.nombre + "!");
        indiceEnemigoActual++;

        if (indiceEnemigoActual < enemigos.size()) {
            enemigoActual = enemigos.get(indiceEnemigoActual);
            nombreEnemigo.setText(enemigoActual.nombre);
            vidaEnemigo.setText("Vida: " + enemigoActual.vida);
            barraVidaEnemigo.setMaximum(enemigoActual.vida);
            barraVidaEnemigo.setValue(enemigoActual.vida);
        } else {
            areaTexto.append("\n¡Has derrotado a todos los enemigos en este combate!");
            botonSiguiente.setVisible(true);
        }
    }



    private void mostrarGif(String nombreGif) {
        String rutaGif = "/resources/gifs/" + nombreGif;
        java.net.URL gifURL = getClass().getResource(rutaGif);

        if (gifURL != null) {
            ImageIcon gif = new ImageIcon(gifURL);
            JLabel etiquetaGif = new JLabel(gif);
            JOptionPane.showMessageDialog(null, etiquetaGif, "Ataque en acción", JOptionPane.PLAIN_MESSAGE);
        } else {
            System.err.println("No se pudo cargar el GIF: " + rutaGif);
        }
    }

    private void realizarAtaque(int indiceAtaque) {
        int danio = jugador.atacar(indiceAtaque);
        enemigoActual.recibirDanio(danio);
        areaTexto.setText(jugador.nombre + " usó " + jugador.ataques[indiceAtaque].nombre + " e hizo " + danio + " de daño!");

        // Buscar el GIF correspondiente al ataque
        String nombreAtaque = jugador.ataques[indiceAtaque].nombre;

        switch (nombreAtaque) {
            case "Gomu Gomu no Rifle":
                mostrarGif("Luffy-Rifle.gif");
                break;
            case "Gomu Gomu no Pistol":
                mostrarGif("Luffy-Pistol.gif");
                break;
            case "Gomu Gomu no Gatling":
                mostrarGif("Luffy-Gatling.gif");
                break;
            case "Gomu Gomu no Bazooka":
                mostrarGif("Luffy-Bazooka.gif");
                break;
            default:
                System.out.println("No hay GIF para este ataque.");
                break;
        }

        actualizarBarraVida();
        if (!enemigoActual.estaVivo()) {
            pasarAlSiguienteEnemigo();
        } else {
            turnoEnemigo();
        }
    }

    private void turnoEnemigo() {
        // Seleccionar un ataque aleatorio del enemigo
        int indiceAtaque = (int) (Math.random() * enemigoActual.ataques.length);
        Ataque ataqueEnemigo = enemigoActual.ataques[indiceAtaque];

        // Aplicar el daño al jugador
        int danio = enemigoActual.atacar(indiceAtaque);
        jugador.recibirDanio(danio);

        // Mostrar el mensaje del ataque
        areaTexto.append("\n" + enemigoActual.nombre + " usó " + ataqueEnemigo.nombre + " e hizo " + danio + " de daño!");

        // Actualizar la barra de vida
        actualizarBarraVida();
    }


    private void mostrarOpcionesAtaque() {
        JPopupMenu menuAtaques = new JPopupMenu();
        for (int i = 0; i < jugador.ataques.length; i++) {
            int indice = i;
            JMenuItem opcion = new JMenuItem(jugador.ataques[i].nombre + " (" + jugador.ataques[i].potencia + ")");
            opcion.addActionListener(e -> realizarAtaque(indice));
            menuAtaques.add(opcion);
        }
        menuAtaques.show(botonAtacar, botonAtacar.getWidth() / 2, botonAtacar.getHeight() / 2);
    }

    private void usarObjeto() {
        // Actualizamos el array de objetos disponibles
        String[] objetos = {"Carne (Cura 50)", "Inyeccion Ivankov (Restaura toda la vida)"};

        // Mostrar cuadro de selección de objeto
        String seleccion = (String) JOptionPane.showInputDialog(
                null, "Elige un objeto:", "Objeto", JOptionPane.QUESTION_MESSAGE,
                null, objetos, objetos[0]);

        // Verificar que se ha seleccionado un objeto
        if (seleccion != null) {
            String nombreGif = ""; // Variable para almacenar el GIF a mostrar

            // Si el objeto seleccionado es "Carne"
            if (seleccion.startsWith("Carne")) {
                jugador.vida += 50;  // Restaurar 50 puntos de vida
                if (jugador.vida > barraVidaJugador.getMaximum()) {
                    jugador.vida = barraVidaJugador.getMaximum(); // No exceder la vida máxima
                }
                actualizarBarraVida();
                areaTexto.setText("Te han dado carne y recuperaste 50 puntos de vida.");
                nombreGif = "Objeto-Carne.gif";  // GIF para la carne

                // Si el objeto seleccionado es "Inyección Ivankov"
            } else if (seleccion.startsWith("Inyeccion Ivankov")) {
                jugador.vida = 170;  // Restaurar 100 puntos de vida
                if (jugador.vida > barraVidaJugador.getMaximum()) {
                    jugador.vida = barraVidaJugador.getMaximum(); // No exceder la vida máxima
                }
                actualizarBarraVida();
                areaTexto.setText("Te han dado una inyección Ivankov y recuperaste toda la vida.");
                nombreGif = "Objeto-Inyeccion.gif";  // GIF para la inyección Ivankov
            }

            // Mostrar el GIF correspondiente al objeto usado
            if (!nombreGif.isEmpty()) {
                mostrarGif(nombreGif);
            }
        }
    }

    private void actualizarBarraVida() {
        barraVidaJugador.setValue(jugador.vida);
        barraVidaEnemigo.setValue(enemigoActual.vida);
        vidaJugador.setText("Vida: " + jugador.vida);
        vidaEnemigo.setText("Vida: " + enemigoActual.vida);

        // Comprobar si la vida del jugador es 0
        if (jugador.vida <= 0) {
            mostrarGifDerrota("GameOver.gif"); // Mostrar el GIF de Game Over
            terminarJuego();  // Llamar a la función para terminar el juego
        }
    }

    private void terminarJuego() {
        // Crear el GIF que se va a mostrar
        String rutaGif = "/resources/gifs/GameOver.gif";  // Ruta del GIF que quieres mostrar
        java.net.URL gifURL = getClass().getResource(rutaGif);

        if (gifURL != null) {
            ImageIcon gif = new ImageIcon(gifURL);
            JLabel etiquetaGif = new JLabel(gif);

            // Mostrar el mensaje de Game Over con el GIF
            JOptionPane pane = new JOptionPane(etiquetaGif, JOptionPane.PLAIN_MESSAGE);
            JDialog dialog = pane.createDialog(null, "Game Over");

            // Mostrar el diálogo de Game Over
            dialog.setVisible(true);

            // Cuando el usuario cierra la ventana emergente (diálogo)
            // Cambiar al panel "PORTADA" (o cualquier otro panel que necesites)
            ventana.cambiarAPanelCombate("PORTADA");
        } else {
            System.err.println("No se pudo cargar el GIF: " + rutaGif);
        }
    }


    private void mostrarGifDerrota(String nombreGif) {
        String rutaGif = "/resources/gifs/" + nombreGif;
        java.net.URL gifURL = getClass().getResource(rutaGif);

        if (gifURL != null) {
            ImageIcon gif = new ImageIcon(gifURL);
            JLabel etiquetaGif = new JLabel(gif);
            JOptionPane.showMessageDialog(null, etiquetaGif, "¡Game Over!", JOptionPane.PLAIN_MESSAGE);
        } else {
            System.err.println("No se pudo cargar el GIF: " + rutaGif);
        }
    }

    private void mostrarEstadisticas() {
        StringBuilder stats = new StringBuilder();
        stats.append("Vida: ").append(jugador.vida).append("\n");
        stats.append("Ataque: ").append(jugador.ataqueBase).append("\n");
        stats.append("Ataques:\n");
        for (Ataque ataque : jugador.ataques) {
            stats.append(" - ").append(ataque.nombre).append(" (Potencia: ").append(ataque.potencia).append(")\n");
        }
        JOptionPane.showMessageDialog(null, stats.toString(), "Estadísticas", JOptionPane.INFORMATION_MESSAGE);
    }
}
