public class Cuenta
{
    public Usuario Usuario { get; set; }
    public double Saldo { get; set; }
    public List<Gasto> ListaGastos { get; set; } = new List<Gasto>();
    public List<Ingreso> ListaIngresos { get; set;} = new List<Ingreso>();
    

    public Cuenta(Usuario usuario)
    {
        Usuario = usuario;
        Saldo = 0.0;
    }

    public void AddGastoBasico(double cantidad, string descripcion)
    {
        var gasto = new GastoBasico(cantidad, descripcion, DateTime.Now);
        ListaGastos.Add(gasto);
        Saldo -= cantidad;
    }

    public void AddGastoExtra(double cantidad, string descripcion, bool prescindible)
    {
        var gasto = new GastoExtra(cantidad, descripcion, DateTime.Now, prescindible);
        ListaGastos.Add(gasto);
        Saldo -= cantidad;
    }

    public void AddIngreso(double cantidad, string description)
    {
        var Ingreso = new Ingreso(cantidad, description, DateTime.Now);
        ListaIngresos.Add(Ingreso);
        Saldo += cantidad;
    }

    public List<GastoBasico> GetGastosBasicos(bool esteMes)
    {
        return ListaGastos.OfType<GastoBasico>()
            .Where(g => !esteMes || g.Fecha.Month == DateTime.Now.Month && g.Fecha.Year == DateTime.Now.Year)
            .ToList();
    }

    public List<GastoExtra> GetGastosExtras(bool esteMes)
    {
        return ListaGastos.OfType<GastoExtra>()
            .Where(g => !esteMes || g.Fecha.Month == DateTime.Now.Month && g.Fecha.Year == DateTime.Now.Year)
            .ToList();
    }

    public double GetAhorro(DateTime desde, DateTime hasta)
    {
        var ingresos = ListaIngresos.Where(i => i.Fecha >= desde && i.Fecha <= hasta).Sum(i => i.Cantidad);
        var gastos = ListaGastos.Where(g =>
            (g is GastoBasico gb && gb.Fecha >= desde && gb.Fecha <= hasta) ||
            (g is GastoExtra ge && ge.Fecha >= desde && ge.Fecha <= hasta)
        ).Sum(g => g.Cantidad);
        return ingresos - gastos;
    }

    public double GetGastosImprescindibles(DateTime desde, DateTime hasta)
    {
        var gastosBasicos = ListaGastos.OfType<GastoBasico>().Where(g => g.Fecha >= desde 
        && g.Fecha <= hasta).Sum(g => g.Cantidad);
        var gastosExtrasNoPrescindibles = ListaGastos.OfType<GastoExtra>().Where(g => g.Fecha >= desde 
        && g.Fecha <= hasta && !g.Prescindible).Sum(g => g.Cantidad);
        return gastosBasicos + gastosExtrasNoPrescindibles;
    }

    public double GetPosiblesAhorrosMesPasado()
    {
        var mesPasado = DateTime.Now.AddMonths(-1);
        return ListaGastos.OfType<GastoExtra>()
            .Where(g => g.Fecha.Month == mesPasado.Month && g.Fecha.Year == mesPasado.Year && g.Prescindible)
            .Sum(g => g.Cantidad);
    }

    public override string ToString()
    {
        return $"Cuenta: {Saldo} €";
    }


}
