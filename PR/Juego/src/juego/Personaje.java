package juego;

import java.util.Random;

public class Personaje {
    public String nombre;
    public int vida;
    public int ataqueBase;
    public int defensa;
    public Ataque[] ataques;

    public Personaje(String nombre, int vida, int ataqueBase, int defensa, Ataque[] ataques) {
        this.nombre = nombre;
        this.vida = vida;
        this.ataqueBase = ataqueBase;
        this.defensa = defensa;
        this.ataques = ataques;
    }

    public boolean estaVivo() {
        return this.vida > 0;
    }

    public void recibirDanio(int danio) {
        int danioReal = Math.max(0, danio - this.defensa); // Reducir daño por defensa
        this.vida -= danioReal;
        if (this.vida < 0)
            this.vida = 0;
    }

    public int atacar(int indiceAtaque, int defensaEnemiga) {
        Random random = new Random();
        boolean esCritico = random.nextInt(100) < 20;

        // Calcular el daño base
        int danioBase = this.ataqueBase + this.ataques[indiceAtaque].potencia;

        // Si es crítico, ignoramos la defensa
        if (esCritico) {
            System.out.println("¡ATAQUE CRÍTICO! Ignoras la defensa del enemigo.");
            return danioBase;
        }

        // Si no es crítico, restamos la defensa del enemigo
        System.out.println("¡Ataque normal! La defensa del enemigo reduce el daño.");
        int danioFinal = danioBase - defensaEnemiga;
        return Math.max(0, danioFinal); // Garantizamos que el daño no sea negativo
    }

}
