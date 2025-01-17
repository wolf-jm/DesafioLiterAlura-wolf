package com.aluracursosByWolf.LiterAlura.repository;

import com.aluracursosByWolf.LiterAlura.model.Autores;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
public interface IAutoresRepository extends JpaRepository<Autores, Long> {

    @Query("SELECT COUNT(b) > 0 FROM Autores b WHERE b.nombre = :nombre")
    Optional<Autores>ExistNombre (String nombre);

    @Query("SELECT b FROM Autores b ORDER BY b.id DESC")
    List<Autores> listaAutores();
}
