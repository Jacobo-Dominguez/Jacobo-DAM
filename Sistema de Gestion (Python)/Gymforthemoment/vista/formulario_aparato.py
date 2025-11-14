import tkinter as tk
from tkinter import ttk

from vista.utilidades import centrar_ventana


class FormularioAparato(tk.Toplevel):
    def __init__(self, master, titulo, callback, aparato=None):
        super().__init__(master)
        self.callback = callback
        self.title(titulo)
        self.geometry("350x250")
        self.resizable(False, False)
        self.transient(master)
        self.focus_set()

        campos = ["nombre", "tipo"]
        self.vars = {c: tk.StringVar(value=getattr(aparato, c, "")) for c in campos}
        self.var_estado = tk.StringVar(value=getattr(aparato, "estado", "Disponible"))

        centrar_ventana(self, ancho=400, alto=250)

        for i, c in enumerate(campos):
            ttk.Label(self, text=c.capitalize()).grid(row=i, column=0, sticky="w", padx=10, pady=5)
            ttk.Entry(self, textvariable=self.vars[c]).grid(row=i, column=1, padx=10, pady=5)

        ttk.Label(self, text="Estado").grid(row=3, column=0, sticky="w", padx=10, pady=5)
        estados = ["Disponible", "En mantenimiento", "Ocupado"]
        ttk.Combobox(self, textvariable=self.var_estado, values=estados, state="readonly").grid(row=3, column=1, padx=10, pady=5)

        ttk.Button(self, text="Guardar", command=self._guardar).grid(row=5, column=0, columnspan=2, pady=10)

    def _guardar(self):
        datos = {k: v.get() for k, v in self.vars.items()}
        datos["estado"] = self.var_estado.get()
        self.callback(datos)
        self.destroy()
