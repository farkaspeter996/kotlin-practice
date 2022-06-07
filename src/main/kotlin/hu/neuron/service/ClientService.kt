package hu.neuron.service

import hu.neuron.dto.ClientDTO

interface ClientService {

    fun existsClientByName(clientName: String) : Boolean

    fun getClientByName(clientName: String): List<ClientDTO>

    fun saveClient(clientDTO: ClientDTO) : Int

    fun getClientById(clientId: Long): ClientDTO
}