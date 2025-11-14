import tkinter as tk
from tkinter import ttk, messagebox

from vista.utilidades import centrar_ventana


class FormularioReserva(tk.Toplevel):
    def __init__(self, master, titulo, callback, clientes, aparatos, reserva=None):
        super().__init__(master)
        self.callback = callback
        self.title(titulo)
        self.geometry("400x300")
        self.resizable(False, False)
        self.transient(master)
        self.focus_set()

        self.clientes = clientes
        self.aparatos = aparatos

        # Variables
        self.var_cliente = tk.StringVar()
        self.var_aparato = tk.StringVar()
        self.var_fecha = tk.StringVar(value=getattr(reserva, "fecha", ""))
        self.var_hora = tk.StringVar(value=getattr(reserva, "hora", ""))

        centrar_ventana(self, ancho=400, alto=250)

        # Cliente
        ttk.Label(self, text="Cliente").grid(row=0, column=0, padx=10, pady=5, sticky="w")
        lista_clientes = [f"{c.id}-{c.nombre}" for c in clientes]
        ttk.Combobox(self, textvariable=self.var_cliente, values=lista_clientes, state="readonly").grid(row=0, column=1, padx=10, pady=5)

        # Aparato
        ttk.Label(self, text="Aparato").grid(row=1, column=0, padx=10, pady=5, sticky="w")
        lista_aparatos = [f"{a.id}-{a.nombre}" for a in aparatos]
        ttk.Combobox(self, textvariable=self.var_aparato, values=lista_aparatos, state="readonly").grid(row=1, column=1, padx=10, pady=5)

        # Fecha
        ttk.Label(self, text="Fecha (YYYY-MM-DD)").grid(row=2, column=0, padx=10, pady=5, sticky="w")
        ttk.Entry(self, textvariable=self.var_fecha).grid(row=2, column=1, padx=10, pady=5)

        # Hora
        ttk.Label(self, text="Hora (HH:MM)").grid(row=3, column=0, padx=10, pady=5, sticky="w")
        ttk.Entry(self, textvariable=self.var_hora).grid(row=3, column=1, padx=10, pady=5)

        ttk.Button(self, text="Guardar", command=self._guardar).grid(row=4, column=0, columnspan=2, pady=15)

        # Cargar valores existentes si editando
        if reserva:
            self.var_cliente.set(f"{reserva.id_cliente}-{self._nombre_cliente(reserva.id_cliente)}")
            self.var_aparato.set(f"{reserva.id_aparato}-{self._nombre_aparato(reserva.id_aparato)}")

    def _nombre_cliente(self, id_cliente):
        for c in self.clientes:
            if c.id == id_cliente:
                return c.nombre
        return ""

    def _nombre_aparato(self, id_aparato):
        for a in self.aparatos:
            if a.id == id_aparato:
                return a.nombre
        return ""

    def _guardar(self):
        try:
            cliente_id = int(self.var_cliente.get().split("-")[0])
            aparato_id = int(self.var_aparato.get().split("-")[0])
            datos = {
                "id_cliente": cliente_id,
                "id_aparato": aparato_id,
                "fecha": self.var_fecha.get(),
                "hora": self.var_hora.get()
            }
            self.callback(datos)
            self.destroy()
        except ValueError as e:
            messagebox.showerror("Error", str(e))
