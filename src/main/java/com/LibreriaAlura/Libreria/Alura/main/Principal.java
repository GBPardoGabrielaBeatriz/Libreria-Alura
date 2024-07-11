package com.LibreriaAlura.Libreria.Alura.main;

import com.LibreriaAlura.Libreria.Alura.model.*;
import com.LibreriaAlura.Libreria.Alura.repository.AutorRepository;
import com.LibreriaAlura.Libreria.Alura.repository.LibroRepository;
import com.LibreriaAlura.Libreria.Alura.service.ConsumoAPI;
import com.LibreriaAlura.Libreria.Alura.service.ConvierteDatos;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

@Component
public class Principal {
    private Scanner tipeo = new Scanner(System.in);
    private ConsumoAPI consumoAPI = new ConsumoAPI();
    public static final String URL_BASE = "https://gutendex.com/books/";
    private ConvierteDatos conversor = new ConvierteDatos();
    private LibroRepository libroRepositorio;
    private AutorRepository autorRepositorio;

    @Autowired
    public Principal(LibroRepository repositorio) {
        this.libroRepositorio = repositorio;
    }
    @Autowired
    public void setAutorRepositorio(AutorRepository autorRepositorio) {
        this.autorRepositorio = autorRepositorio;
    }
    public Principal() {
    }
    public void verMenu() {
        var json = consumoAPI.obtenerDatos(URL_BASE);
        //System.out.println(json);
        var datos = conversor.obtenerDatos(json, Datos.class);
        //System.out.println(datos);
        var opcion = -1;
        while (opcion != 0) {
            var menu = """
                    1 - Buscar Libro por titulo 
                    2 - Buscar libros registrados
                    3 - Buscar autores 
                    4 - Buscar autores vivos por año
                    5 - Buscar libro por idioma
                                       
                    0 - Salir
                    """;
            System.out.println(menu);
            opcion = tipeo.nextInt();
            tipeo.nextLine();

            switch (opcion) {
                case 1:
                    buscarLibroPorTitulo();
                    break;
                case 2:
                    buscarLibrosRegistrados();
                    break;
                case 3:
                     buscarAutores();
                    //listarAutores();
                    break;
                case 4:
                    buscarAutoresVivosPorAño();
                    break;
                case 5:
                    //buscarLibroPorIdioma();
                    listarLibrosRegistradosPorIdioma();
                    break;
                case 0:
                    System.out.println("Cerrando la aplicación...");
                    break;
                default:
                    System.out.println("Opción inválida");
            }
        }

    }

    // 1)
    private DatosLibros obtenerDatosDelLibro(String tituloLibro) {
        var json = consumoAPI.obtenerDatos(URL_BASE + "?search=" + tituloLibro.replace(" ", "+"));
        var datosBusqueda = conversor.obtenerDatos(json, Datos.class);

        return datosBusqueda.libros().stream()
                .filter(l -> l.titulo().toUpperCase().contains(tituloLibro.toUpperCase()))
                .findFirst()
                .orElse(null);
    }

    private void buscarLibroPorTitulo() {
        System.out.println("Escribe el nombre del libro que deseas buscar");
        String tituloLibro = tipeo.nextLine();
        DatosLibros libro = obtenerDatosDelLibro(tituloLibro);

        if (libro != null) {
            Libro libroEntidad = new Libro(libro);
            System.out.println("Aca lo encontramos! ");
            System.out.println("+++++++++ LIBRO +++++++++" +
                    "\nTítulo: " + libroEntidad.getTitulo() +
                    "\nIdioma: " + libroEntidad.getIdioma() +
                    "\nAutor: " + libroEntidad.getAutor() +
                    "\nNúmero de descargas: " + libroEntidad.getNumeroDeDescargas() +
                    "\n");
            try {
                libroEntidad = libroRepositorio.save(libroEntidad);
                System.out.println("\n Procederemos a guardarlo..\n");
                if (libroEntidad.getId() != null) {

                    System.out.println(" Guardado exitosamente!");
                    System.out.println("*************");
                    System.out.println(libroEntidad.getTitulo() + "\n ID: " + libroEntidad.getId());
                } else {
                    System.out.println("Error al guardar libro . No se encuentra ID del mismo.");
                }
            } catch (DataIntegrityViolationException e) {
                System.out.println("Ya se encuentra almacenado en la base de datos!\n");
            }
        } else {
            System.out.println("Libro no encontrado, intenta con otro título\n");
        }
    }

    // 2)
    private void buscarLibrosRegistrados() {
        List<Libro> listadoLibros = libroRepositorio.findAll();
        listadoLibros.forEach(l -> System.out.println(
                "+++++++++ LIBRO +++++++++" +
                        "\nTítulo: " + l.getTitulo() +
                        "\nIdioma: " + l.getIdioma() +
                        "\nAutor: " + l.getAutor() +
                        "\nNúmero de descargas: " + l.getNumeroDeDescargas() +
                        "\n"
        ));

    }

    // 3)
    private void buscarAutores() {
        List<Autor> listaAutores = autorRepositorio.mostrarAutores(); // Assuming autorRepository is correctly autowired

           if (listaAutores.isEmpty()) {
               System.out.println("No hay autores registrados");
           } else {
               System.out.println("****************");
               System.out.println("Lista de los autores registrados : \n");
               listaAutores.forEach(autor -> {
                   System.out.println("Nombre: " + autor.getNombre());
                   System.out.println("Fecha de Nacimiento: " + autor.getFechaDeNacimiento());
                   System.out.println("Fecha de Fallecimiento: " + (autor.getFechaDeMuerte() != null ? autor.getFechaDeMuerte() : "N/A"));
                   System.out.println("*****************\n");
               });
           }}

// 4)
private void buscarAutoresVivosPorAño() {
    System.out.println("Ingrese el año vivo de Autore(s) que desea buscar: ");
    try {
        var fecha = tipeo.nextLine();
        List<Autor> autores= autorRepositorio.findAutoresByYear(fecha);
        if (autores.isEmpty()){
            System.out.println("No hay autores en ese rango");
        } else {
            autores.stream().forEach(System.out::println);
        }
    } catch (InputMismatchException e) {
        System.out.println("Ingrese un año correcto");
        tipeo.nextLine();
    }

}

//5
private void listarLibrosRegistradosPorIdioma() {
    List<String> idiomasRegistrados = libroRepositorio.findAll().stream()
            .map(Libro::getIdioma)
            .filter(idioma -> idioma != null && !idioma.isEmpty())
            .distinct()
            .collect(Collectors.toList());

    if (idiomasRegistrados.isEmpty()) {
        System.out.println("No hay idiomas registrados.");
        return;
    }

    System.out.println("Seleccione uno de los idiomas registrados:");
    idiomasRegistrados.forEach(System.out::println);

    var idiomaSeleccionado = tipeo.nextLine().trim().toLowerCase(); // Convertir a minúsculas

    List<String> idiomasRegistradosLowerCase = idiomasRegistrados.stream()
            .map(String::toLowerCase)
            .collect(Collectors.toList());

    if (idiomasRegistradosLowerCase.contains(idiomaSeleccionado)) {
        List<Libro> librosPorIdioma = libroRepositorio.findAll().stream()
                .filter(l -> l.getIdioma() != null && l.getIdioma().equalsIgnoreCase(idiomaSeleccionado))
                .collect(Collectors.toList());

        if (librosPorIdioma.isEmpty()) {
            System.out.println("No hay libros registrados en el idioma seleccionado.");
        } else {
            librosPorIdioma.forEach(l -> System.out.println(
                    "+++++++++ LIBRO +++++++++" +
                            "\nTítulo: " + l.getTitulo() +
                            "\nIdioma: " + l.getIdioma() +
                            "\nAutor: " + l.getAutor() +
                            "\nNúmero de descargas: " + l.getNumeroDeDescargas() +
                            "\n"));
        }
    } else {
        System.out.println("Opción inválida.");
    }
}

}
















