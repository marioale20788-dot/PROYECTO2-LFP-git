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
public class ProgramaNodo {
    private ArrayList<DirectivaNodo> directivas = new ArrayList<>();
    private ArrayList<AgenteNodo> agentes = new ArrayList<>();
    private ArrayList<EjecucionNodo> ejecuciones = new ArrayList<>();
    private ExportarNodo exportar;

    public ArrayList<DirectivaNodo> getDirectivas() {
        return directivas;
    }

    public ArrayList<AgenteNodo> getAgentes() {
        return agentes;
    }

    public ArrayList<EjecucionNodo> getEjecuciones() {
        return ejecuciones;
    }

    public void setExportar(ExportarNodo exportar) {
        this.exportar = exportar;
    }
    
    
    

    public ExportarNodo getExportar() {
        return exportar;
    }

    
}
