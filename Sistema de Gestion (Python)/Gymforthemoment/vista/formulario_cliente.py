import tkinter as tk
from tkinter import ttk

from vista.utilidades import centrar_ventana


class FormularioCliente(tk.Toplevel):
    def __init__(self, master, titulo, callback, cliente=None):
        super().__init__(master)
        self.callback = callback
        self.title(titulo)
        self.geometry("350x300")
        self.resizable(False, False)
        self.transient(master)
        self.focus_set()

        campos = ["nombre", "apellido", "email", "telefono"]
        self.vars = {c: tk.StringVar(value=getattr(cliente, c, "")) for c in campos}
        self.var_moroso = tk.BooleanVar(value=getattr(cliente, "moroso", False))

        centrar_ventana(self, ancho=400, alto=250)

        for i, c in enumerate(campos):
            ttk.Label(self, text=c.capitalize()).grid(row=i, column=0, sticky="w", padx=10, pady=5)
            ttk.Entry(self, textvariable=self.vars[c]).grid(row=i, column=1, padx=10, pady=5)

        ttk.Checkbutton(self, text="Moroso", variable=self.var_moroso).grid(row=5, column=0, columnspan=2, pady=10)
        ttk.Button(self, text="Guardar", command=self._guardar).grid(row=6, column=0, columnspan=2, pady=10)

    def _guardar(self):
        datos = {k: v.get() for k, v in self.vars.items()}
        datos["moroso"] = self.var_moroso.get()
        self.callback(datos)
        self.destroy()
