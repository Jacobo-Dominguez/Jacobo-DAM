from modelo.base_datos import BaseDatos
from controlador.controlador_reservas import ControladorReservas
from controlador.controlador_aparatos import ControladorAparatos
from controlador.controlador_clientes import ControladorClientes


class ControladorReportes:
    def __init__(self):
        self.db = BaseDatos()
        self.ctrl_reservas = ControladorReservas()
        self.ctrl_aparatos = ControladorAparatos()
        self.ctrl_clientes = ControladorClientes()

    def generar_disponibilidad_dia(self, fecha):
        """
        Genera un reporte de disponibilidad para todos los aparatos en una fecha específica.
        Retorna una lista de diccionarios con información de cada aparato y sus reservas.
        """
        aparatos = self.ctrl_aparatos.listar()
        reporte = []

        for aparato in aparatos:
            # Obtener todas las reservas de este aparato en la fecha
            reservas = self.ctrl_reservas.obtener_reservas_por_aparato_fecha(aparato.id, fecha)
            
            # Crear diccionario de horas ocupadas con información del cliente
            horas_ocupadas = {}
            for reserva in reservas:
                cliente = self.ctrl_clientes.obtener_por_id(reserva.id_cliente)
                nombre_cliente = f"{cliente.nombre} {cliente.apellido}" if cliente else "Desconocido"
                horas_ocupadas[reserva.hora] = nombre_cliente

            # Generar lista de todas las horas posibles (00:00 a 23:30 en intervalos de 30 min)
            todas_las_horas = []
            for h in range(24):
                todas_las_horas.append(f"{h:02d}:00")
                todas_las_horas.append(f"{h:02d}:30")

            # Crear lista de disponibilidad
            disponibilidad = []
            for hora in todas_las_horas:
                if hora in horas_ocupadas:
                    disponibilidad.append({
                        "hora": hora,
                        "estado": "Ocupado",
                        "cliente": horas_ocupadas[hora]
                    })
                else:
                    disponibilidad.append({
                        "hora": hora,
                        "estado": "Libre",
                        "cliente": "-"
                    })

            reporte.append({
                "aparato_id": aparato.id,
                "aparato_nombre": aparato.nombre,
                "aparato_tipo": aparato.tipo,
                "disponibilidad": disponibilidad,
                "total_reservas": len(reservas)
            })

        return reporte

    def obtener_resumen_dia(self, fecha):
        """
        Obtiene un resumen rápido de reservas para una fecha.
        """
        query = """
        SELECT COUNT(*) as total_reservas
        FROM reservas
        WHERE fecha = ?
        """
        resultado = self.db.consultar_uno(query, (fecha,))
        return resultado["total_reservas"] if resultado else 0
