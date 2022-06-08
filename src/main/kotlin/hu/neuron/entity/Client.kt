package hu.neuron.entity

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.MappedCollection


data class Client (

    @Id
    val id: Int = 0,

    val clientName: String = "",

    val clientEmail: String = "",

    val clientPhoneNumber: String = "",

    val clientAddress: String = "",

    val clientAddressPostCode: Int = 0,

    var clientAddressCity: String = "",

    @MappedCollection(idColumn = "client_id", keyColumn = "id")
    var contacts: List<Contact> = emptyList()
)