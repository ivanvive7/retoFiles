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
	    boolean seguirLeyendo = true;
	    boolean hayVehiculos = false;

	    System.out.println("¿Qué tipo de vehículo quieres mostrar?");
	    tipo = Utilidades.introducirCadena("Coche", "Moto", "Furgoneta");

	    if (!fichV.exists()) {
	        System.out.println("El fichero no existe.");
	    } else {

	        try {
	            ois = new ObjectInputStream(new FileInputStream(fichV));

	            while (seguirLeyendo) {
	                try {
	                    Vehiculo vehiculo = (Vehiculo) ois.readObject();

	                    switch (tipo.toLowerCase()) {
	                        case "moto":
	                            if (vehiculo instanceof Moto) {
	                                System.out.println(vehiculo);
	                                vehiculo.visualizar();
	                                hayVehiculos = true;
	                            }
	                            break;

	                        case "coche":
	                            if (vehiculo instanceof Coche) {
	                                System.out.println(vehiculo);
	                                vehiculo.visualizar();
	                                hayVehiculos = true;
	                            }
	                            break;

	                        case "furgoneta":
	                            if (vehiculo instanceof Furgoneta) {
	                                System.out.println(vehiculo);
	                                vehiculo.visualizar();
	                                hayVehiculos = true;
	                            }
	                            break;
	                    }

	                } catch (EOFException e) {
	                    // fin del fichero → salimos del while sin break
	                    seguirLeyendo = false;
	                }
	            }

	            if (!hayVehiculos) {
	                System.out.println("No hay " + tipo + "s registrados.");
	            }

	        } catch (FileNotFoundException e) {
	            System.out.println("No se encontró el fichero.");
	        } catch (ClassNotFoundException e) {
	            System.out.println("Clase no válida.");
	        } catch (IOException e) {
	            System.out.println("Error leyendo el fichero: " + e.getMessage());
	        } finally {
	            try {
	                if (ois != null) ois.close();
	            } catch (IOException e) {
	                System.out.println("Error cerrando el fichero.");
	            }
	        }
	    }
	}
}