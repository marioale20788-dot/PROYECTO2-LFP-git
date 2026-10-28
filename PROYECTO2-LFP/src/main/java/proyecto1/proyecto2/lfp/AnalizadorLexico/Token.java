/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package proyecto1.proyecto2.lfp.AnalizadorLexico;

import java.awt.Color;

/**
 *
 * @author mario
 */
public class Token {
    
    private TokenTipos tipo;
    private String lexema;
    private int fila;
    private int columna;


    public Token(TokenTipos tipo, String lexema, int fila, int columna) {
        this.tipo = tipo;
        this.lexema = lexema;
        this.fila = fila;
        this.columna = columna;

    }

    public TokenTipos getTipo() {
        return tipo;
    }

    public void setTipo(TokenTipos tipo) {
        this.tipo = tipo;
    }

    public String getLexema() {
        return lexema;
    }

    public void setLexema(String lexema) {
        this.lexema = lexema;
    }

    public int getFila() {
        return fila;
    }

    public void setFila(int linia) {
        this.fila = linia;
    }

    public int getColumna() {
        return columna;
    }

    public void setColumna(int columna) {
        this.columna = columna;
    }
    public Color obtenerColorToken() {
    switch (tipo) {
       
        case AGENTE:
        case EJECUTAR:
        case EXPORTAR:
            return new Color(147, 112, 219); 

 
        case COMANDO_IA:
            return new Color(0, 102, 204); 

        case CONECTOR:
            return new Color(0, 150, 136); 

  
        case CADENA:
            return new Color(211, 84, 0); 
        case NUMERO:
            return new Color(46, 204, 113); 

        case IDENTIFICADOR:
            return new Color(44, 62, 80); 

        case VARIABLE:
        case CONTEXTO:
            return new Color(142, 68, 173); 

        case DIRECTIVA:
            return new Color(127, 140, 141); 

        case MAS:
        case IGUAL:
        case FLECHA:
            return new Color(192, 57, 43);

        case ERROR:
            return Color.RED;

        default:
            return Color.BLACK;
    }
}
    
    
    
}
