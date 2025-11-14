class Reserva:
    def __init__(self, id=None, id_cliente=None, id_aparato=None, fecha="", hora=""):
        self.id = id
        self.id_cliente = id_cliente
        self.id_aparato = id_aparato
        self.fecha = fecha
        self.hora = hora

    def __repr__(self):
        return f"Reserva({self.id}, cliente={self.id_cliente}, aparato={self.id_aparato}, {self.fecha} {self.hora})"


