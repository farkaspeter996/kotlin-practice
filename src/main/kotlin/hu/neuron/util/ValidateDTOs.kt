package hu.neuron.util

import hu.neuron.dto.ClientDTO
import hu.neuron.dto.ContactDTO
import kotlin.reflect.full.memberProperties

private fun validateEmail(email: String){
    val emailRegex = Regex("(?:[a-z0-9!#\$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#\$%&'*+/=?^_`{|}~-]+)*|" +
            "\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")" +
            "@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:(2(5[0-5]|[0-4][0-9])|1[0-9][0-9]|[1-9]?[0-9]))\\.)" +
            "{3}(?:(2(5[0-5]|[0-4][0-9])|1[0-9][0-9]|[1-9]?[0-9])|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])")
    if(!emailRegex.matches(email)){
        throw Exception("invalid email");
    }
}

private fun validatePhoneNumber(phoneNumber: String){
    val phoneRegex = Regex("\\d{11}|(?:\\d{4}-){3}\\d{5}|\\(\\d{4}\\)\\d{4}-?\\d{5}")
    if (!phoneRegex.matches(phoneNumber)){
        throw Exception("invalid phone number")
    }
}

fun ClientDTO.validate(){
    val propertiesByName = ClientDTO::class.memberProperties.associateBy { it.name }
    propertiesByName.forEach { prop ->
        try {
            if (prop.key == ClientDTO::clientEmail.name) {
                validateEmail(ClientDTO::clientEmail.get(this))
            }
            if (prop.key == ClientDTO::clientPhoneNumber.name) {
                validatePhoneNumber(ClientDTO::clientPhoneNumber.get(this))
            }
        } catch (exception: Exception){
            println(exception.printStackTrace())
            throw exception
        }
    }
}

fun ContactDTO.validate(){
    val propertiesByName = ContactDTO::class.memberProperties.associateBy { it.name }
    propertiesByName.forEach { prop ->
        try {
            if (prop.key == ContactDTO::clientContactEmail.name) {
                ContactDTO::clientContactEmail.get(this)?.let { validateEmail(it) }
            }
            if (prop.key == ContactDTO::clientContactPhone.name) {
                ContactDTO::clientContactPhone.get(this)?.let { validatePhoneNumber(it) }
            }
        } catch (exception: Exception){
            throw exception
        }
    }
}

