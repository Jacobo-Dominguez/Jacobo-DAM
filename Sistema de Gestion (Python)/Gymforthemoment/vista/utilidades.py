def centrar_ventana(ventana, ancho=400, alto=300):
    """
    Centra una ventana Toplevel en la pantalla.

    :param ventana: instancia de tk.Toplevel
    :param ancho: ancho deseado de la ventana
    :param alto: alto deseado de la ventana
    """
    ventana.update_idletasks()  # Actualiza dimensiones internas
    screen_width = ventana.winfo_screenwidth()
    screen_height = ventana.winfo_screenheight()

    x = (screen_width // 2) - (ancho // 2)
    y = (screen_height // 2) - (alto // 2)

    ventana.geometry(f"{ancho}x{alto}+{x}+{y}")
