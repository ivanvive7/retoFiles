package principal;

import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.regex.Pattern;

import clases.*;
import excepciones.*;
import utilidades.MyObjectOutputStream;
import utilidades.Utilidades;

public class ConcesionarioMain {


	public static void main(String[] args) throws MatriculaException {
		// TODO Auto-generated method stub

		
		File fichV = new File("vehiculos.dat");
		File fichC = new File("clientes.dat");
		File fichT = new File("texto.dat"); 

		int opcion;

		do {
			opcion = menu();
			switch (opcion) {
			case 1:
				Case1.introducirVehiculo(fichV);
				break;
			case 2:
				Case2.listarVehiculos(fichV);
				break;
			case 3:
				Case3.ListarPorTipo(fichV);
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
				Case8.listarClientes(fichC);
				break;
			case 9:
				break;
			case 10:
				System.out.println("Hasta la próxima.");
				break;
			}
		} while (opcion != 10);

	}

	public static int menu() {
		int opcion;
		System.out.println("--------------------------MENÚ--------------------------" 
				+ "\n1.  Introducir vehículos."
				+ "\n2.  Listar vehículos."
				+ "\n3.  Listar vehículos por tipo." 
				+ "\n4.  Modificar vehículo."
				+ "\n5.  Eliminar un vehículo defectuoso."
				+ "\n6.  Introducir clientes que van a reservar o comprar un vehículo."
				+ "\n7.  Añadir una compra o reserva a un cliente ya registrado." + "\n8.  Mostrar clientes."
				+ "\n9.  Buscar clientes." + "\n10. Salir." + "\nSeleccione una opción: ");
		opcion = Utilidades.leerInt(1, 10);
		return opcion;
	}

	public static boolean buscarMatricula(File fichV, String matricula) {
		boolean encontrado = false;
		ObjectInputStream ois=null;
		boolean finArchivo=false;
		if(fichV.exists()) {
			try {
				ois=new ObjectInputStream(new FileInputStream(fichV));
				while (!finArchivo) { 
					try {
						Vehiculo v=(Vehiculo) ois.readObject();
						if(matricula.equals(v.getMatricula())) {
							encontrado=true;
						}
					}catch(EOFException e) {
						finArchivo=true;
					}
				}
				ois.close();
			}catch (FileNotFoundException e) {
				System.out.println("No se encontró el fichero.");
			}catch (ClassNotFoundException e) {
				System.out.println("La clase Vehiculo no es válida.");
			}catch (IOException e) {
				System.out.println("Error leyendo el fichero.");
			}
		}else {
			System.out.println("El fichero no existe.");
		}
		return encontrado;
	}
	
	public static boolean buscarDni(File fichC, String dni) {
		boolean encontrado = false;
		ObjectInputStream ois=null;
		boolean finArchivo=false;
		if(fichC.exists()) {
			try {
				ois=new ObjectInputStream(new FileInputStream(fichC));
				while (!finArchivo) {
					try {
						Cliente c=(Cliente) ois.readObject();
						if(dni.equals(c.getDni())) {
							encontrado=true;
						}
					}catch(EOFException e) {
						finArchivo=true;
					}
				}
				ois.close();
			}catch (FileNotFoundException e) {
				System.out.println("No se encontró el fichero.");
			}catch (ClassNotFoundException e) {
				System.out.println("La clase Cliente no es válida.");
			}catch (IOException e) {
				System.out.println("Error leyendo el fichero.");
			}
		}else {
			System.out.println("El fichero no existe.");
		}
		return encontrado;
	}

	public static boolean validarMatricula(String matricula) throws MatriculaException {
		boolean valido = false;
		if (Pattern.matches("^[0-9]{4}[ ]?[BCDFGHJKLMNPQRSTVWXYZ]{3}$", matricula)) {
			valido = true;
		} else {
			throw new MatriculaException("Error: el formato de la matrícula no es correcto."
					+ "\nLa matrícula debe estar formada por cuatro números y tres letras (exluyendo vocales, Ñ y Q)");
		}
		return valido;
	}

	public static boolean validarDni(String dni) throws DniException {
		boolean valido = false;
		if (Pattern.matches("^[0-9]{8}[A-HJ-NP-TV-Z]$", dni)) {
			valido = true;
		} else {
			throw new DniException("Error: el formato del DNI no es correcto");
		}
		return valido;
	}

}
