package hu.neuron.dto

import com.fasterxml.jackson.annotation.JsonFormat
import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import com.fasterxml.jackson.databind.annotation.JsonSerialize
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer
import java.time.LocalDateTime

data class ContactDTO (

    val id: Long = 0,

    val clientContactName: String = "",

    val clientContactEmail: String? = null,

    val clientContactPhone: String? = null,

    val clientContactType: String = "",

    @JsonSerialize(using = LocalDateTimeSerializer::class, `as` = LocalDateTime::class)
    @JsonDeserialize(using = LocalDateTimeDeserializer::class, `as` = LocalDateTime::class)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    val clientContactDate: LocalDateTime = LocalDateTime.now(),

    val clientId: Long = 0
)