package com.paulo.evChallangeWebfluxKotlin.controllers

import com.paulo.evChallangeWebfluxKotlin.entities.Event
import com.paulo.evChallangeWebfluxKotlin.services.EventService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@RestController
@RequestMapping("/events")
class EventController {

    @Autowired
    lateinit var service: EventService

    @GetMapping
    fun findAll() : Flux<Event> {
        return service.findAll()
    }

    @GetMapping("/{id}")
    fun findById(@PathVariable id: String) : Mono<Event> {
        return service.findById(id)
    }

    @PostMapping
    fun save(@RequestBody event: Event) : Mono<Event> {
        return service.save(event)
    }

    @PutMapping("/{id}")
    fun update(@PathVariable id: String, @RequestBody event: Event) : Mono<Event> {
        return service.update(id, event)
    }

    @DeleteMapping("/{id}")
    fun deleteById(@PathVariable id: String) : Mono<Void> {
        return service.deleteById(id)
    }

    @DeleteMapping
    fun deleteAll() : Mono<Void> {
        return service.deleteAll()
    }
}