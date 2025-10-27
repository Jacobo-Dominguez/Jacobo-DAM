class Jugador:
    def __init__(self, nombre):
        self.nombre = nombre
        self.puntos = 30

    def restar_puntos(self, cantidad):
        self.puntos = max(0, self.puntos - cantidad)