import tkinter as tk
from tkinter import messagebox

# HE USADO EL MISMO ESTILO QUE EN EL PROYECTO DE 6 HORAS (COCTELES) POR ESO PARECE SE NOTA QUE LE PASE A LA IA PARA QUE ME LO CAMBIARA
# LA ANTERIOR VERSION ESTA EN OTRO ARCHIVO (vista_antigua.py) QUE ERA UNA VENTANA MUY BASICA Y GENERICA

class VistaJuego:
    def __init__(self, controlador):
        self.controlador = controlador
        self.ventana = tk.Tk()
        self.ventana.title("🎬 Adivina la Película o Serie")
        self.ventana.geometry("900x600")
        self.ventana.configure(bg="#222831")

        # --- Encabezado ---
        encabezado = tk.Frame(self.ventana, bg="#222831", height=80)
        encabezado.pack(fill="x")

        # Logo (opcional)
        logo_label = tk.Label(encabezado, text="🎬", font=("Helvetica", 36), bg="#222831", fg="#FFD369")
        logo_label.pack(side="left", padx=20)

        # Título
        tk.Label(encabezado, text="ADIVINA LA PELÍCULA", font=("Helvetica", 26, "bold"),
                 bg="#222831", fg="#FFD369").pack(side="left", padx=10)

        # --- Contenedor principal ---
        contenedor = tk.Frame(self.ventana, bg="#1c1c1c")
        contenedor.pack(fill="both", expand=True, padx=20, pady=20)

        # --- Panel superior: nombre de jugador ---
        frame_nombre = tk.Frame(contenedor, bg="#1c1c1c")
        frame_nombre.pack(pady=10)
        tk.Label(frame_nombre, text="Nombre del jugador:", font=("Helvetica", 12, "bold"),
                 fg="white", bg="#1c1c1c").pack(side="left", padx=5)
        self.entry_nombre = tk.Entry(frame_nombre, font=("Helvetica", 12))
        self.entry_nombre.pack(side="left", padx=5)
        self.btn_iniciar = tk.Button(frame_nombre, text="Iniciar Juego", font=("Helvetica", 12),
                                     bg="#FFD369", fg="#222831", command=self.iniciar_juego)
        self.btn_iniciar.pack(side="left", padx=10)

        # --- Panel de pista ---
        self.lbl_pista = tk.Label(contenedor, text="", font=("Helvetica", 14, "italic"),
                                  fg="#FFD369", bg="#1c1c1c", wraplength=800, justify="center")
        self.lbl_pista.pack(pady=20)

        # --- Área de pistas (movida dentro del contenedor) ---
        self.lbl_pistas = tk.Label(contenedor, text="📜 Pistas obtenidas:", font=("Helvetica", 12, "bold"),
                                   bg="#1c1c1c", fg="#FFD369")
        self.lbl_pistas.pack(pady=(0, 5))

        self.txt_pistas = tk.Text(contenedor, height=8, width=60, bg="#FFFFFF", fg="#222831", wrap="word")
        self.txt_pistas.pack(pady=(0, 10))
        self.txt_pistas.config(state="disabled")

        # --- Botón de pedir pista justo debajo ---
        self.btn_pista = tk.Button(contenedor, text="Pedir Pista (- pts)", font=("Helvetica", 12),
                                   bg="#00ccff", fg="white", command=self.pedir_pista)
        self.btn_pista.pack(pady=5)

        # --- Panel de respuesta ---
        frame_respuesta = tk.Frame(contenedor, bg="#1c1c1c")
        frame_respuesta.pack(pady=20)
        self.entry_respuesta = tk.Entry(frame_respuesta, font=("Helvetica", 12), width=30)
        self.entry_respuesta.pack(side="left", padx=5)
        self.btn_responder = tk.Button(frame_respuesta, text="Comprobar", font=("Helvetica", 12),
                                       bg="#00cc66", fg="white", command=self.comprobar_respuesta)
        self.btn_responder.pack(side="left", padx=5)

        # --- Botón siguiente ---
        self.btn_siguiente = tk.Button(contenedor, text="Siguiente Película", font=("Helvetica", 12),
                                       bg="#ff6666", fg="white", command=self.siguiente_pelicula, state="disabled")
        self.btn_siguiente.pack(pady=20)

    # ----------------- Métodos de interacción -----------------
    def iniciar_juego(self):
        nombre = self.entry_nombre.get().strip()
        if nombre:
            self.controlador.iniciar_juego(nombre)
        else:
            messagebox.showwarning("Aviso", "Introduce tu nombre antes de jugar.")

    def pedir_pista(self):
        self.controlador.pedir_pista()

    def comprobar_respuesta(self):
        respuesta = self.entry_respuesta.get()
        self.controlador.comprobar_respuesta(respuesta)

    def siguiente_pelicula(self):
        self.controlador.siguiente_pelicula()
        self.btn_siguiente.config(state="disabled")  # Desactivar hasta que vuelva a acertar

    def habilitar_siguiente(self, habilitar):
        estado = "normal" if habilitar else "disabled"
        self.btn_siguiente.config(state=estado)

    # ----------------- Métodos para mostrar info -----------------
    def mostrar_pista(self, pista, puntos_restantes):
        """Muestra una nueva pista y la agrega al historial."""
        self.txt_pistas.config(state="normal")
        self.txt_pistas.insert(tk.END, f"• {pista}\n")
        self.txt_pistas.insert(tk.END, f"(Puntos actuales: {puntos_restantes})\n\n")
        self.txt_pistas.config(state="disabled")
        self.txt_pistas.see(tk.END)  # Hacer scroll automático hacia abajo

    def limpiar_pistas(self):
        """Borra el historial de pistas al iniciar una nueva película."""
        self.txt_pistas.config(state="normal")
        self.txt_pistas.delete("1.0", tk.END)
        self.txt_pistas.config(state="disabled")


    def mostrar_mensaje(self, texto):
        messagebox.showinfo("Información", texto)

    def iniciar(self):
        self.ventana.mainloop()