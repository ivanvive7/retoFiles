package principal;

import utilidades.Utilidades;

public class ConcesionarioMain {
	
	public static int menu() {
		int opcion;
		System.out.println("--------------------------MENÚ--------------------------"
				+ "\n1.  Introducir vehículos."
				+ "\n2.  Listar vehículos."
				+ "\n3.  Listar vehículos por tipo."
				+ "\n4.  Modificar vehículo."
				+ "\n5.  Eliminar un vehículo defectuoso."
				+ "\n6.  Introducir clientes que van a reservar o comprar un vehículo."
				+ "\n7.  Añadir una compra o reserva a un cliente ya registrado."
				+ "\n8.  Mostrar clientes."
				+ "\n9.  Buscar clientes."
				+ "\n10. Salir."
				+ "\nSeleccione una opción: ");
		opcion=Utilidades.leerInt(1,10);
		return opcion;
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println();
		int opcion;
		
		do {
			opcion=menu();
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
			case 8:
				break;
			case 9: 
				break;
			case 10:
				System.out.println("Hasta la próxima.");
				break;
			}
		}while(opcion!=10);
		
		
		
	}

}
