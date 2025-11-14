import tkinter as tk
from tkinter import ttk

class FormularioCliente(ttk.Frame):
    def __init__(self, master, callback_guardar, cliente=None):
        super().__init__(master)
        self.callback_guardar = callback_guardar
        self.cliente = cliente

        campos = ["nombre", "apellido", "email", "telefono"] # Lista de campos que tendrá el formulario
        # Creamos un diccionario de variables de Tkinter con valores iniciales
        self.vars = {c: tk.StringVar(value=getattr(cliente, c, "")) for c in campos}
        # Variable para el estado del aparato, con valor inicial si se pasa un cliente, si no "moroso"
        self.var_moroso = tk.BooleanVar(value=getattr(cliente, "moroso", False))

        # Creamos etiquetas y entradas para nombre y tipo
        for i, c in enumerate(campos):
            ttk.Label(self, text=c.capitalize()).grid(row=i, column=0, sticky="w", padx=10, pady=5)
            ttk.Entry(self, textvariable=self.vars[c]).grid(row=i, column=1, padx=10, pady=5)

        ttk.Checkbutton(self, text="Moroso", variable=self.var_moroso).grid(row=len(campos), column=0, columnspan=2, pady=10)
        ttk.Button(self, text="Guardar", command=self._guardar).grid(row=len(campos)+1, column=0, columnspan=2, pady=10)

    def _guardar(self):
        datos = {k: v.get() for k, v in self.vars.items()} # Diccionario con los valores de las variables de los campos
        datos["moroso"] = self.var_moroso.get()
        self.callback_guardar(datos)
        self.pack_forget()
