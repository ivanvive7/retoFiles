package clases;

import java.time.LocalDate;

public class Furgoneta extends Vehiculo{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	//ATRIBUTOS
	private int mma;
	private boolean puertasCorrederas;
	private int nAsientos;
	//CONSTRUCTORES
	public Furgoneta(String matricula, String marca, String modelo, double precioBase, Estado estado, Combustible combustible, String color, LocalDate fechaAlta, int mma, boolean puertasCorrederas, int nAsientos) {
		super(matricula, marca, modelo, precioBase, estado, combustible, color, fechaAlta);
		this.mma = mma;
		this.puertasCorrederas = puertasCorrederas;
		this.nAsientos = nAsientos;
	}
	
	public Furgoneta() {
		super();
		this.mma = 0;
		this.puertasCorrederas = false;
		this.nAsientos = 0;
	}
	//GETTERS Y SETTERS
	public int getMma() {
		return mma;
	}

	public void setMma(int mma) {
		this.mma = mma;
	}

	public boolean isPuertasCorrederas() {
		return puertasCorrederas;
	}

	public void setPuertasCorrederas(boolean puertasCorrederas) {
		this.puertasCorrederas = puertasCorrederas;
	}

	public int getnAsientos() {
		return nAsientos;
	}

	public void setnAsientos(int nAsientos) {
		this.nAsientos = nAsientos;
	}
	//TOSTRING
	@Override
	public String toString() {
		return "Furgoneta [mma=" + mma + ", puertasCorrederas=" + puertasCorrederas + ", nAsientos=" + nAsientos
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
