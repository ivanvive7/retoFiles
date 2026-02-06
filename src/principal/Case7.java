package principal;

import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import clases.Cliente;
import clases.Estado;
import clases.Vehiculo;
import excepciones.DniException;
import excepciones.MatriculaException;
import utilidades.Utilidades;

public class Case7 {

	@SuppressWarnings("unlikely-arg-type")
	public static void introducirCliente(File fichC, File fichV) {
		String dni = "", reservarComprar, matricula = "";
		boolean valido, existe, finArchivo = false, encontrado = false;
		
		Cliente cliente;
		Vehiculo vehiculo = null;
		
		File fichVAux = new File("vehiculosAuxiliar7.dat");
		File fichCAux = new File("clientesAuxiliar7.dat");
		
		ObjectOutputStream oos;
		ObjectInputStream ois;
		
		if (fichC.exists()) {
			valido = false;
			do {
				try {
					System.out.println("Introduce el DNI del cliente:");
					dni = Utilidades.introducirCadena();
					valido = ConcesionarioMain.validarDni(dni);
				} catch (DniException e) {
					System.out.println(e.getMessage());
				}
			} while (!valido);
			existe = ConcesionarioMain.buscarDni(fichC, dni);
			if (existe) {
				System.out.println("Qué va a hacer el cliente?");
				reservarComprar=Utilidades.introducirCadena("RESERVAR", "COMPRAR");
				if (reservarComprar.equalsIgnoreCase("RESERVAR")) {
					try {
						ois = new ObjectInputStream(new FileInputStream(fichV));
						oos = new ObjectOutputStream(new FileOutputStream(fichVAux));
						do {
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
							while (!finArchivo) {
								try {
									Vehiculo v = (Vehiculo) ois.readObject();
									if (matricula.equals(v.getMatricula())) {
										if (v.getEstado().equals("RESERVADO")) {
											System.out.println("Este vehículo ya ha sido reservado por otra perosna.");
										} else {
											v.setEstado(Estado.valueOf("RESERVADO"));
											vehiculo = v;
											encontrado = true;
										}
									}
									oos.writeObject(v);
								} catch (EOFException e) {
									finArchivo = true;
								} catch (ClassNotFoundException e) {
									e.printStackTrace();
								}
							}
							if (!encontrado) {
								System.out.println("No existe ningun vehículo con esa matrícula.");
							}
						} while (!encontrado);
						oos.close();
						ois.close();
						fichV.delete();
						fichVAux.renameTo(fichV);
						System.out.println("Vehículo reservado con éxito.");
					} catch (FileNotFoundException e) {
						System.out.println("No se encontró el fichero");
					} catch (IOException e) {
						System.out.println("Error leyendo el fichero");
					}
				} else {
					try {
						do {
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
							ois = new ObjectInputStream(new FileInputStream(fichC));
							oos = new ObjectOutputStream(new FileOutputStream(fichCAux));
							while (!finArchivo) {
								try {
									Cliente c = (Cliente) ois.readObject();
									if (c.getDni().equals(dni)) {
										for (Vehiculo v: c.getMapaVehiculos().values()) {
											if (matricula.equals(v.getMatricula())) {
												if (v.getEstado().equals("RESERVADO")) {
													v.setEstado(Estado.valueOf("VENDIDO"));
													encontrado=true;
												} else {
													System.out.println("Este vehículo ya lo ha comprado.");
												}
											}	
										}
									}
								} catch (EOFException e) {
									finArchivo = true;
								} catch (ClassNotFoundException e) {
									e.printStackTrace();
								}
							}
							oos.close();
							ois.close();
							fichC.delete();
							fichCAux.renameTo(fichC);
							if (!encontrado) {
								ois = new ObjectInputStream(new FileInputStream(fichV));
								oos = new ObjectOutputStream(new FileOutputStream(fichVAux));
								while (!finArchivo) {
									try {
										Vehiculo v = (Vehiculo) ois.readObject();
										if (matricula.equals(v.getMatricula())) {
											vehiculo = v;
											encontrado = true;
										} else {
											oos.writeObject(v);
										}
									} catch (EOFException e) {
										finArchivo = true;
									} catch (ClassNotFoundException e) {
										e.printStackTrace();
									}
								}
							}
							oos.close();
							ois.close();
							fichV.delete();
							fichVAux.renameTo(fichV);
							if (!encontrado) {
								System.out.println("No existe ningun vehículo con esa matrícula.");
							}
						} while (!encontrado);
					} catch (FileNotFoundException e) {
						System.out.println("No se encontró el fichero");
					} catch (IOException e) {
						System.out.println("Error leyendo el fichero");
					}
					System.out.println("Vehículo comprado con éxito.");
				}
			} else {
				System.out.println("Ese cliente no existe");
			}
		} else {
			System.out.println("No hay clientes registrados.");
		}
	}
	
}
