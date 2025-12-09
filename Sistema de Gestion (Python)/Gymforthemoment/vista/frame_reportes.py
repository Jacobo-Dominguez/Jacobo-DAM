import tkinter as tk
from tkinter import ttk, messagebox
import customtkinter as ctk
from tkcalendar import DateEntry
from controlador.controlador_reportes import ControladorReportes
from datetime import date, timedelta


class FrameReportes(ttk.Frame):
    def __init__(self, master, volver_callback):
        super().__init__(master)
        self.ctrl = ControladorReportes()
        self.volver_callback = volver_callback

        self._crear_widgets()

    def _crear_widgets(self):
        ttk.Label(self, text="📊 Reportes de Disponibilidad", font=("Helvetica", 18, "bold")).pack(pady=15)

        # Frame superior para controles
        top_frame = ttk.Frame(self)
        top_frame.pack(fill="x", padx=10, pady=10)

        ttk.Label(top_frame, text="📅 Seleccionar Fecha:").pack(side="left", padx=5)
        
        # Calendario en lugar de entrada de texto
        self.date_entry = DateEntry(
            top_frame,
            width=15,
            background='darkblue',
            foreground='white',
            borderwidth=2,
            date_pattern='yyyy-mm-dd',
            locale='es_ES'
        )
        self.date_entry.pack(side="left", padx=5)
        
        btn_generar = ctk.CTkButton(
            top_frame, 
            text="📊 Generar Reporte", 
            command=self._generar_reporte,
            fg_color="#00d4ff",
            hover_color="#00a8cc",
            width=150,
            height=32
        )
        btn_generar.pack(side="left", padx=5)
        
        btn_hoy = ctk.CTkButton(
            top_frame, 
            text="📆 Hoy", 
            command=self._seleccionar_hoy,
            fg_color="#2ecc71",
            hover_color="#27ae60",
            width=100,
            height=32
        )
        btn_hoy.pack(side="left", padx=5)
        
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

        # Frame para el reporte
        self.reporte_frame = ttk.Frame(self)
        self.reporte_frame.pack(fill="both", expand=True, padx=10, pady=10)

        # Mensaje inicial
        ttk.Label(self.reporte_frame, text="Seleccione una fecha y haga clic en 'Generar Reporte'", 
                  font=("Helvetica", 12)).pack(pady=50)

    def _seleccionar_hoy(self):
        self.date_entry.set_date(date.today())

    def _generar_reporte(self):
        # Validar fecha
        try:
            fecha_seleccionada = self.date_entry.get_date()
        except Exception:
            messagebox.showerror("Error", "Seleccione una fecha válida")
            return

        # Validar que sea de lunes a viernes
        if fecha_seleccionada.weekday() > 4:
            messagebox.showwarning("Atención", "El gimnasio solo está abierto de lunes a viernes")
            return

        # Limpiar frame de reporte
        for widget in self.reporte_frame.winfo_children():
            widget.destroy()

        # Generar reporte
        reporte = self.ctrl.generar_disponibilidad_dia(str(fecha_seleccionada))

        # Mostrar información de la fecha
        info_frame = ttk.Frame(self.reporte_frame)
        info_frame.pack(fill="x", pady=5)
        
        dias_semana = ["Lunes", "Martes", "Miércoles", "Jueves", "Viernes", "Sábado", "Domingo"]
        dia_nombre = dias_semana[fecha_seleccionada.weekday()]
        
        ttk.Label(info_frame, text=f"Reporte para: {dia_nombre}, {fecha_seleccionada.strftime('%d/%m/%Y')}", 
                  font=("Helvetica", 12, "bold")).pack()

        # Crear notebook (pestañas) para cada aparato
        notebook = ttk.Notebook(self.reporte_frame)
        notebook.pack(fill="both", expand=True, pady=10)

        if not reporte:
            ttk.Label(self.reporte_frame, text="No hay aparatos registrados", 
                      font=("Helvetica", 11)).pack(pady=20)
            return

        for aparato_info in reporte:
            # Crear frame para cada aparato
            tab_frame = ttk.Frame(notebook)
            notebook.add(tab_frame, text=f"{aparato_info['aparato_nombre']} ({aparato_info['total_reservas']} reservas)")

            # Información del aparato
            info = ttk.Frame(tab_frame)
            info.pack(fill="x", padx=10, pady=5)
            ttk.Label(info, text=f"Tipo: {aparato_info['aparato_tipo']} | Total de reservas: {aparato_info['total_reservas']}", 
                      font=("Helvetica", 10)).pack()

            # Tabla de disponibilidad
            tabla_frame = ttk.Frame(tab_frame)
            tabla_frame.pack(fill="both", expand=True, padx=10, pady=5)

            # Crear tabla con scroll
            tabla = ttk.Treeview(tabla_frame, columns=("hora", "estado", "cliente"), show="headings", height=20)
            tabla.heading("hora", text="Hora")
            tabla.heading("estado", text="Estado")
            tabla.heading("cliente", text="Cliente")
            
            tabla.column("hora", width=100, anchor="center")
            tabla.column("estado", width=100, anchor="center")
            tabla.column("cliente", width=200)

            # Scrollbar
            scrollbar = ttk.Scrollbar(tabla_frame, orient="vertical", command=tabla.yview)
            tabla.configure(yscrollcommand=scrollbar.set)

            tabla.pack(side="left", fill="both", expand=True)
            scrollbar.pack(side="right", fill="y")

            # Llenar tabla con disponibilidad
            for slot in aparato_info['disponibilidad']:
                # Colorear filas según estado
                tag = "ocupado" if slot['estado'] == "Ocupado" else "libre"
                tabla.insert("", "end", values=(slot['hora'], slot['estado'], slot['cliente']), tags=(tag,))

            # Configurar colores
            tabla.tag_configure("ocupado", background="#ffcccc")
            tabla.tag_configure("libre", background="#ccffcc")

        # Resumen general
        total_reservas = sum(a['total_reservas'] for a in reporte)
        resumen_frame = ttk.Frame(self.reporte_frame)
        resumen_frame.pack(fill="x", pady=10)
        ttk.Label(resumen_frame, text=f"Total de reservas del día: {total_reservas}", 
                  font=("Helvetica", 11, "bold")).pack()
