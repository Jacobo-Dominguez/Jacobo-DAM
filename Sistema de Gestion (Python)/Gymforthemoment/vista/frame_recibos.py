import tkinter as tk
from tkinter import ttk, messagebox, simpledialog
import customtkinter as ctk
from controlador.controlador_recibos import ControladorRecibos
from controlador.controlador_clientes import ControladorClientes
from vista.formulario_recibo import FormularioRecibo
from datetime import date

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
        ttk.Label(self, text="💰 Gestión de Recibos", font=("Helvetica", 18, "bold")).pack(pady=15)

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
            command=self._eliminar_recibo,
            fg_color="#e74c3c",
            hover_color="#c0392b",
            width=100,
            height=32
        )
        btn_eliminar.pack(side="left", padx=5)
        
        btn_generar = ctk.CTkButton(
            top_frame, 
            text="📝 Generar Recibos", 
            command=self._generar_recibos_masivos,
            fg_color="#2ecc71",
            hover_color="#27ae60",
            width=150,
            height=32
        )
        btn_generar.pack(side="left", padx=5)
        
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

    def _generar_recibos_masivos(self):
        """Genera recibos para todos los clientes del mes/año seleccionado"""
        # Crear diálogo personalizado
        dialogo = tk.Toplevel(self)
        dialogo.title("Generar Recibos del Mes")
        dialogo.geometry("350x220")
        dialogo.resizable(False, False)
        dialogo.transient(self)
        dialogo.grab_set()
        
        # Obtener mes y año actual como valores por defecto
        hoy = date.today()
        mes_actual = hoy.month
        anio_actual = hoy.year
        
        # Lista de nombres de meses
        nombres_meses = [
            "Enero", "Febrero", "Marzo", "Abril", "Mayo", "Junio",
            "Julio", "Agosto", "Septiembre", "Octubre", "Noviembre", "Diciembre"
        ]
        
        # Frame principal
        frame = ttk.Frame(dialogo, padding=20)
        frame.pack(fill="both", expand=True)
        
        ttk.Label(frame, text="Seleccione el mes y año para generar recibos:", 
                  font=("Helvetica", 10, "bold")).pack(pady=(0, 15))
        
        # Selector de mes
        ttk.Label(frame, text="Mes:").pack(anchor="w", pady=(5, 0))
        var_mes = tk.StringVar(value=nombres_meses[mes_actual - 1])
        combo_mes = ttk.Combobox(frame, textvariable=var_mes, values=nombres_meses, 
                                 state="readonly", width=25)
        combo_mes.pack(fill="x", pady=(0, 10))
        
        # Selector de año
        ttk.Label(frame, text="Año:").pack(anchor="w", pady=(5, 0))
        var_anio = tk.StringVar(value=str(anio_actual))
        anios = [str(a) for a in range(2020, 2101)]
        combo_anio = ttk.Combobox(frame, textvariable=var_anio, values=anios, 
                                  state="readonly", width=25)
        combo_anio.pack(fill="x", pady=(0, 15))
        
        # Variable para almacenar resultado
        resultado = {"confirmado": False}
        
        def confirmar():
            resultado["confirmado"] = True
            resultado["mes"] = nombres_meses.index(var_mes.get()) + 1
            resultado["anio"] = int(var_anio.get())
            dialogo.destroy()
        
        def cancelar():
            dialogo.destroy()
        
        # Botones
        frame_botones = ttk.Frame(frame)
        frame_botones.pack(fill="x")
        ttk.Button(frame_botones, text="Generar", command=confirmar).pack(side="left", padx=5)
        ttk.Button(frame_botones, text="Cancelar", command=cancelar).pack(side="left", padx=5)
        
        # Centrar diálogo
        dialogo.update_idletasks()
        x = (dialogo.winfo_screenwidth() // 2) - (dialogo.winfo_width() // 2)
        y = (dialogo.winfo_screenheight() // 2) - (dialogo.winfo_height() // 2)
        dialogo.geometry(f"+{x}+{y}")
        
        # Esperar a que se cierre el diálogo
        self.wait_window(dialogo)
        
        # Si no confirmó, salir
        if not resultado["confirmado"]:
            return
        
        mes = resultado["mes"]
        anio = resultado["anio"]
        
        # Confirmar acción
        if not messagebox.askyesno(
            "Confirmar",
            f"¿Generar recibos para todos los clientes de {nombres_meses[mes-1]} {anio}?\n\nSe crearán recibos solo para clientes que no tengan uno en este periodo."
        ):
            return

        # Generar recibos
        try:
            recibos_creados = self.ctrl.generar_recibos_mes_todos(nombres_meses[mes-1], anio)
            messagebox.showinfo(
                "Éxito",
                f"Se generaron {recibos_creados} recibos nuevos para {nombres_meses[mes-1]}/{anio}"
            )
            self._cargar_recibos()
        except Exception as e:
            messagebox.showerror("Error", f"Error al generar recibos: {str(e)}")
