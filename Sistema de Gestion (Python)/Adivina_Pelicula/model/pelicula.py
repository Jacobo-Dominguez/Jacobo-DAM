class Pelicula:
    def __init__(self, titulo, pistas):
        self.titulo = titulo
        self.pistas = pistas
        self.indice_pista = 1  # Empieza en 1 porque la pista 0 se muestra gratis
        # (antes era 0 pero lo cambie para tener una pista inicial gratis)

    def obtener_pista(self):
        if self.indice_pista < len(self.pistas):
            pista = self.pistas[self.indice_pista]
            self.indice_pista += 1
            return pista
        return "No hay más pistas disponibles."

    def pista_inicial(self):
        return self.pistas[0] if self.pistas else "No hay pistas disponibles."