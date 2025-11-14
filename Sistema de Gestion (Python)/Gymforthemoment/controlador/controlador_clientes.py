from modelo.modelo_clientes import ModeloClientes
from modelo.cliente import Cliente

class ControladorClientes:
    def __init__(self):
        self.modelo = ModeloClientes()

    def agregar(self, datos):
        cliente = Cliente(**datos)
        self.modelo.insertar(cliente)

    def editar(self, id_cliente, datos):
        cliente = self.modelo.obtener_por_id(id_cliente)
        if cliente:
            for k, v in datos.items():
                setattr(cliente, k, v)
            self.modelo.actualizar(cliente)

    def eliminar(self, id_cliente):
        self.modelo.eliminar(id_cliente)

    def listar(self, filtro=""):
        return self.modelo.obtener_todos(filtro)

    def marcar_moroso(self, id_cliente, estado):
        cliente = self.modelo.obtener_por_id(id_cliente)
        if cliente:
            cliente.moroso = estado
            self.modelo.actualizar(cliente)
