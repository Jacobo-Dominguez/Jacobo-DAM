package logica;

public class Enemigos {
    public Personaje[] obtenerEnemigos() {
        return new Personaje[] {
                new Personaje("Alvida", 50, 5,
                        new Ataque[] { new Ataque("Club Swing", 10), new Ataque("Iron Mace", 15),
                                new Ataque("Counter", 12), new Ataque("Slam", 8) }),
                new Personaje("Buggy", 70, 6,
                        new Ataque[] { new Ataque("Bara Bara Cannon", 12), new Ataque("Bara Bara Punch", 14),
                                new Ataque("Split Assault", 10), new Ataque("Bara Bara Chop", 8) }),
                new Personaje("Arlong", 90, 10,
                        new Ataque[] { new Ataque("Shark On Darts", 15), new Ataque("Water Cannon", 18),
                                new Ataque("Saw Slash", 20), new Ataque("Bite", 22) }),
                new Personaje("Crocodile", 120, 12,
                        new Ataque[] { new Ataque("Desert Spada", 18), new Ataque("Sables Pesado", 20),
                                new Ataque("Desert Girasole", 22), new Ataque("Ground Death", 25) }),
                new Personaje("Enel", 150, 14,
                        new Ataque[] { new Ataque("El Thor", 25), new Ataque("60,0000,000 Volt Lightning Dragon", 22),
                                new Ataque("200,000,000 Volt Amaru", 30), new Ataque("100,0000,000 Volt Vari", 28) }),
                new Personaje("Rob Lucci", 180, 16,
                        new Ataque[] { new Ataque("Rokuogan", 30), new Ataque("Shigan", 20), new Ataque("Rankyaku", 25),
                                new Ataque("Tekkai Kenpo", 18) }),
                new Personaje("Moria", 200, 15,
                        new Ataque[] { new Ataque("Shadow's Asgard", 28), new Ataque("Brick Bat", 20),
                                new Ataque("Doppelman", 15), new Ataque("Scissors", 23) }),
                new Personaje("Hody", 220, 20,
                        new Ataque[] { new Ataque("Water Shot", 33), new Ataque("Water Kick", 30),
                                new Ataque("Fishman Karate", 32), new Ataque("Water Heart", 35) }),
                new Personaje("Doflamingo", 250, 25,
                        new Ataque[] { new Ataque("God Thread", 38), new Ataque("Bullet String", 35),
                                new Ataque("Overheat", 36), new Ataque("Five Color Strings", 37) }),
                new Personaje("Katakuri", 300, 28,
                        new Ataque[] { new Ataque("Yaku Mochi", 40), new Ataque("Chikara Mochi", 41),
                                new Ataque("Amadare Mochi", 42), new Ataque("Zan Giri Mochi", 44) }),
                new Personaje("Kaido", 400, 33,
                        new Ataque[] { new Ataque("Bolo Breath", 46), new Ataque("Raimei Hakke", 47),
                                new Ataque("Ragnaraku", 50), new Ataque("Kaifu", 45) }),
                new Personaje("Kaido Dragon", 450, 33,
                        new Ataque[] { new Ataque("Bolo Breath", 46), new Ataque("Raimei Hakke", 47),
                                new Ataque("Ragnaraku", 50), new Ataque("Kaifu", 45) })
        };
    }
}
