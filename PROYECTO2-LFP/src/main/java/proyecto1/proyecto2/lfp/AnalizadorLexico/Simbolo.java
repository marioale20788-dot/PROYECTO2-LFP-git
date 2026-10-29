/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package proyecto1.proyecto2.lfp.AnalizadorLexico;

/**
 *
 * @author mario
 */
public class Simbolo {
 private String nombre;
    private String tipo;
    private String valor;
    private int fila;
    private int columna;

    public Simbolo(String nombre, String tipo, String valor, int fila, int columna) {
        this.nombre = nombre;
        this.tipo = tipo;
        this.valor = valor;
        this.fila = fila;
        this.columna = columna;
    }

    public String getNombre() {
        return nombre;
    }

    public String getTipo() {
        return tipo;
    }

    public String getValor() {
        return valor;
    }

    public int getFila() {
        return fila;
    }

    public int getColumna() {
        return columna;
    }

    
    
    
    
}
