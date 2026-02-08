package clases;

import java.time.LocalDate;

public class Coche extends Vehiculo{

	private static final long serialVersionUID = 1L;
	//ATRIBUTOS
	private int nPuertas;
	private boolean automatico;
	private boolean descapotable;
	//CONSTRUCTORES
	public Coche(String matricula, String marca, String modelo, double precioBase, Combustible combustible, String color, LocalDate fechaAlta, int nPuertas, boolean automatico, boolean descapotable) {
		super(matricula, marca, modelo, precioBase, combustible, color, fechaAlta);
		this.nPuertas = nPuertas;
		this.automatico = automatico;
		this.descapotable = descapotable;
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
						+ "Número de puertas: "+nPuertas+"\n"
						+ "Caja: "+automatico_Manual()+"\n"
						+ "Techo: "+techo());
	}
	
	public String automatico_Manual() {
		String texto="";
		
		if (automatico==false) {
			texto="Manual";
		} else {
			texto="Automático";
		}
		
		return texto;
	}
	
	public String techo() {
		String texto="";
		
		if (descapotable==false) {
			texto="Cerrado";
		} else {
			texto="Descapotable";
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
