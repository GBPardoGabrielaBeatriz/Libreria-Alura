package com.LibreriaAlura.Libreria.Alura.service;

public interface IConvierteDatos {

    <T> T obtenerDatos(String json, Class <T> clase );
}
