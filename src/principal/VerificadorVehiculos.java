package principal;

import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.time.LocalDate;
import java.time.Period;
import clases.*;
import utilidades.Utilidades;

public class VerificadorVehiculos {

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
                    System.out.println("Tiempo en stock: " + periodo.getYears() + " año(s) y " + periodo.getMonths() + " mes(es) \n");
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
}