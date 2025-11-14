from modelo.cliente import Cliente
from modelo.base_datos import BaseDatos

class ModeloClientes:
    def __init__(self):
        self.db = BaseDatos()
        self._crear_tabla()

    def _crear_tabla(self):
        query = """
        CREATE TABLE IF NOT EXISTS clientes (
            id INTEGER PRIMARY KEY AUTOINCREMENT,
            nombre TEXT NOT NULL,
            apellido TEXT NOT NULL,
            email TEXT,
            telefono TEXT,
            moroso INTEGER DEFAULT 0
        )
        """
        self.db.ejecutar(query)

    def insertar(self, cliente: Cliente):
        query = """
        INSERT INTO clientes (nombre, apellido, email, telefono, moroso)
        VALUES (?, ?, ?, ?, ?)
        """
        self.db.ejecutar(query, (cliente.nombre, cliente.apellido, cliente.email, cliente.telefono, int(cliente.moroso)))

    def actualizar(self, cliente: Cliente):
        query = """
        UPDATE clientes
        SET nombre=?, apellido=?, email=?, telefono=?, moroso=?
        WHERE id=?
        """
        self.db.ejecutar(query, (cliente.nombre, cliente.apellido, cliente.email, cliente.telefono, int(cliente.moroso), cliente.id))

    def eliminar(self, cliente_id):
        self.db.ejecutar("DELETE FROM clientes WHERE id=?", (cliente_id,))

    def obtener_todos(self, filtro=""):
        query = "SELECT * FROM clientes"
        params = ()
        if filtro:
            query += " WHERE nombre LIKE ? apellido LIKE ? OR email LIKE ? OR telefono LIKE ? OR moroso LIKE ?"
            params = (f"%{filtro}%", f"%{filtro}%", f"%{filtro}%", f"%{filtro}%", f"%{filtro}%")
        filas = self.db.consultar(query, params)
        return [Cliente(**f) for f in filas]

    def obtener_por_id(self, cliente_id):
        fila = self.db.consultar_uno("SELECT * FROM clientes WHERE id=?", (cliente_id,))
        return Cliente(**fila) if fila else None
