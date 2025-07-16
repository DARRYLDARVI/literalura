package com.alura.literalura.principal;

import com.alura.literalura.model.*;
import com.alura.literalura.repository.AutorRepository;
import com.alura.literalura.repository.LibroRepository;
import com.alura.literalura.service.ConsumoAPI;
import com.alura.literalura.service.ConvierteDatos;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class Principal {
    private static final String LINK_BASE = "https://gutendex.com/books/";
    private ConsumoAPI consumoAPI = new ConsumoAPI();
    private ConvierteDatos conversor = new ConvierteDatos();
    Scanner teclado = new Scanner(System.in);
    private LibroRepository repositorio;
    private AutorRepository autorRepositorio;
    List<Libro> libros;


    public Principal(LibroRepository repository, AutorRepository autorRepository) {
        this.repositorio = repository;
        this.autorRepositorio = autorRepository;
    }


    public void muestraElMenu(){

        var opcion = -1;
        while (opcion != 0){

            var menu ="""
                    ****************************************************
                    Bienvenido Seleccione la opcion deseada
                    
                    1- Busqueda de libros por titulo
                    2- Lista de todos los libros buscados
                    3- Lista de autores
                    4- Lista de autores en determinado año
                    5- Buscar titulos por idioma
                    6- Salir
                    
                    """;
            System.out.println(menu);
            opcion = teclado.nextInt();
            teclado.nextLine();

            switch (opcion){
                case 1:
                    buscarLibro();
                    break;
                case 2:
                    mostrarLibrosBuscados();
                    break;
                case 3:
                    mostrarAutores();
                    break;
                case 4:
                    buscarAutoresVivosEnAnio();
                    break;
                case 5:
                    libroPorIdiomas();
                    break;
                case 6:
                    System.out.println("saliendo");
                    opcion = 0;
                    break;
                default:
                    System.out.println("opcion no valida");
                    break;
            }

        }


    }

    private void libroPorIdiomas() {
        List<Libro> librosPorIdioma;
        var menu ="""
                    ****************************************************
                    Bienvenido Seleccione la opcion deseada
                    
                    1- Frances
                    2- Ingles
                    3- Español

                    
                    """;

        System.out.println(menu);
        var opcion = teclado.nextInt();

        switch (opcion) {

            case 1:
                librosPorIdioma = repositorio.buscarLibroPorIdioma("fr");
                librosPorIdioma.forEach(libro -> {
                    System.out.println("Título: " + libro.getTitulo());
                    System.out.println("Autor: " + libro.getAutor().getNombre());
                    System.out.println("Idiomas: " + libro.getIdiomas());
                    System.out.println("Descargas: " + libro.getDescargas());
                    System.out.println("-----");
                });


                break;
            case 2:
                librosPorIdioma = repositorio.buscarLibroPorIdioma("en");
                librosPorIdioma.forEach(libro -> {
                    System.out.println("Título: " + libro.getTitulo());
                    System.out.println("Autor: " + libro.getAutor().getNombre());
                    System.out.println("Idiomas: " + libro.getIdiomas());
                    System.out.println("Descargas: " + libro.getDescargas());
                    System.out.println("-----");
                });

                break;
            case 3:
                librosPorIdioma = repositorio.buscarLibroPorIdioma("es");
                librosPorIdioma.forEach(libro -> {
                    System.out.println("Título: " + libro.getTitulo());
                    System.out.println("Autor: " + libro.getAutor().getNombre());
                    System.out.println("Idiomas: " + libro.getIdiomas());
                    System.out.println("Descargas: " + libro.getDescargas());
                    System.out.println("-----");
                });

                break;
            default:
                System.out.println("opcion no valida");
                break;
        }
    }


    private void buscarLibro() {
        System.out.println("Escribe el nombre del libro que deseas buscar:");
        var nombreLibro = teclado.nextLine();
        var json = consumoAPI.obtenerDatos(LINK_BASE + "?search=" + nombreLibro.replace(" ", "+"));
        var datosBusqueda = conversor.obtenerDatos(json, Datos.class);

        Optional<DatosLibros> libroBuscado = datosBusqueda.resultados().stream()
                .filter(l -> l.titulo().toUpperCase().contains(nombreLibro.toUpperCase()))
                .findFirst();

        if (libroBuscado.isPresent()) {
            System.out.println("Libro encontrado");
            System.out.println(libroBuscado.get());

            DatosLibros datos = libroBuscado.get();

            // Obtener el primer autor
            if (datos.autor().isEmpty()) {
                System.out.println("No se encontró autor para este libro.");
                return;
            }

            DatosAutor datosAutor = datos.autor().get(0);

            // Verificar si el autor ya existe en la base
            Autor autor = autorRepositorio.findByNombre(datosAutor.nombre())
                    .orElseGet(() -> {
                        Autor nuevoAutor = new Autor(datosAutor);
                        return autorRepositorio.save(nuevoAutor);
                    });

            // Crear y guardar el libro asociado al autor
            Libro libro = new Libro(libroBuscado);
            libro.setAutor(autor);
            repositorio.save(libro);

        } else {
            System.out.println("Libro no encontrado");
        }
    }

    private void mostrarLibrosBuscados() {
        libros = repositorio.findAll();
        if (libros.isEmpty()) {
            System.out.println("No hay libros guardados aún.");
        } else {
            libros.forEach(libro -> {
                System.out.println("Título: " + libro.getTitulo());
                System.out.println("Autor: " + libro.getAutor().getNombre());
                System.out.println("Idiomas: " + libro.getIdiomas());
                System.out.println("Descargas: " + libro.getDescargas());
                System.out.println("-----");
            });
        }
    }

    private void mostrarAutores() {
        var autores = autorRepositorio.findAll();
        if (autores.isEmpty()) {
            System.out.println("No hay autores guardados aún.");
        } else {
            autores.forEach(autor -> {
                System.out.println("Nombre: " + autor.getNombre());
                System.out.println("Fecha de nacimiento: " + autor.getFechaNacimiento());
                System.out.println("Fecha de fallecimiento: " + autor.getFechaMuerte());
                System.out.println("-----");
            });
        }
    }

    private void buscarAutoresVivosEnAnio() {
        try {
            System.out.print("Ingrese el año para buscar autores vivos: ");
            int anio = Integer.parseInt(teclado.nextLine());

            List<Autor> autores = autorRepositorio.buscarAutoresVivosEnAnio(anio);

            if (autores.isEmpty()) {
                System.out.println("No se encontraron autores vivos en el año " + anio);
            } else {
                System.out.println("Autores vivos en el año " + anio + ":");
                autores.forEach(autor -> {
                    System.out.println("Nombre: " + autor.getNombre());
                    System.out.println("Año de nacimiento: " + autor.getFechaNacimiento());
                    if (autor.getFechaMuerte() != null) {
                        System.out.println("Año de muerte: " + autor.getFechaMuerte());
                    }
                    System.out.println("-----");
                });
            }
        } catch (NumberFormatException e) {
            System.out.println("Debe ingresar un año válido.");
        }
    }


}


