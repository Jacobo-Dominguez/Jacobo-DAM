class Pelicula:
    def __init__(self, titulo, pistas):
        self.titulo = titulo
        self.pistas = pistas
        self.indice_pista = 1  # Empieza en 1 porque la pista 0 se muestra gratis

    def obtener_pista(self):
        if self.indice_pista < len(self.pistas):
            pista = self.pistas[self.indice_pista]
            self.indice_pista += 1
            return pista
        return None  # No hay más pistas

    def pista_inicial(self):
        """Devuelve la primera pista sin aumentar el índice."""
        if self.pistas:
            return self.pistas[0]
        return None
