package com.ignite.events_microservice.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity(name="subscription")
@Table(name="subscription")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of="id")
public class Subscripbtion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Várias inscrições para um mesmo Evento (Relacionamento)
    @ManyToOne
    private Event event;

    private String participantEmail;
}
