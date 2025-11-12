class Recibo:
    def __init__(self, id_recibo=None, id_cliente=None, mes=None, anio=None, pagado=False):
        self.id_recibo = id_recibo
        self.id_cliente = id_cliente
        self.mes = mes
        self.anio = anio
        self.pagado = pagado
