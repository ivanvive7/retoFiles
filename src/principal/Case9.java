package principal;

import java.io.*;

import clases.*;
import utilidades.Utilidades;


public class Case9 {
	
	File fichC=new File("clientes.dat");
	
	public static void mostrarCliente(File fichC) {
		ObjectInputStream ois=null;
		boolean finArchivo=false;
		String dni;
		
		System.out.println("Introduce el DNI: ");
		dni=Utilidades.introducirCadena();
		
		if(fichC.exists()) {
			try {
				ois=new ObjectInputStream(new FileInputStream(fichC));
				while (!finArchivo) {
					try {
						Cliente c=(Cliente) ois.readObject();
						if(c.getDni().equals(dni)) {
							System.out.println(c);
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
	}
	
}
