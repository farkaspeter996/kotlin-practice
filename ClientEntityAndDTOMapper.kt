package hu.neuron.mapper

import hu.neuron.dto.ClientDTO
import hu.neuron.entity.Client
import kotlin.reflect.full.memberProperties

fun Client.toClientDTO() = with(::ClientDTO) {
    val propertiesByName = Client::class.memberProperties.associateBy { it.name }
    callBy(parameters.associateWith { parameter ->
        propertiesByName[parameter.name]?.get(this@toClientDTO)
    })
}

fun ClientDTO.toClientEntity() = with(::Client) {
    val propertiesByName = ClientDTO::class.memberProperties.associateBy { it.name }
    callBy(parameters.associateWith { parameter ->
        when(parameter.name){
            ClientDTO::contacts.name ->  contacts.map { it.toContactEntity() }
            else -> propertiesByName[parameter.name]?.get(this@toClientEntity)
        }
    })
}



