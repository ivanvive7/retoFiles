package principal;

import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import clases.*;
import excepciones.MatriculaException;
import utilidades.*;

public class Case5 {

	public static void eliminarVehiculo(File fichV, File fichAux) {
		String matricula = null;
		ObjectOutputStream oos = null;
		ObjectInputStream ois = null;
		boolean finArchivo = false, encontrado = false, valido = false;
		
		if (fichV.exists()) {
			try {
				do {
					try {
						System.out.println("Introduce la matrícula del vehículo:");
						matricula = Utilidades.introducirCadena();
						valido = ConcesionarioMain.validarMatricula(matricula);
					} catch (MatriculaException e) {
						System.out.println(e.getMessage());
					}
				} while (!valido);
				
				ois = new ObjectInputStream(new FileInputStream(fichV));
				oos = new ObjectOutputStream(new FileOutputStream(fichAux));
				
				while (!finArchivo) {
					try {
						Vehiculo v = (Vehiculo) ois.readObject();
						if (!v.getMatricula().equalsIgnoreCase(matricula)) {
							oos.writeObject(v);
						} else {
							encontrado = true;
						}
					} catch (EOFException e) {
						finArchivo = true;
					}
				}
				
				ois.close();
				oos.close();

				if (encontrado) {
					fichV.delete();
					fichAux.renameTo(fichV);
					System.out.println("Vehículo eliminado correctamente.");
				} else {
					fichAux.delete(); 
					System.out.println("No hay ningún vehículo registrado con esa matrícula.");
				}

			} catch (FileNotFoundException e) {
				System.out.println("No se encontró el fichero.");
			} catch (ClassNotFoundException e) {
				System.out.println("La clase Vehiculo no es válida.");
			} catch (IOException e) {
				System.out.println("Error leyendo/escribiendo el fichero: " + e.getMessage());
			}
		} else {
			System.out.println("El fichero no existe.");
		}
	}
}