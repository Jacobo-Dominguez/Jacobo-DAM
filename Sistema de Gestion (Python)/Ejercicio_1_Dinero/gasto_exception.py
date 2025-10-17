class GastoException(Exception):
    def __init__(self, mensaje="Saldo insuficiente para realizar el gasto."):
        super().__init__(mensaje)
