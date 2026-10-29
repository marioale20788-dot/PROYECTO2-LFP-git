/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package proyecto1.proyecto2.lfp.AnalizadorLexico;

import java.io.IOException;
import java.util.ArrayList;
import proyecto1.proyecto2.lfp.AnalizadorLexico.AST.AgenteNodo;
import proyecto1.proyecto2.lfp.AnalizadorLexico.AST.DirectivaNodo;
import proyecto1.proyecto2.lfp.AnalizadorLexico.AST.EjecucionNodo;
import proyecto1.proyecto2.lfp.AnalizadorLexico.AST.ExportarNodo;
import proyecto1.proyecto2.lfp.AnalizadorLexico.AST.InstruccionesNodo;
import proyecto1.proyecto2.lfp.AnalizadorLexico.AST.ProgramaNodo;
import proyecto1.proyecto2.lfp.AnalizadorLexico.AST.VariableNodo;

/**
 *
 * @author mario
 */
public class AnalizadorSintactico {
    private AnalizadorLexico analizadorLexico;
    private Token tokenActual;
    private ArrayList<Token> listaDeTokens;
    private ArrayList<ErrorSintactico> erroresSintacticos;
    private TablaSimbolos tablaSimbolos;

    public AnalizadorSintactico(AnalizadorLexico analizador) {
        this.analizadorLexico = analizador;
        this.listaDeTokens = new ArrayList<>();
        this.erroresSintacticos = new ArrayList<>();
        this.tablaSimbolos = new TablaSimbolos();
        avanzar();
    }

    public void avanzar() {
        if (this.tokenActual != null && this.tokenActual.getTipo() == TokenTipos.EOF) {
            return;
        }
        try {
            this.tokenActual = analizadorLexico.yylex();
            if (this.tokenActual == null) {
                this.tokenActual = new Token(TokenTipos.EOF, "EOF", 0, 0);
            } else if (this.tokenActual.getTipo() != TokenTipos.EOF) {
                this.listaDeTokens.add(tokenActual);
            }
        } catch (IOException ex) {
            this.tokenActual = new Token(TokenTipos.EOF, "EOF", 0, 0);
        }
    }

    public void validaciones(TokenTipos tipoEsperado) {
        if (tokenActual.getTipo() == tipoEsperado) {
            avanzar();
        } else {
            errorEncontrado(tipoEsperado.toString());
            modoPanico();
        }
    }

    public void errorEncontrado(String tokenEsperado) {
        ErrorSintactico error = new ErrorSintactico(
            tokenActual.getLexema(),
            tokenActual.getTipo().toString(),
            tokenEsperado,
            tokenActual.getFila(),
            tokenActual.getColumna()
        );
        erroresSintacticos.add(error);
    }

    public void modoPanico() {
        while (tokenActual.getTipo() != TokenTipos.EOF) {
            switch (tokenActual.getTipo()) {
                case AGENTE:
                case LLAVE_CERRADA:
                case COMANDO_IA:
                case VARIABLE:
                case EJECUTAR:
                case EXPORTAR:
                    return; 
                default:
                    avanzar();
                    break;
            }
        }
    }

    public ProgramaNodo analizarPrograma() {
        ProgramaNodo programa = new ProgramaNodo();
        
        programa.getDirectivas().addAll(analizarDirectivas());
        programa.getAgentes().addAll(analizarAgentes());
        programa.getEjecuciones().addAll(analizarEjecuciones());
        programa.setExportar(analizarExportar());
        
        validaciones(TokenTipos.EOF);
        return programa;
    }

    public ArrayList<DirectivaNodo> analizarDirectivas() {
        ArrayList<DirectivaNodo> lista = new ArrayList<>();
        while (tokenActual.getTipo() == TokenTipos.DIRECTIVA) {
            String dir = tokenActual.getLexema();
            validaciones(TokenTipos.DIRECTIVA);
            
            String val = tokenActual.getLexema();
            validaciones(TokenTipos.CADENA);
            
            lista.add(new DirectivaNodo(dir, val, tokenActual.getFila(), tokenActual.getColumna()));
        }
        return lista;
    }

    public ArrayList<AgenteNodo> analizarAgentes() {
        ArrayList<AgenteNodo> lista = new ArrayList<>();
        lista.add(analizarAgente());
        lista.addAll(masAgentes());
        return lista;
    }

    public ArrayList<AgenteNodo> masAgentes() {
        ArrayList<AgenteNodo> lista = new ArrayList<>();
        if (tokenActual.getTipo() == TokenTipos.AGENTE) {
            lista.add(analizarAgente());
            lista.addAll(masAgentes());
        }
        return lista;
    }

    public AgenteNodo analizarAgente() {
        validaciones(TokenTipos.AGENTE);
        
        String nombreAgente = tokenActual.getLexema();
        int fila = tokenActual.getFila();
        int columna = tokenActual.getColumna();
        validaciones(TokenTipos.IDENTIFICADOR);
        
        tablaSimbolos.agregar(new Simbolo(nombreAgente, "AGENTE", null, fila, columna));
        
        AgenteNodo agente = new AgenteNodo(nombreAgente, fila, columna);
        validaciones(TokenTipos.LLAVE_ABIERTA);

        agente.setContexto(analizarContexto());
        agente.getVariables().addAll(analizarVariables(nombreAgente));
        agente.getInstrucciones().addAll(analizarComandos(nombreAgente));

        validaciones(TokenTipos.LLAVE_CERRADA);
        return agente;
    }

    public String analizarContexto() {
        String ctx = "";
        if (tokenActual.getTipo() == TokenTipos.CONTEXTO) {
            validaciones(TokenTipos.CONTEXTO);
            validaciones(TokenTipos.IGUAL);
            ctx = tokenActual.getLexema();
            validaciones(TokenTipos.CADENA);
        }
        return ctx;
    }

    public ArrayList<VariableNodo> analizarVariables(String nombreAgente) {
        ArrayList<VariableNodo> lista = new ArrayList<>();
        if (tokenActual.getTipo() == TokenTipos.VARIABLE) {
            validaciones(TokenTipos.VARIABLE);
            
            String nombreVar = tokenActual.getLexema();
            int fila = tokenActual.getFila();
            int columna = tokenActual.getColumna();
            validaciones(TokenTipos.IDENTIFICADOR);
            
            validaciones(TokenTipos.IGUAL);
            
            String valorVar = analizarExpresion(null); 

            tablaSimbolos.agregar(new Simbolo(nombreAgente + "." + nombreVar, "VARIABLE", valorVar, fila, columna));

            lista.add(new VariableNodo(nombreVar, valorVar, fila, columna));
            lista.addAll(analizarVariables(nombreAgente));
        }
        return lista;
    }

    public ArrayList<InstruccionesNodo> analizarComandos(String nombreAgente) {
        ArrayList<InstruccionesNodo> lista = new ArrayList<>();
        lista.add(analizarComando(nombreAgente));
        lista.addAll(masComandos(nombreAgente));
        return lista;
    }

    public ArrayList<InstruccionesNodo> masComandos(String nombreAgente) {
        ArrayList<InstruccionesNodo> lista = new ArrayList<>();
        if (tokenActual.getTipo() == TokenTipos.COMANDO_IA) {
            lista.add(analizarComando(nombreAgente));
            lista.addAll(masComandos(nombreAgente));
        }
        return lista;
    }

    public InstruccionesNodo analizarComando(String nombreAgente) {
        String accion = tokenActual.getLexema();
        int fila = tokenActual.getFila();
        int columna = tokenActual.getColumna();
        validaciones(TokenTipos.COMANDO_IA);

        ArrayList<String> variablesUsadas = new ArrayList<>();

        StringBuilder detalle = new StringBuilder();
        detalle.append(analizarExpresion(variablesUsadas));

        if (tokenActual.getTipo() == TokenTipos.CONECTOR) {
            detalle.append(" ").append(analizarConectores(variablesUsadas));
        }

        validaciones(TokenTipos.FLECHA);
        
        String varDestino = tokenActual.getLexema();
        validaciones(TokenTipos.IDENTIFICADOR);

        tablaSimbolos.agregar(new Simbolo(nombreAgente + "." + varDestino, "VARIABLE", "", fila, columna));

        InstruccionesNodo inst = new InstruccionesNodo(accion, detalle.toString(), varDestino, fila, columna);
        inst.getVariablesUsadas().addAll(variablesUsadas);
        return inst;
    }

    public String analizarConectores(ArrayList<String> variablesUsadas) {
        StringBuilder sb = new StringBuilder();
        if (tokenActual.getTipo() == TokenTipos.CONECTOR) {
            sb.append(tokenActual.getLexema());
            validaciones(TokenTipos.CONECTOR);
            sb.append(" ").append(analizarExpresion(variablesUsadas));
            
            if (tokenActual.getTipo() == TokenTipos.CONECTOR) {
                sb.append(" ").append(analizarConectores(variablesUsadas));
            }
        }
        return sb.toString();
    }

    public String analizarExpresion(ArrayList<String> variablesUsadas) {
        StringBuilder sb = new StringBuilder();
        sb.append(analizarTermino(variablesUsadas));
        sb.append(masTerminos(variablesUsadas));
        return sb.toString().trim();
    }

    public String masTerminos(ArrayList<String> variablesUsadas) {
        StringBuilder sb = new StringBuilder();
        if (tokenActual.getTipo() == TokenTipos.MAS) {
            sb.append(" + ");
            validaciones(TokenTipos.MAS);
            sb.append(analizarTermino(variablesUsadas));
            sb.append(masTerminos(variablesUsadas));
        }
        return sb.toString();
    }

    public String analizarTermino(ArrayList<String> variablesUsadas) {
        String lex = tokenActual.getLexema();
        switch (tokenActual.getTipo()) {
            case CADENA:
                validaciones(TokenTipos.CADENA);
                return lex;
            case IDENTIFICADOR:
                if (variablesUsadas != null) {
                    variablesUsadas.add(lex); 
                }
                validaciones(TokenTipos.IDENTIFICADOR);
                return lex;
            case NUMERO:
                validaciones(TokenTipos.NUMERO);
                if (tokenActual.getTipo() == TokenTipos.IDENTIFICADOR) {
                    lex = lex + " " + tokenActual.getLexema();
                    validaciones(TokenTipos.IDENTIFICADOR);
                }
                return lex;
            case CARGAR:
                validaciones(TokenTipos.CARGAR);
                validaciones(TokenTipos.PARENTESIS_ABIERTO);
                String archivo = tokenActual.getLexema();
                validaciones(TokenTipos.CADENA);
                validaciones(TokenTipos.PARENTESIS_CERRADO);
                return "CARGAR(" + archivo + ")";
            default:
                errorEncontrado("CADENA, IDENTIFICADOR, NUMERO o CARGAR");
                modoPanico();
                return "";
        }
    }

    public ArrayList<EjecucionNodo> analizarEjecuciones() {
        ArrayList<EjecucionNodo> lista = new ArrayList<>();
        validaciones(TokenTipos.EJECUTAR);
        String nombre = tokenActual.getLexema();
        int fila = tokenActual.getFila();
        int columna = tokenActual.getColumna();
        validaciones(TokenTipos.IDENTIFICADOR);

        tablaSimbolos.agregar(new Simbolo(nombre, "EJECUTADO", null, fila, columna));
        
        lista.add(new EjecucionNodo(nombre, fila, columna));
        lista.addAll(masEjecuciones());
        return lista;
    }

    public ArrayList<EjecucionNodo> masEjecuciones() {
        ArrayList<EjecucionNodo> lista = new ArrayList<>();
        if (tokenActual.getTipo() == TokenTipos.EJECUTAR) {
            validaciones(TokenTipos.EJECUTAR);
            String nombre = tokenActual.getLexema();
            int fila = tokenActual.getFila();
            int columna = tokenActual.getColumna();
            validaciones(TokenTipos.IDENTIFICADOR);

            tablaSimbolos.agregar(new Simbolo(nombre, "EJECUTADO", null, fila, columna));
            
            lista.add(new EjecucionNodo(nombre, fila, columna));
            lista.addAll(masEjecuciones());
        }
        return lista;
    }

    public ExportarNodo analizarExportar() {
        validaciones(TokenTipos.EXPORTAR);
        ExportarNodo exp = new ExportarNodo(tokenActual.getFila(), tokenActual.getColumna());
        exp.getVariablesExportadas().add(tokenActual.getLexema());
        validaciones(TokenTipos.IDENTIFICADOR);
        exp.getVariablesExportadas().addAll(masIds());
        return exp;
    }

    public ArrayList<String> masIds() {
        ArrayList<String> lista = new ArrayList<>();
        if (tokenActual.getTipo() == TokenTipos.COMA) {
            validaciones(TokenTipos.COMA);
            lista.add(tokenActual.getLexema());
            validaciones(TokenTipos.IDENTIFICADOR);
            lista.addAll(masIds());
        }
        return lista;
    }

    public ArrayList<Token> getListaDeTokens() { 
        return listaDeTokens; 
    }
    public ArrayList<ErrorSintactico> getErroresSintacticos() { 
        return erroresSintacticos; 
    }
    public TablaSimbolos getTablaSimbolos() {
        return tablaSimbolos;
    }
}
