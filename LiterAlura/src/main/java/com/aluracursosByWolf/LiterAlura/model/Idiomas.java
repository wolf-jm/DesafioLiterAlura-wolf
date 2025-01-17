package com.aluracursosByWolf.LiterAlura.model;

import jakarta.persistence.*;

@Entity
@Table(name = "Idiomas")
public class Idiomas {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String idioma;
    private String descripcion;

    public Idiomas() {}

    public Idiomas(String datosIdiomas) {
        this.idioma = datosIdiomas;
        this.descripcion = ILibrosMetodos.seleccionarIdioma(datosIdiomas);
    }
@ManyToOne
private Libros libros;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIdioma() {
        return idioma;
    }

    public void setIdioma(String idioma) {
        this.idioma = idioma;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Libros getLibros() {
        return libros;
    }

    public void setLibros(Libros libros) {
        this.libros = libros;
    }
@Override
    public String toString() {
        int i = 0;
        return "\n ------------Libro------------" +
                "\n Titulo: " + libros.getTitulo() +
                "\n Autor: '" + libros.getAutoresList().get(0).getNombre() + '\"' +
                "\n Idioma: '" + libros.getIdiomasLists().get(i).getDescripcion() + '\'' +
                "\n Numero de Descargas: '" + libros.getNumeroDescargas() + '\'' +
                "\n -----------------------------";
}
}
