package hu.neuron.entity

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.MappedCollection

data class Client(
    @Id
    val id: Long = 0,

    val clientName: String = "",

    val clientEmail: String = "",

    val clientPhoneNumber: String = "",

    val clientAddress: String = "",

    val clientAddressPostCode: Int = 0,

    val clientAddressCity: String = "",

    @MappedCollection(idColumn = "CLIENT_ID", keyColumn = "id")
    var contacts: Set<Contact> = emptySet()
)
