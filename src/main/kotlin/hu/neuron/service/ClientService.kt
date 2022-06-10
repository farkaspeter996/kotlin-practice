package hu.neuron.service

import hu.neuron.dto.ClientDTO
import hu.neuron.dto.ClientWithoutContactDTO

interface ClientService {

    fun getClientByName(clientName: String): Set<ClientDTO>

    fun saveClient(clientDTO: ClientDTO): Long

    fun getClientById(clientId: Long): ClientDTO

    fun getClientsByContactDate(startDate: String, endDate: String): Set<ClientWithoutContactDTO>
}
