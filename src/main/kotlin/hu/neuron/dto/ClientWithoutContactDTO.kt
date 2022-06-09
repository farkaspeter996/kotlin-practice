package hu.neuron.dto

data class ClientWithoutContactDTO (

    val id: Long = 0,

    val clientName: String = "",

    val clientEmail: String = "",

    val clientPhoneNumber: String = "",

    val clientAddress: String = "",

    val clientAddressPostCode: Int = 0,

    var clientAddressCity: String? = null

    )
