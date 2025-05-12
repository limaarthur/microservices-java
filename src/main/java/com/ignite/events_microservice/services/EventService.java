package com.ignite.events_microservice.services;

import com.ignite.events_microservice.domain.Event;
import com.ignite.events_microservice.domain.Subscription;
import com.ignite.events_microservice.dtos.EmailRequestDTO;
import com.ignite.events_microservice.dtos.EventRequestDTO;
import com.ignite.events_microservice.exceptions.EventFullException;
import com.ignite.events_microservice.exceptions.EventNotFoundException;
import com.ignite.events_microservice.repositories.EventRepository;
import com.ignite.events_microservice.repositories.SubscriptionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class EventService {

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private SubscriptionRepository subscriptionRepository;

    @Autowired
    private EmailServiceClient emailServiceClient;

    // Método que lista todos os eventos da tabela
    public List<Event> getAllEvents() {
        return eventRepository.findAll();
    }

    // Método que lista todos os eventos próximos
    public List<Event> getUpcomingEvents() {
        return eventRepository.findUpcomingEvents(LocalDateTime.now());
    }

    public Event createEvent(EventRequestDTO eventRequest) {
        Event newEvent = new Event(eventRequest);
        return eventRepository.save(newEvent);
    }

    private Boolean isEventFull(Event event){
        return event.getRegisteredParticipants() >= event.getMaxParticipants();
    }

    public void registerParticipant(String eventId, String participantEmail) {
        Event event = eventRepository.findById(eventId).orElseThrow(EventNotFoundException::new);

        if (isEventFull(event)) {
            throw new EventFullException();
        }

        Subscription subscription = new Subscription(event, participantEmail);
        subscriptionRepository.save(subscription);

        event.setRegisteredParticipants(event.getRegisteredParticipants() + 1);

        EmailRequestDTO emailRequest = new EmailRequestDTO(participantEmail, "Confirmação de Inscrição", "Você foi inscrito no evento com sucesso!");

        emailServiceClient.sendEmail(emailRequest);
    }
}
