from ingreso import Ingreso
from gasto import Gasto
from gasto_exception import GastoException
from usuario import Usuario

class Cuenta:
    def __init__(self, usuario):
        self._saldo = 0.0
        self._usuario = usuario
        self._gastos = []
        self._ingresos = []

    # Esto convierte el saldo en un atributo de solo lectura.
    @property
    def saldo(self) -> float:
        return self._saldo

    # Usando un .setter para controlar cómo se modifica el valor, permitiendo validarlo después
    @saldo.setter
    def saldo(self, value: float):
        self._saldo = value

    # Esto convierte el usuario en un atributo de solo lectura.
    @property
    def usuario(self) -> Usuario:
        return self._usuario

    # Usando un .setter para controlar cómo se modifica el valor, permitiendo validarlo después
    @usuario.setter
    def usuario(self, value: Usuario):
        self._usuario = value

    # Esto convierte los ingresos en un atributo de solo lectura.
    @property
    def ingresos(self):
        return self._ingresos

    # Esto convierte los gastos en un atributo de solo lectura.
    @property
    def gastos(self):
        return self._gastos

    def add_ingresos(self, description: str, cantidad: float) -> float:
        if cantidad < 0:
            raise ValueError("No se puede ingresar una cantidad negativa.")
        ingreso = Ingreso(cantidad, description)
        self._ingresos.append(ingreso)
        self._saldo += cantidad
        return self._saldo

    def add_gastos(self, description: str, cantidad: float) -> float:
        if cantidad < 0:
            raise ValueError("No se puede gastar una cantidad negativa.")
        if cantidad > self._saldo:
            raise GastoException()
        gasto = Gasto(cantidad, description)
        self._gastos.append(gasto)
        self._saldo -= cantidad
        return self._saldo

    def __str__(self):
        return f"{self._usuario}\nSaldo actual: {self._saldo} €"
