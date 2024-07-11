package com.LibreriaAlura.Libreria.Alura.model;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "autores")
    public class Autor {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;
        private String nombre;
        private String fechaDeNacimiento;
        private String fechaDeMuerte;
       // @OneToMany(mappedBy = "autor")
       @OneToMany(mappedBy = "autor", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
        private List<Libro>libros;

        public Autor(){}

        public Autor(DatosAutor autor) {
            this.nombre = autor.nombre();
            this.fechaDeNacimiento = autor.fechaDeNacimiento();
            this.fechaDeMuerte = autor.fechaDeMuerte();
        }

        public String getNombre() {
            return nombre;
        }

        public void setNombre(String nombre) {
            this.nombre = nombre;
        }

        public String getFechaDeNacimiento() {
            return fechaDeNacimiento;
        }

        public void setFechaDeNacimiento(String fechaDeNacimiento) {
            this.fechaDeNacimiento = fechaDeNacimiento;
        }

    public List<Libro> getLibros() {
        return libros;
    }

    public void setLibros(List<Libro> libros) {
        this.libros = libros;
    }
    public String getFechaDeMuerte() {
            return fechaDeMuerte;}

    public void setFechaDeMuerte(String fechaDeMuerte) {
            this.fechaDeMuerte = fechaDeMuerte;}

    @Override
        public String toString() {
            return "AutorBd{" +
                    "id=" + id +
                    ", nombre='" + nombre + '\'' +
                    ", fechaDeNacimiento='" + fechaDeNacimiento + '\'' +
                    ", fechaDeMuerte='" + fechaDeMuerte + '\'' +
                    //", libroDd=" + libro +
                    '}';
        }
}

