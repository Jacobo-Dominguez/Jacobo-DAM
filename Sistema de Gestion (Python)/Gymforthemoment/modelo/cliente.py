class Cliente:
    def __init__(self, id=None, nombre="", apellido="", email="", telefono="", moroso=False):
        self.id = id
        self.nombre = nombre
        self.apellido = apellido
        self.email = email
        self.telefono = telefono
        self.moroso = moroso

    def __repr__(self):
        return f"Cliente({self.id}, {self.nombre}, {self.apellido}, moroso={self.moroso})"

