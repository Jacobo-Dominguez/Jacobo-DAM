class Recibo:
    def __init__(self, id=None, id_cliente=None, mes="", anio="", pagado=False):
        self.id = id
        self.id_cliente = id_cliente
        self.mes = mes
        self.anio = anio
        self.pagado = pagado

    def __repr__(self):
        return f"Recibo({self.id}, Cliente={self.id_cliente}, {self.mes}/{self.anio}, pagado={self.pagado})"
