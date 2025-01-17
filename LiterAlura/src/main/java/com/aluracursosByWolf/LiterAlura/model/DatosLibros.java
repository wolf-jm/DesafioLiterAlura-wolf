package com.aluracursosByWolf.LiterAlura.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record DatosLibros(
        @JsonAlias("title") String titulo,
        @JsonAlias("authors") List<DatosAutores> datosAutoresList,
        @JsonAlias("languages")List<String> idiomasList,
        @JsonAlias("download_count") Double numeroDescargas ) {
}
