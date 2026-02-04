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
import utilidades.*;

public class Case5 {
	

	public static void eliminarVehiculo(File fichAux, File fichV) {
		String matricula;
		ObjectOutputStream oos;
		ObjectInputStream ois=null;
		boolean finArchivo=false, encontrado=false;
		if(fichV.exists()) {
			try {
				oos=new ObjectOutputStream(new FileOutputStream(fichAux));
				ois=new ObjectInputStream(new FileInputStream(fichV));
				System.out.println("Introduzca la matricula del coche que desea eliminar");
				matricula = Utilidades.introducirCadena();
				
				while (!finArchivo) {
					try {
						Vehiculo v=(Vehiculo) ois.readObject();
						if(!v.getMatricula().equalsIgnoreCase(matricula)) {
							oos.writeObject(v);
						}else {
							encontrado = true;
						}
					}catch(EOFException e) {
						finArchivo=true;
					}
				}
				ois.close();
				oos.close();
				
				if(encontrado) {
					fichV.delete();
					fichAux.renameTo(fichV);
					System.out.println("Vehículo eliminado correctamente.");
				}else {
					System.out.println("No hay ningun vehiculo registrado con esa matricula.");
				}
				
				
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
	}
}
