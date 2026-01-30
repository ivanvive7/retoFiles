package principal;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import clases.Vehiculo;
import excepciones.MatriculaException;
import utilidades.MyObjectOutputStream;
import utilidades.Utilidades;

public class Case1 {
	
	public static void introducirVehiculo(File fichV) throws MatriculaException {
		ObjectInputStream ois = null;
		ObjectOutputStream oos = null;
		MyObjectOutputStream moos = null;
		boolean finArchivo = false, valida = false;
		String matricula;
		
		if (!fichV.exists()) {
			try {
				oos = new ObjectOutputStream(new FileOutputStream(fichV));
				System.out.println("Creacion del fichero");
				do {
				    System.out.println("Introduce la matricula del vehiculo: ");
				    matricula = Utilidades.introducirCadena();

				    try {
				        valida = ConcesionarioMain.validarMatricula(matricula);
				        System.out.println("Matrícula válida ✅");
				    } catch (MatriculaException e) {
				        System.out.println(e.getMessage());
				    }

				} while (!valida);
					
			} catch (FileNotFoundException e) {
				System.out.println("Error, fichero no encontrado");
			} catch (IOException e) {
				System.out.println("Error en la entrada de datos");
			}
			
			System.out.println("Introduce la marca del vehiculo: ");
			
		} else {
			
		}
		 
	}

}
