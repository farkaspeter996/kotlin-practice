package hu.neuron.repository

import hu.neuron.entity.Client
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface ClientRepo : CrudRepository<Client, Long> {
    fun existsClientByClientName(clientName: String): Boolean

    fun findClientsByClientName(clientName: String): List<Client>

    fun findClientById(clientId : Long): Client
}