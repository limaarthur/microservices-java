package com.ignite.events_microservice.dtos;

public record EmailRequestDTO(String to, String subject, String body) {
    // DTO
    // Nenhum construtor, getter ou setter são necessários,
    // pois o 'record' gera automaticamente esses métodos imutáveis.
}
