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
        self.formulario_activo = None

        self._crear_widgets()
        self._cargar_recibos()

    def _crear_widgets(self):
        ttk.Label(self, text="Gestión de Recibos", font=("Helvetica", 16, "bold")).pack(pady=10)

        top_frame = ttk.Frame(self)
        top_frame.pack(fill="x", padx=10)

        ttk.Label(top_frame, text="Buscar:").pack(side="left", padx=5)
        self.var_buscar = tk.StringVar()
        ttk.Entry(top_frame, textvariable=self.var_buscar).pack(side="left", padx=5)
        ttk.Button(top_frame, text="Filtrar", command=self._filtrar).pack(side="left", padx=5)
        ttk.Button(top_frame, text="Agregar", command=self._abrir_formulario_agregar).pack(side="left", padx=5)
        ttk.Button(top_frame, text="Editar", command=self._abrir_formulario_editar).pack(side="left", padx=5)
        ttk.Button(top_frame, text="Eliminar", command=self._eliminar_recibo).pack(side="left", padx=5)
        ttk.Button(top_frame, text="Volver al menú", command=self.volver_callback).pack(side="right", padx=5)

        # Tabla de recibos con cliente, mes, año y pagado
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
            cliente = self.ctrl_clientes.obtener_por_id(r.id_cliente)
            nombre_cliente = f"{cliente.nombre} {cliente.apellido}" if cliente else "Desconocido"
            self.tabla.insert("", "end", values=(r.id, nombre_cliente, r.mes, r.anio, "Sí" if r.pagado else "No"))

    def _filtrar(self):
        filtro = self.var_buscar.get()
        for fila in self.tabla.get_children():
            self.tabla.delete(fila)
        for r in self.ctrl.listar(filtro):
            cliente = self.ctrl_clientes.obtener_por_id(r.id_cliente)
            nombre_cliente = f"{cliente.nombre} {cliente.apellido}" if cliente else "Desconocido"
            self.tabla.insert("", "end", values=(r.id, nombre_cliente, r.mes, r.anio, "Sí" if r.pagado else "No"))

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

    def _abrir_formulario_agregar(self):
        if self.formulario_activo:
            self.formulario_activo.pack_forget()
        self.formulario_activo = FormularioRecibo(self, self._guardar_nuevo_recibo)
        self.formulario_activo.pack(padx=10, pady=10, fill="x")

    def _abrir_formulario_editar(self):
        id_recibo = self._get_recibo_seleccionado()
        if not id_recibo:
            return
        recibo = self.ctrl.obtener_por_id(id_recibo)
        if self.formulario_activo:
            self.formulario_activo.pack_forget()
        self.formulario_activo = FormularioRecibo(self, lambda datos: self._guardar_edicion_recibo(id_recibo, datos), recibo)
        self.formulario_activo.pack(padx=10, pady=10, fill="x")

    def _guardar_nuevo_recibo(self, datos):
        self.ctrl.agregar(datos)
        self._cargar_recibos()

    def _guardar_edicion_recibo(self, id_recibo, datos):
        self.ctrl.editar(id_recibo, datos)
        self._cargar_recibos()
