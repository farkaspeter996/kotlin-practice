package hu.neuron.mapper

import hu.neuron.dto.ClientDTO
import hu.neuron.dto.ClientWithoutContactDTO
import hu.neuron.entity.Client
import kotlin.reflect.full.memberProperties

fun Client.toClientDTO() = with(::ClientDTO) {
    val propertiesByName = Client::class.memberProperties.associateBy { it.name }
    callBy(parameters.associateWith { parameter ->
        when (parameter.name) {
            Client::contacts.name -> contacts.map { it.toContactDTO() }.toSet()
            else -> propertiesByName[parameter.name]?.get(this@toClientDTO)
        }
    })
}

fun ClientDTO.toClientEntity() = with(::Client) {
    val propertiesByName = ClientDTO::class.memberProperties.associateBy { it.name }
    callBy(parameters.associateWith { parameter ->
        when (parameter.name) {
            ClientDTO::contacts.name -> contacts.map { it.toContactEntity() }.toSet()
            else -> propertiesByName[parameter.name]?.get(this@toClientEntity)
        }
    })
}

fun Client.toClientWithoutContactDTO() = with(::ClientWithoutContactDTO) {
    val propertiesByName = Client::class.memberProperties.associateBy { it.name }
    callBy(parameters.associateWith { parameter ->
        when (parameter.name) {
            Client::contacts.name -> null
            else -> propertiesByName[parameter.name]?.get(this@toClientWithoutContactDTO)
        }
    })
}
