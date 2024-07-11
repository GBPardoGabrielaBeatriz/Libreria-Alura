package com.LibreriaAlura.Libreria.Alura.model;

import java.util.ArrayList;
import java.util.List;

public enum Idioma {

    ES("es"),
    EN("en"),
    FR("fr"),
    IT("it"),
    PT("pt");

    private String idioma;

    @Override
    public String toString() {
        return idioma;
    }

    public String getIdioma() {
        return idioma;
    }

    public void setIdioma(String idioma) {
        this.idioma = idioma;
    }

    Idioma (String idioma){
        this.idioma = idioma;
    }

    public static Idioma fromString (String text){
        for (Idioma idioma : Idioma.values()){
            if (idioma.idioma.equalsIgnoreCase(text)){
                return idioma;
            }
        }
        throw new IllegalArgumentException("Ningún idioma encontrado" +  text);
    }
}