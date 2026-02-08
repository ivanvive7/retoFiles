package principal;

import java.io.*;

import clases.*;

public class Case2 {

	public static void listarVehiculos(File fichV) {
		ObjectInputStream ois = null;
		boolean finArchivo = false;
		boolean hayVehiculos = false;
		
		if (fichV.exists()) {
			try {
				ois = new ObjectInputStream(new FileInputStream(fichV));
				while (!finArchivo) {
					try {
						Vehiculo v = (Vehiculo) ois.readObject();
						v.visualizar();
						hayVehiculos = true;

					} catch (EOFException v) {
						finArchivo = true;
					}
				}

				ois.close();
				
				if (!hayVehiculos) {
					System.out.println("No hay vehículos registrados.");
				}

			} catch (FileNotFoundException e) {
				System.out.println("No se encontró el fichero.");
			} catch (ClassNotFoundException e) {
				System.out.println("La clase Vehículo no es válida.");
			} catch (IOException e) {
				System.out.println("Error leyendo el fichero.");
			}
		} else {
			System.out.println("No hay vehículos registrados.");
		}
	}

}