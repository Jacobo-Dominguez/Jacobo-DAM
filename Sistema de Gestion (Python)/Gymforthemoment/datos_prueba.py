from controlador.controlador_clientes import ControladorClientes
from controlador.controlador_aparatos import ControladorAparatos
from controlador.controlador_reservas import ControladorReservas
from controlador.controlador_recibos import ControladorRecibos

def generar_datos_prueba():
    # --- Clientes ---
    ctrl_clientes = ControladorClientes()
    clientes = [
        {"nombre": "Juan", "apellido": "Pérez", "email": "juan@example.com", "telefono": "600111222", "moroso": False},
        {"nombre": "Ana", "apellido": "García", "email": "ana@example.com", "telefono": "600333444", "moroso": True},
        {"nombre": "Luis", "apellido": "Martínez", "email": "luis@example.com", "telefono": "600555666", "moroso": False},
    ]
    for c in clientes:
        ctrl_clientes.agregar(c)

    # --- Aparatos ---
    ctrl_aparatos = ControladorAparatos()
    aparatos = [
        {"nombre": "Cinta de correr", "descripcion": "Cinta eléctrica de alta gama"},
        {"nombre": "Bicicleta estática", "descripcion": "Bicicleta para entrenamiento cardiovascular"},
        {"nombre": "Máquina de pesas", "descripcion": "Para ejercicios de fuerza"},
    ]
    for a in aparatos:
        ctrl_aparatos.agregar(a)

    # --- Reservas ---
    ctrl_reservas = ControladorReservas()
    reservas = [
        {"id_cliente": 1, "id_aparato": 1, "dia": "Lunes", "hora": "09:00"},
        {"id_cliente": 2, "id_aparato": 2, "dia": "Martes", "hora": "10:30"},
        {"id_cliente": 3, "id_aparato": 3, "dia": "Miércoles", "hora": "11:00"},
    ]
    for r in reservas:
        ctrl_reservas.agregar(r)

    # --- Recibos ---
    ctrl_recibos = ControladorRecibos()
    recibos = [
        {"id_cliente": 1, "mes": "2025-11", "pagado": True},
        {"id_cliente": 2, "mes": "2025-11", "pagado": False},
        {"id_cliente": 3, "mes": "2025-11", "pagado": True},
    ]
    for rec in recibos:
        ctrl_recibos.agregar(rec)

    print("Datos de prueba generados correctamente.")

if __name__ == "__main__":
    generar_datos_prueba()
