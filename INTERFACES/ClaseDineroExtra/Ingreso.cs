public class Ingreso : Dinero
{
    public DateTime Fecha { get; set; }

    public Ingreso(double cantidad, string descripcion, DateTime fecha) : base(cantidad, descripcion)
    {
        Fecha = fecha;
    }
    public override string ToString()
    {
        return $"Ingreso: {Cantidad}, Descripcion: {Descripcion} ({Fecha})";
    }
}
