package hu.neuron.service

import hu.neuron.dto.ClientDTO
import java.time.LocalDateTime

interface ClientService {

    fun getClientByName(clientName: String): Set<ClientDTO>

    fun saveClient(clientDTO: ClientDTO) : Long

    fun getClientById(clientId: Long): ClientDTO

    fun getClientsByContactDate(startDate: String, endDate: String): Set<ClientDTO>
}