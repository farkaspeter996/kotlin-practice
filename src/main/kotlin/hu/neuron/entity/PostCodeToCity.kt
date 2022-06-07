package hu.neuron.entity

import org.springframework.data.annotation.Id

data class PostCodeToCity (

    @Id
    val postcode: Long = 0,

    val city: String = ""

    )