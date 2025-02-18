package fp.police.test;

import java.util.*;
import fp.police.Tiroteo;
import fp.police.FactoriaTiroteos;

public class TestFactoriaTiroteos {

	public static void main(String[] args) {
		testCreacionTiroteos("data/fatal-police-shootings-data.csv");

	}

	private static void testCreacionTiroteos(String filename) {
		System.out.println("\nTEST de la creacion de tiroteos");
		try {
			List<Tiroteo> tiroteo = FactoriaTiroteos.leeDeFichero(filename);
			System.out.println("   TIROTEOS: "+ tiroteo);
		} catch(Exception e) {
			System.out.println("Excepción inesperada capturada:\n   " + e);	
		}
		
	}
	
	

}
