package hu.neuron.util

import hu.neuron.dto.ClientDTO

fun ClientDTO.validateEmail() : String{
    val emailRegex  = "^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@ [^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$";

    if(!ClientDTO::clientEmail.name.matches(Regex(emailRegex))){
        return "Wrong email format"
    }
    return ""
}
