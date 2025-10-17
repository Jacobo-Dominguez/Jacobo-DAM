from dinero import Dinero

class Gasto(Dinero):
    def __init__(self, gasto: float, description: str):
        super().__init__(gasto, description)

    def __str__(self):
        return f"Gasto: {self.dinero} € - {self.description}"
