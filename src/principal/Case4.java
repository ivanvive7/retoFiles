package principal;

import java.io.*;
import principal.*;
import java.time.LocalDate;
import java.util.regex.Pattern;

import clases.*;
import excepciones.MatriculaException;
import utilidades.Utilidades;



public class Case4 {

	public static void modificarVehiculo(File fichV) throws MatriculaException {
		File fichAux=new File("vehiculosAux.dat");
		int opcion;
		String matricula, marca, modelo, combustible="", color, respuesta;
		double precioBase;
		LocalDate fechaAlta;
		ObjectOutputStream oos;
		ObjectInputStream ois=null;
		boolean finArchivo=false, encontrado=false, correctoCombustible=false, matriculaRepe=false;

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
								do {
									encontrado=true;
									finArchivo=false;
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
										matriculaRepe=false;
										do {
											System.out.println("Introduce la nueva matrícula: ");
											matricula=Utilidades.introducirCadena();
											if(ConcesionarioMain.buscarMatricula(fichV, matricula)) {
												matriculaRepe=true;
												System.out.println("Ya hay un vehículo registrado con esa matrícula.");
											}
											//esto hay que cambiarlo cuando lo pasemos al main
										}while(!ConcesionarioMain.validarMatricula(matricula)||matriculaRepe);
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
										precioBase=Utilidades.leerDouble(0, Integer.MAX_VALUE);
										v.setPrecioBase(precioBase);
										break;
									case 5:
										System.out.println("Introduce el combustible del vehiculo (GASOLINA | DIESEL | HIBRIDO | ELECTRICO): ");
										  
										while (!correctoCombustible) {
											try {
												combustible = Utilidades.introducirCadena();
												v.setCombustible( Combustible.valueOf(combustible.toUpperCase()));
												correctoCombustible = true;
											} catch (IllegalArgumentException e) {
												System.out.println("El valor \"" + combustible + "\" no es válido. Inténtalo de nuevo.");
												System.out.println("Debe ser: GASOLINA, DIESEL, HIBRIDO o ELECTRICO");
											}
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
									
									System.out.println("¿Quieres modificar otra cosa?");
									respuesta=Utilidades.introducirCadena("SI", "NO");
								
								}while(respuesta.equalsIgnoreCase("Si"));
								
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
					fichAux.delete();
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

	}
}