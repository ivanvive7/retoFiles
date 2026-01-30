package principal;

import java.io.File;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import clases.Vehiculo;
import clases.*;
import utilidades.*;

public class Case3 {
	public static void ListarPorTipo(File fichV, HashMap<String, Vehiculo> mapaVehiculos) {
		
		String tipo;
		ObjectInputStream ois = null;
		ObjectOutputStream oos = null;
		MyObjectOutputStream moos = null;
		
		System.out.println("¿Que tipo de vehiculo quieres mostrar?:" + " Moto - Coche - Furgoneta.");
		tipo = Utilidades.introducirCadena("Moto", "Coche", "Furgoneta");
		switch(tipo) {
		case "Moto":
			
			break;
		case "Coche":
			
			break;
		case "Furgoneta" :
			
			break;
		}
	}
}