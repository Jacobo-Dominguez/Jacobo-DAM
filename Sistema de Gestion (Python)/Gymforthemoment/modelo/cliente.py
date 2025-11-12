class Cliente:
    def __init__(self, id_cliente=None, nombre="", apellidos="", dni="", telefono="", email="", ha_pagado=False):
        self.id_cliente = id_cliente
        self.nombre = nombre
        self.apellidos = apellidos
        self.dni = dni
        self.telefono = telefono
        self.email = email
        self.ha_pagado = ha_pagado

    def __str__(self):
        return f"{self.nombre} {self.apellidos} ({'Pagado' if self.ha_pagado else 'Moroso'})"
