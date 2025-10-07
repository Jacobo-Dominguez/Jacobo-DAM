using System.Collections.Generic;
using System.Linq;

public class Wishlist
{
    public string Nombre { get; set; }
    public List<Producto> Productos { get; set; }
    public Usuario Usuario { get; set; }

    public Wishlist(string nombre, Usuario usuario)
    {
        Nombre = nombre;
        Usuario = usuario;
        Productos = new List<Producto>();
    }

    public void AddProducto(Producto producto)
    {
        Productos.Add(producto);
    }

    public List<Producto> GetProductosParaComprar(Cuenta cuenta, double gastosBasicosSiguienteMes)
    {
        return Productos.Where(p => cuenta.Saldo - p.Precio >= gastosBasicosSiguienteMes).ToList();
    }

    public override string ToString()
    {
        return $"Wishlist: {Nombre}, Usuario: {Usuario.Nombre}, Productos: {Productos.Count}";
    }
}