class Aparato:
    def __init__(self, id=None, nombre="", tipo="", estado="disponible"):
        self.id = id
        self.nombre = nombre
        self.tipo = tipo
        self.estado = estado

    def __repr__(self):
        return f"Aparato({self.id}, {self.nombre}, {self.tipo}, estado={self.estado})"

    def to_dict(self):
        return {
            "id": self.id,
            "nombre": self.nombre,
            "tipo": self.tipo,
            "estado": self.estado
        }
