package hu.neuron.repository

import hu.neuron.entity.Client
import org.springframework.data.jdbc.repository.query.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import java.time.LocalDateTime

@Repository
interface ClientRepo : CrudRepository<Client, Long> {
    fun existsClientByClientName(clientName: String): Boolean

    @Query("select cli.* from client as cli join contact as con on cli.id = con.client_id where cli.client_name = :clientName")
    fun findClientsByName(@Param("clientName") clientName: String): List<Client>

    fun findClientsByClientName(clientName: String) : List<Client>

    @Query("select cli.* from client cli where cli.id = :clientId")
    fun findClientById(@Param("clientId") clientId : Long): Client

    @Query("select * from client cli inner join contact con on cli.id = con.client_id where (con.client_contact_date between :startDate and :endDate)")
    fun findClientsBetweenDates(@Param("startDate") startDate: LocalDateTime, @Param("endDate") endDate: LocalDateTime): List<Client>
}