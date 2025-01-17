package com.aluracursosByWolf.LiterAlura.repository;

import com.aluracursosByWolf.LiterAlura.model.Autores;
import com.aluracursosByWolf.LiterAlura.model.Idiomas;
import com.aluracursosByWolf.LiterAlura.model.Libros;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ILibrosRepository extends JpaRepository<Libros, Long> {
    @Query("SELECT CASE WHEN COUNT(b) > 0 THEN TRUE ELSE FALSE END FROM Libros b WHERE b.titulo = :titulo")
    boolean existTeTitulo(@Param("titulo") String titulo);

    @Query("SELECT b FROM Libros b JOIN b.autoresList a ORDER BY b.id DESC")
    List<Libros> listaLibros();

    @Query("SELECT l FROM Idiomas l WHERE l.idioma LIKE :idioma")
    List<Idiomas> mostrarLibrosIdioma(@Param("idioma") String idioma);

}
