package principal;

import java.io.File;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import excepciones.DniException;
import excepciones.MatriculaException;
import utilidades.MyObjectOutputStream;
import utilidades.Utilidades;

public class Case6 {
	
	File fichC = new File("clientes.dat");
	
	public void introducirCliente() {
		String dni="";
		boolean existe, valido=false;
		ObjectOutputStream oos;
		MyObjectOutputStream moos;
		ObjectInputStream ois;
		
		do {
			try {
				System.out.println("Introduce el DNI del nuevo cliente:");
				dni=Utilidades.introducirCadena();
				valido=ConcesionarioMain.validarDni(dni);
			} catch (DniException e) {
				System.out.println(e.getMessage());
			}
		} while (!valido);
		existe=ConcesionarioMain.buscarDni(fichC, dni);
		if (!existe) {
			if (fichC.exists()) {
				
			} else {
				
			}
		} else {
			
		}
		
	}
	
}
