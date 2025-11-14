from modelo.modelo_reservas import ModeloReservas
from modelo.reserva import Reserva
from datetime import datetime

class ControladorReservas:
    def __init__(self):
        self.modelo = ModeloReservas()

    def agregar(self, datos):
        # Validar que la fecha no sea anterior a hoy
        fecha_reserva = datetime.strptime(datos["fecha"], "%Y-%m-%d").date()
        if fecha_reserva < datetime.today().date():
            raise ValueError("No se puede crear una reserva para una fecha anterior a hoy.")

        reserva = Reserva(**datos)
        self.modelo.insertar(reserva)

    def editar(self, id_reserva, datos):
        # Validar fecha antes de actualizar
        if "fecha" in datos:
            fecha_reserva = datetime.strptime(datos["fecha"], "%Y-%m-%d").date()
            if fecha_reserva < datetime.today().date():
                raise ValueError("No se puede modificar la reserva a una fecha anterior a hoy.")
        reserva = self.modelo.obtener_por_id(id_reserva)
        if reserva:
            for k, v in datos.items():
                setattr(reserva, k, v)
            self.modelo.actualizar(reserva)

    def eliminar(self, id_reserva):
        self.modelo.eliminar(id_reserva)

    def listar(self, filtro=""):
        return self.modelo.obtener_todos(filtro)
