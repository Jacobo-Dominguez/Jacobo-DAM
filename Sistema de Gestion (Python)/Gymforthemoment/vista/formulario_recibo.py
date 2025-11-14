import tkinter as tk
from tkinter import ttk, messagebox

from vista.utilidades import centrar_ventana


class FormularioRecibo(tk.Toplevel):
    def __init__(self, master, titulo, callback, clientes):
        super().__init__(master)
        self.callback = callback
        self.title(titulo)
        self.geometry("350x250")
        self.resizable(False, False)
        self.transient(master)
        self.focus_set()

        self.clientes = clientes

        self.var_cliente = tk.StringVar()
        self.var_mes = tk.StringVar()
        self.var_anio = tk.StringVar()

        centrar_ventana(self, ancho=400, alto=250)

        ttk.Label(self, text="Cliente").grid(row=0, column=0, padx=10, pady=5, sticky="w")
        lista_clientes = [f"{c.id}-{c.nombre}" for c in clientes]
        ttk.Combobox(self, textvariable=self.var_cliente, values=lista_clientes, state="readonly").grid(row=0, column=1, padx=10, pady=5)

        ttk.Label(self, text="Mes (01-12)").grid(row=1, column=0, padx=10, pady=5, sticky="w")
        ttk.Entry(self, textvariable=self.var_mes).grid(row=1, column=1, padx=10, pady=5)

        ttk.Label(self, text="Año (YYYY)").grid(row=2, column=0, padx=10, pady=5, sticky="w")
        ttk.Entry(self, textvariable=self.var_anio).grid(row=2, column=1, padx=10, pady=5)

        ttk.Button(self, text="Generar", command=self._guardar).grid(row=3, column=0, columnspan=2, pady=15)

    import tkinter.messagebox as messagebox

    def _guardar(self):
        try:
            cliente_id = int(self.var_cliente.get().split("-")[0])
            datos = {
                "id_cliente": cliente_id,
                "mes": self.var_mes.get(),
                "anio": self.var_anio.get(),
                "pagado": False
            }
            self.callback(datos)
            self.destroy()
        except ValueError as e:
            messagebox.showerror("Error", str(e))

