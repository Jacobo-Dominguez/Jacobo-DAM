using System.Text;
using System.Windows;
using System.Windows.Controls;
using System.Windows.Data;
using System.Windows.Documents;
using System.Windows.Input;
using System.Windows.Media;
using System.Windows.Media.Imaging;
using System.Windows.Navigation;
using System.Windows.Shapes;

namespace InterfazWPF
{
    public partial class MainWindow : Window
    {
        private readonly string placeholderText = "Buscar...";
        private List<string> carrito = new List<string>();

        public MainWindow()
        {
            InitializeComponent();
            //SetPlaceholder();
        }

        private void SetPlaceholder()
        {
            /* txtBuscador.Text = placeholderText;
            txtBuscador.Foreground = Brushes.Gray; */
        }
        
        private void TxtBuscador_GotFocus(object sender, RoutedEventArgs e)
        {
            /* if (txtBuscador.Text == placeholderText)
            {
                txtBuscador.Text = "";
                txtBuscador.Foreground = Brushes.Black;
            } */
        }

        private void TxtBuscador_LostFocus(object sender, RoutedEventArgs e)
        {
           /*  if (string.IsNullOrWhiteSpace(txtBuscador.Text))
                SetPlaceholder(); */
        }
        
        private void Buscar_Click(object sender, RoutedEventArgs e)
        {
            /* string busqueda = txtBuscador.Text;
            if (busqueda == placeholderText || string.IsNullOrWhiteSpace(busqueda))
            {
                MessageBox.Show("Introduce un término de búsqueda.", "Buscar", MessageBoxButton.OK, MessageBoxImage.Information);
                return;
            }
            MessageBox.Show($"Buscando: {busqueda}", "Buscar"); */
        }
    

        private void AbrirCarrito_Click(object sender, RoutedEventArgs e)
        {
            if (carrito.Count == 0)
            {
                MessageBox.Show("El carrito está vacío.", "Carrito");
            }
            else
            {
                StringBuilder sb = new StringBuilder();
                sb.AppendLine("Productos en el carrito:");
                foreach (var nombre in carrito)
                {
                    sb.AppendLine($"- {nombre}");
                }
                sb.AppendLine($"\nTotal de artículos: {carrito.Count}");
                MessageBox.Show(sb.ToString(), "Carrito");
            }
        }

        private void AgregarProductoDestacado_Click(object sender, RoutedEventArgs e)
        {
            string nombre = "MSI GeForce RTX 5090";
            carrito.Add(nombre);
            MessageBox.Show($"{nombre} agregado al carrito.", "Carrito");
        }

        private void AgregarProductoSecundario_Click(object sender, RoutedEventArgs e)
        {
            // El sender debe ser el botón que se ha pulsado.
            // Lo convertimos a Button para poder acceder a su Parent.
            Button btn = sender as Button;
            if (btn != null)
            {
                // El botón está dentro de un StackPanel que contiene varios elementos.
                StackPanel panel = btn.Parent as StackPanel;
                if (panel != null)
                {
                    // Guardamos el nombre del producto que encontremos.
                    string nombre = "";

                    // Recorremos los hijos del StackPanel buscando el primer TextBlock.
                    // El primer TextBlock lo consideramos el nombre del producto.
                    foreach (var child in panel.Children)
                    {
                        if (child is TextBlock tb)
                        {
                            if (string.IsNullOrEmpty(nombre))
                            {
                                // Guardamos el texto del TextBlock como nombre
                                nombre = tb.Text;
                                break;
                            }
                        }
                    }

                    // Si hemos conseguido extraer un nombre válido, lo añadimos al carrito.
                    if (!string.IsNullOrEmpty(nombre))
                    {
                        carrito.Add(nombre);
                        MessageBox.Show($"{nombre} agregado al carrito.", "Carrito");
                        return;
                    }
                }
            }
        }
    }
}