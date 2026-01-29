package clases;

import java.time.LocalDate;

public class Moto extends Vehiculo{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	//ATRIBUTOS
	private int cilindrada;
	private boolean deportiva;
	//CONSTRUCTORES
	public Moto(String matricula, String marca, String modelo, double precioBase, Estado estado, Combustible combustible, String color, LocalDate fechaAlta, int cilindrada, boolean deportiva) {
		super(matricula, marca, modelo, precioBase, estado, combustible, color, fechaAlta);
		this.cilindrada = cilindrada;
		this.deportiva = deportiva;
	}
	
	public Moto() {
		super();
		this.cilindrada = 0;
		this.deportiva = false;
	}
	//GETTERS Y SETTERS
	public int getCilindrada() {
		return cilindrada;
	}

	public void setCilindrada(int cilindrada) {
		this.cilindrada = cilindrada;
	}

	public boolean isDeportiva() {
		return deportiva;
	}

	public void setDeportiva(boolean deportiva) {
		this.deportiva = deportiva;
	}
	//TOSTRING
	@Override
	public String toString() {
		return "Moto [cilindrada=" + cilindrada + ", deportiva=" + deportiva + ", toString()=" + super.toString() + "]";
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
