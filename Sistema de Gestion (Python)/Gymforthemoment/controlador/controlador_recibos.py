from modelo.modelo_recibos import ModeloRecibos
from modelo.recibo import Recibo
from datetime import date

class ControladorRecibos:
    def __init__(self):
        self.modelo = ModeloRecibos()

    def generar(self, datos):
        mes = int(datos["mes"])
        anio = int(datos["anio"])
        hoy = date.today()

        # Validación: no se pueden generar recibos para meses anteriores
        if anio < hoy.year or (anio == hoy.year and mes < hoy.month):
            raise ValueError("No se puede generar un recibo para un mes anterior al actual.")
        recibo = Recibo(**datos)
        self.modelo.generar_recibo(recibo)

    def marcar_pagado(self, id_recibo, estado=True):
        recibo = self.modelo.obtener_por_id(id_recibo)
        if recibo:
            recibo.pagado = estado
            self.modelo.actualizar(recibo)

    def eliminar(self, id_recibo):
        self.modelo.eliminar(id_recibo)

    def listar(self, filtro=""):
        return self.modelo.obtener_todos(filtro)

    def listar_morosos(self):
        return self.modelo.obtener_morosos()
