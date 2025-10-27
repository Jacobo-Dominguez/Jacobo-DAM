import tkinter as tk
from tkinter import messagebox


class VistaJuego:
    def __init__(self, root, controlador):
        self.root = root
        self.controlador = controlador
        self.root.title("🎬 Adivina la Película o Serie")

        self.lbl_nombre = tk.Label(root, text="Nombre del jugador:")
        self.lbl_nombre.pack()
        self.entry_nombre = tk.Entry(root)
        self.entry_nombre.pack()

        self.btn_iniciar = tk.Button(root, text="Iniciar Juego", command=self.iniciar_juego)
        self.btn_iniciar.pack(pady=10)

        self.lbl_pista = tk.Label(root, text="", font=("Arial", 12))
        self.lbl_pista.pack(pady=10)

        self.btn_pista = tk.Button(root, text="Pedir Pista (-5 pts)", command=self.pedir_pista)
        self.btn_pista.pack()

        self.entry_respuesta = tk.Entry(root)
        self.entry_respuesta.pack(pady=10)

        self.btn_responder = tk.Button(root, text="Comprobar", command=self.comprobar)
        self.btn_responder.pack()

        self.btn_siguiente = tk.Button(root, text="Siguiente", command=self.siguiente_pelicula, state="disabled")
        self.btn_siguiente.pack(pady=10)

    def iniciar_juego(self):
        nombre = self.entry_nombre.get().strip()
        if nombre:
            self.controlador.iniciar_juego(nombre)
        else:
            messagebox.showwarning("Aviso", "Introduce tu nombre antes de jugar.")

    def pedir_pista(self):
        self.controlador.pedir_pista()

    def comprobar(self):
        respuesta = self.entry_respuesta.get()
        self.controlador.comprobar_respuesta(respuesta)

    def mostrar_pista(self, pista, puntos):
        self.lbl_pista.config(text=f"Pista: {pista}\nPuntos restantes: {puntos}")

    def mostrar_mensaje(self, texto):
        messagebox.showinfo("Información", texto)


