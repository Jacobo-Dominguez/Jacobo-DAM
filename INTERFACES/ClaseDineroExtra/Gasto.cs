public class Gasto : Dinero
{
    public Gasto(double cantidad, string descripcion) : base(cantidad, descripcion)
    {
    }
    public override string ToString()
    {
        return $"Gasto: {Descripcion}, Cantidad: {Cantidad}";
    }
}