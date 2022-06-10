package hu.neuron.repository

import hu.neuron.entity.Client
import java.time.LocalDateTime
import org.springframework.data.jdbc.repository.query.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository

@Repository
interface ClientRepo : CrudRepository<Client, Long> {

    @Query("select cli.* from client cli where cli.client_name = :clientName")
    fun findAllByClientName(@Param("clientName") clientName: String): List<Client>

    @Query(
        "select cli.id, cli.client_name, cli.client_email, cli.client_address, cli.client_address_post_code, cli.client_phone_number" +
                " from client cli inner join contact con on cli.id = con.client_id where (con.client_contact_date between :startDate and :endDate)"
    )
    fun findClientsBetweenDates(
        @Param("startDate") startDate: LocalDateTime,
        @Param("endDate") endDate: LocalDateTime
    ): List<Client>
}
