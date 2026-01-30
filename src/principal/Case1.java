package principal;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import utilidades.MyObjectOutputStream;

public class Case1 {
	
	public static void introducirVehiculo(File fichV) {
		ObjectInputStream ois = null;
		ObjectOutputStream oos = null;
		MyObjectOutputStream moos = null;
		boolean finArchivo = false;
		
		if (!fichV.exists()) {
			try {
				oos = new ObjectOutputStream(new FileOutputStream(fichV));
				
				
				
			} catch (FileNotFoundException e) {
				System.out.println("Error, fichero no encontrado, consulta la ruta especificada");
			} catch (IOException e) {
				
				System.out.println("Error en la entrada salida de datos");
			}
		} else {
			
		}
		 
	}

}
