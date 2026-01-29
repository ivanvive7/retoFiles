package principal;

import java.util.HashMap;
import java.util.regex.Pattern;

import clases.*;
import excepciones.*;
import utilidades.Utilidades;

public class ConcesionarioMain {
	
	public static int menu() {
		int opcion;
		System.out.println("--------------------------MENÚ--------------------------"
				+ "\n1.  Introducir vehículos."
				+ "\n2.  Listar vehículos."
				+ "\n3.  Listar vehículos por tipo."
				+ "\n4.  Modificar vehículo."
				+ "\n5.  Eliminar un vehículo defectuoso."
				+ "\n6.  Introducir clientes que van a reservar o comprar un vehículo."
				+ "\n7.  Añadir una compra o reserva a un cliente ya registrado."
				+ "\n8.  Mostrar clientes."
				+ "\n9.  Buscar clientes."
				+ "\n10. Salir."
				+ "\nSeleccione una opción: ");
		opcion=Utilidades.leerInt(1,10);
		return opcion;
	}
	
	public static boolean buscarMatricula(HashMap<String, Vehiculo> mapaVehiculos, String matricula) {
		boolean encontrado=false;
		for(Vehiculo v: mapaVehiculos.values()) {
			if(v.getMatricula().equalsIgnoreCase(matricula)) {
				encontrado=true;
			}
		}
		return encontrado;
	}
	
	public static boolean validarMatricula(String matricula) throws MatriculaException{
		boolean valido=false;
		if(Pattern.matches("^[0-9]{4}[ ]?[BCDFGHJKLMNPQRSTVWXYZ]{3}$", matricula)) {
			valido=true;
		}else {
			throw new MatriculaException("Error: el formato de la matrícula no es correcto."
					+ "\nLa matrícula debe estar formada por cuatro números y tres letras (exluyendo vocales, Ñ y Q)");
		}
		return valido;
	}
	
	public static boolean validarDni(String dni) throws DniException {
		boolean valido=false;
		if(Pattern.matches("^[0-9]{8}[A-HJ-NP-TV-Z]$", dni)) {
			valido=true;
		}else {
			throw new DniException("Error: el formato del DNI no es correcto");
		}
		return valido;
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		HashMap<String, Vehiculo> mapaVehiculos=new HashMap<String, Vehiculo>();
		int opcion;
		
		do {
			opcion=menu();
			switch(opcion) {
			case 1:
				break;
			case 2:
				break;
			case 3:
				break;
			case 4:
				break;
			case 5:
				break;
			case 6:
				break;
			case 7:
				break;
			case 8:
				break;
			case 9: 
				break;
			case 10:
				System.out.println("Hasta la próxima.");
				break;
			}
		}while(opcion!=10);
		
		
		
	}

}
