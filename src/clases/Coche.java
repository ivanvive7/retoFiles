package clases;

import java.time.LocalDate;

public class Coche extends Vehiculo{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	//ATRIBUTOS
	private int nPuertas;
	private boolean automatico;
	private boolean descapotable;
	//CONSTRUCTORES
	public Coche(String matricula, String marca, String modelo, double precioBase, Estado estado, Combustible combustible, String color, LocalDate fechaAlta, int nPuertas, boolean automatico, boolean descapotable) {
		super(matricula, marca, modelo, precioBase, estado, combustible, color, fechaAlta);
		this.nPuertas = nPuertas;
		this.automatico = automatico;
		this.descapotable = descapotable;
	}
	
	public Coche() {
		super();
		this.nPuertas = 0;
		this.automatico = false;
		this.descapotable = false;
	}
	//GETTERS Y SETTERS
	public int getnPuertas() {
		return nPuertas;
	}

	public void setnPuertas(int nPuertas) {
		this.nPuertas = nPuertas;
	}

	public boolean isAutomatico() {
		return automatico;
	}

	public void setAutomatico(boolean automatico) {
		this.automatico = automatico;
	}

	public boolean isDescapotable() {
		return descapotable;
	}

	public void setDescapotable(boolean descapotable) {
		this.descapotable = descapotable;
	}
	//TOSTRING
	@Override
	public String toString() {
		return "Coche [nPuertas=" + nPuertas + ", automatico=" + automatico + ", descapotable=" + descapotable
				+ ", toString()=" + super.toString() + "]";
	}

	@Override
	public void visualizar() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void calcularPrecioFinal() {
		// TODO Auto-generated method stub
		
	}
	
}
