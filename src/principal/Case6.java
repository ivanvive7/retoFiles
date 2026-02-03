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
	
	public void introducirCliente() {
		String dni="", nom, ape, telf, reservarComprar, matricula="";
		boolean existe, valido, finArchivo=false, encontrado=false;
		Vehiculo vehiculo = null;
		Cliente cliente;
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
						oos = new ObjectOutputStream(new FileOutputStream(fichC));
						ois = new ObjectInputStream(new FileInputStream(fichV));
						System.out.println("Introduce el mombre del cliente:");
						nom=Utilidades.introducirCadena();
						System.out.println("Introduce el apellido del cliente:");
						ape=Utilidades.introducirCadena();
						System.out.println("Introduce el teléfono del cliente:");
						telf=Utilidades.introducirCadena();
						telf=ConcesionarioMain.validarTelf(telf);
						System.out.println("¿Qué va a hacer el cliente?");
						reservarComprar=Utilidades.introducirCadena("RESERVAR", "COMPRAR");
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
									}
								}catch(EOFException e) {
									finArchivo=true;
								} catch (ClassNotFoundException e) {
									e.printStackTrace();
								}
							}
							if (!encontrado) {
								System.out.println("No existe ningun vehículo con esa matrícula.");
							}
						} while (!encontrado);
						if (reservarComprar.equalsIgnoreCase("RESERVAR")) {
							vehiculo.setEstado(Estado.valueOf("RESERVADO"));
							cliente = new Cliente(dni, nom, ape, telf);
							cliente.getMapaVehiculos().put(matricula, vehiculo);
							oos.writeObject(cliente);
							oos.close();
							oos = new ObjectOutputStream(new FileOutputStream(fichAux));
							
						} else {
							
						}
					} catch (FileNotFoundException e) {
				        System.out.println("No se encontró el fichero");
				    } catch (IOException e) {
				        System.out.println("Error leyendo el fichero");
				    }
				} else {
					
				}
			} else {
				System.out.println("El cliente ya está registrado.");
			}
		} else {
			System.out.println("No hay vehículos que comprar o reservar.");
		}
		
	}
	
}
