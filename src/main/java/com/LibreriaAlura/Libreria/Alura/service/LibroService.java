package com.LibreriaAlura.Libreria.Alura.service;

import com.LibreriaAlura.Libreria.Alura.model.Libro;
import com.LibreriaAlura.Libreria.Alura.repository.AutorRepository;
import com.LibreriaAlura.Libreria.Alura.repository.LibroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LibroService {
    @Autowired
    private LibroRepository libroRepository;

    @Autowired
    private AutorRepository autorRepository;

    public Libro buscarLibroPorTitulo(String titulo) {return null;}
        // Implementa la llamada a la API y el mapeo de los datos

/*
    public List<Libro> listarLibros() {
        return libroRepository.findAll();
    }

    public List<Libro> listarLibrosPorIdioma(String idioma) {
        return libroRepository.findByIdioma(idioma);
    }
    */

}
