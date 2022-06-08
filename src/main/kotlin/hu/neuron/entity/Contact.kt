package hu.neuron.entity

import org.springframework.data.annotation.Id
import java.time.LocalDateTime


data class Contact(

    @Id
    val id: Int = 0,

    val clientContactName: String = "",

    val clientContactEmail: String? = null,

    val clientContactPhone: String? = null,

    var clientContactType: String = "",

    val clientContactDate: LocalDateTime = LocalDateTime.now(),

    var clientId: Int = 0
)