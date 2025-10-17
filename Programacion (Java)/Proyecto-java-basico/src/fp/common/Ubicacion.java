package fp.common;

import java.util.ArrayList;
import java.util.List;

public class Ubicacion {
	private String city;
	private String state;
	
	
	public Ubicacion(String city, String state) {
		super();
		this.city = city;
		this.state = state;
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
	
	public static List<String> getUbicacion(List<Ubicacion> ubicaciones){
		List<String> Ubicacion = new ArrayList<>();
        for (Ubicacion ubicacion : ubicaciones) {
            String Ubicacion1 = ubicacion.getCity() + " " + ubicacion.getState();
            Ubicacion.add(Ubicacion1);
        }
        return Ubicacion;
	}


}
