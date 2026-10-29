/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package proyecto1.proyecto2.lfp.AnalizadorLexico;

import java.util.ArrayList;

/**
 *
 * @author mario
 */
public class TablaSimbolos {
    private ArrayList<Simbolo> listaSimbolos;

    public TablaSimbolos() {
        this.listaSimbolos = new ArrayList<>();
    }

    public void agregar(Simbolo simbolo) {
        listaSimbolos.add(simbolo);
    }

    public Simbolo buscar(String nombre) {
        for (Simbolo s : listaSimbolos) {
            if (s.getNombre().equals(nombre)) {
                return s;
            }
        }
        return null;
    }

    public boolean existe(String nombre) {
        return buscar(nombre) != null;
    }

    public ArrayList<Simbolo> getListaSimbolos() {
        return listaSimbolos;
    }
}
