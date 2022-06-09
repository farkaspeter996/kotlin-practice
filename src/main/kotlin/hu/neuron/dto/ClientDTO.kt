package hu.neuron.dto

data class ClientDTO (

    val id: Long = 0,

    val clientName: String = "",

    val clientEmail: String = "",

    val clientPhoneNumber: String = "",

    val clientAddress: String = "",

    val clientAddressPostCode: Int = 0,

    var clientAddressCity: String? = null,

    var contacts: Set<ContactDTO> = emptySet()
)
