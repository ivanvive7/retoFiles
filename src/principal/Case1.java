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
	
	public static void introducirVehiculo(File fichV, HashMap<String, Vehiculo> mapaVehiculos) throws MatriculaException {
		ObjectInputStream ois = null;
		ObjectOutputStream oos = null;
		MyObjectOutputStream moos = null;
		boolean finArchivo = false, correcta = false;
		String matricula;
		
		if (!fichV.exists()) {
			try {
				oos = new ObjectOutputStream(new FileOutputStream(fichV));
				System.out.println("Creacion del fichero");
				System.out.println("Introduce la matricula del vehiculo: ");
				matricula = Utilidades.introducirCadena();
				ConcesionarioMain.validarMatricula(matricula);
				
				3353
				
				
			} catch (FileNotFoundException e) {
				System.out.println("Error, fichero no encontrado, consulta la ruta especificada");
			} catch (IOException e) {
				
				System.out.println("Error en la entrada salida de datos");
			}
		} else {
			
		}
		 
	}

}
