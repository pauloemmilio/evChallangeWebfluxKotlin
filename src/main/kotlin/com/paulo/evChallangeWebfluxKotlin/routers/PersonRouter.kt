package com.paulo.evChallangeWebfluxKotlin.routers

import com.paulo.evChallangeWebfluxKotlin.handlers.PersonHandler
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.MediaType
import org.springframework.web.reactive.function.server.router

@Configuration
class PersonRouter(private val handler: PersonHandler) {

    val endpoint: String = "/api/persons"

    @Bean
    fun route() = router {
        accept(MediaType.APPLICATION_JSON).nest {
            (GET(endpoint)).invoke(handler::findAll)
            (GET("$endpoint/{id}")).invoke(handler::findById)
            (POST(endpoint)).invoke(handler::save)
            (PUT("$endpoint/{id}")).invoke(handler::update)
            (DELETE("$endpoint/{id}")).invoke(handler::deleteById)
            (DELETE(endpoint)).invoke(handler::deleteAll)
        }
    }
}