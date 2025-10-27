from model.jugador import Jugador
from model.pelicula import Pelicula
from model.base_datos import BaseDatos
import random

class ControladorJuego:
    def __init__(self, vista):
        self.vista = vista
        self.bd = BaseDatos()
        self.jugador = None
        self.peliculas = [
            Pelicula("The Dark Knight", [
                "Año: 2008",
                "Director: Christopher Nolan",
                "Actor principal: Christian Bale",
                "Género: Superhéroes / Crimen / Drama",
                "Una ciudad teme a quien lleva sonrisa pintada"
            ]),
            Pelicula("Matrix", [
                "Año: 1999",
                "Director: Hermanas Wachowski",
                "Actor principal: Keanu Reeves",
                "Género: Ciencia ficción / Acción / Filosofía",
                "Nada es real cuando vives dormido y conectado"
            ]),
            Pelicula("John Wick", [
                "Año: 2014",
                "Director: Chad Stahelski",
                "Actor principal: Keanu Reeves",
                "Género: Acción / Venganza / Crimen",
                "La paz termina con el ladrido del pasado"
            ]),
            Pelicula("Pulp Fiction", [
                "Año: 1994",
                "Director: Quentin Tarantino",
                "Actor principal: John Travolta",
                "Género: Crimen / Humor negro / Culto",
                "Una historia desordenada con demasiados coincidencias fatales"
            ]),
            Pelicula("Gladiator", [
                "Año: 2000",
                "Director: Ridley Scott",
                "Actor principal: Russell Crowe",
                "Género: Histórico / Acción / Drama",
                "La arena del coliseo guarda la última batalla por honor"
            ]),
            Pelicula("The Batman", [
                "Año: 2022",
                "Director: Matt Reeves",
                "Actor principal: Robert Pattinson",
                "Género: Superhéroes / Misterio / Thriller",
                "La justicia se esconde entre acertijos y sombras"
            ]),
            Pelicula("Blade Runner", [
                "Año: 1982",
                "Director: Ridley Scott",
                "Actor principal: Harrison Ford",
                "Género: Ciencia ficción / Neo-noir / Culto",
                "¿Sueñan los androides con ovejas eléctricas?"
            ]),
            Pelicula("The Terminator", [
                "Año: 1984",
                "Director: James Cameron",
                "Actor principal: Arnold Schwarzenegger",
                "Género: Ciencia ficción / Acción / Distopía",
                "Una máquina sin emociones viaja por el tiempo"
            ]),
            Pelicula("Fight Club", [
                "Año: 1999",
                "Director: David Fincher",
                "Actor principal: Edward Norton",
                "Género: Psicológico / Drama / Culto",
                "La primera regla es no hablar de ello"
            ]),
            Pelicula("Mad Max: Fury Road", [
                "Año: 2015",
                "Director: George Miller",
                "Actor principal: Tom Hardy",
                "Género: Acción / Postapocalíptico / Aventura",
                "El desierto ruge con motores y redención"
            ])
        ]

    def iniciar_juego(self, nombre):
        self.jugador = Jugador(nombre)
        self.pelicula_actual = random.choice(self.peliculas)

        # Mostrar la pista inicial sin restar puntos
        pista0 = self.pelicula_actual.pista_inicial()
        self.vista.mostrar_pista(pista0, self.jugador.puntos)
        self.vista.mostrar_mensaje("¡Comienza el juego!")

    def pedir_pista(self):
        if self.jugador and self.pelicula_actual:
            pista = self.pelicula_actual.obtener_pista()  # Aquí ya avanza el índice
            penalizacion = 3 + (self.pelicula_actual.indice_pista - 1) * 2  # 3,5,7,9,11
            self.jugador.restar_puntos(penalizacion)
            self.vista.mostrar_pista(pista, self.jugador.puntos)

    def comprobar_respuesta(self, respuesta):
        if respuesta.lower() == self.pelicula_actual.titulo.lower():
            self.vista.mostrar_mensaje(f"¡Correcto! Has acertado con {self.jugador.puntos} puntos")
            self.bd.guardar_puntuacion(self.jugador.nombre, self.jugador.puntos)

            # Habilitar botón “Siguiente”
            self.vista.btn_siguiente.config(state="normal")
        else:
            self.vista.mostrar_mensaje("No es correcto. Intenta otra vez.")

    def siguiente_pelicula(self):
        # Elegir otra película aleatoria
        self.pelicula_actual = random.choice(self.peliculas)

        # Reiniciar índice de pistas
        self.pelicula_actual.indice_pista = 1

        # Mostrar pista inicial sin restar puntos
        pista0 = self.pelicula_actual.pista_inicial()
        self.vista.mostrar_pista(pista0, self.jugador.puntos)

        # Deshabilitar botón siguiente hasta que acierte de nuevo
        self.vista.btn_siguiente.config(state="disabled")

        self.vista.mostrar_mensaje("¡Nueva película! Aquí tienes la primera pista.")
