import tkinter as tk
from tkinter import ttk, messagebox
from tkcalendar import DateEntry
from vista.utilidades import centrar_ventana
from controlador.controlador_clientes import ControladorClientes
from controlador.controlador_aparatos import ControladorAparatos
from controlador.controlador_reservas import ControladorReservas
from datetime import date, time, datetime


class FormularioReserva(tk.Toplevel):
    def __init__(self, master, titulo, callback, reserva=None):
        super().__init__(master)
        self.callback = callback
        self.title(titulo)
        self.geometry("400x350")
        self.resizable(False, False)
        self.transient(master)
        self.focus_set()

        # Controladores para obtener listas
        self.ctrl_clientes = ControladorClientes()
        self.ctrl_aparatos = ControladorAparatos()
        self.ctrl_reservas = ControladorReservas()
        self.reserva = reserva  # Guardar para verificar solapamiento al editar

        # Combobox clientes
        clientes = self.ctrl_clientes.listar()
        self.clientes_map = {f"{c.id} - {c.nombre} {c.apellido}": c.id for c in clientes}
        self.var_cliente = tk.StringVar()
        if reserva:
            sel = f"{reserva.id_cliente} - {next(c.nombre + ' ' + c.apellido for c in clientes if c.id == reserva.id_cliente)}"
            self.var_cliente.set(sel)

        ttk.Label(self, text="Cliente").grid(row=0, column=0, sticky="w", padx=10, pady=5)
        ttk.Combobox(self, textvariable=self.var_cliente, values=list(self.clientes_map.keys()), state="readonly").grid(row=0, column=1, padx=10, pady=5)

        # Combobox aparatos
        aparatos = self.ctrl_aparatos.listar()
        self.aparatos_map = {f"{a.id} - {a.nombre} ({a.tipo})": a.id for a in aparatos}
        self.var_aparato = tk.StringVar()
        if reserva:
            sel = f"{reserva.id_aparato} - {next(a.nombre+' ('+a.tipo+')' for a in aparatos if a.id == reserva.id_aparato)}"
            self.var_aparato.set(sel)

        ttk.Label(self, text="Aparato").grid(row=1, column=0, sticky="w", padx=10, pady=5)
        ttk.Combobox(self, textvariable=self.var_aparato, values=list(self.aparatos_map.keys()), state="readonly").grid(row=1, column=1, padx=10, pady=5)

        # Calendario para fecha
        ttk.Label(self, text="Fecha").grid(row=2, column=0, sticky="w", padx=10, pady=5)
        
        # Valor inicial de fecha
        fecha_inicial = date.today()
        if reserva and reserva.fecha:
            try:
                fecha_inicial = date.fromisoformat(reserva.fecha)
            except:
                pass
        
        self.date_entry = DateEntry(
            self, 
            width=18, 
            background='darkblue',
            foreground='white', 
            borderwidth=2,
            year=fecha_inicial.year,
            month=fecha_inicial.month,
            day=fecha_inicial.day,
            date_pattern='yyyy-mm-dd',
            locale='es_ES'
        )
        self.date_entry.grid(row=2, column=1, padx=10, pady=5, sticky="w")

        # Selector de hora (solo intervalos de 30 minutos)
        ttk.Label(self, text="Hora").grid(row=3, column=0, sticky="w", padx=10, pady=5)
        
        # Generar lista de horas válidas (cada 30 minutos)
        horas_validas = []
        for h in range(24):
            horas_validas.append(f"{h:02d}:00")
            horas_validas.append(f"{h:02d}:30")
        
        self.var_hora = tk.StringVar()
        if reserva and reserva.hora:
            self.var_hora.set(reserva.hora)
        else:
            # Hora por defecto: siguiente intervalo de 30 minutos
            ahora = datetime.now()
            minutos = 0 if ahora.minute < 30 else 30
            self.var_hora.set(f"{ahora.hour:02d}:{minutos:02d}")
        
        combo_hora = ttk.Combobox(self, textvariable=self.var_hora, values=horas_validas, state="readonly", width=18)
        combo_hora.grid(row=3, column=1, padx=10, pady=5, sticky="w")

        # Información adicional
        info_label = ttk.Label(self, text="ℹ️ Gimnasio abierto lunes a viernes, 24h\nSesiones de 30 minutos", 
                               font=("Helvetica", 8), foreground="gray")
        info_label.grid(row=4, column=0, columnspan=2, pady=10)

        ttk.Button(self, text="Guardar", command=self._guardar).grid(row=5, column=0, columnspan=2, pady=10)

        centrar_ventana(self, ancho=400, alto=350)

    def _guardar(self):
        # Validar fecha
        try:
            fecha_seleccionada = self.date_entry.get_date()
        except Exception:
            messagebox.showerror("Error", "Seleccione una fecha válida")
            return

        # Validar que sea de lunes a viernes (0=lunes, 4=viernes, 5=sábado, 6=domingo)
        if fecha_seleccionada.weekday() > 4:
            messagebox.showerror("Error", "El gimnasio solo está abierto de lunes a viernes")
            return

        hora_seleccionada = self.var_hora.get()
        try:
            h, m = map(int, hora_seleccionada.split(":"))
            hora_obj = time(hour=h, minute=m)
        except Exception:
            messagebox.showerror("Error", "Seleccione una hora válida")
            return

        # Validar que los minutos sean 00 o 30 (sesiones de 30 minutos)
        if m not in [0, 30]:
            messagebox.showerror("Error", "Las sesiones son de 30 minutos. Horas válidas: XX:00 o XX:30")
            return

        # Fecha anterior a hoy
        if fecha_seleccionada < date.today():
            messagebox.showerror("Error", "No se puede seleccionar una fecha anterior a hoy")
            return

        # Si es hoy, hora no puede ser anterior a hora actual
        if fecha_seleccionada == date.today() and hora_obj < datetime.now().time():
            messagebox.showerror("Error", "No se puede seleccionar una hora anterior a la hora actual")
            return

        # Verificar solapamiento de reservas
        id_aparato = self.aparatos_map[self.var_aparato.get()]
        id_reserva_actual = self.reserva.id if self.reserva else None
        
        if self.ctrl_reservas.verificar_solapamiento(id_aparato, str(fecha_seleccionada), hora_seleccionada, id_reserva_actual):
            messagebox.showerror("Error", "Ya existe una reserva para este aparato en la misma fecha y hora")
            return

        datos = {
            "id_cliente": self.clientes_map[self.var_cliente.get()],
            "id_aparato": id_aparato,
            "fecha": str(fecha_seleccionada),
            "hora": hora_seleccionada
        }

        self.callback(datos)
        self.destroy()
