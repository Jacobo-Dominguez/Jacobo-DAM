from dinero import Dinero

class Ingreso(Dinero):
    def __init__(self, ingreso: float, description: str):
        super().__init__(ingreso, description)

    def __str__(self):
        return f"Ingreso: {self.dinero} € - {self.description}"
