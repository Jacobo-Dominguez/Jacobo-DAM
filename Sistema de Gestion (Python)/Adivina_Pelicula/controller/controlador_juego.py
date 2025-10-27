from model.jugador import Jugador
from model.pelicula import Pelicula
from model.base_datos import BaseDatos
import json
import random

class ControladorJuego:
    def __init__(self, vista):
        self.vista = vista
        self.bd = BaseDatos()
        self.jugador = None
        self.peliculas = [
            Pelicula("Origen", [
                "Los sueños son un terreno donde todo es posible, pero nada es real.",
                "Un ladrón invade la mente ajena para plantar ideas imposibles.",
                "La línea entre la vigilia y el sueño se vuelve borrosa.",
                "Cada capa que cae es un riesgo de no despertar jamás.",
                "Un trompo gira y decide si lo que ves es verdad o ilusión."
            ]),
            Pelicula("Matrix", [
                "La realidad no siempre es lo que parece.",
                "Una elección entre seguir dormido o descubrir la verdad.",
                "Un guía ofrece una píldora que lo cambia todo.",
                "El destino y el libre albedrío se enfrentan en un mundo digital.",
                "Cuando ves al hombre de negro, quizá ya sea demasiado tarde para despertar."
            ]),
            Pelicula("John Wick", [
                "Un pasado violento llama a la puerta de una vida tranquila.",
                "Una deuda con el dolor desata la tormenta.",
                "Cuando todo lo que amas desaparece, solo queda la venganza.",
                "Nadie sobrevive cuando un fantasma vuelve al trabajo.",
                "Un traje negro, un perro perdido y una furia imparable."
            ]),
            Pelicula("Pulp Fiction", [
                "Las historias se cruzan en una danza sangrienta y absurda.",
                "Dos hombres de traje conversan antes de hacer algo imperdonable.",
                "Un maletín brilla con un misterio que nunca se explica.",
                "Una cena, un baile y una sobredosis que casi lo arruina todo.",
                "El azar conecta a pecadores, santos y asesinos con humor retorcido."
            ]),
            Pelicula("Gladiator", [
                "Un guerrero cae en desgracia y encuentra propósito en la arena.",
                "El rugido de la multitud se mezcla con el eco de la venganza.",
                "Un imperio corrupto teme al hombre que perdió todo.",
                "La gloria no está en la victoria, sino en la redención.",
                "El honor sobrevive más allá de la muerte."
            ]),
            Pelicula("The Batman", [
                "Una ciudad empapada por la lluvia esconde más secretos de los que confiesa.",
                "La venganza observa desde las sombras, vestida de negro.",
                "Un enigma tras otro lleva al corazón de la corrupción.",
                "No todos los héroes son faros de esperanza.",
                "Cuando cae la noche, la justicia deja de ser ciega."
            ]),
            Pelicula("Blade Runner", [
                "Un cazador persigue aquello que no debería tener alma.",
                "La lluvia nunca cesa en la ciudad donde la humanidad se diluye.",
                "Las máquinas lloran por lo que jamás podrán sentir.",
                "En un mundo de neones, la moral se oxida como el metal.",
                "Un último suspiro bajo la lluvia habla de sueños y muerte."
            ]),
            Pelicula("Terminator", [
                "Del futuro llega aquello que no conoce la compasión.",
                "El destino de la humanidad depende de una madre y su hijo.",
                "El tiempo no tiene piedad cuando la máquina ya está en marcha.",
                "No se detiene, no siente, y nunca deja de perseguir.",
                "Entre acero y sangre, el futuro está escrito en el pasado."
            ]),
            Pelicula("El club de la lucha", [
                "El futuro de la humanidad depende de un viaje entre las estrellas.",
                "Un amor que trasciende la gravedad y el tiempo guía la misión.",
                "Mundos desconocidos esconden esperanza y peligro a partes iguales.",
                "La relatividad no es solo teoría, sino un enemigo silencioso.",
                "Entre agujeros de gusano y planetas extraños, la supervivencia es la meta."
            ]),
            Pelicula("Jurassic Park", [
                "Un paraíso tropical esconde secretos que no deberían despertar.",
                "La ciencia juega a ser Dios, y la naturaleza no olvida.",
                "Criaturas prehistóricas vuelven a caminar, y nadie está a salvo.",
                "Cuando el control falla, el instinto domina.",
                "Un parque temático donde los depredadores tienen la ventaja."
            ])
        ]

    def guardar_peliculas_en_bd(self):
        for pelicula in self.peliculas:
            pistas_json = json.dumps(pelicula.pistas)
            self.bd.guardar_pelicula(pelicula.titulo, pistas_json)

    def iniciar_juego(self, nombre):
        self.jugador = Jugador(nombre)
        self.pelicula_actual = random.choice(self.peliculas)

        # Mostrar la pista inicial sin restar puntos
        pista0 = self.pelicula_actual.pista_inicial()
        self.vista.mostrar_pista(pista0, self.jugador.puntos)
        self.vista.mostrar_mensaje("¡Comienza el juego!")

    def pedir_pista(self):
        if self.jugador and self.pelicula_actual:
            pista = self.pelicula_actual.obtener_pista()
            if pista is None:
                self.vista.mostrar_mensaje("No hay más pistas disponibles para esta película.")
                return
            penalizacion = 3 + (self.pelicula_actual.indice_pista - 2) * 2  # Ajuste por índice
            self.jugador.restar_puntos(penalizacion)
            self.vista.mostrar_pista(pista, self.jugador.puntos)

    def comprobar_respuesta(self, respuesta):
        if respuesta.lower().strip() == self.pelicula_actual.titulo.lower():
            self.bd.guardar_puntuacion(self.jugador.nombre, self.jugador.puntos)
            self.vista.mostrar_mensaje(f"¡Correcto! Has ganado {self.jugador.puntos} puntos.")
            # Habilitar botón y refrescar la vista
            self.vista.habilitar_siguiente(True)
            self.vista.ventana.update()  # Forzar refresco visual
        else:
            self.vista.mostrar_mensaje("No es correcto. Intenta otra vez.")

    def siguiente_pelicula(self):
        """Carga una nueva película y reinicia los puntos."""
        self.pelicula_actual = random.choice(self.peliculas)
        self.jugador.puntos = 30  # Reiniciar puntos
        self.pelicula_actual.indice_pista = 1  # Empieza en 1 porque la pista 0 se muestra gratis
        # Limpiar log de pistas y mostrar la pista inicial sin penalización
        self.vista.limpiar_pistas()
        pista_inicial = self.pelicula_actual.pista_inicial()
        self.vista.mostrar_pista(pista_inicial, self.jugador.puntos)
        # Desactivar botón hasta que vuelva a acertar
        self.vista.habilitar_siguiente(False)



