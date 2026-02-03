package principal;

import java.io.File;

public class Case4 {
	File fichV=new File("Vehiculos.dat");
	
	public static void modificarVehiculo() {
		String opcion;
		System.out.println("Selecciona el atributo: "
				+ "\n1. Matrícula."
				+ "\n2. Marca."
				+ "\n3. Modelo."
				+ "\n4. Precio base."
				+ "");
	}
	
}
