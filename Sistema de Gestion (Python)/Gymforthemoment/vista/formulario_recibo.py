import tkinter as tk
from tkinter import ttk, messagebox
from controlador.controlador_clientes import ControladorClientes

MESES = [
    "Enero", "Febrero", "Marzo", "Abril", "Mayo", "Junio",
    "Julio", "Agosto", "Septiembre", "Octubre", "Noviembre", "Diciembre"
]

ANIOS = [str(a) for a in range(2025, 9999)]

class FormularioRecibo(ttk.Frame):
    def __init__(self, master, callback, recibo=None):
        super().__init__(master)
        self.callback = callback
        self.ctrl_clientes = ControladorClientes()
        self.recibo = recibo

        # Cliente
        clientes = self.ctrl_clientes.listar()
        self.clientes_map = {f"{c.id} - {c.nombre} {c.apellido}": c.id for c in clientes}
        self.var_cliente = tk.StringVar()
        if recibo:
            sel = f"{recibo.id_cliente} - {next(c.nombre+' '+c.apellido for c in clientes if c.id == recibo.id_cliente)}"
            self.var_cliente.set(sel)
        ttk.Label(self, text="Cliente").grid(row=0, column=0, padx=10, pady=5, sticky="w")
        ttk.Combobox(self, textvariable=self.var_cliente, values=list(self.clientes_map.keys()), state="readonly").grid(row=0, column=1, padx=10, pady=5)

        # Mes
        ttk.Label(self, text="Mes:").grid(row=1, column=0, sticky="w", padx=10, pady=5)
        self.var_mes = tk.StringVar(value=recibo.mes if recibo else MESES[0])
        self.combo_mes = ttk.Combobox(self, textvariable=self.var_mes, values=MESES, state="readonly")
        self.combo_mes.grid(row=1, column=1, padx=10, pady=5)

        # Año
        ttk.Label(self, text="Año:").grid(row=2, column=0, sticky="w", padx=10, pady=5)
        self.var_anio = tk.StringVar(value=str(recibo.anio) if recibo else ANIOS[0])
        self.combo_anio = ttk.Combobox(self, textvariable=self.var_anio, values=ANIOS, state="readonly")
        self.combo_anio.grid(row=2, column=1, padx=10, pady=5)

        # Pagado
        self.var_pagado = tk.BooleanVar(value=getattr(recibo, "pagado", False))
        ttk.Checkbutton(self, text="Pagado", variable=self.var_pagado).grid(row=3, column=0, columnspan=2, pady=10)

        # Botón guardar
        ttk.Button(self, text="Guardar", command=self._guardar).grid(row=4, column=0, columnspan=2, pady=10)

    def _guardar(self):
        if not self.var_cliente.get():
            messagebox.showerror("Error", "Selecciona un cliente")
            return
        datos = {
            "id_cliente": self.clientes_map[self.var_cliente.get()],
            "mes": self.var_mes.get(),
            "anio": int(self.var_anio.get()),
            "pagado": self.var_pagado.get()
        }
        self.callback(datos)
