from modelo.base_datos import BaseDatos
from modelo.cliente import Cliente
from modelo.aparato import Aparato
from modelo.reserva import Reserva
from modelo.recibo import Recibo
from modelo.modelo_clientes import ModeloClientes
from modelo.modelo_aparatos import ModeloAparatos
from modelo.modelo_reservas import ModeloReservas
from modelo.modelo_recibos import ModeloRecibos

# Inicializamos modelos
modelo_clientes = ModeloClientes()
modelo_aparatos = ModeloAparatos()
modelo_reservas = ModeloReservas()
modelo_recibos = ModeloRecibos()

# --- CLIENTES ---
clientes_demo = [
    {"nombre": "Ana López", "telefono": "600123456", "email": "ana@mail.com", "moroso": False},
    {"nombre": "Juan Pérez", "telefono": "601234567", "email": "juan@mail.com", "moroso": True},
    {"nombre": "Marta García", "telefono": "602345678", "email": "marta@mail.com", "moroso": False},
    {"nombre": "Carlos Ruiz", "telefono": "603456789", "email": "carlos@mail.com", "moroso": False},
    {"nombre": "Laura Torres", "telefono": "604567890", "email": "laura@mail.com", "moroso": True}
]

for c in clientes_demo:
    cliente = Cliente(**c)
    modelo_clientes.insertar(cliente)

clientes = modelo_clientes.obtener_todos()

# --- APARATOS ---
aparatos_demo = [
    {"nombre": "Cinta de correr", "tipo": "Cardio", "estado": "Disponible"},
    {"nombre": "Bicicleta estática", "tipo": "Cardio", "estado": "Disponible"},
    {"nombre": "Máquina de pesas", "tipo": "Fuerza", "estado": "Disponible"},
    {"nombre": "Elíptica", "tipo": "Cardio", "estado": "Disponible"},
    {"nombre": "Banco de abdominales", "tipo": "Fuerza", "estado": "Disponible"}
]

for a in aparatos_demo:
    aparato = Aparato(**a)
    modelo_aparatos.insertar(aparato)

aparatos = modelo_aparatos.obtener_todos()

# --- RESERVAS ---
# Creamos reservas para diferentes clientes y aparatos
reservas_demo = [
    {"id_cliente": clientes[0].id, "id_aparato": aparatos[0].id, "fecha": "2025-11-13", "hora": "08:00"},
    {"id_cliente": clientes[1].id, "id_aparato": aparatos[1].id, "fecha": "2025-11-13", "hora": "08:30"},
    {"id_cliente": clientes[2].id, "id_aparato": aparatos[2].id, "fecha": "2025-11-13", "hora": "09:00"},
    {"id_cliente": clientes[3].id, "id_aparato": aparatos[3].id, "fecha": "2025-11-13", "hora": "09:30"},
    {"id_cliente": clientes[4].id, "id_aparato": aparatos[4].id, "fecha": "2025-11-13", "hora": "10:00"},
    {"id_cliente": clientes[0].id, "id_aparato": aparatos[1].id, "fecha": "2025-11-14", "hora": "08:00"},
    {"id_cliente": clientes[1].id, "id_aparato": aparatos[2].id, "fecha": "2025-11-14", "hora": "08:30"},
    {"id_cliente": clientes[2].id, "id_aparato": aparatos[3].id, "fecha": "2025-11-14", "hora": "09:00"},
    {"id_cliente": clientes[3].id, "id_aparato": aparatos[4].id, "fecha": "2025-11-14", "hora": "09:30"},
    {"id_cliente": clientes[4].id, "id_aparato": aparatos[0].id, "fecha": "2025-11-14", "hora": "10:00"}
]

for r in reservas_demo:
    reserva = Reserva(**r)
    try:
        modelo_reservas.insertar(reserva)
    except Exception as e:
        print(f"No se pudo insertar reserva: {e}")

# --- RECIBOS ---
recibos_demo = [
    {"id_cliente": clientes[0].id, "mes": "11", "anio": "2025", "pagado": True},
    {"id_cliente": clientes[1].id, "mes": "11", "anio": "2025", "pagado": False},
    {"id_cliente": clientes[2].id, "mes": "11", "anio": "2025", "pagado": True},
    {"id_cliente": clientes[3].id, "mes": "11", "anio": "2025", "pagado": True},
    {"id_cliente": clientes[4].id, "mes": "11", "anio": "2025", "pagado": False},
    {"id_cliente": clientes[0].id, "mes": "10", "anio": "2025", "pagado": True},
    {"id_cliente": clientes[1].id, "mes": "10", "anio": "2025", "pagado": True},
    {"id_cliente": clientes[2].id, "mes": "10", "anio": "2025", "pagado": True},
    {"id_cliente": clientes[3].id, "mes": "10", "anio": "2025", "pagado": True},
    {"id_cliente": clientes[4].id, "mes": "10", "anio": "2025", "pagado": False}
]

for rec in recibos_demo:
    recibo = Recibo(**rec)
    try:
        modelo_recibos.generar_recibo(recibo)
    except Exception as e:
        print(f"No se pudo insertar recibo: {e}")

print("Datos de prueba insertados correctamente.")
