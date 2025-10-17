import re

class Usuario:
    def __init__(self, nombre="", edad=0, dni=""):
        self._nombre = nombre
        self._edad = edad
        self._dni = None
        if dni:
            self.dni = dni

    # Esto convierte el nombre en un atributo de solo lectura.
    @property
    def nombre(self) -> str:
        return self._nombre

    # Usando un .setter para controlar cómo se modifica el valor, permitiendo validarlo después
    @nombre.setter
    def nombre(self, value: str):
        self._nombre = value

    # Esto convierte la edad en un atributo de solo lectura.
    @property
    def edad(self) -> int:
        return self._edad

    # Usando un .setter para controlar cómo se modifica el valor, permitiendo validarlo después
    @edad.setter
    def edad(self, value: int):
        self._edad = value

    # Esto convierte el dni en un atributo de solo lectura.
    @property
    def dni(self) -> str:
        return self._dni

    # Usando un .setter para controlar cómo se modifica el valor, permitiendo validarlo después
    @dni.setter
    def dni(self, value: str):
        patron = r"^[0-9]{8}[A-Za-z]$"
        if re.match(patron, value):
            self._dni = value
        else:
            raise ValueError("DNI incorrecto. Debe tener 8 dígitos y una letra final.")

    def __str__(self):
        return f"Usuario: {self._nombre}, Edad: {self._edad}, DNI: {self._dni}"
