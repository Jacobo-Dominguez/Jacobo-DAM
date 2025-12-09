import customtkinter as ctk
from vista.frame_clientes import FrameClientes
from vista.frame_aparatos import FrameAparatos
from vista.frame_reservas import FrameReservas
from vista.frame_recibos import FrameRecibos
from vista.frame_reportes import FrameReportes

ctk.set_appearance_mode("dark")
ctk.set_default_color_theme("blue")

class InterfazGym:
    def __init__(self):
        self.root = ctk.CTk()
        self.root.title("GymForTheMoment")
        self.root.geometry("1280x720")

        # Frames de la aplicación
        self.menu_frame = ctk.CTkFrame(self.root, width=240, corner_radius=0, fg_color="#1a1a2e")
        self.menu_frame.pack(side="left", fill="y")

        self.content_frame = ctk.CTkFrame(self.root, fg_color="#16213e")
        self.content_frame.pack(side="right", fill="both", expand=True)

        # Instanciar frames funcionales (se pasan parent y callback de volver)
        self.frame_clientes = FrameClientes(self.content_frame, self.mostrar_menu)
        self.frame_aparatos = FrameAparatos(self.content_frame, self.mostrar_menu)
        self.frame_reservas = FrameReservas(self.content_frame, self.mostrar_menu)
        self.frame_recibos = FrameRecibos(self.content_frame, self.mostrar_menu)
        self.frame_reportes = FrameReportes(self.content_frame, self.mostrar_menu)

        self._crear_menu()
        self.mostrar_menu()

    def _crear_menu(self):
        # Título con gradiente visual
        titulo = ctk.CTkLabel(
            self.menu_frame, 
            text="GymForTheMoment", 
            font=("Helvetica", 20, "bold"),
            text_color="#00d4ff"
        )
        titulo.pack(pady=25)

        # Botones del menú con colores distintos y emojis
        btn1 = ctk.CTkButton(
            self.menu_frame, 
            text="👥 Gestión de Clientes", 
            command=self.mostrar_clientes,
            fg_color="#e74c3c",
            hover_color="#c0392b",
            height=45,
            font=("Helvetica", 13, "bold")
        )
        btn1.pack(fill="x", padx=15, pady=8)

        btn2 = ctk.CTkButton(
            self.menu_frame, 
            text="💪 Gestión de Aparatos", 
            command=self.mostrar_aparatos,
            fg_color="#3498db",
            hover_color="#2980b9",
            height=45,
            font=("Helvetica", 13, "bold")
        )
        btn2.pack(fill="x", padx=15, pady=8)

        btn3 = ctk.CTkButton(
            self.menu_frame, 
            text="📅 Gestión de Reservas", 
            command=self.mostrar_reservas,
            fg_color="#2ecc71",
            hover_color="#27ae60",
            height=45,
            font=("Helvetica", 13, "bold")
        )
        btn3.pack(fill="x", padx=15, pady=8)

        btn4 = ctk.CTkButton(
            self.menu_frame, 
            text="💰 Gestión de Recibos", 
            command=self.mostrar_recibos,
            fg_color="#f39c12",
            hover_color="#e67e22",
            height=45,
            font=("Helvetica", 13, "bold")
        )
        btn4.pack(fill="x", padx=15, pady=8)

        btn5 = ctk.CTkButton(
            self.menu_frame, 
            text="📊 Reportes", 
            command=self.mostrar_reportes,
            fg_color="#9b59b6",
            hover_color="#8e44ad",
            height=45,
            font=("Helvetica", 13, "bold")
        )
        btn5.pack(fill="x", padx=15, pady=8)

        # Información en la parte inferior
        info = ctk.CTkLabel(
            self.menu_frame, 
            text="v2.0 • Sistema Completo",
            font=("Helvetica", 10),
            text_color="#7f8c8d"
        )
        info.pack(side="bottom", pady=20)

    def ocultar_contenido(self):
        for widget in self.content_frame.winfo_children():
            widget.pack_forget()

    def mostrar_menu(self):
        self.ocultar_contenido()
        panel = ctk.CTkFrame(self.content_frame, fg_color="#16213e")
        panel.pack(fill="both", expand=True, padx=20, pady=20)
        
        label = ctk.CTkLabel(
            panel, 
            text="Bienvenido a GymForTheMoment", 
            font=("Helvetica", 28, "bold"),
            text_color="#00d4ff"
        )
        label.pack(pady=30)
        
        subt = ctk.CTkLabel(
            panel, 
            text="Seleccione una opción en el menú lateral para comenzar", 
            font=("Helvetica", 16),
            text_color="#95a5a6"
        )
        subt.pack(pady=10)

    def mostrar_clientes(self):
        self.ocultar_contenido()
        self.frame_clientes.pack(fill="both", expand=True)

    def mostrar_aparatos(self):
        self.ocultar_contenido()
        self.frame_aparatos.pack(fill="both", expand=True)

    def mostrar_reservas(self):
        self.ocultar_contenido()
        self.frame_reservas.pack(fill="both", expand=True)

    def mostrar_recibos(self):
        self.ocultar_contenido()
        self.frame_recibos.pack(fill="both", expand=True)

    def mostrar_reportes(self):
        self.ocultar_contenido()
        self.frame_reportes.pack(fill="both", expand=True)

    def run(self):
        self.root.mainloop()
