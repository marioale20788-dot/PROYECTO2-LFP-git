/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package proyecto1.proyecto2.lfp.AnalizadorLexico.AST;

/**
 *
 * @author mario
 */
public class EjecucionNodo {
    private String nombreAgente;
     private int fila;
    private int columna;


 
    public EjecucionNodo(String nombreAgente, int fila, int columna) {
        this.nombreAgente = nombreAgente;
        this.fila = fila;
        this.columna = columna;
    }

    public int getFila() {
        return fila;
    }

    public void setFila(int fila) {
        this.fila = fila;
    }

    public int getColumna() {
        return columna;
    }

    public void setColumna(int columna) {
        this.columna = columna;
    }

    public String getNombreAgente() {
        return nombreAgente; 
    }
}
