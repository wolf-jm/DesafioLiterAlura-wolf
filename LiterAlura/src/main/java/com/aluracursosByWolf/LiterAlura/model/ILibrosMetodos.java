package com.aluracursosByWolf.LiterAlura.model;

public interface ILibrosMetodos {
    public static String seleccionarIdioma(String text) {
        switch (text){
            case "en":
                text = "Inglés";
                break;
            case "es":
                text = "Español";
                break;
            case "fr":
                text = "Francés";
                break;
            case "pt":
                text = "Portugues";
                break;
            default:
                text = "Idioma Desconocido";
                break;
        }
        return text;
    }
}
