package logica;

public class Protagonista {
    public Personaje obtenerLuffyBase() {
        Ataque[] ataquesBase = {
                new Ataque("Gomu Gomu no Pistol", 23),
                new Ataque("Gomu Gomu no Gatling", 33),
                new Ataque("Gomu Gomu no Bazooka", 26),
                new Ataque("Gomu Gomu no Rifle", 30)
        };
        return new Personaje("Luffy (Base)", 170, 15, ataquesBase);
    }

    public Personaje obtenerLuffyGear2() {
        Ataque[] ataquesGear2 = {
                new Ataque("Gomu Gomu no Jet Pistol", 20),
                new Ataque("Gomu Gomu no Jet Gatling", 33),
                new Ataque("Gomu Gomu no Jet Bazooka", 22),
                new Ataque("Gomu Gomu no Jet Combo", 27)
        };
        return new Personaje("Luffy (Gear 2)", 190, 18, ataquesGear2);
    }

    public Personaje obtenerLuffyGear3() {
        Ataque[] ataquesGear3 = {
                new Ataque("Gomu Gomu no Jet Pistol", 22),
                new Ataque("Gomu Gomu no Jet Gatling", 34),
                new Ataque("Gomu Gomu no Jet Bazooka", 23),
                new Ataque("Gomu Gomu no Jet Shell", 36)
        };
        return new Personaje("Luffy (Gear 3)", 220, 20, ataquesGear3);
    }

    public Personaje obtenerLuffyGear2_3() {
        Ataque[] ataquesGear2_3 = {
                new Ataque("Gomu Gomu no Jet Gatling", 30),
                new Ataque("Gomu Gomu no Jet Bazooka", 25),
                new Ataque("Gomu Gomu no Red Hawk", 35),
                new Ataque("Gomu Gomu no Elephant Gatling", 38)
        };
        return new Personaje("Luffy (Gear 2&3 Perfecto)", 240, 22, ataquesGear2_3);
    }

    public Personaje obtenerLuffyGear4() {
        Ataque[] ataquesGear4BoundMan = {
                new Ataque("Gomu Gomu no Kong Gun", 34),
                new Ataque("Gomu Gomu no Rhino Schneider", 30),
                new Ataque("Gomu Gomu no Culverin", 32),
                new Ataque("Gomu Gomu no King Kong Gun", 40)
        };
        return new Personaje("Luffy (Gear 4 Bound Man)", 270, 27, ataquesGear4BoundMan);
    }

    public Personaje obtenerLuffyGear4SnakeMan() {
        Ataque[] ataquesGear4SnakeMan = {
                new Ataque("Gomu Gomu no Jet Culverin", 42),
                new Ataque("Gomu Gomu no Culverin", 41),
                new Ataque("Gomu Gomu no Black Mamba", 43),
                new Ataque("Gomu Gomu no King Kobra", 46)
        };
        return new Personaje("Luffy (Gear 4 Snake Man)", 330, 30, ataquesGear4SnakeMan);
    }

    public Personaje obtenerLuffyWano() {
        Ataque[] ataquesWano = {
                new Ataque("Gomu Gomu no Kong Gun", 42),
                new Ataque("Gomu Gomu no Kong Rifle", 43),
                new Ataque("Gomu Gomu no Kong Gatling", 44),
                new Ataque("Gomu Gomu no Red Roc", 48)
        };
        return new Personaje("Luffy (Wano)", 400, 33, ataquesWano);
    }

    public Personaje obtenerLuffyGear5() {
        Ataque[] ataquesGear5 = {
                new Ataque("Gomu Gomu no Kaminari", 47),
                new Ataque("Gomu Gomu no CounterBlow", 45),
                new Ataque("Gomu Gomu no Dawn Rocket", 46),
                new Ataque("Gomu Gomu no Bajrang Gun", 52)
        };
        return new Personaje("Luffy (Gear 5)", 500, 40, ataquesGear5);
    }


    public Personaje luffy;

    public Protagonista() {
        // Definir ataques de Luffy
        Ataque[] ataquesLuffy = {
                new Ataque("Gomu Gomu no Kong Gun", 15),
                new Ataque("Gomu Gomu no Kong Rifle", 20),
                new Ataque("Gomu Gomu no Kong Gatling", 25),
                new Ataque("Gomu Gomu no Red Roc", 30)
        };
        // Crear a Luffy
        luffy = new Personaje("Luffy", 350, 23, ataquesLuffy);
    }

    public Personaje obtenerLuffy() {

        return this.luffy;
    }
}
