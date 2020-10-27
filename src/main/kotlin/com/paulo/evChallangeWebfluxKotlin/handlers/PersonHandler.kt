package com.paulo.evChallangeWebfluxKotlin.handlers

import com.paulo.evChallangeWebfluxKotlin.entities.Person
import com.paulo.evChallangeWebfluxKotlin.services.PersonService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.body
import reactor.core.publisher.Mono

@Component
class PersonHandler {

    @Autowired
    lateinit var personService: PersonService

    private val notFound : Mono<ServerResponse> = ServerResponse.notFound().build()

    fun findAll(request: ServerRequest): Mono<ServerResponse> {
        return ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(personService.findAll())
    }

    fun findById(request: ServerRequest): Mono<ServerResponse> {
        val id:String = request.pathVariable("id")
        val personMono: Mono<Person> = personService.findById(id)
        return personMono.flatMap { person ->
            ServerResponse.ok()
                    .contentType(MediaType.APPLICATION_JSON)
                    .bodyValue(person)
        }.switchIfEmpty(notFound);
    }

    fun save(request: ServerRequest): Mono<ServerResponse> {
        val person: Mono<Person> = request.bodyToMono(Person::class.java)
        return person.flatMap { p ->
            ServerResponse.ok()
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(personService.save(p))
        }
    }

    fun update(request: ServerRequest): Mono<ServerResponse> {
        val id: String = request.pathVariable("id")
        val personMono: Mono<Person> = request.bodyToMono(Person::class.java)
        val personUpdated: Mono<Person> = personMono.flatMap {
            person -> personService.update(id, person)
        }
        return personUpdated.flatMap { person ->
            ServerResponse.ok()
                    .contentType(MediaType.APPLICATION_JSON)
                    .bodyValue(person)
                    .switchIfEmpty(notFound)
        }
    }

    fun deleteById(request: ServerRequest): Mono<ServerResponse> {
        val id: String = request.pathVariable("id")
        return ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(personService.deleteById(id))
    }

    fun deleteAll(request: ServerRequest): Mono<ServerResponse> {
        return ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(personService.deleteAll())
    }
}