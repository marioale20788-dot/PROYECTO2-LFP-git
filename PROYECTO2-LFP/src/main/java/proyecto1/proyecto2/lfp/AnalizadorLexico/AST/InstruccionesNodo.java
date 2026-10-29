/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package proyecto1.proyecto2.lfp.AnalizadorLexico.AST;

import java.util.ArrayList;

/**
 *
 * @author mario
 */
public class InstruccionesNodo {
  private String accion;
    private String detalle;
    private String varDestino;
    private ArrayList<String> variablesUsadas = new ArrayList<>();
    private int fila;
    private int columna;

    public InstruccionesNodo(String accion, String detalle, String varDestino, int fila, int columna) {
        this.accion = accion;
        this.detalle = detalle;
        this.varDestino = varDestino;
        this.variablesUsadas = variablesUsadas;
        this.fila = fila;
        this.columna = columna;
    }

    public String getAccion() {
        return accion;
    }

    public void setAccion(String accion) {
        this.accion = accion;
    }

    public String getDetalle() {
        return detalle;
    }

    public void setDetalle(String detalle) {
        this.detalle = detalle;
    }

    public String getVarDestino() {
        return varDestino;
    }

    public void setVarDestino(String varDestino) {
        this.varDestino = varDestino;
    }

    public ArrayList<String> getVariablesUsadas() {
        return variablesUsadas;
    }

    public void setVariablesUsadas(ArrayList<String> variablesUsadas) {
        this.variablesUsadas = variablesUsadas;
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
public String aformatoTexto() {
        StringBuilder sb = new StringBuilder();
        sb.append(accion).append(" ").append(detalle);
        if (varDestino != null && !varDestino.isEmpty()) {
            sb.append(" -> ").append(varDestino);
        }
        return sb.toString();
    }
  
  
}
