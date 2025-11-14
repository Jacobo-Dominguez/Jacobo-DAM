from modelo.recibo import Recibo
from modelo.base_datos import BaseDatos

class ModeloRecibos:
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

    def generar_recibo(self, recibo: Recibo):
        # Evitar duplicados
        existe = self.db.consultar(
            "SELECT * FROM recibos WHERE id_cliente=? AND mes=? AND anio=?",
            (recibo.id_cliente, recibo.mes, recibo.anio)
        )
        if existe:
            raise Exception("El recibo ya existe para este cliente y mes.")
        query = """
        INSERT INTO recibos (id_cliente, mes, anio, pagado)
        VALUES (?, ?, ?, ?)
        """
        self.db.ejecutar(query, (recibo.id_cliente, recibo.mes, recibo.anio, int(recibo.pagado)))

    def actualizar(self, recibo: Recibo):
        query = """
        UPDATE recibos
        SET pagado=?
        WHERE id=?
        """
        self.db.ejecutar(query, (int(recibo.pagado), recibo.id))

    def eliminar(self, recibo_id):
        self.db.ejecutar("DELETE FROM recibos WHERE id=?", (recibo_id,))

    def obtener_todos(self, filtro=""):
        query = "SELECT * FROM recibos"
        params = ()
        if filtro:
            query += " WHERE mes LIKE ? OR anio LIKE ?"
            params = (f"%{filtro}%", f"%{filtro}%")
        filas = self.db.consultar(query, params)
        return [Recibo(**f) for f in filas]

    def obtener_por_id(self, recibo_id):
        fila = self.db.consultar_uno("SELECT * FROM recibos WHERE id=?", (recibo_id,))
        return Recibo(**fila) if fila else None

    def obtener_morosos(self):
        filas = self.db.consultar("SELECT * FROM recibos WHERE pagado=0")
        return [Recibo(**f) for f in filas]
