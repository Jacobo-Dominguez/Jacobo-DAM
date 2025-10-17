package logica;

import java.util.Random;

public class Personaje {
    public String nombre;
    public int vida;
    public int ataqueBase;
    public Ataque[] ataques;

    public Personaje(String nombre, int vida, int ataqueBase, Ataque[] ataques) {
        this.nombre = nombre;
        this.vida = vida;
        this.ataqueBase = ataqueBase;
        this.ataques = ataques;
    }

    public boolean estaVivo() {
        return this.vida > 0;
    }

    public void recibirDanio(int danio) {
        int danioReal = danio; // Reducir daño por defensa
        this.vida -= danioReal;
        if (this.vida < 0)
            this.vida = 0;
    }

    public int atacar(int indiceAtaque) {
        Random random = new Random();
        boolean esCritico = random.nextInt(100) < 20;

        // Calcular el daño base
        int danioBase = this.ataqueBase + this.ataques[indiceAtaque].potencia;
        return danioBase;
    }

}
