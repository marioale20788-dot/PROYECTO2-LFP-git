/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package proyecto1.proyecto2.lfp.AnalizadorLexico;

/**
 *
 * @author mario
 */
public class ErrorSintactico {
    private String error;
    private String tipoEncontrado;
    private String tipoEsperado;
    private int fila;
    private int columna;

    public ErrorSintactico(String error, String tipoEncontrado, String tipoEsperado, int fila, int columna) {
        this.error = error;
        this.tipoEncontrado = tipoEncontrado;
        this.tipoEsperado = tipoEsperado;
        this.fila = fila;
        this.columna = columna;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getTipoEncontrado() {
        return tipoEncontrado;
    }

    public void setTipoEncontrado(String tipoEncontrado) {
        this.tipoEncontrado = tipoEncontrado;
    }

    public String getTipoEsperado() {
        return tipoEsperado;
    }

    public void setTipoEsperado(String tipoEsperado) {
        this.tipoEsperado = tipoEsperado;
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
    
    
    
}
