public class GastoExtra : Gasto
{
    public DateTime Fecha {  get; set; }
    public bool Prescindible { get; set; }

    public GastoExtra(double cantidad, string descripcion, DateTime fecha, bool prescindible) : base(cantidad, descripcion)
    {
        Fecha = fecha;
        Prescindible = prescindible;
    }

    public override string ToString()
    {
        return $"Gasto Extra: {Cantidad}, Descripcion: {Descripcion} ({Fecha}), Prescindible: {Prescindible}";
    }

}