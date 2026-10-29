/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package proyecto1.proyecto2.lfp.AnalizadorLexico.AST;

import java.util.ArrayList;
import proyecto1.proyecto2.lfp.AnalizadorLexico.Token;
import proyecto1.proyecto2.lfp.AnalizadorLexico.TokenTipos;

/**
 *
 * @author mario
 */
public class Ast {
    private ArrayList<Token> tokens;
    private int p = 0;

    public ProgramaNodo construir(ArrayList<Token> listaTokens) {
        this.tokens = listaTokens;
        this.p = 0;

        ProgramaNodo programa = new ProgramaNodo();
        AgenteNodo agenteActual = null;

        while (p < tokens.size()) {
            Token t = tokens.get(p);
int fila = t.getFila();
                 int columna = t.getColumna();
            if (t.getTipo() == TokenTipos.DIRECTIVA) {
                String dir = t.getLexema().toLowerCase();
                 
                p++;
                if (p < tokens.size() && tokens.get(p).getTipo() == TokenTipos.CADENA) {
                    String val = tokens.get(p).getLexema();
                   
                           
                    programa.getDirectivas().add(new DirectivaNodo(dir, val,fila,columna));
                    p++;
                }

            } else if (t.getTipo() == TokenTipos.AGENTE) {
                p++;
                if (p < tokens.size()) {
                    String nombreAgente = tokens.get(p).getLexema();
                    agenteActual = new AgenteNodo(nombreAgente,fila,columna);
                    programa.getAgentes().add(agenteActual);
                }
                p++;

            } else if (t.getTipo() == TokenTipos.EJECUTAR) {
                p++;
                if (p < tokens.size() && tokens.get(p).getTipo() == TokenTipos.IDENTIFICADOR) {
                    String nombreAgente = tokens.get(p).getLexema();
                    programa.getEjecuciones().add(new EjecucionNodo(nombreAgente,fila,columna));
                    p++;
                }

            } else if (t.getTipo() == TokenTipos.EXPORTAR) {
                ExportarNodo expNode = new ExportarNodo(fila,columna);
                p++;
                while (p < tokens.size() && tokens.get(p).getTipo() == TokenTipos.IDENTIFICADOR) {
                    expNode.getVariablesExportadas().add(tokens.get(p).getLexema());
                    p++;
                }
                programa.setExportar(expNode);
                agenteActual = null;

            } else if (agenteActual != null) {
                String lex = t.getLexema();

                if (lex.equalsIgnoreCase("contexto")) {
                    p++;
                    if (p < tokens.size() && tokens.get(p).getTipo() == TokenTipos.IGUAL)
                        p++;
                    if (p < tokens.size()) {
                        agenteActual.setContexto(tokens.get(p).getLexema());
                    }
                    p++;

                } else if (lex.equalsIgnoreCase("variable")) {
                    p++;
                    if (p + 2 < tokens.size()) {
                        String nombreVar = tokens.get(p).getLexema();
                        p += 2;
                        String valor = tokens.get(p).getLexema();
                        agenteActual.getVariables().add(new VariableNodo(nombreVar, valor,fila,columna));
                    }
                    p++;

                } else if (t.getTipo() == TokenTipos.COMANDO_IA) {
                    agenteActual.getInstrucciones().add(pComandoIA());

                } else {
                    p++;
                }
            } else {
                p++;
            }
        }
        return programa;
    }

    private InstruccionesNodo pComandoIA() {
        String accion = tokens.get(p).getLexema();
        p++;

        StringBuilder detalle = new StringBuilder();
        String varDestino = "";

        while (p < tokens.size()) {
            Token t = tokens.get(p);

            if (esFinComando(p)) {
                break;
            }

            if (t.getLexema().equals("->") && p + 1 < tokens.size()) {
                p++;
                varDestino = tokens.get(p).getLexema();
                p++;
                break;
            } else {
                detalle.append(t.getLexema()).append(" ");
            }
            p++;
        }

        return new InstruccionesNodo(accion, detalle.toString().trim(), varDestino,tokens.getLast().getFila(),tokens.getLast().getFila());
    }

    private boolean esFinComando(int pos) {
        Token t = tokens.get(pos);
        if (t.getTipo() == TokenTipos.COMANDO_IA || 
            t.getTipo() == TokenTipos.AGENTE || 
            t.getTipo() == TokenTipos.EJECUTAR || 
            t.getTipo() == TokenTipos.LLAVE_CERRADA || 
            t.getTipo() == TokenTipos.EXPORTAR) {
            return true;
        }
        String lex = t.getLexema();
        return lex.equalsIgnoreCase("variable") || lex.equalsIgnoreCase("contexto");
    }
}
