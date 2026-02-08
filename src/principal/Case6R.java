package principal;

import java.io.*;
import clases.*;
import excepciones.*;
import utilidades.*;

public class Case6R {

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
}