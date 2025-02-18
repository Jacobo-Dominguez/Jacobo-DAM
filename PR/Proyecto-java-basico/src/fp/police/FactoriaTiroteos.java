package fp.police;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import fp.common.Genero;
import fp.common.Race;
import fp.utiles.Ficheros;

public class FactoriaTiroteos {
	public static List<Tiroteo> leeDeFichero(String ruta){
		List<Tiroteo> ls = new ArrayList<>();
		List<String> lineas = Ficheros.leeFichero("ERROR", ruta);
		//Eliminamos la primera linea de la lista
		lineas.remove(0);
		for(String linea:lineas) {
			Tiroteo c = parse(linea);
			ls.add(c);
		}
		return ls;
	}
	
	public static Tiroteos leeDeFicheroStream(String ruta) {
		Tiroteos res = null;
		try {
			Stream<Tiroteo> tiroteos = Files.lines(Paths.get(ruta))
					.skip(1)
					.map(FactoriaTiroteos::parse);
			res = new Tiroteos(tiroteos);
		}catch(IOException e) {
			System.out.println("Fichero no encontrado: " + ruta);
			e.printStackTrace();
		}return res;
	}
	

	private static Tiroteo parse(String linea) {
		// "id;name;date;death;armed;age;gender;race;city;state;mental_illness;threat;flee;body_camera
		// 3,Tim Elliot,2015-01-02,shot,gun,53,M,A,Shelton,WA,True,attack,Not fleeing,False
		String [] partes = linea.split(",");
//		if(partes.length!=14) {
//			throw new IllegalArgumentException("Formato: \"id,name,date,death,armed," 
//							+ "age,gender,race,city,state,mental_illness,threat,flee,body_camera\"");
//		}
		
		Integer id = Integer.parseInt(partes[0].trim());
		String name = partes[1].trim();
		LocalDate date = LocalDate.parse(partes[2].trim(), DateTimeFormatter.ofPattern("yyyy-MM-dd"));
		String death = partes[3].trim();
		String armed = partes[4].trim();
		Integer age = parseAge(partes[5].trim());
		Genero gender = parseGenero(partes[6].trim());
		Race race = parseRace(partes[7].trim());
		String city = partes[8].trim();
		String state = partes[9].trim();
		String mental_illness = partes[10].trim();
		String threat = partes[11].trim();
		String flee = partes[12].trim();
		String body_camera = partes[13].trim();
		
		
		Tiroteo s = new Tiroteo(id, name, date, death, armed, age, gender, 
				race, city, state, mental_illness, threat, flee, body_camera);
		return s;
	}
	
	private static Genero parseGenero(String txt) {
		Genero letra = null;
		if (txt.isEmpty() || txt==null) {
			letra = Genero.None;
			
		}else {
			letra = Genero.valueOf(txt.toUpperCase());
		}
		return letra;
	}
	
	private static Race parseRace(String txt) {
		Race letra = null;
		if (txt.isEmpty() || txt==null) {
			letra = Race.None;
			
		}else {
			letra = Race.valueOf(txt.toUpperCase());
		}
		return letra;
	}
	
	private static Integer parseAge(String txt) {
		Integer numero = null;
		if (txt.isEmpty() || txt==null) {
			numero = 0;
			
		}else {
			numero = Integer.parseInt(txt);
		}
		return numero;
	}
	
	
	/**********test**********/
	public static void main(String[] args) {
		List<Tiroteo> ls = leeDeFichero("./data/fatal-police-shootings-data.csv");
		for(Tiroteo s:ls){
			System.out.println(s);
		}
	}

}
