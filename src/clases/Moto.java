package clases;

import java.time.LocalDate;

public class Moto extends Vehiculo{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	//ATRIBUTOS
	private int cilindrada;
	private TipoMoto tipo;
	//CONSTRUCTORES
	public Moto(String matricula, String marca, String modelo, double precioBase, Estado estado, Combustible combustible, String color, LocalDate fechaAlta, int cilindrada, TipoMoto tipo) {
		super(matricula, marca, modelo, precioBase, estado, combustible, color, fechaAlta);
		this.cilindrada = cilindrada;
		this.tipo = tipo;
	}
	
	public Moto() {
		super();
		this.cilindrada = 0;
		this.tipo = null;
	}
	//GETTERS Y SETTERS
	public int getCilindrada() {
		return cilindrada;
	}

	public void setCilindrada(int cilindrada) {
		this.cilindrada = cilindrada;
	}

	public TipoMoto isDeportiva() {
		return tipo;
	}

	public void setDeportiva(TipoMoto tipo) {
		this.tipo = tipo;
	}

	@Override
	public void visualizar() {
		System.out.println("Matrícula del coche: "+matricula+"\n"
						+ "Marca: "+marca+"\n"
						+ "Modelo: "+modelo+"\n"
						+ "Precio final: "+calcularPrecioFinal()+"\n"
						+ "Estado: "+estado+"\n"
						+ "Combustible: "+combustible+"\n"
						+ "Color: "+color+"\n"
						+ "Fecha de alta: "+fechaAlta+"\n"
						+ "Cilindrada: "+cilindrada+"\n"
						+ "Estilo: "+tipo);
	}

	@Override
	public double calcularPrecioFinal() {
		double precio=0;
		
		precio=precioBase+precioBase*0.18;
		
		return precio;
	}
	
}
