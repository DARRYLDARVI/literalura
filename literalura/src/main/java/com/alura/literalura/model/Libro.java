package com.alura.literalura.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Entity
@Table(name = "libros")
public class Libro {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;
    private String titulo;

    @ManyToOne
    private Autor autor;

    private String idiomas; // CAMBIO: antes era List<String>

    private double descargas;

    public Libro() {
    }

    public Libro(Optional<DatosLibros> datosLibros) {
        if (datosLibros.isPresent()) {
            DatosLibros datos = datosLibros.get();
            this.titulo = datos.titulo();
            if (!datos.autor().isEmpty()) {
                this.autor = new Autor(datos.autor().get(0)); // Primer autor
            }
            this.idiomas = String.join(",", datos.idiomas()); // CAMBIO: unir lista en un solo string
            this.descargas = datos.descargas();
        } else {
            this.titulo = "Desconocido";
            this.autor = null;
            this.idiomas = "";
            this.descargas = 0;
        }
    }

    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        Id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public Autor getAutor() {
        return autor;
    }

    public void setAutor(Autor autor) {
        this.autor = autor;
    }

    public String getIdiomas() {
        return idiomas;
    }

    public void setIdiomas(String idiomas) {
        this.idiomas = idiomas;
    }

    public double getDescargas() {
        return descargas;
    }

    public void setDescargas(double descargas) {
        this.descargas = descargas;
    }
}