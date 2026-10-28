/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package proyecto1.proyecto2.lfp.AnalizadorLexico;

import java.io.File;
import java.io.StringReader;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author mario
 */
public class MotorFrame {
       private AnalizadorSintactico analizador;
       private AnalizadorLexico lexer;

    private FramePrincipal principalFrame = new FramePrincipal();
    Archivo archivo = new Archivo();

    public void inciarAnalizador() {
        principalFrame.setVisible(true);
        principalFrame.setMotor(this);

    }

    public void cargarArchivo() {
        
        File archivoDirecto = principalFrame.getArchivo();
        String texto = archivo.leerArchivo(archivoDirecto);
        principalFrame.getTextoFuente().setText(texto);

    }

    public void analizarArchivo() {

        try {
            StringReader reader = new StringReader(principalFrame.getTextoFuente().getText());
            lexer = new AnalizadorLexico(reader);
            analizador = new AnalizadorSintactico(lexer);
            analizador.analizarPrograma();
            cargaTablaTokensCorrectos();
        
        } catch (NullPointerException e) {
          e.printStackTrace();
        }

    }

    public void cargaTablaTokensCorrectos() {
        DefaultTableModel modeloTabla = (DefaultTableModel) principalFrame.getTablaTokens().getModel();
       ArrayList <Token>tokens= analizador.getListaDeTokens();
    
        modeloTabla.setRowCount(0);
int contador=0;
        for (Token token: tokens) {
            contador++;
            modeloTabla.addRow(new Object[]{
                    contador,
                    token.getLexema(),
                    token.getTipo(),
                    token.getFila(),
                    token.getColumna()
                });
        }
    

    }



    public void crearHtmlTokensCorrectos(String nombre) {
        String ruta = principalFrame.getDireccionHtml();
    
    }

    public void crearHtmlTokensIncorrectos(String nombre) {
        String ruta = principalFrame.getDireccionHtml();

      

    }
    public void crearEstadisitca(String nombre){
          String ruta = principalFrame.getDireccionHtml();
        
    }

    public void crearPz(String nombre) {
        String ruta = principalFrame.getDireccionHtml();
        String texto = principalFrame.getTextoFuente().getText();
        archivo.pzTexto(nombre, texto, ruta);

    }

}
