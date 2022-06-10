package hu.neuron.entity

import java.time.LocalDateTime
import org.springframework.data.annotation.Id

data class Contact(
    @Id
    val id: Long = 0,

    val clientContactName: String = "",

    val clientContactEmail: String? = null,

    val clientContactPhone: String? = null,

    var clientContactType: String = "",

    val clientContactDate: LocalDateTime = LocalDateTime.now(),

    var clientId: Long = 0
)
