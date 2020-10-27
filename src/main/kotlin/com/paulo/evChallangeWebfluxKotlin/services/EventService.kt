package com.paulo.evChallangeWebfluxKotlin.services

import com.paulo.evChallangeWebfluxKotlin.entities.Event
import com.paulo.evChallangeWebfluxKotlin.repositories.EventRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.web.server.ResponseStatusException
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@Service
class EventService {

    @Autowired
    lateinit var repository: EventRepository

    fun findAll(): Flux<Event> {
        return repository.findAll()
    }

    fun findById(id: String) : Mono<Event> {
        return repository.findById(id)
                .switchIfEmpty(Mono.error(ResponseStatusException(HttpStatus.NOT_FOUND)))
    }

    fun save(event: Event) : Mono<Event> {
        return repository.save(event)
    }

    fun update(id: String, event: Event) : Mono<Event> {
        return findById(id)
                .flatMap { existingEvent: Event ->
                    existingEvent.name = event.name
                    save(existingEvent)
                }
                .switchIfEmpty(Mono.error(ResponseStatusException(HttpStatus.NOT_FOUND)))
    }

    fun deleteById(id: String) : Mono<Void> {
        return findById(id)
                .flatMap { repository.deleteById(id) }
    }

    fun deleteAll() : Mono<Void> {
        return repository.deleteAll()
    }
}