package com.aluracursosByWolf.LiterAlura.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ConvierteDatos implements IConvierteDatos {
    private ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public <T> T obtenerDatos(String json, Class<T> clase) {
//        if (json == null || json.trim().isEmpty()) {
//            System.out.println("Los datos de enrada estan vacios");
//            throw new IllegalArgumentException("Los datos de entrada estan vacios");
//        }
        try {
            return objectMapper.readValue(json, clase);
        } catch (JsonProcessingException e) {
            System.out.println("No lo pudimos encontrar, lo siento");
            throw new RuntimeException(e);
        }
    }
}
