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
		boolean finArchivo = false;

		System.out.println("¿Que tipo de vehiculo quieres mostrar?:" + " Moto - Coche - Furgoneta.");
		tipo = Utilidades.introducirCadena("Moto", "Coche", "Furgoneta");
		switch (tipo.toLowerCase()) {
		case "moto":
			if (fichV.exists()) {
				try {
					ois = new ObjectInputStream(new FileInputStream(fichV));
					while (!finArchivo) {
						try {
							Vehiculo moto = (Moto) ois.readObject();
							System.out.println(moto);
						} catch (EOFException m) {
							System.out.println("No hay motos registradas");
							finArchivo = true;
						}
					}
				} catch (FileNotFoundException e) {
					System.out.println("No se encontró el fichero.");
				} catch (ClassNotFoundException e) {
					System.out.println("La clase Moto no es válida.");
				} catch (IOException e) {
					System.out.println("Error leyendo el fichero.");
				}
			} else {
				System.out.println("El fichero no existe.");
			}
			break;
		case "coche":
			if (fichV.exists()) {
				try {
					ois = new ObjectInputStream(new FileInputStream(fichV));
					while (!finArchivo) {
						try {
							Vehiculo coche = (Coche) ois.readObject();
							System.out.println(coche);
						} catch (EOFException c) {
							System.out.println("No hay Coches registrados");
							finArchivo = true;
						}
					}
				} catch (FileNotFoundException e) {
					System.out.println("No se encontró el fichero.");
				} catch (ClassNotFoundException e) {
					System.out.println("La clase Coche no es válida.");
				} catch (IOException e) {
					System.out.println("Error leyendo el fichero.");
				}
			} else {
				System.out.println("El fichero no existe.");
			}
			break;
		case "furgoneta":
			if (fichV.exists()) {
				try {
					ois = new ObjectInputStream(new FileInputStream(fichV));
					while (!finArchivo) {
						try {
							Vehiculo furgoneta = (Furgoneta) ois.readObject();
							System.out.println(furgoneta);
						} catch (EOFException c) {
							System.out.println("No hay furgonetas registradas");
							finArchivo = true;
						}
					}
				} catch (FileNotFoundException e) {
					System.out.println("No se encontró el fichero.");
				} catch (ClassNotFoundException e) {
					System.out.println("La clase Furgoneta no es válida.");
				} catch (IOException e) {
					System.out.println("Error leyendo el fichero.");
				}
			} else {
				System.out.println("El fichero no existe.");
			}
			break;
		}
	}
}