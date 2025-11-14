from modelo.recibo import Recibo
from modelo.base_datos import BaseDatos

class ControladorRecibos:
    def __init__(self):
        self.db = BaseDatos()
        self._crear_tabla()

    def _crear_tabla(self):
        query = """
        CREATE TABLE IF NOT EXISTS recibos (
            id INTEGER PRIMARY KEY AUTOINCREMENT,
            id_cliente INTEGER NOT NULL,
            mes TEXT NOT NULL,
            anio TEXT NOT NULL,
            pagado INTEGER DEFAULT 0,
            FOREIGN KEY(id_cliente) REFERENCES clientes(id)
        )
        """
        self.db.ejecutar(query)

    def agregar(self, datos):
        recibo = Recibo(**datos)
        query = "INSERT INTO recibos (id_cliente, mes, anio, pagado) VALUES (?, ?, ?, ?)"
        self.db.ejecutar(query, (recibo.id_cliente, recibo.mes, recibo.anio, int(recibo.pagado)))

    def editar(self, id_recibo, datos):
        recibo = self.obtener_por_id(id_recibo)
        if not recibo:
            return
        for k, v in datos.items():
            setattr(recibo, k, v)
        query = "UPDATE recibos SET id_cliente=?, mes=?, pagado=? WHERE id=?"
        self.db.ejecutar(query, (recibo.id_cliente, recibo.mes, int(recibo.pagado), id_recibo))

    def eliminar(self, id_recibo):
        self.db.ejecutar("DELETE FROM recibos WHERE id=?", (id_recibo,))

    def listar(self, filtro=""):
        query = "SELECT * FROM recibos"
        params = ()
        if filtro:
            query += " WHERE mes LIKE ? OR pagado LIKE ?"
            params = (f"%{filtro}%", f"%{filtro}%")
        filas = self.db.consultar(query, params)
        return [Recibo(**f) for f in filas]

    def obtener_por_id(self, id_recibo):
        fila = self.db.consultar_uno("SELECT * FROM recibos WHERE id=?", (id_recibo,))
        return Recibo(**fila) if fila else None

    def marcar_pagado(self, id_recibo, estado=True):
        recibo = self.obtener_por_id(id_recibo)
        if recibo:
            recibo.pagado = estado
            self.editar(id_recibo, {"pagado": estado})
