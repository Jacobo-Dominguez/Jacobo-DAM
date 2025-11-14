import tkinter as tk
from tkinter import ttk, messagebox
from controlador.controlador_clientes import ControladorClientes
from vista.formulario_cliente import FormularioCliente

class FrameClientes(ttk.Frame):
    def __init__(self, master, volver_callback):
        super().__init__(master)
        self.ctrl = ControladorClientes()
        self.volver_callback = volver_callback

        self.formulario_activo = None

        self._crear_widgets()
        self._cargar_clientes()

    def _crear_widgets(self):
        ttk.Label(self, text="Gestión de Clientes", font=("Helvetica", 16, "bold")).pack(pady=10)

        top_frame = ttk.Frame(self)
        top_frame.pack(fill="x", padx=10)

        ttk.Label(top_frame, text="Buscar:").pack(side="left", padx=5)
        self.var_buscar = tk.StringVar()
        ttk.Entry(top_frame, textvariable=self.var_buscar).pack(side="left", padx=5)
        ttk.Button(top_frame, text="Filtrar", command=self._filtrar).pack(side="left", padx=5)
        ttk.Button(top_frame, text="Agregar", command=self._abrir_formulario_agregar).pack(side="left", padx=5)
        ttk.Button(top_frame, text="Editar", command=self._abrir_formulario_editar).pack(side="left", padx=5)
        ttk.Button(top_frame, text="Eliminar", command=self._eliminar_cliente).pack(side="left", padx=5)
        ttk.Button(top_frame, text="Volver al menú", command=self.volver_callback).pack(side="right", padx=5)

        self.tabla = ttk.Treeview(self, columns=("id", "nombre", "apellido", "email", "telefono", "moroso"), show="headings")
        for col in self.tabla["columns"]:
            self.tabla.heading(col, text=col.capitalize())
            self.tabla.column(col, width=120)
        self.tabla.pack(fill="both", expand=True, padx=10, pady=10)

        scroll = ttk.Scrollbar(self, orient="vertical", command=self.tabla.yview)
        self.tabla.configure(yscrollcommand=scroll.set)
        scroll.pack(side="right", fill="y")

    def _cargar_clientes(self):
        for fila in self.tabla.get_children():
            self.tabla.delete(fila)

        for c in self.ctrl.listar():
            self.tabla.insert("", "end", values=(c.id, c.nombre, c.apellido, c.email, c.telefono, "Sí" if c.moroso else "No"))

    def _filtrar(self):
        filtro = self.var_buscar.get()
        for fila in self.tabla.get_children():
            self.tabla.delete(fila)

        for c in self.ctrl.listar(filtro):
            self.tabla.insert("", "end", values=(c.id, c.nombre, c.apellido, c.email, c.telefono, "Sí" if c.moroso else "No"))

    def _get_cliente_seleccionado(self):
        seleccionado = self.tabla.focus()
        if not seleccionado:
            messagebox.showwarning("Atención", "Selecciona un cliente.")
            return None
        valores = self.tabla.item(seleccionado, "values")
        return valores[0]

    def _eliminar_cliente(self):
        id_cliente = self._get_cliente_seleccionado()
        if id_cliente and messagebox.askyesno("Confirmar", "¿Eliminar cliente seleccionado?"):
            self.ctrl.eliminar(id_cliente)
            self._cargar_clientes()

    def _abrir_formulario_agregar(self):
        if self.formulario_activo:
            self.formulario_activo.pack_forget()
        self.formulario_activo = FormularioCliente(self, self._guardar_nuevo_cliente)
        self.formulario_activo.pack(padx=10, pady=10, fill="x")

    def _abrir_formulario_editar(self):
        id_cliente = self._get_cliente_seleccionado()
        if not id_cliente:
            return
        cliente = self.ctrl.obtener_por_id(id_cliente)
        if self.formulario_activo:
            self.formulario_activo.pack_forget()
        self.formulario_activo = FormularioCliente(self, lambda datos: self._guardar_edicion_cliente(id_cliente, datos), cliente)
        self.formulario_activo.pack(padx=10, pady=10, fill="x")

    def _guardar_nuevo_cliente(self, datos):
        self.ctrl.agregar(datos)
        self._cargar_clientes()

    def _guardar_edicion_cliente(self, id_cliente, datos):
        self.ctrl.editar(id_cliente, datos)
        self._cargar_clientes()
