package principal;

import excepciones.DniException;
import excepciones.MatriculaException;
import utilidades.Utilidades;

public class Case6 {
	
	public void introducirCliente() {
		String dni;
		boolean existe;
		
		try {
			System.out.println("Introduce el DNI del nuevo cliente:");
			dni=Utilidades.introducirCadena();
			existe=ConcesionarioMain.validarDni(dni);
		} catch (DniException e) {
			System.out.println(e.getMessage());
		}
		
	}
	
}
