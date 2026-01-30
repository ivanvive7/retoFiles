package principal;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import clases.*;
import excepciones.MatriculaException;
import utilidades.MyObjectOutputStream;
import utilidades.Utilidades;

public class Case1 {
	
	public static void introducirVehiculo(File fichV) throws MatriculaException {
		ObjectInputStream ois = null;
		ObjectOutputStream oos = null;
		MyObjectOutputStream moos = null;
		boolean finArchivo = false, valida = false, correcto = false;
		String matricula="", marca ="", modelo = "", respuestaEstado = "";
		double precioBase = 0.0;
		Estado estado = null;
		Combustible combustible = null;
		
		if (!fichV.exists()) {
			try {
				oos = new ObjectOutputStream(new FileOutputStream(fichV));
				System.out.println("Creacion del fichero");
				do {
				    System.out.println("Introduce la matricula del vehiculo: ");
				    matricula = Utilidades.introducirCadena();

				    try {
				        valida = ConcesionarioMain.validarMatricula(matricula);
				        
				    } catch (MatriculaException e) {
				        System.out.println(e.getMessage());
				    }

				} while (!valida);
				System.out.println("Matrícula válida");
			
			} catch (FileNotFoundException e) {
				System.out.println("Error, fichero no encontrado");
			} catch (IOException e) {
				System.out.println("Error en la entrada de datos");
			}
			
			System.out.println("Introduce la marca del vehiculo: ");
			marca = Utilidades.introducirCadena();
			System.out.println("Introduce el modelo del vehiculo: ");
			modelo = Utilidades.introducirCadena();
			System.out.println("Introduce el precio base del vehiculo: ");
			precioBase = Utilidades.leerDouble();
			System.out.println("Introduce el estado del vehiculo (DISONIBLE | RESERVADO | VENDIDO): ");

			while (!correcto) {
			    try {
			        respuestaEstado = Utilidades.introducirCadena();
			        estado = Estado.valueOf(respuestaEstado.toUpperCase());
			        correcto = true;
			    } catch (IllegalArgumentException e) {
			        System.out.println("El valor \"" + respuestaEstado + "\" no es válido. Inténtalo de nuevo.");
			        System.out.println("Debe ser: DISPONIBLE, RESERVADO o VENDIDO");
			    }
			}
			
			System.out.println("Introduce el estado del vehiculo (GASOLINA | DIESEL | HIBRIDO | ELECTRICO): ");
			
			while (!correcto) {
			    try {
			        respuestaEstado = Utilidades.introducirCadena();
			        estado = Estado.valueOf(respuestaEstado.toUpperCase());
			        correcto = true;
			    } catch (IllegalArgumentException e) {
			        System.out.println("El valor \"" + respuestaEstado + "\" no es válido. Inténtalo de nuevo.");
			        System.out.println("Debe ser: GASOLINA, DIESEL, HIBRIDO o ELECTRICO");
			    }
			}
			
		} else {
			
		}
		 
	}

}
