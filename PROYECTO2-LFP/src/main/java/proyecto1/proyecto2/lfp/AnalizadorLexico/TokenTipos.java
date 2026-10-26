/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package proyecto1.proyecto2.lfp.AnalizadorLexico;

/**
 *
 * @author mario
 */
public enum TokenTipos {
        DIRECTIVA,

        AGENTE,
        CONTEXTO,
        VARIABLE,
        EJECUTAR,
        EXPORTAR,

        COMANDO_IA,
        CARGAR,

        CONECTOR,
        FLECHA,
        IGUAL,
        MAS,

        LLAVE_ABIERTA, 
        LLAVE_CERRADA, 
        PARENTESIS_ABIERTO,   
        PARENTESIS_CERRADO,   
        COMA,    

        IDENTIFICADOR,
        CADENA,
        NUMERO,

        EOF,
        ERROR
    }
