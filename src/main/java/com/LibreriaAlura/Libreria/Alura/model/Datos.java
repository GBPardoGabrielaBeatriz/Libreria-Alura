package com.LibreriaAlura.Libreria.Alura.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record Datos( @JsonAlias("data") List<DatosLibro> libros){

}
