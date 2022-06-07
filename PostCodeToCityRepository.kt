package hu.neuron.repository

import hu.neuron.entity.PostCodeToCity
import org.springframework.data.repository.CrudRepository

interface PostCodeToCityRepository : CrudRepository<PostCodeToCity, Long> {

}