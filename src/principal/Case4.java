package principal;

import java.io.File;

import utilidades.Utilidades;

public class Case4 {
	File fichV=new File("Vehiculos.dat");
	
	public static void modificarVehiculo() {
<<<<<<< HEAD
		String opcion;
		System.out.println("Selecciona el atributo: "
				+ "\n1. Matrícula."
				+ "\n2. Marca."
				+ "\n3. Modelo."
				+ "\n4. Precio base."
				+ "");
=======
		int opcion;
		String respuesta;
		
		do {
			System.out.println("¿Qué quieres modificar?"
					+ "\n1. Matricula."
					+ "\n2. Marca."
					+ "\n3. Modelo."
					+ "\n4. Precio base."
					+ "\n5. Combustible."
					+ "\n6. Fecha de alta."
					+ "\n7. Color."
					+ "\nSelecciona una opción: ");
			opcion=Utilidades.leerInt(1,7);
			
			switch(opcion) {
			case 1:
				break;
			case 2:
				break;
			case 3:
				break;
			case 4:
				break;
			case 5:
				break;
			case 6:
				break;
			case 7:
				break;
			}
			
			System.out.println("¿Quieres modificar algo más?");
			respuesta=Utilidades.introducirCadena("SI", "NO");
		}while(respuesta.equalsIgnoreCase("Si"));
>>>>>>> branch 'main' of https://github.com/ivanvive7/retoFiles
	}
}