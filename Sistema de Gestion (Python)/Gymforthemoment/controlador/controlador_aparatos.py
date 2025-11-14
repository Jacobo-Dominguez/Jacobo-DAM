from modelo.modelo_aparatos import ModeloAparatos
from modelo.aparato import Aparato

class ControladorAparatos:
    def __init__(self):
        self.modelo = ModeloAparatos()

    def agregar(self, datos):
        aparato = Aparato(**datos)
        self.modelo.insertar(aparato)

    def editar(self, id_aparato, datos):
        aparato = self.modelo.obtener_por_id(id_aparato)
        if aparato:
            for k, v in datos.items():
                setattr(aparato, k, v)
            self.modelo.actualizar(aparato)

    def eliminar(self, id_aparato):
        self.modelo.eliminar(id_aparato)

    def listar(self, filtro=""):
        return self.modelo.obtener_todos(filtro)
