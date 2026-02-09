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
	public Furgoneta(String matricula, String marca, String modelo, double precioBase, Combustible combustible, String color, LocalDate fechaAlta, int mma, boolean puertasCorrederas, int nAsientos) {
		super(matricula, marca, modelo, precioBase, combustible, color, fechaAlta);
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

	@Override
	public void visualizar() {
		System.out.println("────────── FURGONETA ──────────\n"
						+ "Matrícula de la furgoneta: "+matricula+"\n"
						+ "Marca: "+marca+"\n"
						+ "Modelo: "+modelo+"\n"
						+ "Precio final: "+calcularPrecioFinal()+"\n"
						+ "Estado: "+estado+"\n"
						+ "Combustible: "+combustible+"\n"
						+ "Color: "+color+"\n"
						+ "Fecha de alta: "+fechaAlta+"\n"
						+ "Masa Máxima Autorizada: "+mma+"\n"
						+ "Tipo de puertas: "+tipoPuerta()+"\n"
						+ "Número de asientos: "+nAsientos+"\n");
	}
	
	public String tipoPuerta() {
		String texto="";
		
		if (puertasCorrederas==false) {
			texto="Convencionales";
		} else {
			texto="Correderas";
		}
		
		return texto;
	}

	@Override
	public double calcularPrecioFinal() {
		double precio=0;
		
		precio=precioBase+precioBase*0.21;
		
		return precio;
	}

}
