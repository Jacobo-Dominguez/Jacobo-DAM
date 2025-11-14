import tkinter as tk
from tkinter import ttk, messagebox
from controlador.controlador_recibos import ControladorRecibos
from controlador.controlador_clientes import ControladorClientes
from vista.formulario_recibo import FormularioRecibo


class FrameRecibos(ttk.Frame):
    def __init__(self, master, volver_callback):
        super().__init__(master)
        self.ctrl = ControladorRecibos()
        self.ctrl_clientes = ControladorClientes()
        self.volver_callback = volver_callback

        self._crear_widgets()
        self._cargar_recibos()

    def _crear_widgets(self):
        ttk.Label(self, text="Gestión de Recibos", font=("Helvetica", 16, "bold")).pack(pady=10)

        top_frame = ttk.Frame(self)
        top_frame.pack(fill="x", padx=10)

        ttk.Label(top_frame, text="Filtrar mes/año:").pack(side="left", padx=5)
        self.var_buscar = tk.StringVar()
        ttk.Entry(top_frame, textvariable=self.var_buscar).pack(side="left", padx=5)
        ttk.Button(top_frame, text="Filtrar", command=self._filtrar).pack(side="left", padx=5)
        ttk.Button(top_frame, text="Generar", command=self._abrir_formulario_generar).pack(side="left", padx=5)
        ttk.Button(top_frame, text="Marcar Pagado", command=self._marcar_pagado).pack(side="left", padx=5)
        ttk.Button(top_frame, text="Eliminar", command=self._eliminar_recibo).pack(side="left", padx=5)
        ttk.Button(top_frame, text="Volver al menú", command=self.volver_callback).pack(side="right", padx=5)

        self.tabla = ttk.Treeview(self, columns=("id", "cliente", "mes", "anio", "pagado"), show="headings")
        for col in self.tabla["columns"]:
            self.tabla.heading(col, text=col.capitalize())
            self.tabla.column(col, width=120)
        self.tabla.pack(fill="both", expand=True, padx=10, pady=10)

        scroll = ttk.Scrollbar(self, orient="vertical", command=self.tabla.yview)
        self.tabla.configure(yscrollcommand=scroll.set)
        scroll.pack(side="right", fill="y")

    def _cargar_recibos(self):
        for fila in self.tabla.get_children():
            self.tabla.delete(fila)
        for r in self.ctrl.listar():
            cliente = self.ctrl_clientes.modelo.obtener_por_id(r.id_cliente)
            self.tabla.insert("", "end", values=(r.id, cliente.nombre if cliente else "", r.mes, r.anio,
                                                 "Sí" if r.pagado else "No"))

    def _filtrar(self):
        filtro = self.var_buscar.get()
        for fila in self.tabla.get_children():
            self.tabla.delete(fila)
        for r in self.ctrl.listar(filtro):
            cliente = self.ctrl_clientes.modelo.obtener_por_id(r.id_cliente)
            self.tabla.insert("", "end", values=(r.id, cliente.nombre if cliente else "", r.mes, r.anio,
                                                 "Sí" if r.pagado else "No"))

    def _get_recibo_seleccionado(self):
        seleccionado = self.tabla.focus()
        if not seleccionado:
            messagebox.showwarning("Atención", "Selecciona un recibo.")
            return None
        valores = self.tabla.item(seleccionado, "values")
        return valores[0]

    def _eliminar_recibo(self):
        id_recibo = self._get_recibo_seleccionado()
        if id_recibo and messagebox.askyesno("Confirmar", "¿Eliminar recibo seleccionado?"):
            self.ctrl.eliminar(id_recibo)
            self._cargar_recibos()

    def _marcar_pagado(self):
        id_recibo = self._get_recibo_seleccionado()
        if id_recibo:
            self.ctrl.marcar_pagado(id_recibo, True)
            self._cargar_recibos()

    def _abrir_formulario_generar(self):
        FormularioRecibo(self, "Generar Recibo", self._generar_recibo, self.ctrl_clientes.listar())

    def _generar_recibo(self, datos):
        try:
            self.ctrl.generar(datos)
            self._cargar_recibos()
        except Exception as e:
            messagebox.showerror("Error", str(e))
