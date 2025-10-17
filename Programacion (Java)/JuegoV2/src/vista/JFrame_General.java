package vista;

import logica.Enemigos;
import logica.Personaje;
import logica.Protagonista;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.util.List;
import java.util.Objects;

public class JFrame_General extends JFrame {
    private CardLayout cardLayout;
    private JPanel panelContenedor;
    private Clip musicaFondoClip;

    private Panel_Portada panelPortada;
    private Panel_Combate1 panelCombate1;
    private Panel_Combate2 panelCombate2;
    private Panel_Combate3 panelCombate3;
    private Panel_Combate4 panelCombate4;
    private Panel_Combate5 panelCombate5;
    private Panel_Combate6 panelCombate6;
    private Panel_Combate7 panelCombate7;
    private Panel_Combate8 panelCombate8;
    private Panel_Combate9 panelCombate9;
    private Panel_Combate10 panelCombate10;
    private Panel_Combate11 panelCombate11;
    private Panel_Combate12 panelCombate12;
    private Panel_Creditos panelCreditos;

    private Personaje luffy;
    private List<Personaje> enemigos;

    public JFrame_General(){
        this.setTitle("One Piece - Aventura de Luffy");
        // Hacer la ventana pantalla completa
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);  // Maximiza en ambas direcciones (Pantalla Completa)
        this.setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Establecer icono de la ventana
        ImageIcon icono = new ImageIcon(Objects.requireNonNull(getClass().getResource("/resources/imagenes/Icono.jpg")));
        setIconImage(icono.getImage());

        reproducirMusicaFondo("/resources/sonidos/One-Piece-OP-1-We-Are_-Lyrics.wav");

        // Crear a Luffy a través de la clase Protagonista
        Protagonista protagonista = new Protagonista();
        Personaje luffyBase = protagonista.obtenerLuffyBase();
        Personaje luffyGear2 = protagonista.obtenerLuffyGear2();
        Personaje luffyGear3 = protagonista.obtenerLuffyGear3();
        Personaje luffyGear2_3 = protagonista.obtenerLuffyGear2_3();
        Personaje luffyGear4BoundMan = protagonista.obtenerLuffyGear4();
        Personaje luffyGear4SnakeMan = protagonista.obtenerLuffyGear4SnakeMan();
        Personaje luffyWano = protagonista.obtenerLuffyWano();
        Personaje luffyGear5 = protagonista.obtenerLuffyGear5();


        // Crear la lista de enemigos a través de la clase Enemigos
        Enemigos enemigosClass = new Enemigos();
        enemigos = List.of(enemigosClass.obtenerEnemigos());

        // Usamos CardLayout para cambiar entre pantallas
        cardLayout = new CardLayout();
        panelContenedor = new JPanel(cardLayout);

        // Crear los paneles
        panelPortada = new Panel_Portada(this);
        panelCombate1 = new Panel_Combate1(luffyBase, enemigos.subList(0, 1), this); // Alvida
        panelCombate2 = new Panel_Combate2(luffyBase, enemigos.subList(1, 2), this); // Buggy
        panelCombate3 = new Panel_Combate3(luffyBase, enemigos.subList(2, 3), this); // Arlong
        panelCombate4 = new Panel_Combate4(luffyBase, enemigos.subList(3, 4), this); // Crocodile
        panelCombate5 = new Panel_Combate5(luffyBase, enemigos.subList(4, 5), this); // Enel
        panelCombate6 = new Panel_Combate6(luffyGear2, enemigos.subList(5, 6), this); // Rob Lucci
        panelCombate7 = new Panel_Combate7(luffyGear3, enemigos.subList(6, 7), this); // Moria
        panelCombate8 = new Panel_Combate8(luffyGear2_3, enemigos.subList(7, 8), this); // Hody
        panelCombate9 = new Panel_Combate9(luffyGear4BoundMan, enemigos.subList(8, 9), this); // Doflamingo
        panelCombate10 = new Panel_Combate10(luffyGear4SnakeMan, enemigos.subList(9, 10), this); // Katakuri
        panelCombate11 = new Panel_Combate11(luffyWano, enemigos.subList(10,11), this); // Kaido
        panelCombate12 = new Panel_Combate12(luffyGear5, enemigos.subList(enemigos.size()-1, enemigos.size()), this); // Kaido
        panelCreditos = new Panel_Creditos(this); // El panel de créditos

        // Agregar los paneles al contenedor con nombres para identificarlos
        panelContenedor.add(panelPortada, "PORTADA");
        panelContenedor.add(panelCombate1, "COMBATE");
        panelContenedor.add(panelCombate2, "COMBATE2");
        panelContenedor.add(panelCombate3, "COMBATE3");
        panelContenedor.add(panelCombate4, "COMBATE4");
        panelContenedor.add(panelCombate5, "COMBATE5");
        panelContenedor.add(panelCombate6, "COMBATE6");
        panelContenedor.add(panelCombate7, "COMBATE7");
        panelContenedor.add(panelCombate8, "COMBATE8");
        panelContenedor.add(panelCombate9, "COMBATE9");
        panelContenedor.add(panelCombate10, "COMBATE10");
        panelContenedor.add(panelCombate11, "COMBATE11");
        panelContenedor.add(panelCombate12, "COMBATE12");
        panelContenedor.add(panelCreditos, "CREDITOS");

        this.add(panelContenedor);
        cardLayout.show(panelContenedor, "PORTADA");
    }

    // Metodo para cambiar el panel
    public void cambiarAPanelCombate(String nombrePanel) {
        cardLayout.show(panelContenedor, nombrePanel);
        if (nombrePanel.equals("PORTADA")) {
            reproducirMusicaFondo("/resources/sonidos/One-Piece-OP-1-We-Are_-Lyrics.wav"); // Música de portada
        }
        else if (nombrePanel.equals("CREDITOS")) {
            reproducirMusicaFondo("/resources/sonidos/One-Piece-Ending.wav"); // Música de ending
        }
        else {
            reproducirMusicaFondo("/resources/sonidos/One-Piece-Fight-Music-Compilation-_OST_.wav"); // Música de combate
        }
    }

    // Metodo para reproducir música de fondo
    public void reproducirMusicaFondo(String ruta) {
        try {
            if (musicaFondoClip != null && musicaFondoClip.isRunning()) {
                musicaFondoClip.stop(); // Detiene la música actual
                musicaFondoClip.close();
            }
            File archivoSonido = new File(getClass().getResource(ruta).toURI());
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(archivoSonido);
            musicaFondoClip = AudioSystem.getClip();
            musicaFondoClip.open(audioStream);
            musicaFondoClip.loop(Clip.LOOP_CONTINUOUSLY);
            musicaFondoClip.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}