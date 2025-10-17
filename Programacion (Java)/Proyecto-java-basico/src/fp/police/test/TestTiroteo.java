package fp.police.test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import fp.common.Genero;
import fp.common.Race;
import fp.common.Report;
import fp.police.Tiroteo;

public class TestTiroteo {

	public static void main(String[] args) {
		// Comprobacion del funcionamiento del tipo auxiliar Report
		System.out.println("Test del tipo auxiliar");
		Report r1 = new Report("shoot", "rifle");
		System.out.println(r1);
		
		// Creamos y mostramos 2 tiroteos 
		LocalDate f1 = LocalDate.of(2022, 12, 1);
		Tiroteo s1 = new Tiroteo(10000, "Walter White", f1, "shoot", 61, "Phoenix", "AZ");
		
		LocalDate f2 = LocalDate.of(2023, 1, 12);
		Tiroteo s2 = new Tiroteo(7777, "John Smith", f2, "shoot", "rifle", 12, Genero.M, Race.B, 
				"New York", "NY", "True", "atack", "Car", "True", r1);
		
		System.out.println("\nTest de muestreo de 2 tiroteos");
		System.out.println(s1);
		System.out.println(s2);
		
		// Comprobacion del tipo lista
		System.out.println("\nTest del tipo lista");
		System.out.println(s1.getCiudadEstado());
		System.out.println(s2.getCiudadEstado());
						
		//Test de la propiedad derivada 1
		System.out.println("\nTest Propiedad derivada rango de edad");
		//Comprobacion de la propiedad derivada 1
		System.out.println(s1.getRangoEdad());
		System.out.println(s2.getRangoEdad());
		
		//Test del Equals
		System.out.println("\nTest equals");
		// Creamos varios tiroteos para probar el equals.
		Tiroteo s3 = new Tiroteo(6000, "John Smith", f2, "shoot", "rifle", 50, Genero.M, Race.B, 
				"New York", "NY", "True", "atack", "Car", "True", r1);
		Tiroteo s5 = new Tiroteo(6001, "Louis Litt", f2, "shoot", "rifle", 50, Genero.M, Race.B, 
				"New York", "NY", "True", "atack", "Car", "True", r1);
		Tiroteo s4 = s3;
		
		// Probamos los equals
		System.out.println("�Tiroteos 1 y 2 son iguales?");
		System.out.println(s1==s2);
		System.out.println(s1.equals(s2));
		// Creamos dos clientes con la misma informacion para comprobar el equals
		System.out.println("�Tiroteos 3 y 4 son iguales?");
		System.out.println(s3.equals(s4));
		System.out.println(s4==s3);
		// Como se espera sale en pantalla que son identicos
		
		
		// Test CompareTo
		System.out.println("\nTest compareTo");
		// Comprobamos el funcionamiento del compareTo
		System.out.println("Comparamos el tiroteo s2 con el s3: " + s2.compareTo(s3));
		System.out.println("Comparamos el tiroteo s3 con el s4: " + s4.compareTo(s3));

		// Comprobacion de una ordenacion de una lista
		System.out.println("\nLista ordenada por fecha y titulo");
		List<Tiroteo> ListaTiroteos = new ArrayList<>();
		ListaTiroteos.add(s1);
		ListaTiroteos.add(s2);
		ListaTiroteos.add(s3);
		ListaTiroteos.add(s5);
		Collections.sort(ListaTiroteos);
		System.out.println(ListaTiroteos);
	}

}
