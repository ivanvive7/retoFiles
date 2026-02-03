package principal;

import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import clases.*;
import utilidades.*;

public class Case3 {
	public static void ListarPorTipo(File fichV) {
		
		String tipo;
		ObjectInputStream ois = null;
		boolean finArchivo=false;
		
		System.out.println("¿Que tipo de vehiculo quieres mostrar?:" + " Moto - Coche - Furgoneta.");
		tipo = Utilidades.introducirCadena("Moto", "Coche", "Furgoneta");
		switch(tipo) {
		case "Moto":
			if(fichV.exists()) {
				try {
					ois=new ObjectInputStream(new FileInputStream(fichV));
					while(!finArchivo) {
						try {
							Moto moto=(Moto) ois.readObject();
							System.out.println(moto);
							}catch(EOFException m) {
								finArchivo=true;
						}
					}
				}catch (FileNotFoundException e) {
					System.out.println("No se encontró el fichero.");
				}catch (ClassNotFoundException e) {
					System.out.println("La clase Moto no es válida.");
				}catch (IOException e) {
					System.out.println("Error leyendo el fichero.");
				}
			}else {
				System.out.println("El fichero no existe.");
			}
			break;
		case "Coche":
			if(fichV.exists()) {
				try {
					ois=new ObjectInputStream(new FileInputStream(fichV));
					while(!finArchivo) {
						try {
							Coche coche=(Coche) ois.readObject();
							System.out.println(coche);
							}catch(EOFException c) {
								finArchivo=true;
						}
					}
				}catch (FileNotFoundException e) {
					System.out.println("No se encontró el fichero.");
				}catch (ClassNotFoundException e) {
					System.out.println("La clase Coche no es válida.");
				}catch (IOException e) {
					System.out.println("Error leyendo el fichero.");
				}
			}else {
				System.out.println("El fichero no existe.");
			}
			break;
		case "Furgoneta" :
			if(fichV.exists()) {
				try {
					ois=new ObjectInputStream(new FileInputStream(fichV));
					while(!finArchivo) {
						try {
							Furgoneta furgoneta=(Furgoneta) ois.readObject();
							System.out.println(furgoneta);
							}catch(EOFException c) {
								finArchivo=true;
						}
					}
				}catch (FileNotFoundException e) {
					System.out.println("No se encontró el fichero.");
				}catch (ClassNotFoundException e) {
					System.out.println("La clase Furgoneta no es válida.");
				}catch (IOException e) {
					System.out.println("Error leyendo el fichero.");
				}
			}else {
				System.out.println("El fichero no existe.");
			}
			break;
		}
	}
}