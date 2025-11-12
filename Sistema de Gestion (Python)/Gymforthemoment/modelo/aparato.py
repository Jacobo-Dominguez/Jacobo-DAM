class Aparato:
    def __init__(self, id_aparato=None, nombre="", tipo="", estado="Disponible"):
        self.id_aparato = id_aparato
        self.nombre = nombre
        self.tipo = tipo
        self.estado = estado

    def __str__(self):
        return f"{self.nombre} - {self.tipo} ({self.estado})"
