using MVVC_Tienda_DominguezJacobo.Data;
using MVVC_Tienda_DominguezJacobo.Models;
using System;
using System.Collections.ObjectModel;
using System.ComponentModel;


namespace MVVC_Tienda_DominguezJacobo.ViewModels
{
    // ViewModel que maneja la lista de productos
    // y la comunicación con la base de datos (GestorBD)
    public class ProductosViewModel : INotifyPropertyChanged
    {
        private ObservableCollection<Producto> _listaProductos;
        public ObservableCollection<Producto> ListaProductos
        {
            get { return _listaProductos; }
            set
            {
                _listaProductos = value;
                OnPropertyChanged(nameof(ListaProductos));
            }
        }

        // Copia interna de todos los productos (sin filtrar)
        private ObservableCollection<Producto> TodosLosProductos { get; set; }

        public ProductosViewModel()
        {
            // Cargamos los productos directamente desde la base de datos
            GestorBD gestor = new GestorBD();
            var productos = gestor.ObtenerProductos();

            TodosLosProductos = new ObservableCollection<Producto>(productos);
            ListaProductos = new ObservableCollection<Producto>(productos);
        }

        // Buscamos de forma sencilla con LINQ (por nombre)
        public void Filtrar(string termino)
        {
            if (string.IsNullOrWhiteSpace(termino))
            {
                ListaProductos = new ObservableCollection<Producto>(TodosLosProductos);
            }
            else
            {
                var filtrados = TodosLosProductos
                    .Where(p => p.Nombre.ToLower().Contains(termino.ToLower()))
                    .ToList();

                ListaProductos = new ObservableCollection<Producto>(filtrados);
            }
        }

        public event PropertyChangedEventHandler PropertyChanged;
        private void OnPropertyChanged(string propertyName)
        {
            PropertyChanged?.Invoke(this, new PropertyChangedEventArgs(propertyName));
        }
    }
}
