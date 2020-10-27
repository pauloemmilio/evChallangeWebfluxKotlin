package com.paulo.evChallangeWebfluxKotlin.repositories

import com.paulo.evChallangeWebfluxKotlin.entities.Person
import org.springframework.data.mongodb.repository.ReactiveMongoRepository
import org.springframework.stereotype.Repository

@Repository
interface PersonRepository: ReactiveMongoRepository<Person, String> {
}