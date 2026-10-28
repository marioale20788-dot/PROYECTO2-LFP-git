/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package proyecto1.proyecto2.lfp.AnalizadorLexico;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

/**
 *
 * @author mario
 */
public class Archivo {

    private Scanner scanner = new Scanner(System.in);
 private File file;
    private String ruta;
    public String leerArchivo(File archivoPz) {
        String archivo = "";

        try {
            scanner = new Scanner(archivoPz);
            while (scanner.hasNextLine()) {
                archivo = archivo + scanner.nextLine() + "\n";

            }
        } catch (FileNotFoundException ex) {
            System.getLogger(Archivo.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        }

        return archivo;
    }

    public void pzTexto(String nombre, String texto,String ruta) {
       
        file = new File(ruta + "/" + nombre + ".pz");

        try {
            FileWriter writer = new FileWriter(file);

            writer.write(texto);
            writer.close();

        } catch (IOException ex) {
            System.getLogger(Archivo.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        }
    }

}
