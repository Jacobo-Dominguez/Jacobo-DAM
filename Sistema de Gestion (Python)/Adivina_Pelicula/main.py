from controller.controlador_juego import ControladorJuego
from view.vista_juego import VistaJuego

if __name__ == "__main__":
    vista = VistaJuego(None)
    controlador = ControladorJuego(vista)
    vista.controlador = controlador
    vista.iniciar()
