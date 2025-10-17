public class Usuario
{
    public string Nombre {  get; set; }
    public int Edad {  get; set; }
    public string Dni {  get; set; }

    public Usuario(string  nombre, int edad, string dni)
    {
        Nombre = nombre;
        Edad = edad;
        Dni = dni;
    }
}