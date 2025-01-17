package com.aluracursosByWolf.LiterAlura.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record DatosIdiomas(
        @JsonAlias("idiomas") String idioma ) {
}
