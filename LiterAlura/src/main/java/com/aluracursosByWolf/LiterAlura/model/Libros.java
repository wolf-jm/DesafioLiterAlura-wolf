package com.aluracursosByWolf.LiterAlura.model;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "Libros")
public class Libros {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String titulo;
    private Double numeroDescargas;

    @OneToMany(mappedBy = "libros", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Autores> autoresList;



    public List<Autores> getAutoresList() {
        return autoresList;
    }

    public void setAutoresList(List<Autores> autoresList) {
        autoresList.forEach(k -> k.setLibros(this));
        this.autoresList = autoresList;
    }

    @OneToMany(mappedBy = "libros", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Idiomas> idiomasLists;

    public List<Idiomas> getIdiomasLists() {
        return idiomasLists;
    }

    public void setIdiomasLists(List<Idiomas> idiomasList) {
        idiomasList.forEach(k -> k.setLibros(this));
        this.idiomasLists = idiomasList;
    }

    public Libros() {
    }

    public Libros(DatosLibros datosLibros) {
        this.titulo = datosLibros.titulo();
        this.numeroDescargas = datosLibros.numeroDescargas();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public Double getNumeroDescargas() {
        return numeroDescargas;
    }

    public void setNumeroDescargas(Double numeroDescargas) {
        this.numeroDescargas = numeroDescargas;
    }

    @Override
    public String toString() {
        int i = 0;
        return
                "\n ------------Libro------------" +
                        "\n Titulo: " +
                        "\n Autor; " +
                        "\n Idioma: " +
                        "\n Numero de descargas: " +
                        "\n -----------------------------";
    }
}
