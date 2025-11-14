import customtkinter as ctk
from vista.frame_clientes import FrameClientes
from vista.frame_aparatos import FrameAparatos
from vista.frame_reservas import FrameReservas
from vista.frame_recibos import FrameRecibos

ctk.set_appearance_mode("dark")
ctk.set_default_color_theme("blue")

class InterfazGym:
    def __init__(self):
        self.root = ctk.CTk()
        self.root.title("GymForTheMoment")
        self.root.geometry("900x600")

        # Frames de la aplicación
        self.menu_frame = ctk.CTkFrame(self.root, width=220, corner_radius=0)
        self.menu_frame.pack(side="left", fill="y")

        self.content_frame = ctk.CTkFrame(self.root)
        self.content_frame.pack(side="right", fill="both", expand=True)

        # Instanciar frames funcionales (se pasan parent y callback de volver)
        self.frame_clientes = FrameClientes(self.content_frame, self.mostrar_menu)
        self.frame_aparatos = FrameAparatos(self.content_frame, self.mostrar_menu)
        self.frame_reservas = FrameReservas(self.content_frame, self.mostrar_menu)
        self.frame_recibos = FrameRecibos(self.content_frame, self.mostrar_menu)

        self._crear_menu()
        self.mostrar_menu()

    def _crear_menu(self):
        titulo = ctk.CTkLabel(self.menu_frame, text="GymForTheMoment", font=("Helvetica", 18))
        titulo.pack(pady=20)

        btn1 = ctk.CTkButton(self.menu_frame, text="Gestión de Clientes", command=self.mostrar_clientes)
        btn1.pack(fill="x", padx=10, pady=8)

        btn2 = ctk.CTkButton(self.menu_frame, text="Gestión de Aparatos", command=self.mostrar_aparatos)
        btn2.pack(fill="x", padx=10, pady=8)

        btn3 = ctk.CTkButton(self.menu_frame, text="Gestión de Reservas", command=self.mostrar_reservas)
        btn3.pack(fill="x", padx=10, pady=8)

        btn4 = ctk.CTkButton(self.menu_frame, text="Gestión de Recibos", command=self.mostrar_recibos)
        btn4.pack(fill="x", padx=10, pady=8)

        #info = ctk.CTkLabel(self.menu_frame, text="Versión 1.0.0\nGymForTheMoment", font=("Helvetica", 11))
        #info.pack(side="bottom", pady=20)

    def ocultar_contenido(self):
        for widget in self.content_frame.winfo_children():
            widget.pack_forget()

    def mostrar_menu(self):
        self.ocultar_contenido()
        panel = ctk.CTkFrame(self.content_frame)
        panel.pack(fill="both", expand=True, padx=20, pady=20)
        label = ctk.CTkLabel(panel, text="Bienvenido a GymForTheMoment", font=("Helvetica", 22))
        label.pack(pady=20)
        subt = ctk.CTkLabel(panel, text="Seleccione una opción en el menú izquierdo.", font=("Helvetica", 14))
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

    def run(self):
        self.root.mainloop()
