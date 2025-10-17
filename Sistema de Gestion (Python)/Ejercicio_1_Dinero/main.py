from usuario import Usuario
from cuenta import Cuenta
from gasto_exception import GastoException

def main():
    print("=== Bienvenido al sistema bancario ===")

    # Crear usuario
    nombre = input("Introduce el nombre: ")
    while True:
        try:
            edad = int(input("Introduce la edad: "))
            if edad <= 0:
                print("La edad debe ser un número positivo.")
                continue
            break
        except ValueError:
            print("Edad no válida. Debe ser un número entero.")

    usuario = Usuario(nombre, edad)

    while True:
        dni = input("Introduce el DNI: ")
        try:
            usuario.dni = dni
            break
        except ValueError as e:
            print(e)

    # Crear cuenta
    cuenta = Cuenta(usuario)

    # Menú principal
    while True:
        print("\n=== Menú Principal ===")
        print("1. Añadir ingreso")
        print("2. Añadir gasto")
        print("3. Mostrar ingresos")
        print("4. Mostrar gastos")
        print("5. Mostrar cuenta")
        print("6. Salir")

        opcion = input("Elige una opción: ")

        if opcion == "1":
            desc = input("Descripción del ingreso: ")
            while True:
                try:
                    cant = float(input("Cantidad: "))
                    cuenta.add_ingresos(desc, cant)
                    print(f"Ingreso añadido. Saldo actual: {cuenta.saldo} €")
                    break
                except ValueError as e:
                    print(e)

        elif opcion == "2":
            desc = input("Descripción del gasto: ")
            while True:
                try:
                    cant = float(input("Cantidad: "))
                    cuenta.add_gastos(desc, cant)
                    print(f"Gasto añadido. Saldo actual: {cuenta.saldo} €")
                    break
                except (GastoException, ValueError) as e:
                    print(e)

        elif opcion == "3":
            print("\n=== Lista de Ingresos ===")
            for ingreso in cuenta.ingresos:
                print(ingreso)

        elif opcion == "4":
            print("\n=== Lista de Gastos ===")
            for gasto in cuenta.gastos:
                print(gasto)

        elif opcion == "5":
            print("\n=== Información de la Cuenta ===")
            print(cuenta)

        elif opcion == "6":
            print("Fin del programa.\nGracias por utilizar la aplicación.")
            break
        else:
            print("Opción no válida, inténtalo de nuevo.")


if __name__ == "__main__":
    main()
