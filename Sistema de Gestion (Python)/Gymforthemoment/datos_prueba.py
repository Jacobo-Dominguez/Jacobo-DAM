from controlador.controlador_clientes import ControladorClientes
from controlador.controlador_aparatos import ControladorAparatos
from controlador.controlador_reservas import ControladorReservas
from controlador.controlador_recibos import ControladorRecibos
import datetime


def generar_datos_prueba():
    print("Generando datos de prueba...")

    # -----------------------------
    # CLIENTES
    # -----------------------------
    ctrl_clientes = ControladorClientes()
    clientes = [
        {"nombre": "Juan", "apellido": "Pérez", "email": "juan@example.com", "telefono": "600111222", "moroso": False},
        {"nombre": "Ana", "apellido": "García", "email": "ana@example.com", "telefono": "600333444", "moroso": True},
        {"nombre": "Luis", "apellido": "Martínez", "email": "luis@example.com", "telefono": "600555666", "moroso": False},
    ]

    for c in clientes:
        ctrl_clientes.agregar(c)

    # -----------------------------
    # APARATOS
    # -----------------------------
    ctrl_aparatos = ControladorAparatos()
    aparatos = [
        {"nombre": "Cinta de correr", "tipo": "Cardio", "estado": "disponible"},
        {"nombre": "Bicicleta estática", "tipo": "Cardio", "estado": "mantenimiento"},
        {"nombre": "Máquina de pesas", "tipo": "Fuerza", "estado": "ocupado"},
    ]

    for a in aparatos:
        ctrl_aparatos.agregar(a)

    # -----------------------------
    # RESERVAS
    # -----------------------------
    ctrl_reservas = ControladorReservas()

    hoy = datetime.date.today()
    fechas = [
        hoy + datetime.timedelta(days=1),
        hoy + datetime.timedelta(days=2),
        hoy + datetime.timedelta(days=3),
    ]
    horas = ["09:00", "10:30", "11:00"]

    reservas = [
        {"id_cliente": 1, "id_aparato": 1, "fecha": fechas[0].strftime("%Y-%m-%d"), "hora": horas[0]},
        {"id_cliente": 2, "id_aparato": 2, "fecha": fechas[1].strftime("%Y-%m-%d"), "hora": horas[1]},
        {"id_cliente": 3, "id_aparato": 3, "fecha": fechas[2].strftime("%Y-%m-%d"), "hora": horas[2]},
    ]

    for r in reservas:
        ctrl_reservas.agregar(r)

    # -----------------------------
    # RECIBOS
    # -----------------------------
    ctrl_recibos = ControladorRecibos()

    mes_actual = datetime.date.today().month
    anio_actual = datetime.date.today().year

    recibos = [
        {"id_cliente": 1, "mes": mes_actual, "anio": anio_actual, "pagado": True},
        {"id_cliente": 2, "mes": mes_actual, "anio": anio_actual, "pagado": False},
        {"id_cliente": 3, "mes": mes_actual, "anio": anio_actual, "pagado": True},
    ]

    for rec in recibos:
        ctrl_recibos.agregar(rec)

    print("Datos de prueba generados correctamente.")


if __name__ == "__main__":
    generar_datos_prueba()
