/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package proyecto1.proyecto2.lfp.AnalizadorLexico;

import java.util.ArrayList;

/**
 *
 * @author mario
 */
public class modeloAgente {
     private String nombre = "";
   private      String contexto = "";
        private ArrayList<String> asignaciones = new ArrayList<>();
       private  ArrayList<String> instrucciones = new ArrayList<>();
       private  int contadorInstrucciones = 1;

    public String getNombre() {
        return nombre;
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

    public ArrayList<String> getAsignaciones() {
        return asignaciones;
    }

    public void setAsignaciones(ArrayList<String> asignaciones) {
        this.asignaciones = asignaciones;
    }

    public ArrayList<String> getInstrucciones() {
        return instrucciones;
    }

    public void setInstrucciones(ArrayList<String> instrucciones) {
        this.instrucciones = instrucciones;
    }

    public int getContadorInstrucciones() {
        return contadorInstrucciones;
    }
    public void sumarInstrucciones(){
        this.contadorInstrucciones++;
    }

    public void setContadorInstrucciones(int contadorInstrucciones) {
        this.contadorInstrucciones = contadorInstrucciones;
    }
        
        
        
}
