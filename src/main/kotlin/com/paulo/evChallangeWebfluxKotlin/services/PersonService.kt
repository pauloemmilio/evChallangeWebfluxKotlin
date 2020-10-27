package com.paulo.evChallangeWebfluxKotlin.services

import com.paulo.evChallangeWebfluxKotlin.entities.Person
import com.paulo.evChallangeWebfluxKotlin.repositories.PersonRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.web.server.ResponseStatusException
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@Service
class PersonService {

    @Autowired
    lateinit var repository: PersonRepository

    fun findAll(): Flux<Person> {
        return repository.findAll()
    }

    fun findById(id: String): Mono<Person> {
        return repository.findById(id)
                .switchIfEmpty(Mono.error(ResponseStatusException(HttpStatus.NOT_FOUND)))
    }

    fun save(person: Person): Mono<Person> {
        return repository.save(person)
    }

    fun update(id: String, person: Person): Mono<Person> {
        return findById(id)
                .flatMap { p ->
                    p.name = person.name
                    save(p)
                }
                .switchIfEmpty(Mono.error(ResponseStatusException(HttpStatus.NOT_FOUND)))
    }

    fun deleteById(id: String): Mono<Void> {
        return findById(id)
                .flatMap { repository.deleteById(id) }
    }

    fun deleteAll(): Mono<Void> {
        return repository.deleteAll()
    }
}