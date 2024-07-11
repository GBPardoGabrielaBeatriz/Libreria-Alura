package com.LibreriaAlura.Libreria.Alura.repository;
import com.LibreriaAlura.Libreria.Alura.model.Autor;
import com.LibreriaAlura.Libreria.Alura.model.Idioma;
import com.LibreriaAlura.Libreria.Alura.model.Libro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LibroRepository extends JpaRepository<Libro, Long> {
     List<Libro> findByIdioma(String idioma);

    //List<Libro> findTop5ByOrderByNumeroDeDescargasDesc();

}
