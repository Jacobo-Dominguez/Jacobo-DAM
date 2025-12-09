from modelo.reserva import Reserva
from modelo.base_datos import BaseDatos

class ControladorReservas:
    def __init__(self):
        self.db = BaseDatos() # Maneja la conexión y consultas a la base de datos
        self._crear_tabla() # Se asegura de que la tabla exista. Si no existe, la crea.

    def _crear_tabla(self):
        query = """
        CREATE TABLE IF NOT EXISTS reservas (
            id INTEGER PRIMARY KEY AUTOINCREMENT,
            id_cliente INTEGER NOT NULL,
            id_aparato INTEGER NOT NULL,
            fecha TEXT NOT NULL,
            hora TEXT NOT NULL
        )
        """
        self.db.ejecutar(query)

    def agregar(self, datos):
        reserva = Reserva(**datos) # Coge los datos de la clase reserva.py y los pasa como argumentos con nombre
        query = "INSERT INTO reservas (id_cliente, id_aparato, fecha, hora) VALUES (?, ?, ?, ?)"
        self.db.ejecutar(query, (reserva.id_cliente, reserva.id_aparato, reserva.fecha, reserva.hora))

    def editar(self, id_reserva, datos):
        reserva = self.obtener_por_id(id_reserva)
        if reserva:
            for k, v in datos.items(): # Aplica los cambios recibidos en el diccionario de datos
                setattr(reserva, k, v)
            query = "UPDATE reservas SET id_cliente=?, id_aparato=?, fecha=?, hora=? WHERE id=?"
            self.db.ejecutar(query, (reserva.id_cliente, reserva.id_aparato, reserva.fecha, reserva.hora, reserva.id))

    def eliminar(self, id_reserva):
        self.db.ejecutar("DELETE FROM reservas WHERE id=?", (id_reserva,))

    def listar(self, filtro=""):
        query = "SELECT * FROM reservas"
        params = ()
        if filtro:
            query += " WHERE fecha LIKE ? OR hora LIKE ?"
            params = (f"%{filtro}%", f"%{filtro}%")
        filas = self.db.consultar(query, params)
        return [Reserva(**f) for f in filas]

    def obtener_por_id(self, id_reserva):
        fila = self.db.consultar_uno("SELECT * FROM reservas WHERE id=?", (id_reserva,))
        return Reserva(**fila) if fila else None

    def verificar_solapamiento(self, id_aparato, fecha, hora, id_reserva_actual=None):
        """
        Verifica si existe una reserva para el mismo aparato, fecha y hora.
        id_reserva_actual se usa al editar para excluir la reserva que se está editando.
        """
        query = "SELECT * FROM reservas WHERE id_aparato=? AND fecha=? AND hora=?"
        params = (id_aparato, fecha, hora)
        
        if id_reserva_actual:
            query += " AND id!=?"
            params = (id_aparato, fecha, hora, id_reserva_actual)
        
        fila = self.db.consultar_uno(query, params)
        return fila is not None

    def obtener_reservas_por_aparato_fecha(self, id_aparato, fecha):
        """Obtiene todas las reservas de un aparato en una fecha específica"""
        query = "SELECT * FROM reservas WHERE id_aparato=? AND fecha=?"
        filas = self.db.consultar(query, (id_aparato, fecha))
        return [Reserva(**f) for f in filas]

