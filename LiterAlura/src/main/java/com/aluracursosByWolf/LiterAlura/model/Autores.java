package com.aluracursosByWolf.LiterAlura.model;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "autores")
public class Autores {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;
    @Column(unique = true)
    private String nombre;
    private String fechaNacimiento;
    private String fechaDeceso;

    @ManyToOne
    private Libros libros;

    public Libros getLibros() {
        return libros;
    }

    public void setLibros(Libros libros) {
        this.libros = libros;
    }

    public Autores() {}

    public Autores(DatosAutores datosAutor) {
        this.nombre = datosAutor.nombre();
        this.fechaNacimiento = datosAutor.fechaNacimiento();
        this.fechaDeceso = datosAutor.fechaDeceso();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Autores autores = (Autores) o;
        return Objects.equals(nombre, autores.nombre); // compara solo por nombre
    }

    @Override
    public int hashCode() {
        return Objects.hash(nombre); // genera bash baso por el nombre
    }

    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        Id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(String fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public String getFechaDeceso() {
        return fechaDeceso;
    }

    public void setFechaDeceso(String fechaDeceso) {
        this.fechaDeceso = fechaDeceso;
    }

    @Override
    public String toString(){
        return "\n------------Autores y sus Obras Literarias------------" +
               "\n Nombre: " + nombre +
               "\n Fecha de Nacimiento: " + fechaNacimiento +
               "\n Fecha de Deceso: " + fechaDeceso +
               "\n Libros : " + libros +
               "\n -----------------------------------------------------";
    }
}
