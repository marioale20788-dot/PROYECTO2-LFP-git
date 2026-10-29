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
import proyecto1.proyecto2.lfp.AnalizadorLexico.AST.ProgramaNodo;

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
                 
        }

        return archivo;
    }
   

    public void pzTexto(String nombre, String texto, String ruta) {

        file = new File(ruta + "/" + nombre + ".pz");

        try {
            FileWriter writer = new FileWriter(file);

            writer.write(texto);
            writer.close();

        } catch (IOException ex) {
            return;
        }
    }
    
    public void htmlTablaDeSimbolos(String nombre, String ruta){
  
        try {
            file = new File(ruta + "/" + nombre + ".html");
            FileWriter writer = new FileWriter(file);

            writer.write("""
        <!DOCTYPE html>
        <html lang="es">
        <head>
            <meta charset="UTF-8">
            <title>TOKENS</title>
            <style>
                body {
                    background-color: #ffffff;
                    color: #1e293b;
                    font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
                    padding: 30px;
                    margin: 0;
                }
                h1 {
                    color: #0f172a;
                    text-align: center;
                    font-size: 22px;
                    margin-bottom: 25px;
                    text-transform: uppercase;
                    letter-spacing: 1px;
                }
                table {
                    border-collapse: collapse;
                    width: 90%;
                    margin: 0 auto;
                    background-color: #ffffff;
                    border: 1px solid #cbd5e1;
                    border-radius: 6px;
                    overflow: hidden;
                    box-shadow: 0 1px 3px rgba(0, 0, 0, 0.05);
                }
                th {
                    background-color: #f1f5f9;
                    color: #0f172a;
                    padding: 12px;
                    text-align: left;
                    font-size: 13px;
                    border-bottom: 2px solid #cbd5e1;
                    text-transform: uppercase;
                }
                td {
                    border-bottom: 1px solid #e2e8f0;
                    padding: 10px 12px;
                    font-size: 14px;
                }
                td.lexema {
                    font-family: 'Consolas', 'Courier New', monospace;
                    font-weight: bold;
                }
                .badge-tipo {
                    padding: 4px 8px;
                    border-radius: 4px;
                    font-weight: bold;
                    font-size: 12px;
                    color: #ffffff;
                    display: inline-block;
                }
                tr:hover {
                    background-color: #f8fafc;
                }
            </style>
        </head>
        <body>

        <h1>REPORTE DE ERRORES SINTACTICOS</h1>

        <table>
            <tr>
                <th>NOMBRE</th>
                <th>TIPO</th>
                <th>VALOR</th>
                <th>FILA</th>
                <th>COLUMNA</th>

            </tr>
        """);

            writer.close();

        } catch (IOException e) {
          return;
        }
    }
    public void escribirHtmlTablaDeSimbolos(String nombre, String tipo, String valor, int fila, int columna, String color) {

        try (FileWriter writer = new FileWriter(file, true)) {

            writer.write(
                    "<tr>"
                    + "<td>" + nombre + "</td>"
                    + "<td class=\"lexema\"style=\"color: " + color + ";\">" + tipo + "</td>"
                    + "<td><span class=\"badge-tipo\" style=\"background-color: " + color + ";\">" + valor + "</span></td>"
                    + "<td>" + fila + "</td>"
                    + "<td>" + columna + "</td>"
                    + "</tr>\n"
            );

        } catch (IOException e) {
          return;
        }

    }
    
    
     public void htmlErroresSintacticos(String tokenCorrectoIncorrecto,String ruta) {
  
        try {
            file = new File(ruta + "/" + tokenCorrectoIncorrecto + ".html");
            FileWriter writer = new FileWriter(file);

            writer.write("""
        <!DOCTYPE html>
        <html lang="es">
        <head>
            <meta charset="UTF-8">
            <title>TOKENS</title>
            <style>
                body {
                    background-color: #ffffff;
                    color: #1e293b;
                    font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
                    padding: 30px;
                    margin: 0;
                }
                h1 {
                    color: #0f172a;
                    text-align: center;
                    font-size: 22px;
                    margin-bottom: 25px;
                    text-transform: uppercase;
                    letter-spacing: 1px;
                }
                table {
                    border-collapse: collapse;
                    width: 90%;
                    margin: 0 auto;
                    background-color: #ffffff;
                    border: 1px solid #cbd5e1;
                    border-radius: 6px;
                    overflow: hidden;
                    box-shadow: 0 1px 3px rgba(0, 0, 0, 0.05);
                }
                th {
                    background-color: #f1f5f9;
                    color: #0f172a;
                    padding: 12px;
                    text-align: left;
                    font-size: 13px;
                    border-bottom: 2px solid #cbd5e1;
                    text-transform: uppercase;
                }
                td {
                    border-bottom: 1px solid #e2e8f0;
                    padding: 10px 12px;
                    font-size: 14px;
                }
                td.lexema {
                    font-family: 'Consolas', 'Courier New', monospace;
                    font-weight: bold;
                }
                .badge-tipo {
                    padding: 4px 8px;
                    border-radius: 4px;
                    font-weight: bold;
                    font-size: 12px;
                    color: #ffffff;
                    display: inline-block;
                }
                tr:hover {
                    background-color: #f8fafc;
                }
            </style>
        </head>
        <body>

        <h1>REPORTE DE ERRORES SINTACTICOS</h1>

        <table>
            <tr>
                <th>NUMERO</th>
                <th>ERROR</th>
                <th>TIPO ENCONTRADO</th>
                <th>TIPO ESPERADO</th>
                <th>FILA</th>
                            <th>COLUMNA</th>
            </tr>
        """);

            writer.close();

        } catch (IOException e) {
          return;
        }
    }
public void escribirHtmlErroresSintacticos(int numero, String error, String tipoEncontrado,String tipoEsperado, int fila, int columna, String color) {

        try (FileWriter writer = new FileWriter(file, true)) {

            writer.write(
                    "<tr>"
                    + "<td>" + numero + "</td>"
                    + "<td class=\"lexema\"style=\"color: " + color + ";\">" + error + "</td>"
                    + "<td><span class=\"badge-tipo\" style=\"background-color: " + color + ";\">" + tipoEncontrado + "</span></td>"
                             + "<td><span class=\"badge-tipo\" style=\"background-color: " + color + ";\">" + tipoEsperado + "</span></td>"
                    + "<td>" + fila + "</td>"
                    + "<td>" + columna + "</td>"
                    + "</tr>\n"
            );

        } catch (IOException e) {
          return;
        }

    }

    public void htmlTokens(String tokenCorrectoIncorrecto,String ruta) {
  
        try {
            file = new File(ruta + "/" + tokenCorrectoIncorrecto + ".html");
            FileWriter writer = new FileWriter(file);

            writer.write("""
        <!DOCTYPE html>
        <html lang="es">
        <head>
            <meta charset="UTF-8">
            <title>TOKENS</title>
            <style>
                body {
                    background-color: #ffffff;
                    color: #1e293b;
                    font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
                    padding: 30px;
                    margin: 0;
                }
                h1 {
                    color: #0f172a;
                    text-align: center;
                    font-size: 22px;
                    margin-bottom: 25px;
                    text-transform: uppercase;
                    letter-spacing: 1px;
                }
                table {
                    border-collapse: collapse;
                    width: 90%;
                    margin: 0 auto;
                    background-color: #ffffff;
                    border: 1px solid #cbd5e1;
                    border-radius: 6px;
                    overflow: hidden;
                    box-shadow: 0 1px 3px rgba(0, 0, 0, 0.05);
                }
                th {
                    background-color: #f1f5f9;
                    color: #0f172a;
                    padding: 12px;
                    text-align: left;
                    font-size: 13px;
                    border-bottom: 2px solid #cbd5e1;
                    text-transform: uppercase;
                }
                td {
                    border-bottom: 1px solid #e2e8f0;
                    padding: 10px 12px;
                    font-size: 14px;
                }
                td.lexema {
                    font-family: 'Consolas', 'Courier New', monospace;
                    font-weight: bold;
                }
                .badge-tipo {
                    padding: 4px 8px;
                    border-radius: 4px;
                    font-weight: bold;
                    font-size: 12px;
                    color: #ffffff;
                    display: inline-block;
                }
                tr:hover {
                    background-color: #f8fafc;
                }
            </style>
        </head>
        <body>

        <h1>REPORTE DE TOKENS</h1>

        <table>
            <tr>
                <th>NUMERO</th>
                <th>LEXEMA</th>
                <th>TIPO</th>
                <th>FILA</th>
                <th>COLUMNA</th>
            </tr>
        """);

            writer.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void escribirHtml(int numero, String lexema, String tipo, int fila, int columna, String color) {

        try (FileWriter writer = new FileWriter(file, true)) {

            writer.write(
                    "<tr>"
                    + "<td>" + numero + "</td>"
                    + "<td class=\"lexema\"style=\"color: " + color + ";\">" + lexema + "</td>"
                    + "<td><span class=\"badge-tipo\" style=\"background-color: " + color + ";\">" + tipo + "</span></td>"
                    + "<td>" + fila + "</td>"
                    + "<td>" + columna + "</td>"
                    + "</tr>\n"
            );

        } catch (IOException e) {
            return;
        }

    }
  public void htmlReporteAST(String nombreArchivo, String ruta) {
     file = new File(ruta + "/" + nombreArchivo + ".html");

    try (FileWriter writer = new FileWriter(file)) {

        writer.write("""
        <!DOCTYPE html>
        <html lang="es">
        <head>
            <meta charset="UTF-8">
            <title>REPORTE AST - PROMPTZAL V3</title>
            <style>
                body {
                    font-family: system-ui, sans-serif;
                    padding: 20px;
                    margin: 0;
                    background: #ffffff;
                    color: #1e293b;
                }
                h1 {
                    text-align: center;
                    font-size: 20px;
                    text-transform: uppercase;
                }
                pre {
                    background: #1e293b;
                    color: #38bdf8;
                    padding: 20px;
                    border-radius: 8px;
                    font-family: monospace;
                    font-size: 14px;
                    overflow-x: auto;
                    margin: 0 auto;
                    max-width: 900px;
                }
            </style>
        </head>
        <body>

            <h1>Reporte de AST </h1>

            <pre>
        """);

    } catch (IOException e) {
        System.err.println("Error al generar la plantilla HTML: " + e.getMessage());
    }
}
    public void escribirHtmlAST(String texto) {
   

    try (FileWriter writer = new FileWriter(file, true)) {

        writer.write(texto);

        writer.write("""
            </pre>
        </div>
        </body>
        </html>
        """);

    } catch (IOException e) {
       return;
    }
}

}
