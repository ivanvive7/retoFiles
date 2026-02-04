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
	
	File fichC = new File("clientes.dat");
	File fichV = new File("vehiculos.dat");
	
	public static void introducirCliente(File fichC, File fichV) {
		String dni="", nom, ape, telf;
		boolean existe, valido=false;
		Cliente cliente;
		Vehiculo vehiculo = null;
		File fichAux = new File("vehiculosAuxiliar.dat");
		ObjectOutputStream oos;
		MyObjectOutputStream moos;
		ObjectInputStream ois;
		
		if (fichV.exists()) {
			valido=false;
			do {
				try {
					System.out.println("Introduce el DNI del nuevo cliente:");
					dni=Utilidades.introducirCadena();
					valido=ConcesionarioMain.validarDni(dni);
				} catch (DniException e) {
					System.out.println(e.getMessage());
				}
			} while (!valido);
			existe=ConcesionarioMain.buscarDni(fichC, dni);
			if (!existe) {
				if (!fichC.exists()) {
					try {
						System.out.println("Introduce el mombre del cliente:");
						nom=Utilidades.introducirCadena();
						System.out.println("Introduce el apellido del cliente:");
						ape=Utilidades.introducirCadena();
						System.out.println("Introduce el teléfono del cliente:");
						telf=Utilidades.introducirCadena();
						telf=ConcesionarioMain.validarTelf(telf);
						vehiculo=reservaCompra(fichAux, fichV, vehiculo);
						oos = new ObjectOutputStream(new FileOutputStream(fichC));
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
						System.out.println("Introduce el mombre del cliente:");
						nom=Utilidades.introducirCadena();
						System.out.println("Introduce el apellido del cliente:");
						ape=Utilidades.introducirCadena();
						System.out.println("Introduce el teléfono del cliente:");
						telf=Utilidades.introducirCadena();
						telf=ConcesionarioMain.validarTelf(telf);
						vehiculo=reservaCompra(fichAux, fichV, vehiculo);
						moos = new MyObjectOutputStream(new FileOutputStream(fichC, true));
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
	
	public static Vehiculo reservaCompra(File fichAux, File fichV, Vehiculo vehiculo) throws FileNotFoundException, IOException {
		String reservarComprar, matricula="";
		boolean valido, finArchivo=false, encontrado=false;
		ObjectOutputStream oos;
		ObjectInputStream ois;
		
		System.out.println("¿Qué va a hacer el cliente?");
		reservarComprar=Utilidades.introducirCadena("RESERVAR", "COMPRAR");
		if (reservarComprar.equalsIgnoreCase("RESERVAR")) {
			oos = new ObjectOutputStream(new FileOutputStream(fichAux));
			ois = new ObjectInputStream(new FileInputStream(fichV));
			do {
				valido=false;
				do {
					try {
						System.out.println("Introduce la matrícula del vehículo:");
						matricula=Utilidades.introducirCadena();
						valido=ConcesionarioMain.validarMatricula(matricula);
					} catch (MatriculaException e) {
						System.out.println(e.getMessage());
					}
				} while (!valido);
				while (!finArchivo) { 
					try {
						Vehiculo v=(Vehiculo) ois.readObject();
						if(matricula.equals(v.getMatricula())) {
							vehiculo.setEstado(Estado.valueOf("RESERVADO"));
							vehiculo=v;
							encontrado=true;
						}
						oos.writeObject(v);
					}catch(EOFException e) {
						finArchivo=true;
					} catch (ClassNotFoundException e) {
						e.printStackTrace();
					}
				}
				if (!encontrado) {
					System.out.println("No existe ningun vehículo con esa matrícula.");
				}
				oos.close();
				ois.close();
				fichV.delete();
				fichAux.renameTo(fichV);
			} while (!encontrado);
		} else {
			oos = new ObjectOutputStream(new FileOutputStream(fichAux));
			ois = new ObjectInputStream(new FileInputStream(fichV));
			do {
				valido=false;
				do {
					try {
						System.out.println("Introduce la matrícula del vehículo:");
						matricula=Utilidades.introducirCadena();
						valido=ConcesionarioMain.validarMatricula(matricula);
					} catch (MatriculaException e) {
						System.out.println(e.getMessage());
					}
				} while (!valido);
				while (!finArchivo) { 
					try {
						Vehiculo v=(Vehiculo) ois.readObject();
						if(matricula.equals(v.getMatricula())) {
							vehiculo=v;
							encontrado=true;
						} else {
							oos.writeObject(v);
						}
						oos.writeObject(v);
					}catch(EOFException e) {
						finArchivo=true;
					} catch (ClassNotFoundException e) {
						e.printStackTrace();
					}
				}
				if (!encontrado) {
					System.out.println("No existe ningun vehículo con esa matrícula.");
				}
				oos.close();
				ois.close();
				fichV.delete();
				fichAux.renameTo(fichV);
			} while (!encontrado);
		}
		return vehiculo;
	}
}
