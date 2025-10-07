public class Producto
{
    public string Nombre { get; set; }
    public double Precio { get; set; }
    public string Enlace { get; set; }

    public Producto(string nombre, double precio, string enlace)
    {
        Nombre = nombre;
        Precio = precio;
        Enlace = enlace;
    }

    public override string ToString()
    {
        return $"Producto: {Nombre}, Precio: {Precio} €, Enlace: {Enlace}";
    }
}