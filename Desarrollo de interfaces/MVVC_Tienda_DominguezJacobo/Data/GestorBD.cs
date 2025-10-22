using System;
using System.Collections.Generic;
using System.Text;
using System.Data;
using MVVC_Tienda_DominguezJacobo.Models;
using System.Data.SqlClient;

namespace MVVC_Tienda_DominguezJacobo.Data
{
    // Clase sencilla para manejar la conexión con SQL Server
    // y realizar las consultas básicas (solo lectura de momento)
    public class GestorBD
    {
        // Cadena de conexión 
        private string cadenaConexion = @"Server=LAPTOP-HHK0M7PT\SQLEXPRESS;Database=TiendaMVVC;Trusted_Connection=True;";

        // Método que obtiene todos los productos de la base de datos
        public List<Producto> ObtenerProductos()
        {
            List<Producto> lista = new List<Producto>();

            // Creamos la conexión y el comando
            using (SqlConnection conexion = new SqlConnection(cadenaConexion))
            {
                string query = "SELECT Id, Nombre, Categoria, Precio, Stock, Imagen, Descripcion FROM Productos";

                SqlCommand comando = new SqlCommand(query, conexion);

                try
                {
                    conexion.Open();
                    SqlDataReader lector = comando.ExecuteReader();

                    // Recorremos cada fila del resultado y la convertimos en un objeto Producto
                    while (lector.Read())
                    {
                        Producto p = new Producto
                        {
                            Id = lector.GetInt32(0),
                            Nombre = lector.GetString(1),
                            Categoria = lector.IsDBNull(2) ? "" : lector.GetString(2),
                            Precio = lector.GetDecimal(3),
                            Stock = lector.IsDBNull(4) ? 0 : lector.GetInt32(4),
                            Imagen = lector.IsDBNull(5) ? "" : lector.GetString(5),
                            Descripcion = lector.IsDBNull(6) ? "" : lector.GetString(6)
                        };

                        lista.Add(p);
                    }

                    lector.Close();
                }
                catch (Exception ex)
                {
                    // Mostramos error en consola o log
                    Console.WriteLine("Error al obtener productos: " + ex.Message);
                }
            }

            return lista;
        }

        // Guarda los productos del carrito en la tabla Pedidos

        // Método para registrar las compras en la base de datos.
        // Guarda el nombre del producto y la fecha actual en la tabla Pedidos.
        public void RegistrarCompra(List<string> carrito)
        {
            using (SqlConnection conexion = new SqlConnection(cadenaConexion))
            {
                conexion.Open();

                foreach (string producto in carrito)
                {
                    string query = "INSERT INTO Pedidos (Producto) VALUES (@nombre)";
                    SqlCommand cmd = new SqlCommand(query, conexion);
                    cmd.Parameters.AddWithValue("@nombre", producto);
                    cmd.ExecuteNonQuery();
                }
            }
        }

        // -------------------------------------------------------------
        // CRUD básico de usuarios
        // -------------------------------------------------------------
        // Usa SqlConnection y SqlCommand para ejecutar consultas
        // INSERT, SELECT, UPDATE y DELETE sobre la tabla Usuarios.

        public void InsertarUsuario(Usuario u)
        {
            using var con = new SqlConnection(cadenaConexion);
            string sql = "INSERT INTO Usuarios (Nombre, Apellidos, Email, Contrasena, Rol) VALUES (@n, @a, @e, @c, @r)";
            var cmd = new SqlCommand(sql, con);
            cmd.Parameters.AddWithValue("@n", u.Nombre);
            cmd.Parameters.AddWithValue("@a", u.Apellidos);
            cmd.Parameters.AddWithValue("@e", u.Email);
            cmd.Parameters.AddWithValue("@c", u.Contrasena);
            cmd.Parameters.AddWithValue("@r", u.Rol);
            con.Open();
            cmd.ExecuteNonQuery();
        }

        // Leer todos los usuarios
        public List<Usuario> ObtenerUsuarios()
        {
            var lista = new List<Usuario>();
            using var con = new SqlConnection(cadenaConexion);
            var cmd = new SqlCommand("SELECT Id, Nombre, Apellidos, Email, Contrasena, Rol FROM Usuarios", con);
            con.Open();
            var rd = cmd.ExecuteReader();

            while (rd.Read())
            {
                lista.Add(new Usuario
                {
                    Id = rd.GetInt32(0),
                    Nombre = rd.GetString(1),
                    Apellidos = rd.GetString(2),
                    Email = rd.GetString(3),
                    Contrasena = rd.GetString(4),
                    Rol = rd.GetString(5)
                });
            }
            return lista;
        }

        // Eliminar usuario por id
        public void EliminarUsuario(int id)
        {
            using var con = new SqlConnection(cadenaConexion);
            var cmd = new SqlCommand("DELETE FROM Usuarios WHERE Id=@id", con);
            cmd.Parameters.AddWithValue("@id", id);
            con.Open();
            cmd.ExecuteNonQuery();
        }

        // Actualizar usuario
        public void ActualizarUsuario(Usuario u)
        {
            using var con = new SqlConnection(cadenaConexion);
            string sql = "UPDATE Usuarios SET Nombre=@n, Apellidos=@a, Email=@e, Contrasena=@c, Rol=@r WHERE Id=@id";
            var cmd = new SqlCommand(sql, con);
            cmd.Parameters.AddWithValue("@n", u.Nombre);
            cmd.Parameters.AddWithValue("@a", u.Apellidos);
            cmd.Parameters.AddWithValue("@e", u.Email);
            cmd.Parameters.AddWithValue("@c", u.Contrasena);
            cmd.Parameters.AddWithValue("@r", u.Rol);
            cmd.Parameters.AddWithValue("@id", u.Id);
            con.Open();
            cmd.ExecuteNonQuery();
        }
    }
}