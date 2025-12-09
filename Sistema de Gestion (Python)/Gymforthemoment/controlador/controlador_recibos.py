from modelo.recibo import Recibo
from modelo.base_datos import BaseDatos

class ControladorRecibos:
    def __init__(self):
        self.db = BaseDatos() # Maneja la conexión y consultas a la base de datos
        self._crear_tabla() # Se asegura de que la tabla exista. Si no existe, la crea.

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
        recibo = Recibo(**datos) # Coge los datos de la clase recibo.py y los pasa como argumentos con nombre
        query = "INSERT INTO recibos (id_cliente, mes, anio, pagado) VALUES (?, ?, ?, ?)"
        self.db.ejecutar(query, (recibo.id_cliente, recibo.mes, recibo.anio, int(recibo.pagado)))

    def editar(self, id_recibo, datos):
        recibo = self.obtener_por_id(id_recibo)
        if not recibo:
            return
        for k, v in datos.items(): # Aplica los cambios recibidos en el diccionario de datos
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

    def verificar_recibo_existe(self, id_cliente, mes, anio):
        """Verifica si ya existe un recibo para un cliente en un mes/año específico"""
        query = "SELECT * FROM recibos WHERE id_cliente=? AND mes=? AND anio=?"
        fila = self.db.consultar_uno(query, (id_cliente, mes, anio))
        return fila is not None

    def generar_recibos_mes_todos(self, mes, anio):
        """
        Genera recibos para todos los clientes para un mes/año específico.
        Retorna el número de recibos creados.
        """
        from controlador.controlador_clientes import ControladorClientes
        ctrl_clientes = ControladorClientes()
        
        clientes = ctrl_clientes.listar()
        recibos_creados = 0

        for cliente in clientes:
            # Verificar si ya existe recibo para este cliente en este mes/año
            if not self.verificar_recibo_existe(cliente.id, mes, anio):
                datos = {
                    "id_cliente": cliente.id,
                    "mes": mes,
                    "anio": anio,
                    "pagado": False
                }
                self.agregar(datos)
                recibos_creados += 1

        return recibos_creados

