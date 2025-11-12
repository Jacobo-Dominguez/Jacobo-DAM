class Reserva:
    def __init__(self, id_reserva=None, id_cliente=None, id_aparato=None, fecha="", hora_inicio="", hora_fin=""):
        self.id_reserva = id_reserva
        self.id_cliente = id_cliente
        self.id_aparato = id_aparato
        self.fecha = fecha
        self.hora_inicio = hora_inicio
        self.hora_fin = hora_fin
