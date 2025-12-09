import tkinter as tk
from tkinter import ttk, messagebox
import customtkinter as ctk
from controlador.controlador_clientes import ControladorClientes
from vista.formulario_cliente import FormularioCliente

class FrameClientes(ttk.Frame):
    def __init__(self, master, volver_callback):
        super().__init__(master)
        self.ctrl = ControladorClientes()
        self.volver_callback = volver_callback

        self.formulario_activo = None
        self.mostrando_morosos = False  # Para rastrear si estamos mostrando solo morosos

        self._crear_widgets()
        self._cargar_clientes()

    def _crear_widgets(self):
        ttk.Label(self, text="👥 Gestión de Clientes", font=("Helvetica", 18, "bold")).pack(pady=15)

        top_frame = ttk.Frame(self)
        top_frame.pack(fill="x", padx=10, pady=10)

        # Buscador
        ttk.Label(top_frame, text="🔍 Buscar:").pack(side="left", padx=5)
        self.var_buscar = tk.StringVar()
        ttk.Entry(top_frame, textvariable=self.var_buscar, width=20).pack(side="left", padx=5)
        
        # Botón Filtrar
        btn_filtrar = ctk.CTkButton(
            top_frame, 
            text="🔎 Filtrar", 
            command=self._filtrar,
            fg_color="#00d4ff",
            hover_color="#00a8cc",
            width=100,
            height=32
        )
        btn_filtrar.pack(side="left", padx=5)
        
        # Botón Agregar
        btn_agregar = ctk.CTkButton(
            top_frame, 
            text="➕ Agregar", 
            command=self._abrir_formulario_agregar,
            fg_color="#00d4ff",
            hover_color="#00a8cc",
            width=100,
            height=32
        )
        btn_agregar.pack(side="left", padx=5)
        
        # Botón Editar
        btn_editar = ctk.CTkButton(
            top_frame, 
            text="✏️ Editar", 
            command=self._abrir_formulario_editar,
            fg_color="#00d4ff",
            hover_color="#00a8cc",
            width=100,
            height=32
        )
        btn_editar.pack(side="left", padx=5)
        
        # Botón Eliminar
        btn_eliminar = ctk.CTkButton(
            top_frame, 
            text="🗑️ Eliminar", 
            command=self._eliminar_cliente,
            fg_color="#e74c3c",
            hover_color="#c0392b",
            width=100,
            height=32
        )
        btn_eliminar.pack(side="left", padx=5)
        
        # Botón Ver Morosos
        btn_morosos = ctk.CTkButton(
            top_frame, 
            text="⚠️ Ver Morosos", 
            command=self._mostrar_morosos,
            fg_color="#f39c12",
            hover_color="#e67e22",
            width=120,
            height=32
        )
        btn_morosos.pack(side="left", padx=5)
        
        # Botón Ver Todos
        btn_todos = ctk.CTkButton(
            top_frame, 
            text="👁️ Ver Todos", 
            command=self._mostrar_todos,
            fg_color="#2ecc71",
            hover_color="#27ae60",
            width=110,
            height=32
        )
        btn_todos.pack(side="left", padx=5)
        
        # Botón Volver
        btn_volver = ctk.CTkButton(
            top_frame, 
            text="🏠 Volver al menú", 
            command=self.volver_callback,
            fg_color="#7f8c8d",
            hover_color="#95a5a6",
            width=140,
            height=32
        )
        btn_volver.pack(side="right", padx=5)

        # Tabla
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

    def _mostrar_morosos(self):
        """Muestra solo los clientes morosos"""
        self.mostrando_morosos = True
        for fila in self.tabla.get_children():
            self.tabla.delete(fila)

        morosos = self.ctrl.listar_morosos()
        for c in morosos:
            self.tabla.insert("", "end", values=(c.id, c.nombre, c.apellido, c.email, c.telefono, "Sí" if c.moroso else "No"))
        
        if not morosos:
            messagebox.showinfo("Información", "No hay clientes morosos")

    def _mostrar_todos(self):
        """Restaura la vista de todos los clientes"""
        self.mostrando_morosos = False
        self._cargar_clientes()
