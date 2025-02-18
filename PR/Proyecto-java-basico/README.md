# Proyecto del Segundo Cuatrimestre Fundamentos de Programación (Curso  22/23)
Autor/a: Jacobo Luis Dominguez Morales   uvus: jacdommor

El proyecto tiene como objetivo analizar los datos de los tiroteos realizados por policias en Estados Unidos
publicados en el dataset de Kaggle que se puede descargar de la siguiente URL 
(https://www.kaggle.com/datasets/mrmorj/data-police-shootings). El dataset original tiene 14 columnas.  

## Estructura de las carpetas del proyecto

* **/src**: Contiene los diferentes archivos que forman parte del proyecto. Debe estar estructurado en los siguentes paquetes
  * **fp.police**: Paquete que contiene los tipos del proyecto.
  * **fp.police.test**: Paquete que contiene las clases de test del proyecto.
  * **fp.common**: Paquete que contiene los tipos auxiliares del proyecto
  * **fp.utiles**:  Paquete que contiene las clases de utilidad. 
* **/data**: Contiene el dataset o datasets del proyecto
    * **fatal-police-shootings-data.csv**: Añade una descripción genérica del dataset.
    
    
## Estructura del *dataset*

Aquí debes describir la estructura del dataset explicando qué representan los datos que contiene y la descripción de cada una de las columnas. Incluye también la URL del dataset original.

El dataset está compuesto por 14 columnas, con la siguiente descripción:

* **id**: de tipo entero, representa la identificacion de cada tiroteo.
* **name**: de tipo cadena, representa el nombre del autor del tiroteo.
* **date**: de tipo fecha, representa la fecha del tiroteo. 
* **manner_of_death**: de tipo cadena, representa como murió la persona que mató el policia en el tiroteo.
* **armed**: de tipo cadena, representa el arma utilizada.
* **age**: de tipo entero, representa la edad de la persona que fue disparada.
* **gender**: de tipo enumerado, representa el genero de la persona que fue disparada. 
* **race**: de tipo enumerado, representa la raza de la persona disparada.
* **city**: de tipo cadena, representa la ciudad donde ocurrio el tiroteo. 
* **state**: de tipo cadena, representa el estado de Estados Unidos donde ocurrio el tiroteo.
* **signs_of_mental_illness**: de tipo cadena, representa si la persona disparada tenia signos de una enfermedad mental.
* **threat_level**: de tipo cadena, representa el nivel de amenaza de la persona que fue disparada.
		de familia que puedan ser posibles contagios. 
* **flee**: de tipo cadena, representa si la persona que realizo el tiroteo realizo una huida.
* **body_camera**: de tipo cadena, representa si el policia tenia colocada una camara corporal que grabara parte o totalidad del tiroteo.


## Tipos implementados

Los tipos que se han implementado en el proyecto son los siguientes:

### Tipo Base - Shootings
Representa una recopilacion de datos de tiroteos realizados en Estados Unidos por la policia.

**Propiedades**:

- id: de tipo Integer, representa la identificacion de cada tiroteo. 
- name: de tipo String, representa el nombre del autor del tiroteo. 
- date: de tipo LocalDate, representa la fecha del tiroteo.
- death: de tipo String, representa como murió la persona que mató el policia en el tiroteo.
- armed: de tipo String, representa el arma utilizada.
- age: de tipo String, representa la edad de la persona que fue disparada.
- gender: de tipo Genero, representa el genero de la persona que fue disparada.
- race: de tipo Race, representa la raza de la persona disparada.
- city: de tipo String, representa la ciudad donde ocurrio el tiroteo. 
- state: de tipo String, representa el estado de Estados Unidos donde ocurrio el tiroteo.
- mental_illness: de tipo String, representa si la persona disparada tenia signos de una enfermedad mental.
- threat: de tipo String, representa el nivel de amenaza de la persona que fue disparada.
- flee: de tipo String, representa si la persona que realizo el tiroteo realizo una huida.
- body_camera: de tipo String, representa si el policia tenia colocada una camara corporal que grabara parte o totalidad del tiroteo.
- shootReport: de tipo Report, representa si el reporte de la muerte y arma realizada y usada respectivamente.

**Constructores**: 

- C1: Tiene un parámetro por cada propiedad básica del tipo.
- C2: Tiene un parámetro por las propiedades básicas: id, name, date, death, age, city, state del tipo.
- C3: Tiene un parámetro por cada propiedad básica del tipo salvo para shootReport.

**Restricciones**:
 
- R1: La fecha del tiroteo no puede ser posterior a la fecha actual.
- R2: La edad no puede ser menor que 0.

- 
**Criterio de igualdad**: Dos tiroteos son iguales si lo son su edad, genero, nombre y raza.

**Criterio de ordenación**: Se ordenan los tiroteos por fecha del tiroteo y por nombre.

**Otras operaciones**:
 
-	rangoEdad: Segun la propiedad edad, describimos varios rangos entre los que estan: menos, joven, adulto, anciano.
- List<String> getCiudadEstado(): Creamos una lista con el par ciudad y estado donde se ha realizado el tiroteo.

#### Tipos auxiliares

- Genero, enumerado puede tomar los valores M, F, None. Donde M: Male F: Female None: unknown
- Race, enumerado que puedo tomar los valores W, B, A, N, H, O, None. Donde W: White, non-Hispanic B: Black, non-Hispanic A: Asian N: Native American H: Hispanic 
  O: Other None: unknown
- RangoEdad, enumrado puede tomar los valores Menor, Joven, Adulto, Anciano;
 

### Factoría
Clase de factoría para construir objetos de tipo FactoriaTiroteos.

- _método 1_: List\<Shootings\> leeDeFichero(String ruta): Crea un objeto de tipo Shootings a partir de la información recogida en el archivo csv, cuya ruta se da como parámetro.

### Tipo Contenedor

Clase contenedora de los objetos de tipo Shootings.

**Propiedades**:

- tiroteos, de tipo List\<Shootings\>, consultable. Lista de tiroteos. 
- numeroTiroteos, de tipo \<Integer\>, Numero de tiroteos del contenedor. 

**Constructores**: 

- C1: Constructor por defecto: Crea un objeto de tipo Shootings sin ningun tiroteo almacenado.

**Criterio de igualdad**: Dos tiroteos son iguales si lo son sus propiedades tiroteos.

**Criterio de ordenación**: Describir el criterio de ordenación (si lo hay).

**Otras operaciones**:

Entrega 2

-	int obtenerNumeroTiroteos(): Añadimos una operacion para obtener el numero de elementos.
- void anyadeTiroteo(Shootings s): Operacion para añadir un elemento a la lista.
- void anyadeTiroteos(List<Shootings>tiroteos): Añadimos una operacion para añadir una coleccion de elementos.
- boolean contieneTiroteo(Shootings s): Añadimos una operacion para ver si la lista de tiroteos contiene uno que recibe  por parametro.
- void eliminarTiroteo(Shootings s): Añadimos una operacion que usa la anterior para eliminar el tiroteo que no existe.

- Boolean existeTiroteoEnEstado(String state): Operacion realizada con bucles que devuelve true si existe un tiroteo con el pais 
  dado  como parametro.
- Set<Integer> obtenerIdDeEstados(String country): Operacion realizada con bucles que devuelve un conjunto con las id's de los tiroteos de un pais dado como parametro.
- List<Shootings> obtenerTiroteoCiudad(String ciudad): Operacion realizada con bucles que obtiene un conjunto con los tiroteos cuya ciudad es Nueva York.
- Map<String, List<Shootings>> agruparTiroteosPorEstado(): Operacion realizada con bucles que devuelve un Map que asocia a cada estado una lista con los objetos de tipo Shootings de ese estado.
-Map<String , Integer> getNumeroTiroteosCiudad(): Un método de acumulación que devuelve un Map en el que las claves sean una propiedad del tipo base, y los valores el conteo o la suma de los objetos del tipo base almacenados en el contenedor que tienen como valor esa propiedad.

Entrega 3

Bloque I

- Boolean existeTiroteoEnEstadoStream(String state): Operacion realizada con streams que devuelve true si existe un tiroteo con el pais 
  dado  como parametro.
- Set<Integer> obtenerIdDeEstadosStream(String country): Operacion realizada con streams que devuelve un conjunto con las id's de los tiroteos de un pais dado como parametro.
- Collection<Shootings> obtenerTiroteoCiudadStream(String ciudad): Operacion realizada con streams que obtiene un conjunto con los tiroteos cuya ciudad es Nueva York.
- List<Tiroteo> obtenerTiroteoIdMasAlto(): Operacion realizada con streams que devuelve el objeto de tipo Shootings con el id mas alto.
- Tiroteo obtenerPersonaMayorEdadCiudad(String ciudad): Seleccion con filtrado y ordenacion

Bloque II

- Map<String, List<Tiroteo>> agruparTiroteosPorEstadoStream(): Usando streams devuelve un Map que asocia a cada estado una lista con los
		objetos de tipo Shootings de ese estado.
- Map<String, Integer> calcularEdadSuperiorDe(Integer minimo): Un método en cuya implementación se use, o bien el Collector collectingAndThen, o bien el Collector mapping.
- Map<String, Set<Integer>> calcularPersonaMayorEdadPorEstado(): Un método que devuelva un Map en el que las claves sean un atributo o una función sobre un atributo, y los valores son máximos/mínimos de los elementos que tienen ese valor.
- SortedMap<String, List<Tiroteo>> calcularNMayoresIdsPorCiudad(Integer n):  Devuelve un SortedMap que asocia a cada país las N mayores id's de esa ciudad.
- Map.Entry<String , Set<Integer>> maximaEdadEnEstado(): Devuelve la clave con el valor asociado (mayor o menor) de todo el map.
