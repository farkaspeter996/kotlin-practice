package hu.neuron.mapper

import ContactTypeEnum
import hu.neuron.dto.ContactDTO
import hu.neuron.entity.Contact
import kotlin.reflect.full.memberProperties

fun Contact.toContactDTO() = with(::ContactDTO) {
    val propertiesByName = Contact::class.memberProperties.associateBy { it.name }
    callBy(parameters.associateWith { parameter ->
        when (parameter.name) {
            Contact::clientContactType.name -> ContactTypeEnum.valueOf(clientContactType).contactValue
            else -> propertiesByName[parameter.name]?.get(this@toContactDTO)
        }
    })
}

fun ContactDTO.toContactEntity() = with(::Contact) {
    val propertiesByName = ContactDTO::class.memberProperties.associateBy { it.name }
    callBy(parameters.associateWith { parameter ->
        propertiesByName[parameter.name]?.get(this@toContactEntity)
    })
}
