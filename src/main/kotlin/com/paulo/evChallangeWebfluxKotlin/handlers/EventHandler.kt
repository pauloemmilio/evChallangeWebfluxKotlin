package com.paulo.evChallangeWebfluxKotlin.handlers

import com.paulo.evChallangeWebfluxKotlin.entities.Event
import com.paulo.evChallangeWebfluxKotlin.services.EventService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import reactor.core.publisher.Mono


@Component
class EventHandler {

    @Autowired
    lateinit var eventService: EventService

    private val notFound : Mono<ServerResponse> = ServerResponse.notFound().build()

    fun findAll(request: ServerRequest) : Mono<ServerResponse> {
        return ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(eventService.findAll(), Event::class.java)
    }

    fun findById(request: ServerRequest) : Mono<ServerResponse> {
        val id : String = request.pathVariable("id")
        val eventMono: Mono<Event> = eventService.findById(id)
        return eventMono.flatMap { event ->
            ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(event)
        }.switchIfEmpty(notFound);
    }

    fun save(request: ServerRequest) : Mono<ServerResponse> {
        val event: Mono<Event> = request.bodyToMono(Event::class.java);
        return event.flatMap { e ->
            ServerResponse.ok()
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(eventService.save(e), Event::class.java)
        }
    }

    fun update(request: ServerRequest) : Mono<ServerResponse> {
        val id: String = request.pathVariable("id")
        val eventMono: Mono<Event> = request.bodyToMono(Event::class.java)
        val updatedEvent: Mono<Event> = eventMono.flatMap {
            event -> eventService.update(id, event) }
        return updatedEvent.flatMap { event ->
            ServerResponse.ok()
                    .contentType(MediaType.APPLICATION_JSON)
                    .bodyValue(event)
                    .switchIfEmpty(notFound)
        }
    }

    fun deleteById(request: ServerRequest) : Mono<ServerResponse> {
        val id: String = request.pathVariable("id")
        return ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(eventService.deleteById(id), Void::class.java)
    }

    fun deleteAll(request: ServerRequest): Mono<ServerResponse> {
        return ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(eventService.deleteAll(), Void::class.java)
    }
}