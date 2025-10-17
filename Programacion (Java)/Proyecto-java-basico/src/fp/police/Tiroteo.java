package fp.police;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import fp.common.Genero;
import fp.common.Race;
import fp.common.RangoEdad;
import fp.common.Report;
import fp.utiles.Checkers;

public class Tiroteo implements Comparable<Tiroteo>{
	
	private Integer id;
	private String name;
	private LocalDate date;
	private String death;
	private String armed;
	private Integer age;
	private Genero gender;
	private Race race;
	private String city;
	private String state;
	private String mental_illness;
	private String threat;
	private String flee;
	private String body_camera;
	private Report shootReport; // Tipo auxiliar 
	
	
	// Tipo Lista 
	
	public List<String> getCiudadEstado(){
		List<String> parCiudadEstado = new ArrayList<>();
		parCiudadEstado.add(city);
		parCiudadEstado.add(state);
		return parCiudadEstado;
	}
	
	// Propiedada derivada
	
	public RangoEdad getRangoEdad() {
		RangoEdad res;
		if (age<18) {
			res = RangoEdad.Menor;
		}else {
			if(age>=18 && age<=30) {
				res = RangoEdad.Joven;
			}else {
				if(age>30 && age<60) {
					res = RangoEdad.Adulto;
				}else {
					res = RangoEdad.Anciano;
					}
				}
			}
			return res;
		}

	// Constructores y restricciones

	public Tiroteo(Integer id, String name, LocalDate date, String death, Integer age, String city, String state) {
		Checkers.check("La fecha del tiroteo no puede ser posterior a la fecha actual", !date.isAfter(LocalDate.now()));
		Checkers.check("La edad no puede ser menor que 0", age>=0);
		this.id = id;
		this.name = name;
		this.date = date;
		this.death = death;
		this.age = age;
		this.city = city;
		this.state = state;
	}

	public Tiroteo(Integer id, String name, LocalDate date, String death, String armed, Integer age, Genero gender,
			Race race, String city, String state, String mental_illness, String threat, String flee, String body_camera,
			Report shootReport) {
		Checkers.check("La fecha del tiroteo no puede ser posterior a la fecha actual", !date.isAfter(LocalDate.now()));
		Checkers.check("La edad no puede ser menor que 0", age>=0);
		this.id = id;
		this.name = name;
		this.date = date;
		this.death = death;
		this.armed = armed;
		this.age = age;
		this.gender = gender;
		this.race = race;
		this.city = city;
		this.state = state;
		this.mental_illness = mental_illness;
		this.threat = threat;
		this.flee = flee;
		this.body_camera = body_camera;
		this.shootReport = shootReport;
	}
	
	

	public Tiroteo(Integer id, String name, LocalDate date, String death, String armed, Integer age, Genero gender,
			Race race, String city, String state, String mental_illness, String threat, String flee,
			String body_camera) {
		Checkers.check("La fecha del tiroteo no puede ser posterior a la fecha actual", !date.isAfter(LocalDate.now()));
		Checkers.check("La edad no puede ser menor que 0", age>=0);
		this.id = id;
		this.name = name;
		this.date = date;
		this.death = death;
		this.armed = armed;
		this.age = age;
		this.gender = gender;
		this.race = race;
		this.city = city;
		this.state = state;
		this.mental_illness = mental_illness;
		this.threat = threat;
		this.flee = flee;
		this.body_camera = body_camera;
	}

	// Getters y Setters
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

	public String getDeath() {
		return death;
	}

	public void setDeath(String death) {
		this.death = death;
	}

	public String getArmed() {
		return armed;
	}

	public void setArmed(String armed) {
		this.armed = armed;
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	public Genero getGender() {
		return gender;
	}

	public void setGender(Genero gender) {
		this.gender = gender;
	}

	public Race getRace() {
		return race;
	}

	public void setRace(Race race) {
		this.race = race;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getMental_illness() {
		return mental_illness;
	}

	public void setMental_illness(String mental_illness) {
		this.mental_illness = mental_illness;
	}

	public String getThreat() {
		return threat;
	}

	public void setThreat(String threat) {
		this.threat = threat;
	}

	public String getFlee() {
		return flee;
	}

	public void setFlee(String flee) {
		this.flee = flee;
	}

	public String getBody_camera() {
		return body_camera;
	}

	public void setBody_camera(String body_camera) {
		this.body_camera = body_camera;
	}

	public Report getShootReport() {
		return shootReport;
	}

	public void setShootReport(Report shootReport) {
		this.shootReport = shootReport;
	}

	// Criterio de igualdad: dos tiroteos son iguales si lo son su edad, genero, nombre y raza.

	public int hashCode() {
		return Objects.hash(age, gender, name, race);
	}

	
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Tiroteo other = (Tiroteo) obj;
		return Objects.equals(age, other.age) && gender == other.gender && Objects.equals(name, other.name)
				&& race == other.race;
	}
	
	//	Criterio de orden: por fecha del tiroteo y por nombre.
	
	public int compareTo(Tiroteo o) {
		int res;
		res = this.name.compareTo(getName());
		if (res==0) {
			res = this.date.compareTo(getDate());
		}
		return 0;
	}

	
	public String toString() {
		return "Shootings [id=" + id + ", name=" + name + ", date=" + date + ", death=" + death + ", armed=" + armed
				+ ", age=" + age + ", gender=" + gender + ", race=" + race + ", city=" + city + ", state=" + state
				+ ", mental_illness=" + mental_illness + ", threat=" + threat + ", flee=" + flee + ", body_camera="
				+ body_camera + ", shootReport=" + shootReport + "]";
	}
	

}
