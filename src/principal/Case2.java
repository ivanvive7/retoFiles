package principal;

import java.io.*;

import clases.*;

public class Case2 {

	public static void listarVehiculos(File fichV) {
		ObjectInputStream ois=null;
		boolean finArchivo=false;
		if(fichV.exists()) {
			try {
				ois=new ObjectInputStream(new FileInputStream(fichV));
				while(!finArchivo) {
					try {
						Vehiculo v=(Vehiculo) ois.readObject();
						System.out.println(v);
						}catch(EOFException v) {
							finArchivo=true;
					}
				}
			}catch (FileNotFoundException e) {
				System.out.println("No se encontró el fichero.");
			}catch (ClassNotFoundException e) {
				System.out.println("La clase Vehículo no es válida.");
			}catch (IOException e) {
				System.out.println("Error leyendo el fichero.");
			}
		}else {
			System.out.println("El fichero no existe.");
		}
	}

}
