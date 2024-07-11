package com.LibreriaAlura.Libreria.Alura.model;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table(name = "libros")
public class Libro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String titulo;

   // @Enumerated(EnumType.STRING)
    //private List<String> idiomas;
    private String idioma;

    private Double numeroDeDescargas;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "autor_id", referencedColumnName = "id")
    private Autor autor;

    public Libro(DatosLibros datos) {
        this.titulo = datos.titulo();
        this.idioma = datos.idiomas().toString();
        this.numeroDeDescargas = datos.numeroDeDescargas();
        if (datos.autores() != null && !datos.autores().isEmpty()) {
            this.autor = datos.autores().get(0); // Asigna el primer autor de la lista
        }
    }

    public Libro() {
    }

    // Getters y Setters

    @Override
    public String toString() {
        return "Libro{" +
                "id=" + id +
                ", titulo='" + titulo + '\'' +
                ", autor='" + autor + '\'' +
                ", idiomas='" + idioma + '\'' +
                ", numeroDeDescargas=" + numeroDeDescargas +
                '}';
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

    public String getIdioma() {
        return idioma;
    }

    public void setIdioma(String idioma) {
        this.idioma = idioma;
    }

    public Double getNumeroDeDescargas() {
        return numeroDeDescargas;
    }

    public void setNumeroDeDescargas(Double numeroDeDescargas) {
        this.numeroDeDescargas = numeroDeDescargas;
    }

    public Autor getAutor() {
        return autor;
    }

    public void setAutor(Autor autor) {
        this.autor = autor;
    }
}

