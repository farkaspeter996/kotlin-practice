package hu.neuron.service

import hu.neuron.dto.ClientDTO
import hu.neuron.entity.Client
import hu.neuron.mapper.toClientDTO
import hu.neuron.mapper.toClientEntity
import hu.neuron.repository.ClientRepo
import hu.neuron.repository.ContactRepository
import hu.neuron.util.sortByFields
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional


@Service
class ClientServiceImplementation(
    @Autowired private val clientRepo: ClientRepo,
    @Autowired private val contactRepo: ContactRepository
) : ClientService {

    //@Cacheable(cacheNames = ["existsClientByName"])
    override fun existsClientByName(clientName: String): Boolean {
        return clientRepo.existsClientByClientName(clientName)
    }

    override fun getClientByName(clientName: String): List<ClientDTO> {

        val clients: List<Client> = clientRepo.findClientsByClientName(clientName)

        if (clients.isEmpty()) {
            throw Exception("Cannot find user with: $clientName username")
        }

        val clientDTOS = clients.map { it.toClientDTO() }

        clientDTOS.toMutableList().sortByFields(ClientDTO::clientAddressPostCode, true)
        return clientDTOS
    }

    @Transactional(rollbackFor = [Exception::class])
    override fun saveClient(clientDTO: ClientDTO): Int {
        try {
            val client = clientDTO.toClientEntity()
            val clientId = clientRepo.save(client).id
            client.contacts.forEach { it.clientId = clientId }
            contactRepo.saveAll(client.contacts)
        } catch (exception: Exception) {
            println(exception.printStackTrace())
            throw Exception("Save was not successful")
        }
        return 0
    }

    override fun getClientById(clientId: Long): ClientDTO {
        val client: Client = clientRepo.findClientById(clientId)
        return client.toClientDTO()
    }

}