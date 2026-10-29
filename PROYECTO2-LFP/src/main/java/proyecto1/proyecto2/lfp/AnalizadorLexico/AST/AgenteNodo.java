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
public class AgenteNodo {
    private String nombre;
    private String contexto = "";
    private ArrayList<VariableNodo> variables = new ArrayList<>();
    private ArrayList<InstruccionesNodo> instrucciones = new ArrayList<>();
    private int fila;
    private int columna;

    public AgenteNodo(String nombre, int fila, int columna) {
        this.nombre = nombre;
        this.fila = fila;
        this.columna = columna;
    }

    public String getNombre() {
        return nombre;
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

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getContexto() {
        return contexto;
    }

    public void setContexto(String contexto) {
        this.contexto = contexto;
    }

    public ArrayList<VariableNodo> getVariables() {
        return variables;
    }

    public void setVariables(ArrayList<VariableNodo> variables) {
        this.variables = variables;
    }

    public ArrayList<InstruccionesNodo> getInstrucciones() {
        return instrucciones;
    }

    public void setInstrucciones(ArrayList<InstruccionesNodo> instrucciones) {
        this.instrucciones = instrucciones;
    }
    
    
    
}
