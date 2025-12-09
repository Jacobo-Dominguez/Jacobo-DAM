from modelo.cliente import Cliente
from modelo.base_datos import BaseDatos

class ControladorClientes:
    def __init__(self):
        self.db = BaseDatos() # Maneja la conexión y consultas a la base de datos
        self._crear_tabla() # Se asegura de que la tabla exista. Si no existe, la crea.

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

    def agregar(self, datos):
        cliente = Cliente(**datos) # Coge los datos de la clase cliente.py y los pasa como argumentos con nombre
        query = """
        INSERT INTO clientes (nombre, apellido, email, telefono, moroso)
        VALUES (?, ?, ?, ?, ?)
        """
        self.db.ejecutar(query, (cliente.nombre, cliente.apellido, cliente.email, cliente.telefono, int(cliente.moroso)))

    def editar(self, id_cliente, datos):
        cliente = self.obtener_por_id(id_cliente)
        if not cliente:
            return
        for k, v in datos.items():  # Aplica los cambios recibidos en el diccionario de datos
            setattr(cliente, k, v)
        query = """
        UPDATE clientes
        SET nombre=?, apellido=?, email=?, telefono=?, moroso=?
        WHERE id=?
        """
        self.db.ejecutar(query, (cliente.nombre, cliente.apellido, cliente.email, cliente.telefono, int(cliente.moroso), id_cliente))

    def eliminar(self, id_cliente):
        self.db.ejecutar("DELETE FROM clientes WHERE id=?", (id_cliente,))

    def listar(self, filtro=""):
        query = "SELECT * FROM clientes"
        params = ()
        if filtro:
            query += " WHERE nombre LIKE ? OR apellido LIKE ? OR email LIKE ? OR telefono LIKE ? OR moroso LIKE ?"
            params = (f"%{filtro}%", f"%{filtro}%", f"%{filtro}%", f"%{filtro}%", f"%{filtro}%")
        filas = self.db.consultar(query, params)
        return [Cliente(**f) for f in filas]

    def obtener_por_id(self, id_cliente):
        fila = self.db.consultar_uno("SELECT * FROM clientes WHERE id=?", (id_cliente,))
        return Cliente(**fila) if fila else None

    def marcar_moroso(self, id_cliente, estado=True):
        cliente = self.obtener_por_id(id_cliente)
        if cliente:
            cliente.moroso = estado
            self.editar(id_cliente, {"moroso": estado})

    def listar_morosos(self):
        """Retorna solo los clientes que son morosos"""
        query = "SELECT * FROM clientes WHERE moroso=1"
        filas = self.db.consultar(query)
        return [Cliente(**f) for f in filas]

