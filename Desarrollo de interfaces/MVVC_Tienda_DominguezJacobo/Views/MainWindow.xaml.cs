using MVVC_Tienda_DominguezJacobo.Models;
using MVVC_Tienda_DominguezJacobo.Data; 
using MVVC_Tienda_DominguezJacobo.ViewModels;
using System.Text;
using System.Windows;
using System.Windows.Controls;
using System;
using System.Collections.Generic;

namespace MVVC_Tienda_DominguezJacobo.Views
{
    public partial class MainWindow : Window
    {
        private readonly string placeholderText = "Buscar...";
        private List<string> carrito = new List<string>();

        public MainWindow()
        {
            InitializeComponent();
            DataContext = new ProductosViewModel();
        }    

        private void AgregarProductoDestacado_Click(object sender, RoutedEventArgs e)
        {
            carrito.Add("MSI GeForce RTX 5090");
            MessageBox.Show("MSI GeForce RTX 5090 añadido al carrito");
        }

        private void AgregarProductoSecundario_Click(object sender, RoutedEventArgs e)
        {
            if (sender is Button btn && btn.DataContext is Producto p)
            {
                carrito.Add(p.Nombre);
                MessageBox.Show($"{p.Nombre} añadido al carrito");
            }
        }

        private void AbrirCarrito_Click(object sender, RoutedEventArgs e)
        {
            if (!carrito.Any())
            {
                MessageBox.Show("El carrito está vacío");
                return;
            }

            // Guardar en la base de datos
            GestorBD gestor = new GestorBD();
            gestor.RegistrarCompra(carrito);

            StringBuilder sb = new StringBuilder("Productos en el carrito:\n");
            foreach (var item in carrito) sb.AppendLine($"- {item}");
            MessageBox.Show(sb.ToString());

            // Opcional: limpiar carrito después de guardar
            carrito.Clear();
        }

        private void Buscar_Click(object sender, RoutedEventArgs e)
        {
            string busqueda = txtBuscador.Text;
            if (busqueda == placeholderText || string.IsNullOrWhiteSpace(busqueda))
            {
                MessageBox.Show("Introduce un término de búsqueda.", "Buscar", MessageBoxButton.OK, MessageBoxImage.Information);
                return;
            }
            MessageBox.Show($"Buscando: {busqueda}", "Buscar"); 
        }
    }
}