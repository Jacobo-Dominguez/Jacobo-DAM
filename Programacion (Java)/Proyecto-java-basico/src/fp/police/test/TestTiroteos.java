package fp.police.test;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import fp.common.Genero;
import fp.common.Race;
import fp.police.FactoriaTiroteos;
import fp.police.Tiroteo;
import fp.police.Tiroteos;

public class TestTiroteos {
	
	public static void main(String[] args) {
		//Creamos un objeto del tipo contenedor 
		Tiroteos ts = new Tiroteos();
						
		//Comprobamos la lista vacia
		System.out.println("Lista de tiroteos vacia" + ts);
				
		//Creamos un tiroteo y lo añadimos a la lista vacia
		Tiroteo ts1 = new Tiroteo(6000, "John Carpenter", LocalDate.of(2023, 4, 2), "shoot", "rifle", 25, 
				Genero.M, Race.B, "New York", "NY", "True", "atack", "Car", "True", null);
						
		ts.anyadeTiroteo(ts1);
		//Comprobacion de que se ha añadido el tiroteo 1 a la lista
		System.out.println("\nLista con 1 solo tiroteo" + ts);
				
		//Añadimos un segundo tiroteo que tras comprobar que se ha añadido, lo eliminaremos de la lista
		Tiroteo ts2 = new Tiroteo(6001, "Charles Bateman", LocalDate.of(2023, 3, 2), "shoot", "rifle", 30, 
				Genero.M, Race.H, "Austin", "TX", "True", "atack", "Car", "True", null);
						
		ts.anyadeTiroteo(ts2);
		//Comprobacion de que se ha añadido el 2º tiroteo 
		System.out.println("\nLista con 2 tiroteos" + ts);
						
		ts.eliminarTiroteo(ts1);
		
		//Comprobacion de que se ha eliminado el tiroteo
		System.out.println("\nLista tras eliminar un tiroteo" + ts);
		
		
		//Usamos la factoria para leer el fichero y completar la lista
		List<Tiroteo>lps = FactoriaTiroteos.leeDeFichero("./data/fatal-police-shootings-data.csv");
		ts.anyadeTiroteos(lps);
		System.out.println("\nLista completa de tiroteos" + ts);
						
					
		//Probamos la operacion de contar el numero de elementos 
		System.out.println("\nComprobamos el numero de elementos de la lista de tiroteo");
		System.out.println(ts.obtenerNumeroTiroteos());
		
		
		/********************** Test de los tratamientos secuenciales ************************/
		
		/********************** Entrega 2 ************************/
		
		/********************** Ejercicio 1 (Existe) ************************/
				
		//Probamos la operacion con bucles para comprobar si existe algun tiroteo que el estado sea el dado.
		System.out.println("\nComprobamos la existencia de un tiroteo con una propiedad concreta");
		System.out.println("¿Hay algun tiroteo en el estado de Nueva York? " 
		+ ts.existeTiroteoEnEstado("NY"));
			
		/********************** Ejercicio 2 (Suma) ************************/
		
		//Devuelve un conjunto con las id's de los tiroteos de un pais dada como par�metro
		System.out.println("\nComprobamos el conjunto id's de tiroteos en base a un estado");
		System.out.println("Las id's de los tiroteos de ese estado son: " + "\n" +
		ts.obtenerIdDeEstados("NY"));
		
		/********************** Ejercicio 3 (Filtrado) ************************/
		
		//Obtiene un conjunto con los tiroteos cuya ciudad es Nueva York.
		System.out.println("\nComprobamos cual es el conjunto de tiroteos de una estado dado");
		System.out.println("El conjunto de tiroteos del estado de 'San Francisco' son " + "\n" 
		+ ts.obtenerTiroteoCiudad("San Francisco"));
		
		/********************** Ejercicio 4 (Agrupacion) ************************/
		
		//Devuelve un Map que asocia a cada estado una lista con los
		//objetos de tipo Shootings de ese estado
		System.out.println("\nComprobamos el conjunto de tiroteos en base a su estado");
		System.out.println("Los tiroteos por cada estado son: ");
		imprimeMap(ts.agruparTiroteosPorEstado());
		
		/********************** Ejercicio 5 (Acomulacion) ************************/
		
		//Un método de acumulación que devuelva un Map en el que las claves sean una propiedad del tipo base, 
		//y los valores el conteo o la suma de los objetos del tipo base almacenados en el contenedor que tienen como valor esa propiedad.
		System.out.println("\nComprobamos la agrupacion de tiroteos del estado");
		System.out.println("El numero de tiroteos en de los estado son" + "\n" + ts.getNumeroTiroteosCiudad());
		
		
		
		/********************** Entrega 3 Bloque I ************************/
		
		/********************** Ejercicio 1 con streams(Existe) ************************/
		
		//Probamos la operacion con streams para comprobar si existe algun tiroteo que el estado sea el dado.
		System.out.println("\nUsando streams comprobamos la existencia de un tiroteo con una propiedad concreta");
		System.out.println("¿Hay algun tiroteo en el estado de Nueva York? " 
		+ ts.existeTiroteoEnEstadoStream("NY"));
		
		/********************** Ejercicio 2 con streams (Suma) ************************/
		
		//Probamos la operacion con streams para que devuelva un conjunto con las id's de los tiroteos de un pais dada como par�metro
		System.out.println("\nUsando streams comprobamos el conjunto id's de tiroteos en base a un estado");
		System.out.println("Las id's de los tiroteos de ese estado son: " + "\n" +
		ts.obtenerIdDeEstadosStream("NY"));
		
		/********************** Ejercicio 3 con streams (Filtrado) ************************/
		
		//Probamos la operacion con streams para que obtenga un conjunto con los tiroteos cuya ciudad es Nueva York.
		System.out.println("\nUsando streams comprobamos cual es el conjunto de tiroteos de una estado dado");
		System.out.println("El conjunto de tiroteos del estado de 'San Francisco' son " + "\n" 
		+ ts.obtenerTiroteoCiudadStream("San Francisco"));
		
		/********************** Ejercicio 4 con streams (Maximo) ************************/
		
		//Probamos la operacion con streams para comprobar cual es el id mas alto al que pertenece un tiroteo.
		System.out.println("\nUsando streams comprobamos cual es el numero de id mas alto al que pertenece un tiroteo");
		System.out.println("El tiroteo que tiene el id mas alto es: " + "\n" 
		+ ts.obtenerTiroteoIdMasAltoStream());
		
		/********************** Ejercicio 5 con Stream (Seleccion, filtrado, ordenacion) ************************/
		System.out.println("\nComprobacion de la persona con mas edad de una ciudad");
		System.out.println("La persona con mas edad que ha realizon un tiroteo en la ciudad es: " + "\n" 
		+ ts.obtenerPersonaMayorEdadCiudad("New Orleans"));
		
		
		
		/********************** Entrega 3 Bloque II ************************/
		
		/********************** Ejercicio 1 con streams (Agrupacion) ************************/
		
		//Devuelve un Map que asocia a cada estado una lista con los
		//objetos de tipo Shootings de ese estado
		System.out.println("\nUsando streams comprobamos el conjunto de tiroteos en base a su estado");
		System.out.println("Los tiroteos por cada estado son: ");
		imprimeMap(ts.agruparTiroteosPorEstadoStream());
		
		/********************** Ejercicio 2 con streams (collectingAndThen) ************************/
		System.out.println("\nUsando streams comprobamos el metodo que usa collectingAndThen");
		System.out.println("Los nombres cuya edad supera la minima son: " + "\n" + ts.calcularEdadSuperiorDe(18));	
		
		/********************** Ejercicio 3 con Stream (Map valores maximos) ************************/
		System.out.println("\nUsando streams comprobamos el metodo Map con valores maximos");
		System.out.println("Personas con mas edad de cada estado " + "\n" + ts.calcularPersonaMayorEdadPorEstado());
		
		/********************** Ejercicio 4 con Stream (SortedMap) ************************/
		System.out.println("\nComprobamos que devuelve un SortedMap que asocia a cada país las N mayores id's de esa ciudad.");
		System.out.println("Los paises con con los n id mas altas son: " + "\n" + ts.calcularNMayoresIdsPorCiudad(5));
		
		/********************** Ejercicio 5 con Stream (Map con clave con valor asociado) ************************/
		System.out.println("\nComprobamos que devuelve la clave conm mayor valor asociado de todo el map");
		System.out.println("La edad maxima de todo el map es: " + "\n" + ts.maximaEdadEnEstado().getValue());
		
		/********************** Ejercicio defensa ************************/
		System.out.println("\nComprobamos que devuelve el Genero del maximo de las edades medias");
		System.out.println("El genero de la edad media maxima es: " + ts.getGeneroMaximaEdad());
		
	}
	
	private static <T1, T2> void imprimeMap(Map<T1,T2> m) {
		for(Entry<T1, T2> par:m.entrySet()) {
			System.out.println(par.getKey() + " -> " + par.getValue());
		}	
	}

}
