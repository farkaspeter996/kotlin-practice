package hu.neuron.service

import ContactTypeEnum
import hu.neuron.dto.ClientDTO
import hu.neuron.dto.ClientWithoutContactDTO
import hu.neuron.entity.Client
import hu.neuron.mapper.toClientDTO
import hu.neuron.mapper.toClientEntity
import hu.neuron.mapper.toClientWithoutContactDTO
import hu.neuron.repository.ClientRepo
import hu.neuron.repository.ContactRepository
import hu.neuron.repository.PostCodeToCityRepository
import hu.neuron.util.sortByFields
import hu.neuron.util.validate
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.cache.annotation.Cacheable
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.client.HttpClientErrorException
import org.springframework.web.server.ResponseStatusException
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter


@Service
class ClientServiceImplementation(
    @Autowired private val clientRepo: ClientRepo,
    @Autowired private val contactRepo: ContactRepository,
    @Autowired private val postCodeToCityRepo: PostCodeToCityRepository
    //@Autowired private val template : JdbcAggregateTemplate
) : ClientService {

    override fun getClientByName(clientName: String): Set<ClientDTO> {

        val clients: List<Client> = clientRepo.findAllByClientName(clientName)

        if (clients.isEmpty()) {
            throw Exception("Cannot find user with: $clientName username")
        }

        val clientDTOS = clients.map { it.toClientDTO() }

        clientDTOS.toMutableList().sortByFields(ClientDTO::clientAddressPostCode, true)
        return clientDTOS.toSet()
    }

    @Transactional(rollbackFor = [Exception::class])
    override fun saveClient(clientDTO: ClientDTO): Long {
        try {
            //TODO PIPA cache-elni a postcode keresést
            clientDTO.clientAddressCity = getCityByPostCode(clientDTO.clientAddressPostCode)
            clientDTO.validate()

            val client = clientDTO.toClientEntity()

            val contacts = client.contacts
            client.contacts = emptySet()

            val clientId = clientRepo.save(client).id

            contacts.forEach{
                it.clientId = clientId
                it.clientContactType = ContactTypeEnum.findByValue(it.clientContactType).toString()
            }

            contactRepo.saveAll(contacts)

            return clientId
        } catch (exception: Exception) {
            println(exception.printStackTrace())
            throw exception
        }
    }

    override fun getClientById(clientId: Long): ClientDTO {
        val client = clientRepo.findById(clientId)
        if(client.isPresent){
            return client.get().toClientDTO()
        } else{
            //TODO PIPA Badrequest exception
            throw ResponseStatusException(HttpStatus.BAD_REQUEST)
        }
    }

    override fun getClientsByContactDate(startDate: String, endDate: String): Set<ClientWithoutContactDTO> {
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
        val formattedStartDate = LocalDateTime.parse(startDate, formatter)
        val formattedEndDate = LocalDateTime.parse(endDate, formatter)
        val clients = clientRepo.findClientsBetweenDates(formattedStartDate, formattedEndDate)
        return clients.map { it.toClientWithoutContactDTO() }.toSet()
    }

    @Cacheable(cacheNames = ["getCityByPostCode"])
    fun getCityByPostCode(postcode : Int) : String {
       return postCodeToCityRepo.findCityByPostcode(postcode)
    }
}