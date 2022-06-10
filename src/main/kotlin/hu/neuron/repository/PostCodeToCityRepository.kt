package hu.neuron.repository

import hu.neuron.entity.PostCodeToCity
import org.springframework.data.jdbc.repository.query.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.data.repository.query.Param

interface PostCodeToCityRepository : CrudRepository<PostCodeToCity, Long> {

    @Query("select pctc.city from post_code_to_city as pctc where pctc.postcode = :postcode")
    fun findCityByPostcode(@Param("postcode") postcode: Int): String
}
