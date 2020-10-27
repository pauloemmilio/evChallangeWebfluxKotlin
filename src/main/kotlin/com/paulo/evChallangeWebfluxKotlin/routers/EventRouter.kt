package com.paulo.evChallangeWebfluxKotlin.routers

import com.paulo.evChallangeWebfluxKotlin.handlers.EventHandler
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.MediaType.APPLICATION_JSON
import org.springframework.web.reactive.function.server.router

@Configuration
class EventRouter(private val handler: EventHandler) {

    val endpoint: String = "/api/events"

    @Bean
    fun router() = router {
        accept(APPLICATION_JSON).nest {
            (GET(endpoint)).invoke(handler::findAll)
            (GET("$endpoint/{id}")).invoke(handler::findById)
            (POST(endpoint)).invoke(handler::save)
            (PUT("$endpoint/{id}")).invoke(handler::update)
            (DELETE("$endpoint/{id}")).invoke(handler::deleteById)
            (DELETE(endpoint)).invoke(handler::deleteAll)
        }
    }
}