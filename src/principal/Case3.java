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
		boolean hayVehiculos = false;

		  System.out.println("¿Que tipo de vehiculo quieres mostrar?");
		    tipo = Utilidades.introducirCadena("Coche", "Moto", "Furgoneta");
		    if (!fichV.exists()) {
		        System.out.println("El fichero no existe.");
		        return;
		    }

		    try {
		        ois = new ObjectInputStream(new FileInputStream(fichV));
		        
		        while (!hayVehiculos) {
		            try {
		                Vehiculo vehiculo = (Vehiculo) ois.readObject();
		            
		                switch (tipo.toLowerCase()) {
		                    case "moto":
		                        if (vehiculo instanceof Moto) {
		                            vehiculo.visualizar();
		                            hayVehiculos = true;
		                        }
		                        break;
		                    case "coche":
		                        if (vehiculo instanceof Coche) {
		                            vehiculo.visualizar();
		                            hayVehiculos = true;
		                        }
		                        break;
		                    case "furgoneta":
		                        if (vehiculo instanceof Furgoneta) {
		                            vehiculo.visualizar();
		                            hayVehiculos = true;
		                        }
		                        break;
		                }
		            } catch (EOFException e) {
		                hayVehiculos = false;
		            }
		        }
		        
		        if (!hayVehiculos) {
		            System.out.println("No hay " + tipo + "S registrados.");
		        }
		        
		        ois.close();
		        
		    } catch (FileNotFoundException e) {
		        System.out.println("No se encontró el fichero.");
		    } catch (ClassNotFoundException e) {
		        System.out.println("Clase no válida.");
		    } catch (IOException e) {
		        System.out.println("Error leyendo el fichero: " + e.getMessage());
		    }
	}
}