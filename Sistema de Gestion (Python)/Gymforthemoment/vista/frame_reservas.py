import tkinter as tk
from tkinter import ttk, messagebox
from controlador.controlador_reservas import ControladorReservas
from controlador.controlador_clientes import ControladorClientes
from controlador.controlador_aparatos import ControladorAparatos
from vista.formulario_reserva import FormularioReserva

class FrameReservas(ttk.Frame):
    def __init__(self, master, volver_callback):
        super().__init__(master)
        self.ctrl = ControladorReservas()
        self.ctrl_clientes = ControladorClientes()
        self.ctrl_aparatos = ControladorAparatos()
        self.volver_callback = volver_callback

        self._crear_widgets()
        self._cargar_reservas()

    def _crear_widgets(self):
        ttk.Label(self, text="Gestión de Reservas", font=("Helvetica", 16, "bold")).pack(pady=10)

        top_frame = ttk.Frame(self)
        top_frame.pack(fill="x", padx=10)

        ttk.Label(top_frame, text="Filtrar fecha:").pack(side="left", padx=5)
        self.var_buscar = tk.StringVar()
        ttk.Entry(top_frame, textvariable=self.var_buscar).pack(side="left", padx=5)
        ttk.Button(top_frame, text="Filtrar", command=self._filtrar).pack(side="left", padx=5)
        ttk.Button(top_frame, text="Agregar", command=self._abrir_formulario_agregar).pack(side="left", padx=5)
        ttk.Button(top_frame, text="Editar", command=self._abrir_formulario_editar).pack(side="left", padx=5)
        ttk.Button(top_frame, text="Eliminar", command=self._eliminar_reserva).pack(side="left", padx=5)
        ttk.Button(top_frame, text="Volver al menú", command=self.volver_callback).pack(side="right", padx=5)

        self.tabla = ttk.Treeview(self, columns=("id", "cliente", "aparato", "fecha", "hora"), show="headings")
        for col in self.tabla["columns"]:
            self.tabla.heading(col, text=col.capitalize())
            self.tabla.column(col, width=120)
        self.tabla.pack(fill="both", expand=True, padx=10, pady=10)

        scroll = ttk.Scrollbar(self, orient="vertical", command=self.tabla.yview)
        self.tabla.configure(yscrollcommand=scroll.set)
        scroll.pack(side="right", fill="y")

    def _cargar_reservas(self):
        for fila in self.tabla.get_children():
            self.tabla.delete(fila)
        for r in self.ctrl.listar():
            cliente = self.ctrl_clientes.modelo.obtener_por_id(r.id_cliente)
            aparato = self.ctrl_aparatos.modelo.obtener_por_id(r.id_aparato)
            self.tabla.insert("", "end", values=(r.id, cliente.nombre if cliente else "", aparato.nombre if aparato else "", r.fecha, r.hora))

    def _filtrar(self):
        filtro = self.var_buscar.get()
        for fila in self.tabla.get_children():
            self.tabla.delete(fila)
        for r in self.ctrl.listar(filtro):
            cliente = self.ctrl_clientes.modelo.obtener_por_id(r.id_cliente)
            aparato = self.ctrl_aparatos.modelo.obtener_por_id(r.id_aparato)
            self.tabla.insert("", "end", values=(r.id, cliente.nombre if cliente else "", aparato.nombre if aparato else "", r.fecha, r.hora))

    def _get_reserva_seleccionada(self):
        seleccionado = self.tabla.focus()
        if not seleccionado:
            messagebox.showwarning("Atención", "Selecciona una reserva.")
            return None
        valores = self.tabla.item(seleccionado, "values")
        return valores[0]

    def _eliminar_reserva(self):
        id_reserva = self._get_reserva_seleccionada()
        if id_reserva and messagebox.askyesno("Confirmar", "¿Eliminar reserva seleccionada?"):
            self.ctrl.eliminar(id_reserva)
            self._cargar_reservas()

    def _abrir_formulario_agregar(self):
        FormularioReserva(self, "Agregar Reserva", self._guardar_nueva_reserva, self.ctrl_clientes.listar(), self.ctrl_aparatos.listar())

    def _abrir_formulario_editar(self):
        id_reserva = self._get_reserva_seleccionada()
        if not id_reserva:
            return
        reserva = self.ctrl.modelo.obtener_por_id(id_reserva)
        FormularioReserva(self, "Editar Reserva", lambda datos: self._guardar_edicion_reserva(id_reserva, datos),
                          self.ctrl_clientes.listar(), self.ctrl_aparatos.listar(), reserva)

    def _guardar_nueva_reserva(self, datos):
        try:
            self.ctrl.agregar(datos)
            self._cargar_reservas()
        except Exception as e:
            messagebox.showerror("Error", str(e))

    def _guardar_edicion_reserva(self, id_reserva, datos):
        try:
            self.ctrl.editar(id_reserva, datos)
            self._cargar_reservas()
        except Exception as e:
            messagebox.showerror("Error", str(e))
