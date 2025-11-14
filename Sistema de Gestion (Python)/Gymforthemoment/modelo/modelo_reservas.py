from modelo.reserva import Reserva
from modelo.base_datos import BaseDatos

class ModeloReservas:
    def __init__(self):
        self.db = BaseDatos()
        self._crear_tabla()

    def _crear_tabla(self):
        query = """
        CREATE TABLE IF NOT EXISTS reservas (
            id INTEGER PRIMARY KEY AUTOINCREMENT,
            id_cliente INTEGER NOT NULL,
            id_aparato INTEGER NOT NULL,
            fecha TEXT NOT NULL,
            hora TEXT NOT NULL,
            FOREIGN KEY(id_cliente) REFERENCES clientes(id),
            FOREIGN KEY(id_aparato) REFERENCES aparatos(id)
        )
        """
        self.db.ejecutar(query)

    def insertar(self, reserva: Reserva):
        # Verificar solapamiento
        solapamiento = self.db.consultar(
            "SELECT * FROM reservas WHERE id_aparato=? AND fecha=? AND hora=?",
            (reserva.id_aparato, reserva.fecha, reserva.hora)
        )
        if solapamiento:
            raise Exception("Ese aparato ya está reservado en esa fecha y hora.")
        query = """
        INSERT INTO reservas (id_cliente, id_aparato, fecha, hora)
        VALUES (?, ?, ?, ?)
        """
        self.db.ejecutar(query, (reserva.id_cliente, reserva.id_aparato, reserva.fecha, reserva.hora))

    def actualizar(self, reserva: Reserva):
        # Verificar solapamiento excluyendo la reserva actual
        solapamiento = self.db.consultar(
            "SELECT * FROM reservas WHERE id_aparato=? AND fecha=? AND hora=? AND id<>?",
            (reserva.id_aparato, reserva.fecha, reserva.hora, reserva.id)
        )
        if solapamiento:
            raise Exception("Ese aparato ya está reservado en esa fecha y hora.")
        query = """
        UPDATE reservas
        SET id_cliente=?, id_aparato=?, fecha=?, hora=?
        WHERE id=?
        """
        self.db.ejecutar(query, (reserva.id_cliente, reserva.id_aparato, reserva.fecha, reserva.hora, reserva.id))

    def eliminar(self, reserva_id):
        self.db.ejecutar("DELETE FROM reservas WHERE id=?", (reserva_id,))

    def obtener_todos(self, filtro=""):
        query = "SELECT * FROM reservas"
        params = ()
        if filtro:
            query += " WHERE fecha LIKE ? OR hora LIKE ?"
            params = (f"%{filtro}%", f"%{filtro}%")
        filas = self.db.consultar(query, params)
        return [Reserva(**f) for f in filas]

    def obtener_por_id(self, reserva_id):
        fila = self.db.consultar_uno("SELECT * FROM reservas WHERE id=?", (reserva_id,))
        return Reserva(**fila) if fila else None
