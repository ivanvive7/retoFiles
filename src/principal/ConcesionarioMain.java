package principal;

import java.io.*;
import java.time.LocalDate;
import java.time.Period;
import java.util.HashMap;
import java.util.TreeMap;
import java.util.regex.Pattern;

import clases.*;
import excepciones.*;
import utilidades.*;

public class ConcesionarioMain {

	public static void main(String[] args) throws MatriculaException, IOException {
		// TODO Auto-generated method stub

		File fichAux = new File("vehiculosAuxiliar.dat");
		File fichV = new File("vehiculos.dat");
		File fichC = new File("clientes.dat");
		File fichT = new File("texto.dat");
 
		int opcion;

		if (fichV.exists()) {
			verificarAlInicio(fichV);
		}

		if (!fichT.exists()) {
			FillFicheroTexto();
		}

		do {
			opcion = menu();
			switch (opcion) {
			case 1:
				introducirVehiculo(fichV);
				break;
			case 2:
				listarVehiculos(fichV);
				break;
			case 3:
				listarPorTipo(fichV);
				break; 
			case 4:
				modificarVehiculo(fichV, fichAux);
				break;
			case 5:
				eliminarVehiculoPorMatricula(fichV, fichAux);			
				break;
			case 6:
				introducirCliente(fichC, fichV);
				break;
			case 7:
				Case7.introducirCliente(fichC, fichV);
				break;
			case 8:
				listarClientesOrdenados(fichC);
				break;
			case 9:
				buscarClienteDNI(fichC);
				break;
			case 10:
				System.out.println("Hasta la próxima.");
				break;
			}
		} while (opcion != 10);

	}
	
	 public static void introducirCliente(File fichC, File fichV) {
	        String dni = "", nom, ape, telf;
	        boolean existe, valido;
	        Vehiculo vehiculo = null;
	        File fichVAux = new File("vehiculosAuxiliar6.dat");

	        if (fichV.exists()) {
	            valido = false;
	            do {
	                try {
	                    System.out.println("Introduce el DNI del nuevo cliente:");
	                    dni = Utilidades.introducirCadena();
	                    valido = ConcesionarioMain.validarDni(dni);
	                } catch (DniException e) {
	                    System.out.println(e.getMessage());
	                }
	            } while (!valido);

	            existe = ConcesionarioMain.buscarDni(fichC, dni);
	            
	            if (!existe) {
	                System.out.println("Introduce el nombre del cliente:");
	                nom = Utilidades.introducirCadena();
	                System.out.println("Introduce el apellido del cliente:");
	                ape = Utilidades.introducirCadena();
	                System.out.println("Introduce el teléfono del cliente:");
	                telf = Utilidades.introducirCadena();
	                telf = ConcesionarioMain.validarTelf(telf);

	                try {
	                    vehiculo = reservaCompra(fichVAux, fichV);

	                    if (vehiculo != null) {
	                        Cliente cliente = new Cliente(dni, nom, ape, telf);
	                        cliente.getMapaVehiculos().put(vehiculo.getMatricula(), vehiculo);

	                        if (!fichC.exists()) {
	                            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(fichC));
	                            oos.writeObject(cliente);
	                            oos.close();
	                        } else {
	                            MyObjectOutputStream moos = new MyObjectOutputStream(new FileOutputStream(fichC, true));
	                            moos.writeObject(cliente);
	                            moos.close();
	                        }
	                        System.out.println("Cliente registrado con éxito.");
	                    }
	                } catch (IOException e) {
	                    System.out.println("Error procesando los ficheros: " + e.getMessage());
	                }
	            } else {
	                System.out.println("El cliente ya está registrado.");
	            }
	        } else {
	            System.out.println("No hay vehículos disponibles.");
	        }
	    }

	    @SuppressWarnings("unlikely-arg-type")
		public static Vehiculo reservaCompra(File fichVAux, File fichV) throws IOException {
	        String reservarComprar, matricula = "";
	        boolean valido, finArchivo, encontrado = false, respuesta, matriculaEncontrada, cancelado = false;
	        Vehiculo vehiculoResultado = null;
	        
	        ObjectOutputStream oos = null;
	        ObjectInputStream ois = null;

	        System.out.println("¿Qué va a hacer el cliente?");
	        reservarComprar = Utilidades.introducirCadena("RESERVAR", "COMPRAR");

	        do {
	            finArchivo = false;
	            matriculaEncontrada = false;
	            valido = false;

	            do {
	                try {
	                    System.out.println("Introduce la matrícula del vehículo:");
	                    matricula = Utilidades.introducirCadena();
	                    valido = ConcesionarioMain.validarMatricula(matricula);
	                } catch (MatriculaException e) {
	                    System.out.println(e.getMessage());
	                }
	            } while (!valido);

	            oos = new ObjectOutputStream(new FileOutputStream(fichVAux));
	            ois = new ObjectInputStream(new FileInputStream(fichV));

	            while (!finArchivo) {
	                try {
	                    Vehiculo v = (Vehiculo) ois.readObject();
	                    
	                    if (matricula.equalsIgnoreCase(v.getMatricula())) {
	                        matriculaEncontrada = true;
	                        
	                        if (v.getEstado().equals("RESERVADO") && reservarComprar.equals("RESERVAR")) {
	                            System.out.println("Este vehículo ya está reservado por otra persona.");
	                            oos.writeObject(v);
	                        } else {
	                            if (reservarComprar.equals("COMPRAR")) {
	                                ConcesionarioMain.leerFicheroTexto();
	                                System.out.println("¿Confirmar compra?");
	                                respuesta = Utilidades.introducirCadena("SI", "NO").equalsIgnoreCase("SI");
	                                
	                                if (respuesta) {
	                                    v.setEstado(Estado.valueOf("VENDIDO"));
	                                    vehiculoResultado = v;
	                                    encontrado = true;
	                                } else {
	                                    System.out.println("Compra cancelada.");
	                                    cancelado = true;
	                                }
	                            } else {
	                                v.setEstado(Estado.valueOf("RESERVADO"));
	                                vehiculoResultado = v;
	                                encontrado = true;
	                            }
	                            oos.writeObject(v);
	                        }
	                    } else {
	                        oos.writeObject(v);
	                    }
	                } catch (EOFException e) {
	                    finArchivo = true;
	                } catch (ClassNotFoundException e) {
	                    System.out.println("Error de clase: " + e.getMessage());
	                }
	            }
	            
	            oos.close();
	            ois.close();

	            if (!matriculaEncontrada) {
	                System.out.println("No existe ningún vehículo con esa matrícula.");
	            } else {
	                fichV.delete();
	                fichVAux.renameTo(fichV);
	            }

	        } while (!encontrado && !cancelado); 

	        return vehiculoResultado;
	    }

	private static void eliminarVehiculoPorMatricula(File fichV, File fichAux) {
		String matricula = null;
		ObjectOutputStream oos = null;
		ObjectInputStream ois = null;
		boolean finArchivo = false, encontrado = false, valido = false;

		if (fichV.exists()) {
			try {
				do {
					try {
						System.out.println("Introduce la matrícula del vehículo:");
						matricula = Utilidades.introducirCadena();
						valido = ConcesionarioMain.validarMatricula(matricula);
					} catch (MatriculaException e) {
						System.out.println(e.getMessage());
					}
				} while (!valido);

				ois = new ObjectInputStream(new FileInputStream(fichV));
				oos = new ObjectOutputStream(new FileOutputStream(fichAux));

				while (!finArchivo) {
					try {
						Vehiculo v = (Vehiculo) ois.readObject();
						if (!v.getMatricula().equalsIgnoreCase(matricula)) {
							oos.writeObject(v);
						} else {
							encontrado = true;
						}
					} catch (EOFException e) {
						finArchivo = true;
					}
				}

				ois.close();
				oos.close();

				if (encontrado) {
					fichV.delete();
					fichAux.renameTo(fichV);
					System.out.println("Vehículo eliminado correctamente.");
				} else {
					fichAux.delete(); 
					System.out.println("No hay ningún vehículo registrado con esa matrícula.");
				}

			} catch (FileNotFoundException e) {
				System.out.println("No se encontró el fichero.");
			} catch (ClassNotFoundException e) {
				System.out.println("La clase Vehiculo no es válida.");
			} catch (IOException e) {
				System.out.println("Error leyendo/escribiendo el fichero: " + e.getMessage());
			}
		} else {
			System.out.println("El fichero no existe.");
		}
	}

	public static void listarVehiculos(File fichV) {
		ObjectInputStream ois = null;
		boolean finArchivo = false;
		boolean hayVehiculos = false;

		if (fichV.exists()) {
			try {
				ois = new ObjectInputStream(new FileInputStream(fichV));
				while (!finArchivo) {
					try {
						Vehiculo v = (Vehiculo) ois.readObject();
						v.visualizar();
						hayVehiculos = true;

					} catch (EOFException v) {
						finArchivo = true;
					}
				}

				ois.close();

				if (!hayVehiculos) {
					System.out.println("No hay vehículos registrados.");
				}

			} catch (FileNotFoundException e) {
				System.out.println("No se encontró el fichero.");
			} catch (ClassNotFoundException e) {
				System.out.println("La clase Vehículo no es válida.");
			} catch (IOException e) {
				System.out.println("Error leyendo el fichero.");
			}
		} else {
			System.out.println("No hay vehículos registrados.");
		}
	}

	private static void listarPorTipo(File fichV) {

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
					if (ois != null)
						ois.close();
				} catch (IOException e) {
					System.out.println("Error cerrando el fichero.");
				}
			}
		}
	}

	private static File modificarVehiculo(File fichV, File fichAux) throws MatriculaException {
		int opcion;
		String matricula, marca, modelo, combustible = "", color, respuesta;
		double precioBase;
		LocalDate fechaAlta;
		ObjectOutputStream oos;
		ObjectInputStream ois = null;
		boolean finArchivo = false, encontrado = false, correctoCombustible = false, matriculaRepe = false;

		if (fichV.exists()) {
			try {
				oos = new ObjectOutputStream(new FileOutputStream(fichAux));
				ois = new ObjectInputStream(new FileInputStream(fichV));

				System.out.println("Introduce la matrícula: ");
				matricula = Utilidades.introducirCadena();

				while (!finArchivo) {
					try {
						Vehiculo v = (Vehiculo) ois.readObject();
						if (v.getMatricula().equalsIgnoreCase(matricula)) {
							do {
								encontrado = true;
								finArchivo = false;
								System.out.println(
										"¿Qué quieres modificar?" + "\n1. Matricula." + "\n2. Marca." + "\n3. Modelo."
												+ "\n4. Precio base." + "\n5. Combustible." + "\n6. Fecha de alta."
												+ "\n7. Color." + "\n8. Otro." + "\nSelecciona una opción: ");
								opcion = Utilidades.leerInt(1, 8);

								switch (opcion) {
								case 1:
									matriculaRepe = false;
									do {
										System.out.println("Introduce la nueva matrícula: ");
										matricula = Utilidades.introducirCadena();
										if (ConcesionarioMain.buscarMatricula(fichV, matricula)) {
											matriculaRepe = true;
											System.out.println("Ya hay un vehículo registrado con esa matrícula.");
										}
										// esto hay que cambiarlo cuando lo pasemos al main
									} while (!ConcesionarioMain.validarMatricula(matricula) || matriculaRepe);
									v.setMatricula(matricula);
									break;
								case 2:
									System.out.println("Introduce la nueva marca: ");
									marca = Utilidades.introducirCadena();
									v.setMarca(marca);
									break;
								case 3:
									System.out.println("Introduce el nuevo modelo: ");
									modelo = Utilidades.introducirCadena();
									v.setModelo(modelo);
									break;
								case 4:
									System.out.println("Introduce el nuevo precio base: ");
									precioBase = Utilidades.leerDouble(0, Integer.MAX_VALUE);
									v.setPrecioBase(precioBase);
									break;
								case 5:
									System.out.println(
											"Introduce el combustible del vehiculo (GASOLINA | DIESEL | HIBRIDO | ELECTRICO): ");

									while (!correctoCombustible) {
										try {
											combustible = Utilidades.introducirCadena();
											v.setCombustible(Combustible.valueOf(combustible.toUpperCase()));
											correctoCombustible = true;
										} catch (IllegalArgumentException e) {
											System.out.println("El valor \"" + combustible
													+ "\" no es válido. Inténtalo de nuevo.");
											System.out.println("Debe ser: GASOLINA, DIESEL, HIBRIDO o ELECTRICO");
										}
									}
									break;
								case 6:
									System.out.println("Introduce la nueva fecha de alta (dd/MM/yyyy): ");
									fechaAlta = Utilidades.leerFechaDMA();
									v.setFechaAlta(fechaAlta);
									break;
								case 7:
									System.out.println("Introduce el nuevo color: ");
									color = Utilidades.introducirCadena();
									v.setColor(color);
									break;
								case 8:
									modificacionPorTipo(v);
									break;
								}

								System.out.println("¿Quieres modificar otra cosa?");
								respuesta = Utilidades.introducirCadena("SI", "NO");

							} while (respuesta.equalsIgnoreCase("Si"));

						}
						oos.writeObject(v);

					} catch (EOFException e) {
						finArchivo = true;
					}
				}

				ois.close();
				oos.close();

				if (encontrado) {
					fichV.delete();
					fichAux.renameTo(fichV);
				} else {
					fichAux.delete();
					System.out.println("No hay ningún vehículo registrado con esa matrícula.");
				}

			} catch (FileNotFoundException e) {
				System.out.println("No se encontró el fichero.");
			} catch (ClassNotFoundException e) {
				System.out.println("La clase Vehiculo no es válida.");
			} catch (IOException e) {
				System.out.println("Error leyendo el fichero.");
			}
		} else {
			System.out.println("El fichero no existe.");
		}
		return fichAux;
	}

	private static void introducirVehiculo(File fichV) throws IOException {
		ObjectOutputStream oos = null;
		MyObjectOutputStream moos = null;
		boolean valida = false, correctoEstado = false, correctoCombustible = false, correctoSwitch = false,
				correctoTipoMoto = false;
		String matricula = "", marca = "", modelo = "", respuestaEstado = "", respuestaCombustible = "", color = "",
				elegir = "", respuestaTipoMoto = "";
		double precioBase = 0.0;
		Estado estado = null;
		Combustible combustible = null;
		LocalDate fechaAlta = null;

		// Variables coche
		int puertas;
		boolean automatico, descapotable;

		// Variables moto
		int cilindrada;
		TipoMoto tipoMoto = null;

		// Vatiables furgoneta
		int mma;
		boolean correderas;
		int asientos;

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

			System.out.println("Introduzca la fecha de alta del vehiculo (dd/mm/aaaa): ");
			fechaAlta = Utilidades.leerFechaDMA();

			System.out.println("Introduce el tipo de vehículo: ");
			while (!correctoSwitch) {
				elegir = Utilidades.introducirCadena("COCHE", "MOTO", "FURGONETA");

				switch (elegir.toUpperCase()) {
				case "COCHE":

					System.out.println("Has elegido COCHE");

					System.out.println("Número de puertas:");
					puertas = Utilidades.leerInt(2, 5);

					System.out.println("¿Es automático? (SI/NO):");
					automatico = Utilidades.introducirCadena("SI", "NO").equalsIgnoreCase("SI");

					System.out.println("¿Es descapotable? (SI/NO):");
					descapotable = Utilidades.introducirCadena("SI", "NO").equalsIgnoreCase("SI");

					System.out.println("Coche creado con:");
					System.out.println("Puertas: " + puertas);
					System.out.print("Automático: ");
					if (automatico) {
						System.out.println("si");
					} else {
						System.out.println("no");
					}
					System.out.print("Descapotable: ");
					if (descapotable) {
						System.out.println("si");
					} else {
						System.out.println("no");
					}
					Vehiculo c = new Coche(matricula, marca, modelo, precioBase, combustible, color, fechaAlta, puertas,
							automatico, descapotable);
					oos.writeObject(c);
					oos.close();
					correctoSwitch = true;

					break;
				case "MOTO":
					System.out.println("Has elegido MOTO");

					System.out.println("Cilindrada:");
					cilindrada = Utilidades.leerInt(1, 3000);

					System.out.println("¿Que tipo de moto es? (NAKED, CUSTOM, MOTOCROSS, SPORT, TRAIL): ");
					while (!correctoTipoMoto) {
						try {
							respuestaTipoMoto = Utilidades.introducirCadena();
							tipoMoto = TipoMoto.valueOf(respuestaTipoMoto.toUpperCase());
							correctoTipoMoto = true;
						} catch (IllegalArgumentException e) {
							System.out.println(
									"El valor \"" + respuestaTipoMoto + "\" no es válido. Inténtalo de nuevo.");
							System.out.println("Debe ser: NAKED, CUSTOM, MOTOCROSS, SPORT, TRAIL. ");
						}
					}

					System.out.println("Moto creada con:");
					System.out.println("Cilindrada: " + cilindrada);
					System.out.println("Tipo: " + tipoMoto);
					Vehiculo m = new Moto(matricula, marca, modelo, precioBase, combustible, color, fechaAlta,
							cilindrada, tipoMoto);
					oos.writeObject(m);
					oos.close();
					correctoSwitch = true;
					break;
				case "FURGONETA":

					System.out.println("Has elegido FURGONETA");

					System.out.println("MMA:");
					mma = Utilidades.leerInt(0, 3500);

					System.out.println("¿Tiene puertas correderas?");
					correderas = Utilidades.introducirCadena("SI", "NO").equalsIgnoreCase("SI");

					System.out.println("Número de asientos:");
					asientos = Utilidades.leerInt(2, 10);

					System.out.println("Furgoneta creada con:");
					System.out.println("MMA: " + mma);
					System.out.print("Puertas correderas: ");
					if (correderas) {
						System.out.println("si");
					} else {
						System.out.println("no");
					}
					System.out.println("Asientos: " + asientos);
					Vehiculo f = new Furgoneta(matricula, marca, modelo, precioBase, combustible, color, fechaAlta, mma,
							correderas, asientos);
					oos.writeObject(f);
					oos.close();
					correctoSwitch = true;
					break;
				}

			}

		} else {
			try {
				moos = new MyObjectOutputStream(new FileOutputStream(fichV, true));
				System.out.println("Modificacion del fichero");
				do {
					System.out.println("Introduce la matricula del vehiculo: ");
					matricula = Utilidades.introducirCadena();

					try {
						valida = ConcesionarioMain.validarMatricula(matricula);

					} catch (MatriculaException e) {
						System.out.println(e.getMessage());
					}

				} while (!valida);

				if (!ConcesionarioMain.buscarMatricula(fichV, matricula)) {
					System.out.println("Introduce la marca del vehiculo: ");
					marca = Utilidades.introducirCadena();
					System.out.println("Introduce el modelo del vehiculo: ");
					modelo = Utilidades.introducirCadena();
					System.out.println("Introduce el precio base del vehiculo: ");
					precioBase = Utilidades.leerDouble();

					System.out.println(
							"Introduce el combustible del vehiculo (GASOLINA | DIESEL | HIBRIDO | ELECTRICO): ");

					while (!correctoCombustible) {
						try {
							respuestaCombustible = Utilidades.introducirCadena();
							combustible = Combustible.valueOf(respuestaCombustible.toUpperCase());
							correctoCombustible = true;
						} catch (IllegalArgumentException e) {
							System.out.println(
									"El valor \"" + respuestaCombustible + "\" no es válido. Inténtalo de nuevo.");
							System.out.println("Debe ser: GASOLINA, DIESEL, HIBRIDO o ELECTRICO");
						}
					}

					System.out.println("Introduce el color del vehiculo: ");
					color = Utilidades.introducirCadena();

					System.out.println("Introduzca la fecha de alta del vehiculo (dd/mm/aaaa): ");
					fechaAlta = Utilidades.leerFechaDMA();

					System.out.println("Introduce el tipo de vehículo: ");
					while (!correctoSwitch) {
						elegir = Utilidades.introducirCadena("COCHE", "MOTO", "FURGONETA");

						switch (elegir.toUpperCase()) {
						case "COCHE":

							System.out.println("Has elegido COCHE");

							System.out.println("Número de puertas:");
							puertas = Utilidades.leerInt(2, 5);

							System.out.println("¿Es automático?:");
							automatico = Utilidades.introducirCadena("SI", "NO").equalsIgnoreCase("SI");

							System.out.println("¿Es descapotable?");
							descapotable = Utilidades.introducirCadena("SI", "NO").equalsIgnoreCase("SI");

							System.out.println("Coche creado con:");
							System.out.println("Puertas: " + puertas);
							System.out.print("Automático: ");
							if (automatico) {
								System.out.println("si");
							} else {
								System.out.println("no");
							}
							System.out.print("Descapotable: ");
							if (descapotable) {
								System.out.println("si");
							} else {
								System.out.println("no");
							}
							Vehiculo c = new Coche(matricula, marca, modelo, precioBase, combustible, color, fechaAlta,
									puertas, automatico, descapotable);
							moos.writeObject(c);
							moos.close();
							correctoSwitch = true;
							break;

						case "MOTO":

							System.out.println("Has elegido MOTO");

							System.out.println("Cilindrada:");
							cilindrada = Utilidades.leerInt(1, 3000);

							System.out.println("¿Que tipo de moto es? (NAKED, CUSTOM, MOTOCROSS, SPORT, TRAIL): ");
							while (!correctoTipoMoto) {
								try {
									respuestaTipoMoto = Utilidades.introducirCadena();
									tipoMoto = TipoMoto.valueOf(respuestaTipoMoto.toUpperCase());
									correctoTipoMoto = true;
								} catch (IllegalArgumentException e) {
									System.out.println(
											"El valor \"" + respuestaTipoMoto + "\" no es válido. Inténtalo de nuevo.");
									System.out.println("Debe ser: NAKED, CUSTOM, MOTOCROSS, SPORT, TRAIL");
								}
							}

							System.out.println("Moto creada con:");
							System.out.println("Cilindrada: " + cilindrada);
							System.out.println("Tipo: " + tipoMoto);
							Vehiculo m = new Moto(matricula, marca, modelo, precioBase, combustible, color, fechaAlta,
									cilindrada, tipoMoto);
							moos.writeObject(m);
							moos.close();
							correctoSwitch = true;
							break;

						case "FURGONETA":

							System.out.println("Has elegido FURGONETA");

							System.out.println("MMA:");
							mma = Utilidades.leerInt(0, Integer.MAX_VALUE);

							System.out.println("¿Tiene puertas correderas?");
							correderas = Utilidades.introducirCadena("SI", "NO").equalsIgnoreCase("SI");

							System.out.println("Número de asientos:");
							asientos = Utilidades.leerInt(1, 10);

							System.out.println("Furgoneta creada con:");
							System.out.println("MMA: " + mma);
							System.out.print("Puertas correderas: ");
							if (correderas) {
								System.out.println("si");
							} else {
								System.out.println("no");
							}
							System.out.println("Asientos: " + asientos);
							Vehiculo f = new Furgoneta(matricula, marca, modelo, precioBase, combustible, color,
									fechaAlta, mma, correderas, asientos);
							moos.writeObject(f);
							moos.close();
							correctoSwitch = true;
							break;
						}
					}

				} else {
					System.out.println("Ya hay un vehículo registrado con esa matrícula.");
				}

			} catch (FileNotFoundException e) {
				System.out.println("Error, fichero no encontrado");
			} catch (IOException e) {
				System.out.println("Error en la entrada de datos");
			}

		}
	}

	private static void listarClientesOrdenados(File fichC) {
		TreeMap<String, Cliente> clientesOrdenados= new TreeMap<String, Cliente>();
		if(fichC.exists()) {
			meterClientesEnTreeMap(fichC, clientesOrdenados);
			if(clientesOrdenados.isEmpty()) {
				System.out.println("No hay clientes para mostrar.");
			}else {
				for(Cliente c: clientesOrdenados.values()) {
					System.out.println(c);
				}
			}
		}else {
			System.out.println("Todavía no hay ningún cliente registrado.");
		}
		
		
	}

	public static void meterClientesEnTreeMap(File fichC, TreeMap<String, Cliente> clientesOrdenados) {
		boolean finArchivo=false, encontrado=false;
		ObjectInputStream ois=null;

		try {
			ois=new ObjectInputStream(new FileInputStream(fichC));
			while(!finArchivo) {
				try {
					Cliente c=(Cliente) ois.readObject();
					clientesOrdenados.put(c.getNombre(), c);

				}catch(EOFException e) {
					finArchivo=true;
				}
			}
			ois.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	private static void buscarClienteDNI(File fichC) {
		ObjectInputStream ois = null;
		boolean finArchivo = false;
		String dni;

		System.out.println("Introduce el DNI: ");
		dni = Utilidades.introducirCadena();

		if (fichC.exists()) {
			try {
				ois = new ObjectInputStream(new FileInputStream(fichC));
				while (!finArchivo) {
					try {
						Cliente c = (Cliente) ois.readObject();
						if (c.getDni().equals(dni)) {
							System.out.println(c);
						}
					} catch (EOFException e) {
						finArchivo = true;
					}
				}
				ois.close();
			} catch (FileNotFoundException e) {
				System.out.println("No se encontró el fichero.");
			} catch (ClassNotFoundException e) {
				System.out.println("La clase Cliente no es válida.");
			} catch (IOException e) {
				System.out.println("Error leyendo el fichero.");
			}
		} else {
			System.out.println("El fichero no existe.");
		}
	}

	public static int menu() {
		int opcion;
		System.out.println("--------------------------MENÚ--------------------------" + "\n1.  Introducir vehículos."
				+ "\n2.  Listar vehículos." + "\n3.  Listar vehículos por tipo." + "\n4.  Modificar vehículo."
				+ "\n5.  Eliminar un vehículo defectuoso."
				+ "\n6.  Introducir clientes que van a reservar o comprar un vehículo."
				+ "\n7.  Añadir una compra o reserva a un cliente ya registrado." + "\n8.  Mostrar clientes."
				+ "\n9.  Buscar clientes." + "\n10. Salir." + "\nSelecciona una opción: ");
		opcion = Utilidades.leerInt(1, 10);
		return opcion;
	}

	public static boolean buscarMatricula(File fichV, String matricula) {
		boolean encontrado = false;
		ObjectInputStream ois = null;
		boolean finArchivo = false;
		if (fichV.exists()) {
			try {
				ois = new ObjectInputStream(new FileInputStream(fichV));
				while (!finArchivo) {
					try {
						Vehiculo v = (Vehiculo) ois.readObject();
						if (matricula.equals(v.getMatricula())) {
							encontrado = true;
						}
					} catch (EOFException e) {
						finArchivo = true;
					}
				}
				ois.close();
			} catch (FileNotFoundException e) {
				System.out.println("No se encontró el fichero.");
			} catch (ClassNotFoundException e) {
				System.out.println("La clase Vehiculo no es válida.");
			} catch (IOException e) {
				System.out.println("Error leyendo el fichero.");
			}
		}
		return encontrado;
	}

	public static boolean buscarDni(File fichC, String dni) {
		boolean encontrado = false;
		ObjectInputStream ois = null;
		boolean finArchivo = false;
		if (fichC.exists()) {
			try {
				ois = new ObjectInputStream(new FileInputStream(fichC));
				while (!finArchivo) {
					try {
						Cliente c = (Cliente) ois.readObject();
						if (dni.equals(c.getDni())) {
							encontrado = true;
						}
					} catch (EOFException e) {
						finArchivo = true;
					}
				}
				ois.close();
			} catch (FileNotFoundException e) {
				System.out.println("No se encontró el fichero.");
			} catch (ClassNotFoundException e) {
				System.out.println("La clase Cliente no es válida.");
			} catch (IOException e) {
				System.out.println("Error leyendo el fichero.");
			}
		}
		return encontrado;
	}

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

	public static boolean validarDni(String dni) throws DniException {
		boolean valido = false;
		if (Pattern.matches("^[0-9]{8}[A-HJ-NP-TV-Z]$", dni)) {
			valido = true;
		} else {
			throw new DniException("Error: el formato del DNI no es correcto");
		}
		return valido;
	}

	public static void verificarAlInicio(File fichV) throws IOException {
		LocalDate hoy = LocalDate.now();
		boolean hayAvisos = false;
		boolean finFichero = false;
		String presionar = "";

		ObjectInputStream ois = new ObjectInputStream(new FileInputStream(fichV));

		System.out.println("\n AVISOS DE VEHÍCULOS CON 1 AÑO O MÁS: \n");

		while (!finFichero) {
			try {
				Vehiculo v = (Vehiculo) ois.readObject();
				LocalDate fechaAlta = v.getFechaAlta();
				Period periodo = Period.between(fechaAlta, hoy);

				if (periodo.getYears() >= 1) {
					hayAvisos = true;

					System.out.println("Matrícula: " + v.getMatricula());
					System.out.println("Marca: " + v.getMarca());
					System.out.println("Modelo: " + v.getModelo());
					System.out.println("Tiempo en stock: " + periodo.getYears() + " año(s) y " + periodo.getMonths()
					+ " mes(es) \n");
				}

			} catch (EOFException e) {
				finFichero = true;
			} catch (ClassNotFoundException e) {
				System.err.println("Error al leer vehículo");
			}
		}

		ois.close();

		if (hayAvisos) {
			System.out.println("Presiona ENTER para continuar...");
			presionar = Utilidades.introducirCadena();
		}
	}

	public static String validarTelf(String telf) {
		boolean valido = false;
		do {
			if (Pattern.matches("^(\\+34)?[ -]?[0-9]{3}[ -]?[0-9]{3}[ -]?[0-9]{3}$", telf)) {
				valido = true;
			} else {
				System.out.println("Formato de teléfono invalido, inténtelo de nuevo");
				telf = Utilidades.introducirCadena();
			}
		} while (!valido);

		return telf;
	}

	public static void modificacionPorTipo(Vehiculo v) {
		int opcion;
		String respuesta, tipo = null;
		boolean correctoTipo = false;

		if (v instanceof Coche) {
			System.out.println("¿Qué quieres modificar?" + "\n1. Número de puertas." + "\n2. Automático."
					+ "\n3. Descapotable." + "\nSelecciona una opción: ");
			opcion = Utilidades.leerInt(1, 3);
			switch (opcion) {
			case 1:
				System.out.println("Introduce el nuevo número de puertas: ");
				((Coche) v).setnPuertas(Utilidades.leerInt(2, 5));
				break;
			case 2:
				System.out.println("¿Es automático?");
				respuesta = Utilidades.introducirCadena("SI", "NO");
				if (respuesta.equalsIgnoreCase("Si")) {
					((Coche) v).setAutomatico(true);
				} else {
					((Coche) v).setAutomatico(false);
				}
				break;
			case 3:
				System.out.println("¿Es descapotable?");
				respuesta = Utilidades.introducirCadena("Si", "No");
				if (respuesta.equalsIgnoreCase("Si")) {
					((Coche) v).setDescapotable(true);
				} else {
					((Coche) v).setDescapotable(false);
				}
				break;
			}
		} else if (v instanceof Moto) {
			System.out.println("¿Qué quieres modificar?" + "\n1. Cilindrada." + "\n2. Tipo de moto."
					+ "\nSelecciona una opcion: ");
			opcion = Utilidades.leerInt(1, 2);
			switch (opcion) {
			case 1:
				System.out.println("Introduce la nueva cilindrada: ");
				((Moto) v).setCilindrada(Utilidades.leerInt(1, 3000));
				break;
			case 2:
				System.out.println("Introduce el nuevo tipo de moto (NAKED, CUSTOM, MOTOCROSS, SPORT, TRAIL):");
				while (!correctoTipo) {
					try {
						tipo = Utilidades.introducirCadena();
						((Moto) v).setTipo(TipoMoto.valueOf(tipo.toUpperCase()));
						correctoTipo = true;
					} catch (IllegalArgumentException e) {
						System.out.println("El valor \"" + tipo + "\" no es válido. Inténtalo de nuevo.");
						System.out.println("Debe ser: NAKED, CUSTOM, MOTOCROSS, SPORT, TRAIL. ");
					}
				}
				break;
			}
		} else if (v instanceof Furgoneta) {
			System.out.println("¿Qué quieres modificar?" + "\n1. MMA." + "\n2. Puertas correderas."
					+ "\n3. Número de asientos." + "\nSelecciona una opción: ");
			opcion = Utilidades.leerInt(1, 3);
			switch (opcion) {
			case 1:
				System.out.println("Introduce la nueva MMA: ");
				((Furgoneta) v).setMma(Utilidades.leerInt(0, 3500));
				break;
			case 2:
				System.out.println("¿Tiene puertas correderas?");
				respuesta = Utilidades.introducirCadena("SI", "NO");
				if (respuesta.equalsIgnoreCase("Si")) {
					((Furgoneta) v).setPuertasCorrederas(true);
				} else {
					((Furgoneta) v).setPuertasCorrederas(false);
				}
				break;
			case 3:
				System.out.println("Introduce el nuevo número de asientos: ");
				((Furgoneta) v).setnAsientos(Utilidades.leerInt(2, 10));
				break;
			}
		}
	}

	public static void FillFicheroTexto() {
		FileWriter fichero = null;
		BufferedWriter bw = null;

		try {
			fichero = new FileWriter("Condiciones_Privacidad.txt");
			bw = new BufferedWriter(fichero);

			bw.write("CONDICIONES DE COMPRA Y PRIVACIDAD");
			bw.newLine();
			bw.write("1. El comprador confirma haber recibido informacion clara sobre el vehiculo y su precio final.");
			bw.newLine();
			bw.write("2. La compra queda formalizada tras la firma del contrato y el pago acordado.");
			bw.newLine();
			bw.write("3. Cualquier cambio en precio, equipamiento o plazos sera comunicado para su aprobacion.");
			bw.newLine();
			bw.write("4. El comprador aportara la documentacion necesaria para la matriculacion o transferencia.");
			bw.newLine();
			bw.write("5. El desistimiento se regira por las condiciones del contrato de compraventa.");
			bw.newLine();
			bw.write("6. El vehiculo dispone de la garantia legal vigente y, si procede, garantias adicionales.");
			bw.newLine();
			bw.write("7. Los datos del comprador se usaran solo para gestionar la compra y servicios asociados.");
			bw.newLine();
			bw.write(
					"8. El concesionario garantiza la confidencialidad y el cumplimiento de la normativa de proteccion de datos.");
			bw.newLine();
			bw.write("9. El comprador puede ejercer sus derechos legales mediante solicitud al concesionario.");
			bw.newLine();

			bw.close();
			fichero.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void leerFicheroTexto() throws IOException {
		File archivo = new File("Condiciones_Privacidad.txt");
		FileReader fr = null;
		BufferedReader br = null;

		try {
			fr = new FileReader(archivo);
			br = new BufferedReader(fr);

			String linea;

			while ((linea = br.readLine()) != null) {
				System.out.println(linea);
			}

			br.close();
			fr.close();

		} catch (FileNotFoundException e) {
			System.out.println("El archivo no existe o no se encuentra en la ruta indicada.");
			e.printStackTrace();
		}
	}
}