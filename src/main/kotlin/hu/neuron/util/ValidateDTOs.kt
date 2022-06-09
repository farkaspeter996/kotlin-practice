package hu.neuron.util

import hu.neuron.dto.ClientDTO
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
    val foreignPhoneNumber = "^(?:(?!36))[1-9]{1}[0-9]{7,}"
    val hungaryPhoneNumber =
        "^36(22|25|23|24|26|27|28|29|53|31|32|35|33|34|36|37|42|44|45|46|47|48|49|52|54|56|57|59|62|63|66|68|69|72|73|71|74|75|76|77|78|79|82|84|85|83|92|93|87|88|89|94|95|96|99)[1-9]{1}[0-9]{5}"
    val mobilePhoneNumber = "^36(20|30|31|50|70)[1-9]{1}[0-9]{6}"
    val bpPhoneNumber = "^361[1-9]{1}[0-9]{6}"
    val concatenated = Regex("$foreignPhoneNumber|$hungaryPhoneNumber|$mobilePhoneNumber|$bpPhoneNumber")

    if (!concatenated.matches(phoneNumber)){
        throw Exception("invalid phone number")
    }
}

private fun validateFields(prop : Any){
    if(prop == null || prop == "" || prop == 0){
        throw Exception("invalid property")
    }
}

//TODO when-re átütni, checkolni hogy az egyik töltve van-e, logikai javítás
fun ClientDTO.validate(){
    val propertiesByName = ClientDTO::class.memberProperties.associateBy { it.name }
    propertiesByName.forEach { prop ->
        validateFields(prop)
        try {
            when(prop.key){
                ClientDTO::clientEmail.name -> validateEmail(ClientDTO::clientEmail.get(this))
                ClientDTO::clientPhoneNumber.name -> validatePhoneNumber(ClientDTO::clientPhoneNumber.get(this))
                ClientDTO::contacts.name -> contacts
                    .forEach {
                    validateEmail(it.clientContactEmail!!)
                    validatePhoneNumber(it.clientContactPhone!!)
                }
            }
        } catch (exception: Exception){
            throw exception
        }
    }
}

//fun ContactDTO.validate(){
//    val propertiesByName = ContactDTO::class.memberProperties.associateBy { it.name }
//    propertiesByName.forEach { prop ->
//        try {
//            if (prop.key == ContactDTO::clientContactEmail.name) {
//                ContactDTO::clientContactEmail.get(this)?.let { validateEmail(it) }
//            }
//            if (prop.key == ContactDTO::clientContactPhone.name) {
//                ContactDTO::clientContactPhone.get(this)?.let { validatePhoneNumber(it) }
//            }
//
//        } catch (exception: Exception){
//            throw exception
//        }
//    }
//}

