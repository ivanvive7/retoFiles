package clases;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class Cliente implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	//ATRIBUTOS
	private String dni;
	private String nombre;
	private String apellido;
	private int telefono;
	private Map<String, Vehiculo> mapaVehiculos;
	//CONSTRUCTORES
	public Cliente(String dni, String nombre, String apellido, int telefono, Map<String, Vehiculo> mapaVehiculos) {
		super();
		this.dni = dni;
		this.nombre = nombre;
		this.apellido = apellido;
		this.telefono = telefono;
		this.mapaVehiculos = mapaVehiculos;
	}

	public Cliente() {
		super();
		this.dni = "";
		this.nombre = "";
		this.apellido = "";
		this.telefono = 0;
		this.mapaVehiculos = new HashMap<String, Vehiculo>();
	}
	//GETTERS Y SETTERS
	public String getDni() {
		return dni;
	}

	public void setDni(String dni) {
		this.dni = dni;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellido() {
		return apellido;
	}

	public void setApellido(String apellido) {
		this.apellido = apellido;
	}

	public int getTelefono() {
		return telefono;
	}

	public void setTelefono(int telefono) {
		this.telefono = telefono;
	}

	public Map<String, Vehiculo> getMapaVehiculos() {
		return mapaVehiculos;
	}

	public void setMapaVehiculos(Map<String, Vehiculo> mapaVehiculos) {
		this.mapaVehiculos = mapaVehiculos;
	}

	@Override
	public String toString() {
		return "Cliente [dni=" + dni + ", nombre=" + nombre + ", apellido=" + apellido + ", telefono=" + telefono
				+ ", mapaVehiculos=" + mapaVehiculos + "]";
	}
	
}
