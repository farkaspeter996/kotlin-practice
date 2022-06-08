package hu.neuron.service

import hu.neuron.dto.ClientDTO
import hu.neuron.entity.Client
import hu.neuron.mapper.toClientDTO
import hu.neuron.mapper.toClientEntity
import hu.neuron.repository.ClientRepo
import hu.neuron.repository.ContactRepository
import hu.neuron.repository.PostCodeToCityRepository
import hu.neuron.util.sortByFields
import hu.neuron.util.validate
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime


@Service
class ClientServiceImplementation(
    @Autowired private val clientRepo: ClientRepo,
    @Autowired private val contactRepo: ContactRepository,
    @Autowired private val postCodeToCityRepo: PostCodeToCityRepository
    //@Autowired private val template : JdbcAggregateTemplate
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
            clientDTO.validate()
            clientDTO.contacts.forEach { it.validate()}

            val client = clientDTO.toClientEntity()

            val contacts = client.contacts
            client.contacts = emptyList()

            client.clientAddressCity = postCodeToCityRepo.findCityByPostcode(client.clientAddressPostCode)
            val clientId = clientRepo.save(client).id

            contacts.forEach{
                it.clientId = clientId
                it.clientContactType = ContactTypeEnum.from(it.clientContactType)
            }

            contactRepo.saveAll(contacts)

            return clientId
        } catch (exception: Exception) {
            throw exception
        }
    }

    override fun getClientById(clientId: Long): ClientDTO {
        val client = clientRepo.findClientById(clientId)
        return client.toClientDTO()
    }

    override fun getClientsByContactDate(startDate: LocalDateTime, endDate: LocalDateTime): List<ClientDTO> {
        val clients = clientRepo.findClientsBetweenDates(startDate, endDate)
        return clients.map { it.toClientDTO() }
    }
}