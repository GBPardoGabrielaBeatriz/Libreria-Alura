package com.LibreriaAlura.Libreria.Alura.repository;
import com.LibreriaAlura.Libreria.Alura.model.Autor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;



@Repository
public interface AutorRepository extends JpaRepository<Autor, Long> {
   @Query("SELECT a FROM Autor a")
    List<Autor> mostrarAutores();
    @Query("SELECT a FROM Autor a WHERE  a.fechaDeMuerte > :fecha")
    List<Autor> findAutoresByYear( String fecha);
}
