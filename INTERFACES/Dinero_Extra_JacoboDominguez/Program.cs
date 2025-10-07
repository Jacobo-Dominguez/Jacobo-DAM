using System;
using System.Linq;

public class Program
{
    static void Main(string[] args)
    {
        Console.WriteLine("Introduce tu nombre");
        string nombre = Console.ReadLine();
        Console.WriteLine("Introduce tu edad");
        int edad = int.Parse(Console.ReadLine());
        string dni = "";
        while (!VerificarDni(dni))
        {
            Console.WriteLine("Introduce tu DNI: ");
            dni = Console.ReadLine();
        }
        Usuario usuario = new Usuario(nombre, edad, dni);
        Cuenta cuenta = new Cuenta(usuario);
        Wishlist wishlist = new Wishlist("Mi Wishlist", usuario);

        while (true)
        {
            Console.WriteLine("\n--- Menú ---");
            Console.WriteLine("1. Introduce un nuevo gasto básico");
            Console.WriteLine("2. Introduce un nuevo gasto extra");
            Console.WriteLine("3. Introduce un nuevo ingreso");
            Console.WriteLine("4. Mostrar gastos");
            Console.WriteLine("5. Mostrar ingresos");
            Console.WriteLine("6. Mostrar saldo");
            Console.WriteLine("7. Mostrar ahorro de un período");
            Console.WriteLine("8. Mostrar gastos imprescindibles");
            Console.WriteLine("9. Mostrar posibles ahorros del mes pasado");
            Console.WriteLine("10. Mostrar lista de deseos");
            Console.WriteLine("11. Mostrar productos que podemos comprar");
            Console.WriteLine("12. Añadir producto a la wishlist");
            Console.WriteLine("0. Salir");

            string opcion = Console.ReadLine();
            switch (opcion)
            {
                case "1":
                    Console.WriteLine("Introduce importe:");
                    double importeBasico = double.Parse(Console.ReadLine());
                    Console.WriteLine("Concepto:");
                    string conceptoBasico = Console.ReadLine();
                    cuenta.AddGastoBasico(importeBasico, conceptoBasico);
                    break;

                case "2":
                    Console.WriteLine("Introduce importe:");
                    double importeExtra = double.Parse(Console.ReadLine());
                    Console.WriteLine("Concepto:");
                    string conceptoExtra = Console.ReadLine();
                    Console.WriteLine("¿Es prescindible? (s/n):");
                    bool prescindible = Console.ReadLine().ToLower() == "s";
                    cuenta.AddGastoExtra(importeExtra, conceptoExtra, prescindible);
                    break;

                case "3":
                    Console.WriteLine("Introduce importe:");
                    double importeIngreso = double.Parse(Console.ReadLine());
                    Console.WriteLine("Concepto:");
                    string conceptoIngreso = Console.ReadLine();
                    cuenta.AddIngreso(importeIngreso, conceptoIngreso);
                    break;

                case "4":
                    Console.WriteLine("¿Qué gastos quieres ver? (1: Totales, 2: Básicos, 3: Extras):");
                    string tipoGasto = Console.ReadLine();
                    if (tipoGasto == "1")
                        foreach (var gasto in cuenta.ListaGastos) Console.WriteLine(gasto);
                    else if (tipoGasto == "2")
                        foreach (var gasto in cuenta.GetGastosBasicos(false)) Console.WriteLine(gasto);
                    else if (tipoGasto == "3")
                        foreach (var gasto in cuenta.GetGastosExtras(false)) Console.WriteLine(gasto);
                    break;

                case "5":
                    foreach (var ingreso in cuenta.ListaIngresos) Console.WriteLine(ingreso);
                    break;

                case "6":
                    Console.WriteLine(cuenta);
                    break;

                case "7":
                    Console.WriteLine("Introduce fecha inicio (yyyy-mm-dd):");
                    DateTime desde = DateTime.Parse(Console.ReadLine());
                    Console.WriteLine("Introduce fecha fin (yyyy-mm-dd):");
                    DateTime hasta = DateTime.Parse(Console.ReadLine());
                    Console.WriteLine($"Ahorro: {cuenta.GetAhorro(desde, hasta)} €");
                    break;

                case "8":
                    Console.WriteLine("Introduce fecha inicio (yyyy-mm-dd):");
                    DateTime desdeImp = DateTime.Parse(Console.ReadLine());
                    Console.WriteLine("Introduce fecha fin (yyyy-mm-dd):");
                    DateTime hastaImp = DateTime.Parse(Console.ReadLine());
                    Console.WriteLine($"Gastos imprescindibles: {cuenta.GetGastosImprescindibles(desdeImp, hastaImp)} €");
                    break;

                case "9":
                    Console.WriteLine($"Posibles ahorros del mes pasado: {cuenta.GetPosiblesAhorrosMesPasado()} €");
                    break;

                case "10":
                    foreach (var productos in wishlist.Productos)
                        Console.WriteLine(productos);
                    break;

                case "11":
                    double gastosBasicosSiguienteMes = cuenta.GetGastosBasicos(true).Sum(g => g.Cantidad);
                    var productosComprables = wishlist.GetProductosParaComprar(cuenta, gastosBasicosSiguienteMes);
                    foreach (var productos in productosComprables)
                        Console.WriteLine(productos);
                    break;

                case "12":
                    Console.WriteLine("Introduce el nombre del producto:");
                    string nombreProducto = Console.ReadLine();
                    Console.WriteLine("Introduce el precio del producto:");
                    double precioProducto = double.Parse(Console.ReadLine());
                    Console.WriteLine("Introduce el enlace del producto:");
                    string enlaceProducto = Console.ReadLine();
                    Producto producto = new Producto(nombreProducto, precioProducto, enlaceProducto);
                    wishlist.AddProducto(producto);
                    Console.WriteLine("Producto añadido a la wishlist.");
                    break;

                case "0":
                    Console.WriteLine("Fin del programa. Gracias por utilizar la aplicación.");
                    return;

                default:
                    Console.WriteLine("Opción inválida");
                    break;
            }
        }
    }

    static bool VerificarDni(string dni)
    {
        return System.Text.RegularExpressions.Regex.IsMatch(dni, @"^\d{8}-?[A-Za-z]$");
    }
}
