import tkinter as tk
from tkinter import ttk, messagebox
from vista.utilidades import centrar_ventana
from controlador.controlador_clientes import ControladorClientes
from controlador.controlador_aparatos import ControladorAparatos
from datetime import date, time, datetime


class FormularioReserva(tk.Toplevel):
    def __init__(self, master, titulo, callback, reserva=None):
        super().__init__(master)
        self.callback = callback
        self.title(titulo)
        self.geometry("400x300")
        self.resizable(False, False)
        self.transient(master)
        self.focus_set()

        # Controladores para obtener listas
        self.ctrl_clientes = ControladorClientes()
        self.ctrl_aparatos = ControladorAparatos()

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

        # Fecha y hora
        self.var_fecha = tk.StringVar(value=getattr(reserva, "fecha", ""))
        self.var_hora = tk.StringVar(value=getattr(reserva, "hora", ""))

        ttk.Label(self, text="Fecha (YYYY-MM-DD)").grid(row=2, column=0, sticky="w", padx=10, pady=5)
        ttk.Entry(self, textvariable=self.var_fecha).grid(row=2, column=1, padx=10, pady=5)

        ttk.Label(self, text="Hora (HH:MM)").grid(row=3, column=0, sticky="w", padx=10, pady=5)
        ttk.Entry(self, textvariable=self.var_hora).grid(row=3, column=1, padx=10, pady=5)

        ttk.Button(self, text="Guardar", command=self._guardar).grid(row=4, column=0, columnspan=2, pady=10)

        centrar_ventana(self, ancho=400, alto=250)

    def _guardar(self):
        # Validar fecha
        try:
            fecha_seleccionada = date.fromisoformat(self.var_fecha.get())
        except ValueError:
            messagebox.showerror("Error", "Formato de fecha incorrecto (YYYY-MM-DD)")
            return

        hora_seleccionada = self.var_hora.get()
        try:
            h, m = map(int, hora_seleccionada.split(":"))
            hora_obj = time(hour=h, minute=m)
        except Exception:
            messagebox.showerror("Error", "Formato de hora incorrecto (HH:MM)")
            return

        # Fecha anterior a hoy
        if fecha_seleccionada < date.today():
            messagebox.showerror("Error", "No se puede seleccionar una fecha anterior a hoy")
            return

        # Si es hoy, hora no puede ser anterior a hora actual
        if fecha_seleccionada == date.today() and hora_obj < datetime.now().time():
            messagebox.showerror("Error", "No se puede seleccionar una hora anterior a la hora actual")
            return

        datos = {
            "id_cliente": self.clientes_map[self.var_cliente.get()],
            "id_aparato": self.aparatos_map[self.var_aparato.get()],
            "fecha": self.var_fecha.get(),
            "hora": self.var_hora.get()
        }

        self.callback(datos)
        self.destroy()
