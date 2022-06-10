package hu.neuron.entity

import org.springframework.data.annotation.Id

data class PostCodeToCity(
    @Id
    val postcode: Int = 0,

    val city: String = ""
)
