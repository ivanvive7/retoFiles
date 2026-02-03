package principal;

import java.io.*;
import principal.*;
import java.time.LocalDate;
import java.util.regex.Pattern;

import clases.*;
import excepciones.MatriculaException;
import utilidades.Utilidades;



public class Case4 {
	
	public static boolean validarMatricula(String matricula) throws MatriculaException {
		boolean valido = false;
		if (Pattern.matches("^[0-9]{4}[ ]?[BCDFGHJKLMNPQRSTVWXYZ]{3}$", matricula)) {
			valido = true;
		} else {
			throw new MatriculaException("Error: el formato de la matrícula no es correcto."
					+ "\nLa matrícula debe estar formada por cuatro números y tres letras (exluyendo vocales, Ñ y Q)");
		}
		return valido;
	}
	//este método hay que borrarlo cuando se meta el case al main

	File fichV=new File("vehiculos.dat");

	public static void modificarVehiculo(File fichV) throws MatriculaException {
		File fichAux=new File("vehiculosAux.dat");
		int opcion, combustible;
		String matricula, marca, modelo, color, respuesta;
		double precioBase;
		LocalDate fechaAlta;
		ObjectOutputStream oos;
		ObjectInputStream ois=null;
		boolean finArchivo=false, encontrado=false;

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

			if(fichV.exists()) {
				try {
					oos=new ObjectOutputStream(new FileOutputStream(fichAux));
					ois=new ObjectInputStream(new FileInputStream(fichV));
					System.out.println("Introduce la matrícula: ");
					matricula=Utilidades.introducirCadena();

					while(!finArchivo) {
						try {
							Vehiculo v=(Vehiculo) ois.readObject();
							if(v.getMatricula().equalsIgnoreCase(matricula)) {
								encontrado=true;
								switch(opcion) {
								case 1:
									do {
										System.out.println("Introduce la nueva matrícula: ");
										matricula=Utilidades.introducirCadena();
									}while(!validarMatricula(matricula));
									v.setMatricula(matricula);
									break;
								case 2:
									System.out.println("Introduce la nueva marca: ");
									marca=Utilidades.introducirCadena();
									v.setMarca(marca);
									break;
								case 3:
									System.out.println("Introduce el nuevo modelo: ");
									modelo=Utilidades.introducirCadena();
									v.setModelo(modelo);
									break;
								case 4:
									System.out.println("Introduce el nuevo precio base: ");
									precioBase=Utilidades.leerDouble(0,999999999);
									//que sea mayor que cero
									v.setPrecioBase(precioBase);
									break;
								case 5:
									System.out.println("Introduce el tipo de combustible: "
											+ "\n1. Gasolina."
											+ "\n2. Diesel."
											+ "\n3. Eléctrico."
											+ "\n4. Híbrido."
											+ "\nSelecciona una opción: ");
									combustible=Utilidades.leerInt(1,4);
									if(combustible==1) {
										v.setCombustible(Combustible.GASOLINA);
									}else if(combustible==2) {
										v.setCombustible(Combustible.DIESEL);
									}else if(combustible==3) {
										v.setCombustible(Combustible.ELECTRICO);
									}else {
										v.setCombustible(Combustible.HIBRIDO);
									}
									break;
								case 6:
									System.out.println("Introduce la nueva fecha de alta (dd/MM/yyyy): ");
									fechaAlta=Utilidades.leerFechaAMD();
									v.setFechaAlta(fechaAlta);
									break;
								case 7:
									System.out.println("Introduce el nuevo color: ");
									color=Utilidades.introducirCadena();
									v.setColor(color);
									break;
								}
							}
							oos.writeObject(v);
						}catch(EOFException e) {
							finArchivo=true;
						}
					}
					ois.close();
					oos.close();

					if(encontrado) {
						fichV.delete();
						fichAux.renameTo(fichV);
					}else {
						System.out.println("No hay ningún vehículo registrado con esa matrícula.");
					}

				}catch(FileNotFoundException e) {
					System.out.println("No se encontró el fichero.");
				}catch(ClassNotFoundException e) {
					System.out.println("La clase Vehiculo no es válida.");
				}catch(IOException e) {
					System.out.println("Error leyendo el fichero.");
				}
			}else{
				System.out.println("El fichero no existe.");
			}



			System.out.println("¿Quieres modificar algo más?");
			respuesta=Utilidades.introducirCadena("SI", "NO");
		}while(respuesta.equalsIgnoreCase("Si"));

	}
}