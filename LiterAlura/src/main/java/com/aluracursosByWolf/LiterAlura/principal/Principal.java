package com.aluracursosByWolf.LiterAlura.principal;

import com.aluracursosByWolf.LiterAlura.model.*;
import com.aluracursosByWolf.LiterAlura.repository.IAutoresRepository;
import com.aluracursosByWolf.LiterAlura.repository.ILibrosRepository;
import com.aluracursosByWolf.LiterAlura.service.ConsumoAPI;
import com.aluracursosByWolf.LiterAlura.service.ConvierteDatos;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Component;

import java.util.Comparator;
import java.util.DoubleSummaryStatistics;
import java.util.*;
import java.util.Scanner;
import java.util.stream.Collectors;

@Component
public class Principal {
    private final String URL_BASE = "https://gutendex.com/books/?search=";
    private ConsumoAPI consumoAPI = new ConsumoAPI();
    private ConvierteDatos conversor = new ConvierteDatos();
    private Scanner teclado = new Scanner(System.in);

    @Autowired
    private ILibrosRepository librosRepository;
    @Autowired
    private IAutoresRepository autoresRepository;

    public Principal(ILibrosRepository librosRepository) {
        this.librosRepository = librosRepository;
    }

    public void muestraElMenu() {
        var opcion = -1;
        while (opcion != 0) {
            var menu = """
                    ------------------------------------
                    Elige la opcion que deseas ejecutar
                    ------------------------------------
                    1 - Busqueda de Libros por Titulo
                    2 - Mostrar Libros Buscados
                    3 - Mostrar Libros  por Autores
                    4 - Mostrar Libros por Idioma
                    5 - Mostrar Estadisticas del Libros en Gutenberg.com
                    6 - Top 7 de Libros mas Descargados
                    
                    0 - Salir
                    --------------------------by wolf---
                    """;
            System.out.println(menu);
            opcion = teclado.nextInt();
            teclado.nextLine();

            switch (opcion) {
                case 1:
                    buscarLibroWeb();
                    break;
                case 2:
                    mostrarLibrosBuscados();
                    break;
                case 3:
                    mostrarListaAutores();
                    break;
                case 4:
                    mostrarLibrosIdioma();
                    break;
                case 5:
                    datosEstadisticos();
                    break;
                case 6:
                    top7LibrosMasBuscados();
                    break;
                case 0:
                    System.out.println("Cerrando la aplicacion...");
                    break;
                default:
                    System.out.println("Opcion no valida");
            }
        }
    }

    private Optional<DatosLibros> getDatosResult() {

        System.out.println("Escribe el nombre del libro que quieres buscar");
        var nombreLibro = teclado.nextLine();

        var json = consumoAPI.obtenerDatos(URL_BASE + nombreLibro.replace(" ", "+"));
        Datos result = conversor.obtenerDatos(json, Datos.class);

        Optional<DatosLibros> libroBuscado = result.datosLibrosList().stream()
                .filter(l -> l.titulo().toUpperCase().contains(nombreLibro.toUpperCase()))
                        .findFirst();
        if (libroBuscado.isPresent()) {
                System.out.println("----------------LIBRO-----------------");
                System.out.println("Titulo: " + libroBuscado.get().titulo());
                System.out.println("Autor: " + libroBuscado.get().datosAutoresList().stream().map(DatosAutores::nombre).filter(nombre -> nombre != null).collect(Collectors.joining(", ")));
                System.out.println("Idioma: " + libroBuscado.get().idiomasList().stream().collect(Collectors.joining(", ")));

                System.out.println("Numero de Descargas: " + libroBuscado.get().numeroDescargas());
                System.out.println("------------------------------------------------");
        } else {
            System.out.println("------------------------------------");
            System.out.println("-------Libro No Encontrado----------");
            System.out.println("------------------------------------");
        }
        return libroBuscado;
    }

    private void buscarLibroWeb() {

        Optional<DatosLibros> datosLibrosOptional = getDatosResult();

        if (datosLibrosOptional.isEmpty()) {
            System.out.println("No se encontraron datos para el libro buscado.");
            return;
        }

        try {
            DatosLibros datosLibros = datosLibrosOptional.get();

            // Verificar si el libro ya existe en la base de datos
            if (librosRepository.existTeTitulo(datosLibros.titulo())) {
                System.out.println("El libro \"" + datosLibros.titulo() + "\" ya existe en la base de datos.");
                return;
            }

            // Crear y guardar el libro
            Libros libro = new Libros(datosLibros);
            List<Autores> autores = datosLibros.datosAutoresList().stream()
                    .map(Autores::new)
                    .collect(Collectors.toList());
            libro.setAutoresList(autores);

            List<Idiomas> idiomas = datosLibros.idiomasList().stream()
                    .map(Idiomas::new)
                    .collect(Collectors.toList());
            libro.setIdiomasLists(idiomas);

            librosRepository.save(libro);
            System.out.println("El libro \"" + libro.getTitulo() + "\" ha sido guardado exitosamente.");

        } catch (DataIntegrityViolationException ex) {
            System.err.println("Error al guardar el libro: " + ex.getMessage());
        } catch (Exception ex) {
            System.err.println("Ocurrió un error inesperado: " + ex.getMessage());
        }
    }

    private void mostrarLibrosBuscados() {
        List<Libros> listaLibros = librosRepository.listaLibros();
        if (listaLibros.isEmpty()) {
            System.out.println("No hay libros guardados en la base de datos.");
        } else {
            System.out.println("----------Lista de Libros----------");
            for (Libros libro : listaLibros) {
                System.out.println("----------------LIBRO-----------------");
                System.out.println("Titulo: " + libro.getTitulo());
                System.out.println("Autor(es): " + libro.getAutoresList().stream()
                        .map(Autores::getNombre)
                        .collect(Collectors.joining(", ")));
                System.out.println("Idioma(s): " + libro.getIdiomasLists().stream()
                        .map(Idiomas::getIdioma)
                        .collect(Collectors.joining(", ")));
                System.out.println("Numero de Descargas: " + libro.getNumeroDescargas());
                System.out.println("------------------------------------");
            }
        }
    }

    private void mostrarListaAutores() {


        List<Autores> autores = autoresRepository.listaAutores();
        autores.forEach(autor -> {
            System.out.println("Autor ID: " + autor.getId());
            System.out.println("Nombre: " + autor.getNombre());
            System.out.println("Fecha de Nacimiento: " + autor.getFechaNacimiento());
            System.out.println("Fecha de Deceso: " + autor.getFechaDeceso());
            System.out.println("----------------------------------------");
        });


    }

    private void mostrarLibrosIdioma() {
        System.out.println("Ingrese el idoma que desea buscar: ");
        System.out.println("""
                en - Inglés
                es - Español
                fr - Francés
                pt - Portugues
                """);
        var idioma = teclado.nextLine();
        List<Idiomas> idiomasList = librosRepository.mostrarLibrosIdioma(idioma);
        System.out.println(idiomasList);
    }

    private void datosEstadisticos() {
        List<Libros> librosList = librosRepository.listaLibros();
        DoubleSummaryStatistics est = librosList.stream().filter(t -> t.getNumeroDescargas() > 7)
                .mapToDouble(t -> t.getNumeroDescargas())
                .summaryStatistics();
//Busca el libro
        String libroMasDescargado = librosList.stream()
                .filter(t -> t.getNumeroDescargas() == est.getMax())
                .map(Libros::getTitulo)
                .findFirst()
                .orElse("No se encontro ninguno");
        String libroConMenosDescargas = librosList.stream()
                .filter(t -> t.getNumeroDescargas() == est.getMin())
                .map(Libros::getTitulo)
                .findFirst()
                .orElse("No se encontro ninguno");


        System.out.println(" ------------------------------------------------ ");
        System.out.println(" -------Estadistica de descargas de Libros------- ");
        System.out.println(" ------------------------------------------------ ");
        System.out.println(" Media de las Descargas: " + est.getAverage());
        System.out.println(" Libro más leido : " + libroMasDescargado + ", Con un máximo de descargas: " + est.getMax());
        System.out.println(" Libro con menos leido: " + libroConMenosDescargas + ", Con un minimo de descargas: " + est.getMin());
        System.out.println(" Total descargas de libros: " + est.getSum());
    }

    private void top7LibrosMasBuscados() {
        var json = consumoAPI.obtenerDatos(URL_BASE);
        Datos result = conversor.obtenerDatos(json, Datos.class);

        List<DatosLibros> top7másLibros = result.datosLibrosList().stream()
                .sorted(Comparator.comparing(DatosLibros::numeroDescargas).reversed())
                .limit(7)
                .collect(Collectors.toList());

        System.out.println(" --------------------------------------------- ");
        System.out.println(" -------TOP 7 DE LIBROS MAS DESCARGADOS------- ");
        System.out.println(" --------------------------------------------- ");
        top7másLibros.forEach(d -> System.out.println("Libro: " + d.titulo() + ", Numero de Descargas: " + d.numeroDescargas()));

        List<DatosLibros> top7menosLibros = result.datosLibrosList().stream()
                .sorted(Comparator.comparing(DatosLibros::numeroDescargas))
                .limit(7)
                .collect(Collectors.toList());
        System.out.println(" --------------------------------------------- ");
        System.out.println(" -------TOP 7 DE LIBROS MENOS DESCARGADOS------- ");
        System.out.println(" --------------------------------------------- ");
        top7menosLibros.forEach(m -> System.out.println("Libros: " + m.titulo() + ", Numero de Descargas: " + m.numeroDescargas()));
    }
}
