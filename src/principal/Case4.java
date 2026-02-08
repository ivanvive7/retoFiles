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
											+ "\n8. Otro."
											+ "\nSelecciona una opción: ");
									opcion=Utilidades.leerInt(1,8);

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
										fechaAlta=Utilidades.leerFechaDMA();
										v.setFechaAlta(fechaAlta);
										break;
									case 7:
										System.out.println("Introduce el nuevo color: ");
										color=Utilidades.introducirCadena();
										v.setColor(color);
										break;
									case 8:
										modificacionPorTipo(v);
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
	
	public static void modificacionPorTipo(Vehiculo v) {
		int opcion;
		String respuesta, tipo = null;
		boolean correctoTipo=false;
		
		if(v instanceof Coche) {
			System.out.println("¿Qué quieres modificar?"
					+ "\n1. Número de puertas."
					+ "\n2. Automático."
					+ "\n3. Descapotable."
					+ "\nSelecciona una opción: ");
			opcion=Utilidades.leerInt(1,3);
			switch(opcion) {
			case 1:
				System.out.println("Introduce el nuevo número de puertas: ");
				((Coche) v).setnPuertas(Utilidades.leerInt(2,5));
				break;
			case 2:
				System.out.println("¿Es automático?");
				respuesta=Utilidades.introducirCadena("SI", "NO");
				if(respuesta.equalsIgnoreCase("Si")) {
					((Coche) v).setAutomatico(true);
				}else {
					((Coche) v).setAutomatico(false);
				}
				break;
			case 3:
				System.out.println("¿Es descapotable?");
				respuesta=Utilidades.introducirCadena("Si", "No");
				if(respuesta.equalsIgnoreCase("Si")) {
					((Coche) v).setDescapotable(true);
				}else {
					((Coche) v).setDescapotable(false);
				}
				break;
			}
		}else if(v instanceof Moto) {
			System.out.println("¿Qué quieres modificar?"
					+ "\n1. Cilindrada."
					+ "\n2. Tipo de moto."
					+ "\nSelecciona una opcion: ");
			opcion=Utilidades.leerInt(1,2);
			switch(opcion) {
			case 1:
				System.out.println("Introduce la nueva cilindrada: ");
				((Moto) v).setCilindrada(Utilidades.leerInt(1,3000));
				break;
			case 2:
				System.out.println("Introduce el nuevo tipo de moto (NAKED, CUSTOM, MOTOCROSS, SPORT, TRAIL):");
				while(!correctoTipo) {
					try {
						tipo=Utilidades.introducirCadena();
						((Moto) v).setTipo(TipoMoto.valueOf(tipo.toUpperCase()));
						correctoTipo=true;
					}catch(IllegalArgumentException e) {
						System.out.println("El valor \"" + tipo + "\" no es válido. Inténtalo de nuevo.");
						System.out.println("Debe ser: NAKED, CUSTOM, MOTOCROSS, SPORT, TRAIL. ");
					}
				}
				break;
			}
		}else if(v instanceof Furgoneta) {
			System.out.println("¿Qué quieres modificar?"
					+ "\n1. MMA."
					+ "\n2. Puertas correderas."
					+ "\n3. Número de asientos."
					+ "\nSelecciona una opción: ");
			opcion=Utilidades.leerInt(1,3);
			switch (opcion) {
			case 1:
				System.out.println("Introduce la nueva MMA: ");
				((Furgoneta) v).setMma(Utilidades.leerInt(0,3500));
				break;
			case 2:
				System.out.println("¿Tiene puertas correderas?");
				respuesta=Utilidades.introducirCadena("SI", "NO");
				if(respuesta.equalsIgnoreCase("Si")) {
					((Furgoneta) v).setPuertasCorrederas(true);
				}else {
					((Furgoneta) v).setPuertasCorrederas(false);
				}
				break;
			case 3:
				System.out.println("Introduce el nuevo número de asientos: ");
				((Furgoneta) v).setnAsientos(Utilidades.leerInt(2,10));
				break;
			}
		}
	}
}