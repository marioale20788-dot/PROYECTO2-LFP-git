/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package proyecto1.proyecto2.lfp.AnalizadorLexico;

import java.io.IOException;
import java.util.ArrayList;

/**
 *
 * @author mario
 */
public class AnalizadorSintactico {
    
    private AnalizadorLexico analizadorLexico;
    private Token tokenActual;
    private ArrayList<Token> listaDeTokens; 
    private ArrayList<ErrorSintactico> erroresSintacticos;
    
    public AnalizadorSintactico(AnalizadorLexico analizador){
        this.analizadorLexico=analizador;
        this.listaDeTokens = new ArrayList<>();
        this.erroresSintacticos = new ArrayList<>();
        
    }
    
    
    public void avanzar(){      
        try {
            this.tokenActual = analizadorLexico.yylex();
            if(this.tokenActual==null){
                this.tokenActual = new Token(TokenTipos.EOF, "EOF", 0, 0);
            }else{
                if(this.tokenActual.getTipo()!=TokenTipos.EOF){
                    this.listaDeTokens.add(tokenActual);
                }
                
            }
        } catch (IOException ex) {
          tokenActual = new Token(TokenTipos.EOF, "EOF", 0, 0);
        }
        
    }
    
    
    public void validaciones(TokenTipos tipoEsperado){
        if(tokenActual.getTipo()==tipoEsperado){
            avanzar();
        }else{
            errorEncontrado(tipoEsperado.toString());
            modoPanico();
            
        }
    }
    public void errorEncontrado(String tokenEsperado){
        
        ErrorSintactico error = new ErrorSintactico(tokenActual.getLexema(),
                tokenActual.getTipo().toString(), tokenEsperado,
                tokenActual.getFila(), tokenActual.getColumna());
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
    
  
    public void analizarPrograma() {
        analizarDirectivas();
        analizarAgentes();
        analizarEjecuciones();
        analizarExportar();
        validaciones(TokenTipos.EOF);
    }

    public void analizarDirectivas() {
        while (tokenActual.getTipo() == TokenTipos.DIRECTIVA) {
            validaciones(TokenTipos.DIRECTIVA);
            validaciones(TokenTipos.CADENA);
        }
    }

    public void analizarAgentes() {
        analizarAgente();
        masAgentes();
    }

    public void masAgentes() {
        if (tokenActual.getTipo() == TokenTipos.AGENTE) {
            analizarAgente();
            masAgentes();
        }
    }

    public void analizarAgente() {
        validaciones(TokenTipos.AGENTE);
        validaciones(TokenTipos.IDENTIFICADOR);
        validaciones(TokenTipos.LLAVE_ABIERTA);
        analizarContexto();
        analizarVariables();
        analizarComandos();
        validaciones(TokenTipos.LLAVE_CERRADA);
    }

    public void analizarContexto() {
        if (tokenActual.getTipo() == TokenTipos.CONTEXTO) {
            validaciones(TokenTipos.CONTEXTO);
            validaciones(TokenTipos.IGUAL);
            validaciones(TokenTipos.CADENA);
        }
    }

    public void analizarVariables() {
        if (tokenActual.getTipo() == TokenTipos.VARIABLE) {
            validaciones(TokenTipos.VARIABLE);
            validaciones(TokenTipos.IDENTIFICADOR);
            validaciones(TokenTipos.IGUAL);
            analizarExpresion();
            analizarVariables();
        }
    }

    public void analizarComandos() {
        analizarComando();
        masComandos();
    }

    public void masComandos() {
        if (tokenActual.getTipo() == TokenTipos.COMANDO_IA) {
            analizarComando();
            masComandos();
        }
    }

    public void analizarComando() {
        validaciones(TokenTipos.COMANDO_IA);
        analizarExpresion();
        analizarConectores();
        validaciones(TokenTipos.FLECHA);
        validaciones(TokenTipos.IDENTIFICADOR);
    }

    public void analizarConectores() {
        if (tokenActual.getTipo() == TokenTipos.CONECTOR) {
            validaciones(TokenTipos.CONECTOR);
            analizarExpresion();
            analizarConectores();
        }
    }

    public void analizarExpresion() {
        analizarTermino();
        masTerminos();
    }

    public void masTerminos() {
        if (tokenActual.getTipo() == TokenTipos.MAS) {
            validaciones(TokenTipos.MAS);
            analizarTermino();
            masTerminos();
        }
    }

    public void analizarTermino() {
        switch (tokenActual.getTipo()) {
            case CADENA:
                validaciones(TokenTipos.CADENA);
                break;
            case IDENTIFICADOR:
                validaciones(TokenTipos.IDENTIFICADOR);
                break;
            case NUMERO:
                validaciones(TokenTipos.NUMERO);
                if (tokenActual.getTipo() == TokenTipos.IDENTIFICADOR) {
                    validaciones(TokenTipos.IDENTIFICADOR); 
                }
                break;
            case CARGAR:
                validaciones(TokenTipos.CARGAR);
                validaciones(TokenTipos.PARENTESIS_ABIERTO);
                validaciones(TokenTipos.CADENA);
                validaciones(TokenTipos.PARENTESIS_CERRADO);
                break;
            default:
                errorEncontrado("CADENA, IDENTIFICADOR, NUMERO o CARGAR");
                 modoPanico();
                break;
        }
    }

    public void analizarEjecuciones() {
        validaciones(TokenTipos.EJECUTAR);
        validaciones(TokenTipos.IDENTIFICADOR);
        masEjecuciones();
    }

    public void masEjecuciones() {
        if (tokenActual.getTipo() == TokenTipos.EJECUTAR) {
            validaciones(TokenTipos.EJECUTAR);
            validaciones(TokenTipos.IDENTIFICADOR);
            masEjecuciones();
        }
    }

    public void analizarExportar() {
        validaciones(TokenTipos.EXPORTAR);
        validaciones(TokenTipos.IDENTIFICADOR);
        masIds();
    }

    public void masIds() {
        if (tokenActual.getTipo() == TokenTipos.COMA) {
            validaciones(TokenTipos.COMA);
            validaciones(TokenTipos.IDENTIFICADOR);
            masIds();
        }
    }
    
    
    
    
    
}
