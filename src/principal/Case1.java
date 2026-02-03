package principal;

import java.io.*;
import java.time.LocalDate;

import clases.*;
import excepciones.MatriculaException;
import utilidades.*;


public class Case1 {

	public static void introducirVehiculo(File fichV) throws MatriculaException, IOException {
		ObjectInputStream ois = null;
		ObjectOutputStream oos = null;
		MyObjectOutputStream moos = null;
		boolean finArchivo = false, valida = false, correcto = false, correctoCombustible = false, correctoSwitch = false, correctoTipoMoto = false;
		String matricula="", marca ="", modelo = "", respuestaEstado = "",respuestaCombustible = "", color = "", elegir = "", respuestaTipoMoto = "";
		double precioBase = 0.0;
		Estado estado = null;
		Combustible combustible = null;
		TipoMoto tipoMoto = null;
		LocalDate fechaAlta = null;

		if (!fichV.exists()) {
			try {
				oos = new ObjectOutputStream(new FileOutputStream(fichV));
				System.out.println("Creacion del fichero");
				do {
					System.out.println("Introduce la matricula del vehiculo: ");
					matricula = Utilidades.introducirCadena();

					try {
						valida = ConcesionarioMain.validarMatricula(matricula);

					} catch (MatriculaException e) {
						System.out.println(e.getMessage());
					}

				} while (!valida);
				System.out.println("Matrícula válida");

			} catch (FileNotFoundException e) {
				System.out.println("Error, fichero no encontrado");
			} catch (IOException e) {
				System.out.println("Error en la entrada de datos");
			}

			System.out.println("Introduce la marca del vehiculo: ");
			marca = Utilidades.introducirCadena();
			System.out.println("Introduce el modelo del vehiculo: ");
			modelo = Utilidades.introducirCadena();
			System.out.println("Introduce el precio base del vehiculo: ");
			precioBase = Utilidades.leerDouble();
			System.out.println("Introduce el estado del vehiculo (DISPONIBLE | RESERVADO | VENDIDO): ");

			while (!correcto) {
				try {
					respuestaEstado = Utilidades.introducirCadena();
					estado = Estado.valueOf(respuestaEstado.toUpperCase());
					correcto = true;
				} catch (IllegalArgumentException e) {
					System.out.println("El valor \"" + respuestaEstado + "\" no es válido. Inténtalo de nuevo.");
					System.out.println("Debe ser: DISPONIBLE, RESERVADO o VENDIDO");
				}
			}

			System.out.println("Introduce el combustible del vehiculo (GASOLINA | DIESEL | HIBRIDO | ELECTRICO): ");

			while (!correctoCombustible) {
				try {
					respuestaCombustible = Utilidades.introducirCadena();
					combustible = Combustible.valueOf(respuestaCombustible.toUpperCase());
					correctoCombustible = true;
				} catch (IllegalArgumentException e) {
					System.out.println("El valor \"" + respuestaCombustible + "\" no es válido. Inténtalo de nuevo.");
					System.out.println("Debe ser: GASOLINA, DIESEL, HIBRIDO o ELECTRICO");
				}
			}

			System.out.println("Introduce el color del vehiculo: ");
			color = Utilidades.introducirCadena();

			System.out.println("Introduzca la fecha de alta del vehiculo: ");
			fechaAlta = Utilidades.leerFechaDMA();



			System.out.println("Introduce el vehiculo que es (COCHE | MOTO | FURGONETA): ");
			while (!correctoSwitch) {
				elegir = Utilidades.introducirCadena("COCHE", "MOTO", "FURGONETA");

				switch (elegir.toUpperCase()) {
				case "COCHE":
					int puertas;
					boolean automatico, descapotable;

					System.out.println("Has elegido COCHE");

					System.out.println("Número de puertas:");
					puertas = Utilidades.leerInt();

					System.out.println("¿Es automático? (SI/NO):");
					automatico = Utilidades.introducirCadena().equalsIgnoreCase("SI");

					System.out.println("¿Es descapotable? (SI/NO):");
					descapotable = Utilidades.introducirCadena().equalsIgnoreCase("SI");

					System.out.println("Coche creado con:");
					System.out.println("Puertas: " + puertas);
					System.out.println("Automático: " + automatico);
					System.out.println("Descapotable: " + descapotable);
					Vehiculo c = new Coche(matricula, marca, modelo, precioBase, estado, combustible, color, fechaAlta, puertas, automatico, descapotable);
					oos.writeObject(c);
					oos.close();
					correcto = true;

					break;
				case "MOTO":
					int cilindrada;
					boolean deportiva;
					System.out.println("Has elegido MOTO");

					System.out.println("Cilindrada:");
					cilindrada = Utilidades.leerInt();

					System.out.println("¿Que tipo de moto es?:");
					while (!correctoTipoMoto) {
						try {
							respuestaTipoMoto = Utilidades.introducirCadena();
							tipoMoto = TipoMoto.valueOf(respuestaTipoMoto.toUpperCase());
							correctoTipoMoto = true;
						} catch (IllegalArgumentException e) {
							System.out.println("El valor \"" + respuestaTipoMoto + "\" no es válido. Inténtalo de nuevo.");
							System.out.println("Debe ser: GASOLINA, DIESEL, HIBRIDO o ELECTRICO");
						}
					}

					System.out.println("Moto creada con:");
					System.out.println("Cilindrada: " + cilindrada);
					System.out.println("Tipo: " + tipoMoto);
					Vehiculo m = new Moto(matricula, marca, modelo, precioBase, estado, combustible, color, fechaAlta, cilindrada, tipoMoto);
					oos.writeObject(m);
					oos.close();
					correcto = true;
					break;
				case "FURGONETA":
					int mma;
					boolean correderas;
					int asientos;

					System.out.println("Has elegido FURGONETA");

					System.out.println("MMA:");
					mma = Utilidades.leerInt();

					System.out.println("¿Tiene puertas correderas? (SI/NO):");
					correderas = Utilidades.introducirCadena().equalsIgnoreCase("SI");

					System.out.println("Número de asientos:");
					asientos = Utilidades.leerInt();

					System.out.println("Furgoneta creada con:");
					System.out.println("MMA: " + mma);
					System.out.println("Puertas correderas: " + correderas);
					System.out.println("Asientos: " + asientos);
					Vehiculo f = new Furgoneta(matricula, marca, modelo, precioBase, estado, combustible, color, fechaAlta, mma, correderas, asientos);
					oos.writeObject(f);
					oos.close();
					correcto = true;
					break;
				}

			}

		} else {
			try {
				moos = new MyObjectOutputStream(new FileOutputStream(fichV, true));
				System.out.println("Creacion del fichero");
				do {
					System.out.println("Introduce la matricula del vehiculo: ");
					matricula = Utilidades.introducirCadena();

					try {
						valida = ConcesionarioMain.validarMatricula(matricula);

					} catch (MatriculaException e) {
						System.out.println(e.getMessage());
					}

				} while (!valida);
				System.out.println("Matrícula válida");

				if (!ConcesionarioMain.buscarMatricula(fichV, matricula)) {
					System.out.println("Introduce la marca del vehiculo: ");
					marca = Utilidades.introducirCadena();
					System.out.println("Introduce el modelo del vehiculo: ");
					modelo = Utilidades.introducirCadena();
					System.out.println("Introduce el precio base del vehiculo: ");
					precioBase = Utilidades.leerDouble();
					System.out.println("Introduce el estado del vehiculo (DISONIBLE | RESERVADO | VENDIDO): ");

					while (!correcto) {
						try {
							respuestaEstado = Utilidades.introducirCadena();
							estado = Estado.valueOf(respuestaEstado.toUpperCase());
							correcto = true;
						} catch (IllegalArgumentException e) {
							System.out.println("El valor \"" + respuestaEstado + "\" no es válido. Inténtalo de nuevo.");
							System.out.println("Debe ser: DISPONIBLE, RESERVADO o VENDIDO");
						}
					}

					System.out.println("Introduce el combustible del vehiculo (GASOLINA | DIESEL | HIBRIDO | ELECTRICO): ");

					while (!correctoCombustible) {
						try {
							respuestaCombustible = Utilidades.introducirCadena();
							combustible = Combustible.valueOf(respuestaCombustible.toUpperCase());
							correctoCombustible = true;
						} catch (IllegalArgumentException e) {
							System.out.println("El valor \"" + respuestaCombustible + "\" no es válido. Inténtalo de nuevo.");
							System.out.println("Debe ser: GASOLINA, DIESEL, HIBRIDO o ELECTRICO");
						}
					}

					System.out.println("Introduce el color del vehiculo: ");
					color = Utilidades.introducirCadena();

					System.out.println("Introduzca la fecha de alta del vehiculo: ");
					fechaAlta = Utilidades.leerFechaDMA();



					System.out.println("Introduce el vehiculo que es (COCHE | MOTO | FURGONETA): ");
					while (!correctoSwitch) {
						elegir = Utilidades.introducirCadena();

						switch (elegir.toUpperCase()) {
						case "COCHE":
							int puertas;
							boolean automatico, descapotable;

							System.out.println("Has elegido COCHE");

							System.out.println("Número de puertas:");
							puertas = Utilidades.leerInt();

							System.out.println("¿Es automático? (SI/NO):");
							automatico = Utilidades.introducirCadena().equalsIgnoreCase("SI");

							System.out.println("¿Es descapotable? (SI/NO):");
							descapotable = Utilidades.introducirCadena().equalsIgnoreCase("SI");

							System.out.println("Coche creado con:");
							System.out.println("Puertas: " + puertas);
							System.out.println("Automático: " + automatico);
							System.out.println("Descapotable: " + descapotable);
							Vehiculo c = new Coche(matricula, marca, modelo, precioBase, estado, combustible, color, fechaAlta, puertas, automatico, descapotable);
							moos.writeObject(c);
							moos.close();
							correcto = true;

							break;
						case "MOTO":
							int cilindrada;
							boolean deportiva;
							System.out.println("Has elegido MOTO");

							System.out.println("Cilindrada:");
							cilindrada = Utilidades.leerInt();

							System.out.println("¿Que tipo de moto es?:");
							while (!correctoTipoMoto) {
								try {
									respuestaTipoMoto = Utilidades.introducirCadena();
									tipoMoto = TipoMoto.valueOf(respuestaTipoMoto.toUpperCase());
									correctoTipoMoto = true;
								} catch (IllegalArgumentException e) {
									System.out.println("El valor \"" + respuestaTipoMoto + "\" no es válido. Inténtalo de nuevo.");
									System.out.println("Debe ser: GASOLINA, DIESEL, HIBRIDO o ELECTRICO");
								}
							}

							System.out.println("Moto creada con:");
							System.out.println("Cilindrada: " + cilindrada);
							System.out.println("Tipo: " + tipoMoto);
							Vehiculo m = new Moto(matricula, marca, modelo, precioBase, estado, combustible, color, fechaAlta, cilindrada, tipoMoto);
							moos.writeObject(m);
							moos.close();
							correcto = true;
							break;
						case "FURGONETA":
							int mma;
							boolean correderas;
							int asientos;

							System.out.println("Has elegido FURGONETA");

							System.out.println("MMA:");
							mma = Utilidades.leerInt();

							System.out.println("¿Tiene puertas correderas? (SI/NO):");
							correderas = Utilidades.introducirCadena().equalsIgnoreCase("SI");

							System.out.println("Número de asientos:");
							asientos = Utilidades.leerInt();

							System.out.println("Furgoneta creada con:");
							System.out.println("MMA: " + mma);
							System.out.println("Puertas correderas: " + correderas);
							System.out.println("Asientos: " + asientos);
							Vehiculo f = new Furgoneta(matricula, marca, modelo, precioBase, estado, combustible, color, fechaAlta, mma, correderas, asientos);
							moos.writeObject(f);
							moos.close();
							correcto = true;
							break;
						}
					}

				}

			} catch (FileNotFoundException e) {
				System.out.println("Error, fichero no encontrado");
			} catch (IOException e) {
				System.out.println("Error en la entrada de datos");
			}

			System.out.println("Introduce la marca del vehiculo: ");
			marca = Utilidades.introducirCadena();
			System.out.println("Introduce el modelo del vehiculo: ");
			modelo = Utilidades.introducirCadena();
			System.out.println("Introduce el precio base del vehiculo: ");
			precioBase = Utilidades.leerDouble();
			System.out.println("Introduce el estado del vehiculo (DISONIBLE | RESERVADO | VENDIDO): ");

			while (!correcto) {
				try {
					respuestaEstado = Utilidades.introducirCadena();
					estado = Estado.valueOf(respuestaEstado.toUpperCase());
					correcto = true;
				} catch (IllegalArgumentException e) {
					System.out.println("El valor \"" + respuestaEstado + "\" no es válido. Inténtalo de nuevo.");
					System.out.println("Debe ser: DISPONIBLE, RESERVADO o VENDIDO");
				}
			}

			System.out.println("Introduce el combustible del vehiculo (GASOLINA | DIESEL | HIBRIDO | ELECTRICO): ");

			while (!correctoCombustible) {
				try {
					respuestaCombustible = Utilidades.introducirCadena();
					combustible = Combustible.valueOf(respuestaCombustible.toUpperCase());
					correctoCombustible = true;
				} catch (IllegalArgumentException e) {
					System.out.println("El valor \"" + respuestaCombustible + "\" no es válido. Inténtalo de nuevo.");
					System.out.println("Debe ser: GASOLINA, DIESEL, HIBRIDO o ELECTRICO");
				}
			}

			System.out.println("Introduce el color del vehiculo: ");
			color = Utilidades.introducirCadena();

			System.out.println("Introduzca la fecha de alta del vehiculo: ");
			fechaAlta = Utilidades.leerFechaDMA();



			System.out.println("Introduce el vehiculo que es (COCHE | MOTO | FURGONETA): ");
			while (!correctoSwitch) {
				elegir = Utilidades.introducirCadena();

				switch (elegir.toUpperCase()) {
				case "COCHE":
					int puertas;
					boolean automatico, descapotable;

					System.out.println("Has elegido COCHE");

					System.out.println("Número de puertas:");
					puertas = Utilidades.leerInt();

					System.out.println("¿Es automático? (SI/NO):");
					automatico = Utilidades.introducirCadena().equalsIgnoreCase("SI");

					System.out.println("¿Es descapotable? (SI/NO):");
					descapotable = Utilidades.introducirCadena().equalsIgnoreCase("SI");

					System.out.println("Coche creado con:");
					System.out.println("Puertas: " + puertas);
					System.out.println("Automático: " + automatico);
					System.out.println("Descapotable: " + descapotable);
					Vehiculo c = new Coche(matricula, marca, modelo, precioBase, estado, combustible, color, fechaAlta, puertas, automatico, descapotable);
					moos.writeObject(c);
					moos.close();
					correcto = true;

					break;
				case "MOTO":
					int cilindrada;
					boolean deportiva;
					System.out.println("Has elegido MOTO");

					System.out.println("Cilindrada:");
					cilindrada = Utilidades.leerInt();

					System.out.println("¿Que tipo de moto es?:");
					while (!correctoTipoMoto) {
						try {
							respuestaTipoMoto = Utilidades.introducirCadena();
							tipoMoto = TipoMoto.valueOf(respuestaTipoMoto.toUpperCase());
							correctoTipoMoto = true;
						} catch (IllegalArgumentException e) {
							System.out.println("El valor \"" + respuestaTipoMoto + "\" no es válido. Inténtalo de nuevo.");
							System.out.println("Debe ser: GASOLINA, DIESEL, HIBRIDO o ELECTRICO");
						}
					}

					System.out.println("Moto creada con:");
					System.out.println("Cilindrada: " + cilindrada);
					System.out.println("Tipo: " + tipoMoto);
					Vehiculo m = new Moto(matricula, marca, modelo, precioBase, estado, combustible, color, fechaAlta, cilindrada, tipoMoto);
					moos.writeObject(m);
					moos.close();
					correcto = true;
					break;
				case "FURGONETA":
					int mma;
					boolean correderas;
					int asientos;

					System.out.println("Has elegido FURGONETA");

					System.out.println("MMA:");
					mma = Utilidades.leerInt();

					System.out.println("¿Tiene puertas correderas? (SI/NO):");
					correderas = Utilidades.introducirCadena().equalsIgnoreCase("SI");

					System.out.println("Número de asientos:");
					asientos = Utilidades.leerInt();

					System.out.println("Furgoneta creada con:");
					System.out.println("MMA: " + mma);
					System.out.println("Puertas correderas: " + correderas);
					System.out.println("Asientos: " + asientos);
					Vehiculo f = new Furgoneta(matricula, marca, modelo, precioBase, estado, combustible, color, fechaAlta, mma, correderas, asientos);
					moos.writeObject(f);
					moos.close();
					correcto = true;
					break;
				}
			}
		}

	}


}
