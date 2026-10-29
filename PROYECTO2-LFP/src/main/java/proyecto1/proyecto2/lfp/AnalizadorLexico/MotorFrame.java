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
import proyecto1.proyecto2.lfp.AnalizadorLexico.AST.EsribirReporteAST;
import proyecto1.proyecto2.lfp.AnalizadorLexico.AST.ProgramaNodo;

/**
 *
 * @author mario
 */
public class MotorFrame {

    private AnalizadorSintactico analizador;
    private AnalizadorLexico lexer;
    private AnalizadorSemantico analizadorSemantico;

    private FramePrincipal principalFrame = new FramePrincipal();
    ProgramaNodo program = new ProgramaNodo();
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

    public boolean inicializarConARchivo(File archivoAAbrir) {

        if (archivoAAbrir.getName().endsWith(".pz")) {
            principalFrame.setVisible(true);
            principalFrame.setMotor(this);
            String texto = archivo.leerArchivo(archivoAAbrir);
            principalFrame.getTextoFuente().setText(texto);
            return true;

        } else {
            JOptionPane.showMessageDialog(null, "FORMATO DE ARCHIVO INCORRECTO");
            return false;
        }

    }

    public void analizarArchivo() {

        try {
            StringReader reader = new StringReader(principalFrame.getTextoFuente().getText());
            lexer = new AnalizadorLexico(reader);
            analizador = new AnalizadorSintactico(lexer);
            analizadorSemantico = new AnalizadorSemantico();
            this.program = analizador.analizarPrograma();
            ArrayList<ErrorSemantico> erroresSemanticos = (ArrayList<ErrorSemantico>) analizadorSemantico.analizar(program);

            cargaTablaTokensCorrectos();
            cargarTokensIncorrectos();
            cargaTablaErroresSintacticos();
            pintarTexto();
            escribirResultado(program, erroresSemanticos);

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

    public ArrayList<Token> tokensCorrectoss(ArrayList<Token> tokens) {
        ArrayList<Token> tokenCorrecto = new ArrayList<>();
        for (Token token : tokens) {

            if (token.getTipo() != TokenTipos.ERROR) {
                tokenCorrecto.add(token);
            }

        }
        return tokenCorrecto;

    }

    public void escribirResultado(ProgramaNodo programa, ArrayList<ErrorSemantico> errorSemantico) {
        principalFrame.getTxtConsolaResultados().setText("");
        principalFrame.getTxtError().setText("");

        int contadoreSemantico=0;
        if(!errorSemantico.isEmpty()){
            for (ErrorSemantico errorSemantico1 : errorSemantico) {
                contadoreSemantico++;
                principalFrame.getTxtError().append("ERROR SEMANTICO\n");
                principalFrame.getTxtError().append(contadoreSemantico + " Error: fila: " + errorSemantico1.getFila() + " columna: " + errorSemantico1.getColumna() + " error: " + errorSemantico1.getDescripcion()+ "\n");
                principalFrame.getTxtError().append("\n");
            }
            
        }
        if (analizador.getErroresSintacticos().isEmpty() && ErroresLexicos(analizador.getListaDeTokens()).isEmpty()) {
            Prompt promt = new Prompt();
            String texto = promt.generarPrompt(programa);
            principalFrame.getTxtConsolaResultados().setText(texto);

        } else {
            ArrayList<ErrorSintactico> erroresSintactico = analizador.getErroresSintacticos();
            ArrayList<Token> tokenError = ErroresLexicos(analizador.getListaDeTokens());
            int contadorErrorLexico = 0;
            int contadorES = 0;
            for (Token token : tokenError) {
                contadorErrorLexico++;
                principalFrame.getTxtError().append("ERROR LEXICO\n");
                principalFrame.getTxtError().append(contadorErrorLexico + " Error: fila: " + token.getFila() + " columna: " + token.getColumna() + " lexema: " + token.getLexema() + "\n");
                principalFrame.getTxtError().append("\n");

            }
            for (ErrorSintactico error : erroresSintactico) {
                contadorES++;
                principalFrame.getTxtError().append("");
                principalFrame.getTxtError().append("ERROR SINTACTICO\n");
                principalFrame.getTxtError().append(contadorES + " Error: fila: " + error.getFila() + " columna: " + error.getColumna() + " valor esperado: " + error.getTipoEsperado()
                        + " valor encontrado: " + error.getTipoEncontrado() + "\n");
                principalFrame.getTxtError().append("\n");

            }

        }

    }

    public void cargarTokensIncorrectos() {
        DefaultTableModel modeloTabla = (DefaultTableModel) principalFrame.getTablaTokenIncorrectos().getModel();
        ArrayList<Token> tokens = analizador.getListaDeTokens();

        modeloTabla.setRowCount(0);
        int contador = 0;
        for (Token token : tokens) {

            if (token.getTipo() == TokenTipos.ERROR) {
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

    public void crearHtmlTokensCorrectos(String nombre) {
        if (analizador != null) {
            archivo = new Archivo();
            String ruta = principalFrame.getDireccionHtml();
            int contador = 0;
            archivo.htmlTokens(nombre, ruta);
            ArrayList<Token> correctos = tokensCorrectoss(analizador.getListaDeTokens());
            for (Token correcto : correctos) {
                contador++;
                archivo.escribirHtml(contador, correcto.getLexema(), correcto.getTipo().toString(), correcto.getFila(),
                        correcto.getColumna(), correcto.obtenerColorTokenHTML());

            }
        }

    }

    public void crearHtmlErrorSintactico(String nombre) {
        if (analizador != null) {
            archivo = new Archivo();
            String ruta = principalFrame.getDireccionHtml();
            int contador = 0;
            archivo.htmlErroresSintacticos(nombre, ruta);
            ArrayList<ErrorSintactico> errores = analizador.getErroresSintacticos();
            for (ErrorSintactico errore : errores) {
                contador++;
                archivo.escribirHtmlErroresSintacticos(contador,
                        errore.getError(),
                        errore.getTipoEncontrado(),
                        errore.getTipoEsperado(),
                        errore.getFila(),
                        errore.getColumna(),
                        "#FF0000");

            }
        }
    }
    public void generarTablaDeSimbolos(String nombre){
         if (analizador != null) {
            archivo = new Archivo();
            String ruta = principalFrame.getDireccionHtml();
            int contador = 0;
            archivo.htmlTablaDeSimbolos(nombre, ruta);
             TablaSimbolos simbolos = analizador.getTablaSimbolos();
             ArrayList<Simbolo> simbolo = simbolos.getListaSimbolos();
             for (Simbolo simbolo1 : simbolo) {
                 archivo.escribirHtmlTablaDeSimbolos(simbolo1.getNombre(), simbolo1.getTipo(), 
                         simbolo1.getValor(), simbolo1.getFila(), simbolo1.getColumna(),  "#FF0000");
             }
          
        }
    }

    public void crearArbol(String nombre) {
        if (analizador != null) {
            archivo = new Archivo();
            String ruta = principalFrame.getDireccionHtml();

            archivo.htmlReporteAST(nombre, ruta);
            EsribirReporteAST AST = new EsribirReporteAST();
            String texto = AST.generarTextoAST(program);
            archivo.escribirHtmlAST(texto);

        }

    }

    public void crearHtmlTokensIncorrectos(String nombre) {
        if (analizador != null) {
            archivo = new Archivo();
            String ruta = principalFrame.getDireccionHtml();
            int contador = 0;
            archivo.htmlTokens(nombre, ruta);
            ArrayList<Token> incorrecto = ErroresLexicos(analizador.getListaDeTokens());
            for (Token incorrectos : incorrecto) {
                contador++;
                archivo.escribirHtml(contador, incorrectos.getLexema(), incorrectos.getTipo().toString(), incorrectos.getFila(),
                        incorrectos.getColumna(), incorrectos.obtenerColorTokenHTML());

            }
        }

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
