package clases;

import java.io.Serializable;
import java.time.LocalDate;

public abstract class Vehiculo implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	//ATRIBUTOS
	protected String matricula;
	protected String marca;
	protected String modelo;
	protected double precioBase;
	protected Estado estado;
	protected Combustible combustible;
	protected String color;
	protected LocalDate fechaAlta;
	
	//CONSTRUCTORES
	public Vehiculo(String matricula, String marca, String modelo, double precioBase, Estado estado, Combustible combustible, String color, LocalDate fechaAlta) {
		this.matricula = matricula;
		this.marca = marca;
		this.modelo = modelo;
		this.precioBase = precioBase;
		this.estado = estado;
		this.combustible = combustible;
		this.color = color;
		this.fechaAlta = fechaAlta;
	}
	
	public Vehiculo() {
		this.matricula = "";
		this.marca = "";
		this.modelo = "";
		this.precioBase = 0;
		this.estado = null;
		this.combustible = null;
		this.color = "";
		this.fechaAlta = LocalDate.now();
	}
	//GETTERS Y SETTERS
	public String getMatricula() {
		return matricula;
	}

	public void setMatricula(String matricula) {
		this.matricula = matricula;
	}

	public String getMarca() {
		return marca;
	}

	public void setMarca(String marca) {
		this.marca = marca;
	}

	public String getModelo() {
		return modelo;
	}

	public void setModelo(String modelo) {
		this.modelo = modelo;
	}

	public double getPrecioBase() {
		return precioBase;
	}

	public void setPrecioBase(double precioBase) {
		this.precioBase = precioBase;
	}

	public Estado getEstado() {
		return estado;
	}

	public void setEstado(Estado estado) {
		this.estado = estado;
	}

	public Combustible getCombustible() {
		return combustible;
	}

	public void setCombustible(Combustible combustible) {
		this.combustible = combustible;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public LocalDate getFechaAlta() {
		return fechaAlta;
	}

	public void setFechaAlta(LocalDate fechaAlta) {
		this.fechaAlta = fechaAlta;
	}
	
	//TOSTRING
	@Override
	public String toString() {
		return "Vehiculo [matricula=" + matricula + ", marca=" + marca + ", modelo=" + modelo + ", precioBase="
				+ precioBase + ", estado=" + estado + ", combustible=" + combustible + ", color=" + color
				+ ", fechaAlta=" + fechaAlta + "]";
	}
	
	//MÉTODOS ABSTRACTO
	public abstract void visualizar();
	
	public abstract void calcularPrecioFinal();
}
