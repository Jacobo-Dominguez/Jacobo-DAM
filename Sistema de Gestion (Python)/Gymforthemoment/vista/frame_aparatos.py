import tkinter as tk
from tkinter import ttk, messagebox
import customtkinter as ctk
from controlador.controlador_aparatos import ControladorAparatos
from vista.formulario_aparato import FormularioAparato

class FrameAparatos(ttk.Frame):
    def __init__(self, master, volver_callback):
        super().__init__(master)
        self.ctrl = ControladorAparatos()
        self.volver_callback = volver_callback

        self._crear_widgets()
        self._cargar_aparatos()

    def _crear_widgets(self):
        ttk.Label(self, text="💪 Gestión de Aparatos", font=("Helvetica", 18, "bold")).pack(pady=15)

        top_frame = ttk.Frame(self)
        top_frame.pack(fill="x", padx=10, pady=10)

        # Buscador
        ttk.Label(top_frame, text="🔍 Buscar:").pack(side="left", padx=5)
        self.var_buscar = tk.StringVar()
        ttk.Entry(top_frame, textvariable=self.var_buscar, width=20).pack(side="left", padx=5)
        
        # Botones con colores y emojis
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
        
        btn_eliminar = ctk.CTkButton(
            top_frame, 
            text="🗑️ Eliminar", 
            command=self._eliminar_aparato,
            fg_color="#e74c3c",
            hover_color="#c0392b",
            width=100,
            height=32
        )
        btn_eliminar.pack(side="left", padx=5)
        
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
        self.tabla = ttk.Treeview(self, columns=("id", "nombre", "tipo", "estado"), show="headings")
        for col in self.tabla["columns"]:
            self.tabla.heading(col, text=col.capitalize())
            self.tabla.column(col, width=120)
        self.tabla.pack(fill="both", expand=True, padx=10, pady=10)

        scroll = ttk.Scrollbar(self, orient="vertical", command=self.tabla.yview)
        self.tabla.configure(yscrollcommand=scroll.set)
        scroll.pack(side="right", fill="y")

    def _cargar_aparatos(self):
        for fila in self.tabla.get_children():
            self.tabla.delete(fila)

        for a in self.ctrl.listar():
            self.tabla.insert("", "end", values=(a.id, a.nombre, a.tipo, a.estado))

    def _filtrar(self):
        filtro = self.var_buscar.get()
        for fila in self.tabla.get_children():
            self.tabla.delete(fila)

        for a in self.ctrl.listar(filtro):
            self.tabla.insert("", "end", values=(a.id, a.nombre, a.tipo, a.estado))

    def _get_aparato_seleccionado(self):
        seleccionado = self.tabla.focus()
        if not seleccionado:
            messagebox.showwarning("Atención", "Selecciona un aparato.")
            return None
        valores = self.tabla.item(seleccionado, "values")
        return valores[0]

    def _eliminar_aparato(self):
        id_aparato = self._get_aparato_seleccionado()
        if id_aparato and messagebox.askyesno("Confirmar", "¿Eliminar aparato seleccionado?"):
            self.ctrl.eliminar(id_aparato)
            self._cargar_aparatos()

    def _abrir_formulario_agregar(self):
        FormularioAparato(self, "Agregar Aparato", self._guardar_nuevo_aparato)

    def _abrir_formulario_editar(self):
        id_aparato = self._get_aparato_seleccionado()
        if not id_aparato:
            return
        aparato = self.ctrl.obtener_por_id(id_aparato)
        FormularioAparato(self, "Editar Aparato", lambda datos: self._guardar_edicion_aparato(id_aparato, datos), aparato)

    def _guardar_nuevo_aparato(self, datos):
        self.ctrl.agregar(datos)
        self._cargar_aparatos()

    def _guardar_edicion_aparato(self, id_aparato, datos):
        self.ctrl.editar(id_aparato, datos)
        self._cargar_aparatos()
