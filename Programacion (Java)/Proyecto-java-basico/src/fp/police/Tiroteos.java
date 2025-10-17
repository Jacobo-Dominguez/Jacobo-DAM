package fp.police;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import fp.common.Genero;

public class Tiroteos {
	//Aqui añadimos las propiedades para poder crear el tipo contenedor
	private List<Tiroteo> tiroteos;
	
	//Aqui tenemos el constructor para poder crear el tipo contenedor
	public Tiroteos() {
		this.tiroteos = new ArrayList<>();
	}
	
	public Tiroteos(Stream<Tiroteo> stream) {
		this.tiroteos = stream.collect(Collectors.toList());
	}

	//Devolvemos una copia para que nadie pueda tocar la lista desde fuera de la clase
	public List<Tiroteo> getShootings(){
		return new ArrayList<>(this.tiroteos);
	}
	
	//Añadimos una operacion para obtener el numero de elementos
	public int obtenerNumeroTiroteos() {
		return this.tiroteos.size();
	}
	
	//Añadimos una operacion para añadir un elemento
	public void anyadeTiroteo(Tiroteo s) {
		this.tiroteos.add(s);
	}
	
	//Añadimos una operacion para añadir una coleccion de elementos
	public void anyadeTiroteos(List<Tiroteo>tiroteos) {
		this.tiroteos = new ArrayList<>(tiroteos);
		
	}
	
	//Añadimos una operacion para ver si la lista de tiroteos contiene uno pasado por parametro
	public boolean contieneTiroteo(Tiroteo s) {
		return this.tiroteos.contains(s);
	}
	
	//Añadimos una operacion que usa la anterior para eliminar el tiroteo que no existe
	public void eliminarTiroteo(Tiroteo s) {
		if(!this.contieneTiroteo(s)) {
			throw new IllegalArgumentException("El elemento a eliminar no existe");
		}
		this.tiroteos.remove(s);
	}
	
	public String toString() {
		String res = "";
		res +="\n--------------------------------\n";
		for(Tiroteo c:this.tiroteos) {
			res+=c+"\n";
		}
		return res;
	}

//	@Override
//	public String toString() {
//		return "TipoContenedor [shootings=" + tiroteos + "]";
//	}

	@Override
	public int hashCode() {
		return Objects.hash(tiroteos);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Tiroteos other = (Tiroteos) obj;
		return Objects.equals(tiroteos, other.tiroteos);
	}
	
	/********************** Entrega 2 ************************/
	
	/********************** Tratamientos secuenciales simples ************************/
	
	/********************** Ejercicio 1 (Existe) ************************/
	
	public Boolean existeTiroteoEnEstado(String state) {
		/*
		 Operacion realizada con bucles que devuelve true si existe un tiroteo con el pais 
  		 dado  como parametro.
		 */
		Boolean res = false;
		for(Tiroteo s : this.tiroteos) {
			if(s.getState().equals(state)) {
				res = true;
			}
		}
		return res;
	}
	
	/********************** Ejercicio 2 (Suma) ************************/
	
	public Set<Integer> obtenerIdDeEstados(String country){
		/*
		Devuelve un conjunto con las id's de los tiroteos de un pais dado como parametro.
		 */
		Set<Integer> ids = new HashSet<>();
		for(Tiroteo p:this.tiroteos) {
			if(p.getState().equals(country)) {
				ids.add(p.getId());
			}
		}
		return ids;
	}
	
	/********************** Ejercicio 3 (Filtrado) ************************/
	
	public List<Tiroteo> obtenerTiroteoCiudad(String ciudad) {
		//Obtiene un conjunto con los tiroteos cuya ciudad es Nueva York.
		List<Tiroteo> res = new ArrayList<>();
		for(Tiroteo s:this.tiroteos) {
			if(s.getCity().equals(ciudad)) {
				res.add(s);
			}
		}
		return res;
	}
	
	/********************** Ejercicio 4 (Agrupacion) ************************/
	
	public Map<String, List<Tiroteo>> agruparTiroteosPorEstado(){
		/*
		Devuelve un Map que asocia a cada estado una lista con los
		objetos de tipo Shootings de ese estado.
		 */
		Map<String, List<Tiroteo>> m = new HashMap<>();
		for(Tiroteo p : this.tiroteos) {
			String clave = p.getState();
			//Si la clave no esta en el diccionario...
			if(!m.containsKey(clave)) {
				//agregamos la clave y un valor asociado a dicha clave
				m.put(clave, new ArrayList<>());
			}
			//En cualquier caso agregamos el tiroteo que se esta procesando 
			//en la iteracion actual a la lista asociada a la clave
			m.get(clave).add(p);	
		}
		return m;
	}
	
	/********************** Ejercicio 5 (Acumulacion) ************************/
	
	public Map<String , Integer> getNumeroTiroteosCiudad(){
		Map<String, Integer> m = new HashMap<>();
		for(Tiroteo t: this.tiroteos) {
			String clave = t.getCity();
			if(!m.containsKey(clave)) {
				m.put(clave, 0);
			}
			Integer valor = m.get(clave);
			m.put(clave, valor+1);
		}
		return m;
	}
	
	
	
	/********************** Entrega 3 Bloque I ************************/
	
	/********************** Ejercicio 1 con Stream (Existe) ************************/
	
	public Boolean existeTiroteoEnEstadoStream(String state) {
		/*
		 Operacion realizada con stream que devuelve true si existe un tiroteo con el pais 
 		 dado  como parametro.
		 */
		return this.tiroteos.stream()
				.anyMatch(t -> t.getState().equals(state));
	}
	
	/********************** Ejercicio 2 con Stream (Suma) ************************/
	
	public Set<Integer> obtenerIdDeEstadosStream(String country){
		return this.tiroteos.stream()
				.filter(t -> t.getState().equals(country))
				.map(t -> t.getId()).collect(Collectors.toSet());
	}
	
	
	/********************** Ejercicio 3 con Stream (Filtrado) ************************/
	
	public Collection<Tiroteo> obtenerTiroteoCiudadStream(String ciudad){
		//Obtiene un conjunto con los tiroteos cuya ciudad es Nueva York. Con stream
		return this.tiroteos.stream()
				.filter(t -> t.getCity().equals(ciudad))
				.collect(Collectors.toCollection(HashSet::new));
	}
	
	
	/********************** Ejercicio 4 con Stream (Maximo) ************************/
	

	public List<Tiroteo> obtenerTiroteoIdMasAltoStream() {
		/*
		Devuelve el objeto de tipo Shootings con el id mas alto. Con stream
		 */
		return this.tiroteos.stream()
				.sorted(Comparator.comparing(Tiroteo::getId).reversed())
				.toList();
	}
	
	/********************** Ejercicio 5 con Stream (Seleccion, filtrado, ordenacion) ************************/
	
	public Tiroteo obtenerPersonaMayorEdadCiudad(String ciudad){
		// Seleccion con filtrado y ordenacion
		return this.tiroteos.stream()
				.filter(v -> v.getCity().equals(ciudad))
				.max(Comparator.comparing(Tiroteo::getAge))
				.orElseThrow(NoSuchElementException::new);
	}
	
	/********************** Entrega 3 Bloque II ************************/
	
	/********************** Ejercicio 1 con Stream (Agrupacion) ************************/
	
	public Map<String, List<Tiroteo>> agruparTiroteosPorEstadoStream(){
		/*
		Devuelve un Map que asocia a cada estado una lista con los
		objetos de tipo Shootings de ese estado.
		 */
		return this.tiroteos.stream()
				.collect(Collectors.groupingBy(Tiroteo::getState));
	}
	
	/********************** Ejercicio 2 con Stream (collectingAndThen) ************************/
	
	public Map<String, Integer> calcularEdadSuperiorDe(Integer minimo){
		//Un método en cuya implementación se use, o bien el Collector collectingAndThen, o bien el Collector mapping.
		return this.tiroteos.stream()
				.filter(t -> t.getAge() > minimo)
				.collect(Collectors.groupingBy(Tiroteo::getName,
						Collectors.collectingAndThen(Collectors.counting(), Long::intValue)));
	}
	
	/********************** Ejercicio 3 con Stream (Map valores maximos) ************************/
	
	public Map<String, Integer> calcularPersonaMayorEdadPorEstado(){
		/*
		 * Un método que devuelva un Map en el que las claves sean un atributo o 
		 * una función sobre un atributo, y los valores son máximos/mínimos 
		 * de los elementos que tienen ese valor.
		 */
		return this.tiroteos.stream()
				.collect(Collectors.groupingBy(Tiroteo::getState,
						Collectors.collectingAndThen(Collectors.maxBy(Comparator.comparingInt(Tiroteo::getAge)), t -> t.get().getAge())));
	}
	
	
	/********************** Ejercicio 4 con Stream (SortedMap) ************************/
	
	public SortedMap<String, List<Tiroteo>> calcularNMayoresIdsPorCiudad(Integer n){
	/*
	 Devuelve un SortedMap que asocia a cada país las N mayores id's de esa ciudad.
	 */

		return this.tiroteos.stream()
				.collect(Collectors.groupingBy(Tiroteo::getCity, TreeMap::new,
						Collectors.collectingAndThen(Collectors.toList(), t -> t.stream()
								.sorted(Comparator.comparingInt(Tiroteo::getId))
								.limit(n)
								.collect(Collectors.toList()))));
								
	
	}
	
	/********************** Ejercicio 5 con Stream (Map con clave con valor asociado) ************************/
	
	public Map.Entry<String , Integer> maximaEdadEnEstado(){
		// Devuelve la clave con el valor asociado (mayor o menor) de todo el map.
		return calcularPersonaMayorEdadPorEstado().entrySet().stream()
				.max(Map.Entry.comparingByValue())
				.orElse(null);
	}
	
	/********************** Ejercicio de la defensa ************************/
	
	// Genero de maximo de la edad media 
	
	public Map<Genero, Double> getEdadMedia(){
		return this.tiroteos.stream()
				.collect(Collectors.groupingBy(Tiroteo::getGender, Collectors.averagingDouble(Tiroteo::getAge)));
	}
	
	public Entry<Genero, Double> getGeneroMaximaEdad(){
		return getEdadMedia().entrySet().stream().max(Entry.comparingByValue()).orElse(null);
	}
	
}
