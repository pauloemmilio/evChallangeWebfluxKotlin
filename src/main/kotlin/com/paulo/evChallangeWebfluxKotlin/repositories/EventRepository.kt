package com.paulo.evChallangeWebfluxKotlin.repositories

import com.paulo.evChallangeWebfluxKotlin.entities.Event
import org.springframework.data.mongodb.repository.ReactiveMongoRepository
import org.springframework.stereotype.Repository

@Repository
interface EventRepository: ReactiveMongoRepository<Event, String> {
}