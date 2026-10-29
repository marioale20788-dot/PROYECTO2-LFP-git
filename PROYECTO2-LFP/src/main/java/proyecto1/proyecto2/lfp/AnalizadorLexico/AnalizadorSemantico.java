/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package proyecto1.proyecto2.lfp.AnalizadorLexico;
import java.util.ArrayList;
import java.util.List;
import proyecto1.proyecto2.lfp.AnalizadorLexico.AST.AgenteNodo;
import proyecto1.proyecto2.lfp.AnalizadorLexico.AST.EjecucionNodo;
import proyecto1.proyecto2.lfp.AnalizadorLexico.AST.InstruccionesNodo;
import proyecto1.proyecto2.lfp.AnalizadorLexico.AST.ProgramaNodo;
import proyecto1.proyecto2.lfp.AnalizadorLexico.AST.VariableNodo;



public class AnalizadorSemantico {

    private final List<ErrorSemantico> erroresSemanticos = new ArrayList<>();
    private final TablaSimbolos tablaSimbolos = new TablaSimbolos();

    public List<ErrorSemantico> analizar(ProgramaNodo programa) {
        erroresSemanticos.clear();

        if (programa == null) {
            return erroresSemanticos;
        }

        crearTabla(programa);
        validarReglasSemanticas(programa);

        return erroresSemanticos;
    }

    private void crearTabla(ProgramaNodo programa) {
        if (programa.getAgentes() == null) return;

        for (AgenteNodo agente : programa.getAgentes()) {
            tablaSimbolos.agregar(new Simbolo(agente.getNombre(), "AGENTE", null, agente.getFila(), agente.getColumna()));

            if (agente.getVariables() != null) {
                for (VariableNodo var : agente.getVariables()) {
                    tablaSimbolos.agregar(new Simbolo(agente.getNombre() + "." + var.getNombre(), "VARIABLE", var.getValor(), var.getFila(), var.getColumna()));
                }
            }

            if (agente.getInstrucciones() != null) {
                for (InstruccionesNodo inst : agente.getInstrucciones()) {
                    if (inst.getVarDestino() != null && !inst.getVarDestino().isEmpty()) {
                        tablaSimbolos.agregar(new Simbolo(agente.getNombre() + "." + inst.getVarDestino(), "VARIABLE", "", inst.getFila(), inst.getColumna()));
                    }
                }
            }
        }
    }

    private void validarReglasSemanticas(ProgramaNodo programa) {
        ArrayList<String> agentesVistos = new ArrayList<>();
        if (programa.getAgentes() != null) {
            for (AgenteNodo agente : programa.getAgentes()) {
                if (agentesVistos.contains(agente.getNombre())) {
                    reportarError("No puede haber dos agentes con el mismo nombre: '" + agente.getNombre() + "'",
                            agente.getFila(), agente.getColumna());
                } else {
                    agentesVistos.add(agente.getNombre());
                }
            }
        }

        if (programa.getAgentes() != null) {
            for (AgenteNodo agente : programa.getAgentes()) {
                if (agente.getInstrucciones() != null) {
                    for (InstruccionesNodo inst : agente.getInstrucciones()) {
                        for (String varUsada : inst.getVariablesUsadas()) {
                            boolean existeComoVarLocal = tablaSimbolos.existe(agente.getNombre() + "." + varUsada);
                            Simbolo sim = tablaSimbolos.buscar(varUsada);
                            boolean existeComoAgente = (sim != null && "AGENTE".equals(sim.getTipo()));

                            if (!existeComoVarLocal && !existeComoAgente) {
                                reportarError("La variable '" + varUsada + "' no ha sido declarada previamente en el agente '" + agente.getNombre() + "'",
                                        inst.getFila(), inst.getColumna());
                            }
                        }
                    }
                }
            }
        }

        if (programa.getEjecuciones() != null) {
            for (EjecucionNodo ejec : programa.getEjecuciones()) {
                Simbolo simAgente = tablaSimbolos.buscar(ejec.getNombreAgente());
                if (simAgente == null || !"AGENTE".equals(simAgente.getTipo())) {
                    reportarError("EJECUTAR se refiere a un agente que no existe: '" + ejec.getNombreAgente() + "'",
                            ejec.getFila(), ejec.getColumna());
                }
            }
        }

        if (programa.getExportar() != null && programa.getExportar().getVariablesExportadas() != null) {
            for (String varExp : programa.getExportar().getVariablesExportadas()) {
                String varLimpia = varExp.trim();
                if (!esVariableDeAgenteEjecutado(varLimpia, programa)) {
                    reportarError("No se puede exportar '" + varLimpia + "' porque no existe en ningún agente ejecutado.",
                            programa.getExportar().getFila(), programa.getExportar().getColumna());
                }
            }
        }
    }

    private boolean esVariableDeAgenteEjecutado(String varNombre, ProgramaNodo programa) {
        if (programa.getEjecuciones() == null) return false;

        for (EjecucionNodo ejec : programa.getEjecuciones()) {
            String agenteEjecutado = ejec.getNombreAgente();
            if (tablaSimbolos.existe(agenteEjecutado + "." + varNombre)) {
                return true;
            }
        }
        return false;
    }

    private void reportarError(String descripcion, int fila, int columna) {
        erroresSemanticos.add(new ErrorSemantico(descripcion, fila, columna));
    }

    public List<ErrorSemantico> getErroresSemanticos() {
        return erroresSemanticos;
    }

    public TablaSimbolos getTablaSimbolos() {
        return tablaSimbolos;
    }
}