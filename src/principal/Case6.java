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
import utilidades.MyObjectOutputStream;
import utilidades.Utilidades;

public class Case6 {

	public static void introducirCliente(File fichC, File fichV) {
		String dni = "", nom, ape, telf;
		boolean existe, valido;
		
		Cliente cliente;
		Vehiculo vehiculo = null;
		
		File fichVAux = new File("vehiculosAuxiliar6.dat");
		
		ObjectOutputStream oos;
		MyObjectOutputStream moos;

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
				if (!fichC.exists()) {
					try {
						oos = new ObjectOutputStream(new FileOutputStream(fichC));
						System.out.println("Introduce el mombre del cliente:");
						nom = Utilidades.introducirCadena();
						System.out.println("Introduce el apellido del cliente:");
						ape = Utilidades.introducirCadena();
						System.out.println("Introduce el teléfono del cliente:");
						telf = Utilidades.introducirCadena();
						telf = ConcesionarioMain.validarTelf(telf);
						vehiculo = reservaCompra(fichVAux, fichV, vehiculo);
						cliente = new Cliente(dni, nom, ape, telf);
						cliente.getMapaVehiculos().put(vehiculo.getMatricula(), vehiculo);
						oos.writeObject(cliente);
						oos.close();
					} catch (FileNotFoundException e) {
						System.out.println("No se encontró el fichero");
					} catch (IOException e) {
						System.out.println("Error leyendo el fichero");
					}
				} else {
					try {
						moos = new MyObjectOutputStream(new FileOutputStream(fichC, true));
						System.out.println("Introduce el mombre del cliente:");
						nom = Utilidades.introducirCadena();
						System.out.println("Introduce el apellido del cliente:");
						ape = Utilidades.introducirCadena();
						System.out.println("Introduce el teléfono del cliente:");
						telf = Utilidades.introducirCadena();
						telf = ConcesionarioMain.validarTelf(telf);
						vehiculo = reservaCompra(fichVAux, fichV, vehiculo);
						cliente = new Cliente(dni, nom, ape, telf);
						cliente.getMapaVehiculos().put(vehiculo.getMatricula(), vehiculo);
						moos.writeObject(cliente);
						moos.close();
					} catch (FileNotFoundException e) {
						System.out.println("No se encontró el fichero");
					} catch (IOException e) {
						System.out.println("Error leyendo el fichero");
					}
				}
			} else {
				System.out.println("El cliente ya está registrado.");
			}
		} else {
			System.out.println("No hay vehículos que comprar o reservar.");
		}

	}

	@SuppressWarnings("unlikely-arg-type")
	public static Vehiculo reservaCompra(File fichVAux, File fichV, Vehiculo vehiculo) throws FileNotFoundException, IOException {
		String reservarComprar, matricula = "";
		boolean valido, finArchivo = false, encontrado = false, respuesta = false;
		
		ObjectOutputStream oos;
		ObjectInputStream ois;

		System.out.println("¿Qué va a hacer el cliente?");
		reservarComprar = Utilidades.introducirCadena("RESERVAR", "COMPRAR");
		if (reservarComprar.equalsIgnoreCase("RESERVAR")) {
			oos = new ObjectOutputStream(new FileOutputStream(fichVAux));
			ois = new ObjectInputStream(new FileInputStream(fichV));
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
		} else {
			oos = new ObjectOutputStream(new FileOutputStream(fichVAux));
			ois = new ObjectInputStream(new FileInputStream(fichV));
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
								oos.writeObject(v);
								System.out.println("Este vehículo ya ha sido reservado por otra perosna.");
							} else {
								ConcesionarioMain.leerFicheroTexto();
								respuesta=Utilidades.introducirCadena("SI", "NO").equalsIgnoreCase("SI");
								if (respuesta) {
									v.setEstado(Estado.valueOf("VENDIDO"));
									vehiculo = v;
									System.out.println("Vehículo comprado con éxito.");
								} else {
									oos.writeObject(v);
									System.out.println("Compra cancelada.");
								}
								encontrado = true;
							}
						} else {
							oos.writeObject(v);
						}
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
		}
		return vehiculo;
	}
}
