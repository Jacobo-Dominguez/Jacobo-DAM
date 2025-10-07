public class GastoBasico : Gasto
{   
    public DateTime Fecha  { get; set; }

    public GastoBasico(double cantidad, string descripcion, DateTime fecha) : base(cantidad, descripcion)
    {
        Fecha = fecha;
    }
    public override string ToString()
    {
        return $"Gasto Básico: {Cantidad}, Descripcion: {Descripcion} ({Fecha})";
    }
}
