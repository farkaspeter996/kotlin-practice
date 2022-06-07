package hu.neuron.entity

import org.springframework.data.annotation.Id
import java.time.LocalDateTime


data class Contact(

    @Id
    val id: Long = 0,

    val clientContactName: String = "",

    val clientContactEmail: String? = null,

    val clientContactPhone: String? = null,

    val clientContactType: String = "",

    val clientContactDate: LocalDateTime = LocalDateTime.now(),

    val clientId: Long = 0
)