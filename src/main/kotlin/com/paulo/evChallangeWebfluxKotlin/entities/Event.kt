package com.paulo.evChallangeWebfluxKotlin.entities

import com.fasterxml.jackson.annotation.JsonProperty
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document
data class Event (

    @field:Id
    @field:JsonProperty(access = JsonProperty.Access.READ_ONLY)
    val id: String?,

    var name: String?
)