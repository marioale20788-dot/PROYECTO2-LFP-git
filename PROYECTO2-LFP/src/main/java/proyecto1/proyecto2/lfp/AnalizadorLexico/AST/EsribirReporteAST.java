/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package proyecto1.proyecto2.lfp.AnalizadorLexico.AST;

/**
 *
 * @author mario
 */
public class EsribirReporteAST {

    public String generarTextoAST(ProgramaNodo programa) {
        StringBuilder sb = new StringBuilder();
        sb.append("ProgramaNodo\n");

        if (programa != null && programa.getAgentes() != null) {
            for (AgenteNodo agente : programa.getAgentes()) {
                sb.append("├── AgenteNodo [").append(agente.getNombre()).append("]\n");

                if (agente.getVariables() != null) {
                    sb.append("│   ├── Variables\n");
                    for (VariableNodo var : agente.getVariables()) {
                        sb.append("│   │   ├── Variable: ").append(var.getNombre());
                        if (var.getValor() != null) {
                            sb.append(" = ").append(var.getValor());
                        }
                        sb.append("\n");
                    }
                }

                if (agente.getInstrucciones() != null) {
                    sb.append("│   └── Instrucciones\n");
                    for (InstruccionesNodo inst : agente.getInstrucciones()) {
                        sb.append("│       ├── ").append(inst.getAccion());
                        if (inst.getDetalle() != null) {
                            sb.append(" : ").append(inst.getDetalle());
                        }
                        if (inst.getVarDestino() != null) {
                            sb.append(" -> ").append(inst.getVarDestino());
                        }
                        sb.append("\n");
                    }
                }
            }

            if (programa.getEjecuciones() != null) {
                sb.append("├── Ejecuciones\n");
                for (EjecucionNodo ejec : programa.getEjecuciones()) {
                    sb.append("│   └── EJECUTAR: ").append(ejec.getNombreAgente()).append("\n");
                }
            }

            if (programa.getExportar() != null && programa.getExportar().getVariablesExportadas() != null) {
                sb.append("└── Exportar\n");
                for (String varExp : programa.getExportar().getVariablesExportadas()) {
                    sb.append("    └── Variable: ").append(varExp.trim()).append("\n");
                }
            }
        } else {
            sb.append("└── [Árbol Vacío o con Errores Sintácticos]\n");
        }

        return sb.toString();
    }
}
