from abc import ABC, abstractmethod

class Dinero(ABC):
    def __init__(self, cantidad: float, description: str):
        self._dinero = cantidad
        self._description = description

    #Esto convierte el dinero en un atributo de solo lectura.
    @property
    def dinero(self) -> float:
        return self._dinero

    # Usando un .setter para controlar cómo se modifica el valor, permitiendo validarlo después
    @dinero.setter
    def dinero(self, cantidad: float):
        if cantidad < 0:
            raise ValueError("El dinero no puede ser negativo.")
        self._dinero = cantidad

    # Esto convierte la description en un atributo de solo lectura.
    @property
    def description(self) -> str:
        return self._description

    # Usando un .setter para controlar cómo se modifica el valor, permitiendo validarlo después
    @description.setter
    def description(self, value: str):
        self._description = value

    @abstractmethod
    def __str__(self):
        pass
