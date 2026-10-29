/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package proyecto1.proyecto2.lfp.AnalizadorLexico;

import java.util.ArrayList;
import proyecto1.proyecto2.lfp.AnalizadorLexico.AST.AgenteNodo;
import proyecto1.proyecto2.lfp.AnalizadorLexico.AST.Ast;
import proyecto1.proyecto2.lfp.AnalizadorLexico.AST.DirectivaNodo;
import proyecto1.proyecto2.lfp.AnalizadorLexico.AST.InstruccionesNodo;
import proyecto1.proyecto2.lfp.AnalizadorLexico.AST.ProgramaNodo;
import proyecto1.proyecto2.lfp.AnalizadorLexico.AST.VariableNodo;

/**
 *
 * @author mario
 */
public class Prompt {

    public String generarPrompt(ProgramaNodo programa) {
        Ast constructor = new Ast();

        return traducirASTaPromt(programa);
    }

    private String traducirASTaPromt(ProgramaNodo programa) {
        StringBuilder sb = new StringBuilder();
        sb.append("# Prompt generado con PromptZal\n");

        String modelo = "No especificado";
        String rol = "No especificado";
        String formato = "No especificado";

        for (DirectivaNodo dir : programa.getDirectivas()) {
            if (dir.getTipo().contains("modelo")) {
                modelo = dir.getValor();
            } else if (dir.getTipo().contains("rol")) {
                rol = dir.getValor();
            } else if (dir.getTipo().contains("formato")) {
                formato = dir.getValor();
            }
        }

        sb.append("Modelo destino: ").append(modelo).append("\n");
        sb.append("Rol general: ").append(rol).append("\n");
        sb.append("Formato de respuesta: ").append(formato).append("\n\n");

        for (AgenteNodo agente : programa.getAgentes()) {
            sb.append("## Agente: ").append(agente.getNombre()).append("\n");

            String ctx = agente.getContexto();
            if (ctx == null || ctx.isEmpty()) {
                ctx = "Eres un " + rol;
            }
            sb.append("Contexto: ").append(ctx).append("\n");

            if (!agente.getVariables().isEmpty()) {
                sb.append("Variables disponibles:\n");
                for (VariableNodo var : agente.getVariables()) {
                    sb.append("- ").append(var.getNombre()).append(": ").append(var.getValor()).append("\n");
                }
            }

            if (!agente.getInstrucciones().isEmpty()) {
                sb.append("\nInstrucciones:\n");
                int num = 1;
                for (InstruccionesNodo inst : agente.getInstrucciones()) {
                    sb.append(num++).append(". ").append(inst.aformatoTexto()).append("\n");
                }
            }
            sb.append("\n");
        }

        if (programa.getExportar() != null && !programa.getExportar().getVariablesExportadas().isEmpty()) {
            sb.append("## Resultado solicitado\n");
            for (String exp : programa.getExportar().getVariablesExportadas()) {
                sb.append("- ").append(exp).append("\n");
            }
        }

        return sb.toString();
    }

}
