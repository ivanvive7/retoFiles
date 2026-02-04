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
		ObjectInputStream ois2;
		
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
					
				} else {
					try {
						oos = new ObjectOutputStream(new FileOutputStream(fichVAux));
						ois = new ObjectInputStream(new FileInputStream(fichV));
						ois2 = new ObjectInputStream(new FileInputStream(fichC));
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
									Cliente c = (Cliente) ois2.readObject();
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
							if (!encontrado) {
								/*while (!finArchivo) {
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
								}*/
							}
							if (!encontrado) {
								System.out.println("No existe ningun vehículo con esa matrícula.");
							}
							oos.close();
							ois.close();
							ois2.close();
							fichV.delete();
							fichVAux.renameTo(fichV);
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
