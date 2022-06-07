package hu.neuron.util

import hu.neuron.dto.ClientDTO
import kotlin.reflect.KProperty1

fun <T : Comparable<T>> MutableList<ClientDTO>.sortByFields(prop : KProperty1<ClientDTO, T>, direction : Boolean) {

    if(direction){
        this.sortBy { prop.get(it) }
    }
    else{
        this.sortByDescending { prop.get(it) }
    }
}