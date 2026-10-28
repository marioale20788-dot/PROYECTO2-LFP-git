/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package proyecto1.proyecto2.lfp.AnalizadorLexico;

import java.awt.Color;
import java.io.File;
import java.io.StringReader;
import java.text.ListFormat.Style;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.JTextPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.Element;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

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
            cargarTokensIncorrectos();
            cargaTablaErroresSintacticos();
            pintarTexto();
            escribirResultado();

        } catch (NullPointerException e) {
            e.printStackTrace();
        }

    }

    public void cargaTablaTokensCorrectos() {
        DefaultTableModel modeloTabla = (DefaultTableModel) principalFrame.getTablaTokens().getModel();
        ArrayList<Token> tokens = analizador.getListaDeTokens();

        modeloTabla.setRowCount(0);
        int contador = 0;
        for (Token token : tokens) {
            if (token.getTipo() != TokenTipos.ERROR) {
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

    }

    public void cargaTablaErroresSintacticos() {
        DefaultTableModel modeloTabla = (DefaultTableModel) principalFrame.getTablaErroresSintacticos().getModel();
        ArrayList<ErrorSintactico> errores = analizador.getErroresSintacticos();

        modeloTabla.setRowCount(0);
        int contador = 0;
        for (ErrorSintactico error : errores) {
            contador++;
            modeloTabla.addRow(new Object[]{
                contador,
                error.getError(),
                error.getTipoEncontrado(),
                error.getTipoEsperado(),
                error.getFila(),
                error.getColumna()
            });
        }

    }
   

    public void pintarTexto() {
        JTextPane areaTexto = principalFrame.getTextoFuente();
        StyledDocument doc = areaTexto.getStyledDocument();

        javax.swing.text.Style estiloBase = areaTexto.addStyle("EstiloBase", null);
        StyleConstants.setForeground(estiloBase, Color.BLACK);
        StyleConstants.setBackground(estiloBase, Color.WHITE);
        StyleConstants.setUnderline(estiloBase, false);
        StyleConstants.setBold(estiloBase, false);
        doc.setCharacterAttributes(0, doc.getLength(), estiloBase, true);

        javax.swing.text.Style estiloLexico = areaTexto.addStyle("ErrorLexicoStyle", null);
        StyleConstants.setForeground(estiloLexico, Color.RED);
        StyleConstants.setUnderline(estiloLexico, true);
        StyleConstants.setBold(estiloLexico, true);

        javax.swing.text.Style estiloSintactico = areaTexto.addStyle("ErrorSintacticoStyle", null);
        StyleConstants.setForeground(estiloSintactico, new Color(211, 84, 0)); // Naranja quemado
        StyleConstants.setUnderline(estiloSintactico, true);
        StyleConstants.setBold(estiloSintactico, true);

        ArrayList<Token> tokens = analizador.getListaDeTokens();
        if (tokens != null) {
            for (Token token : tokens) {
                int offset = obtenerNumero(doc, token.getFila(), token.getColumna());
                int longitud = (token.getLexema() != null) ? token.getLexema().length() : 1;

                if (offset >= 0 && (offset + longitud) <= doc.getLength()) {
                    if (token.getTipo() == TokenTipos.ERROR) {

                        doc.setCharacterAttributes(offset, longitud, estiloLexico, false);
                    } else {

                        javax.swing.text.Style estiloToken = areaTexto.addStyle("Token_" + token.getTipo(), null);
                        StyleConstants.setForeground(estiloToken, token.obtenerColorToken());
                        StyleConstants.setUnderline(estiloToken, false);
                        doc.setCharacterAttributes(offset, longitud, estiloToken, false);
                    }
                }
            }
        }

        ArrayList<ErrorSintactico> erroresSintacticos = analizador.getErroresSintacticos();
        if (erroresSintacticos != null) {
            for (ErrorSintactico err : erroresSintacticos) {
                int offset = obtenerNumero(doc, err.getFila(), err.getColumna());

                String lexemaError = err.getTipoEncontrado();
                int longitud = (lexemaError != null && !lexemaError.isEmpty()) ? lexemaError.length() : 1;

                if (offset >= 0 && (offset + longitud) <= doc.getLength()) {
                    doc.setCharacterAttributes(offset, longitud, estiloSintactico, false);
                }
            }
        }
    }

    public ArrayList<Token> ErroresLexicos(ArrayList<Token> tokens) {

        ArrayList<Token> erroresLexicos = new ArrayList<>();
        for (Token token : tokens) {

            if (token.getTipo() == TokenTipos.ERROR) {
                erroresLexicos.add(token);
            }

        }
        return erroresLexicos;

    }

    public void escribirResultado() {
        principalFrame.getTxtConsolaResultados().setText("");
        if (analizador.getErroresSintacticos().isEmpty() && ErroresLexicos(analizador.getListaDeTokens()).isEmpty()) {
            Prompt promt = new Prompt();
            String texto = promt.generarPromt(analizador.getListaDeTokens());
            principalFrame.getTxtConsolaResultados().setText(texto);

        } else {
            ArrayList<ErrorSintactico> erroresSintactico = analizador.getErroresSintacticos();
            ArrayList<Token> tokenError = ErroresLexicos(analizador.getListaDeTokens());
            int contadorErrorLexico = 0;
            int contadorES = 0;
            for (Token token : tokenError) {
                contadorErrorLexico++;
                principalFrame.getTxtConsolaResultados().append("ERROR LEXICO\n");
                principalFrame.getTxtConsolaResultados().append(contadorErrorLexico + " Error: fila: " + token.getFila() + " columna: " + token.getColumna() + " lexema: " + token.getLexema() + "\n");
                principalFrame.getTxtConsolaResultados().append("\n");

            }
            for (ErrorSintactico error : erroresSintactico) {
                contadorES++;
                principalFrame.getTxtConsolaResultados().append("");
                principalFrame.getTxtConsolaResultados().append("ERROR SINTACTICO\n");
                principalFrame.getTxtConsolaResultados().append(contadorES + " Error: fila: " + error.getFila() + " columna: " + error.getColumna() + " valor esperado: " + error.getTipoEsperado()
                        + " valor encontrado: " + error.getTipoEncontrado() + "\n");
                principalFrame.getTxtConsolaResultados().append("\n");

            }

        }

    }

    public void cargarTokensIncorrectos() {
        DefaultTableModel modeloTabla = (DefaultTableModel) principalFrame.getTablaTokenIncorrectos().getModel();
        ArrayList<Token> tokens = analizador.getListaDeTokens();

        modeloTabla.setRowCount(0);
        int contador = 0;
        for (Token token : tokens) {
            contador++;
            if (token.getTipo() == TokenTipos.ERROR) {
                modeloTabla.addRow(new Object[]{
                    contador,
                    token.getLexema(),
                    token.getTipo(),
                    token.getFila(),
                    token.getColumna()
                });
            }

        }
    }

    public void crearHtmlTokensCorrectos(String nombre) {
        String ruta = principalFrame.getDireccionHtml();

    }

    public void crearHtmlTokensIncorrectos(String nombre) {
        String ruta = principalFrame.getDireccionHtml();

    }

    public void crearEstadisitca(String nombre) {
        String ruta = principalFrame.getDireccionHtml();

    }

    public void crearPz(String nombre) {
        String ruta = principalFrame.getDireccionHtml();
        String texto = principalFrame.getTextoFuente().getText();
        archivo.pzTexto(nombre, texto, ruta);

    }

    private int obtenerNumero(StyledDocument doc, int fila, int columna) {
        try {
            Element raiz = doc.getDefaultRootElement();
            int indiceLinea = fila - 1; 

            if (indiceLinea < 0 || indiceLinea >= raiz.getElementCount()) {
                return -1;
            }

            Element lineaElem = raiz.getElement(indiceLinea);
            int inicioLinea = lineaElem.getStartOffset();
            int finLinea = lineaElem.getEndOffset();

            int offsetCalculado = inicioLinea + (columna - 1);
            if (offsetCalculado >= finLinea) {
                return finLinea - 1;
            }

            return offsetCalculado;
        } catch (Exception e) {
            return -1;
        }
    }

}
