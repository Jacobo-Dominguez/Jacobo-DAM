class Aparato:
    def __init__(self, id=None, nombre="", tipo="", estado="Disponible"):
        self.id = id
        self.nombre = nombre
        self.tipo = tipo
        self.estado = estado

    def __repr__(self):
        return f"Aparato({self.id}, {self.nombre}, {self.tipo}, estado={self.estado})"


