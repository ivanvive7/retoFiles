package principal;

import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;

import clases.*; 

public class Case8 {
 
	public static void listarClientes(File fichC) {
		ObjectInputStream ois=null;
		boolean finArchivo=false;
		if(fichC.exists()) {
			try {
				ois=new ObjectInputStream(new FileInputStream(fichC));
				while(!finArchivo) {
					try {
						Cliente c=(Cliente) ois.readObject();
						System.out.println(c);
						}catch(EOFException v) {
							finArchivo=true;
					}
				}
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
