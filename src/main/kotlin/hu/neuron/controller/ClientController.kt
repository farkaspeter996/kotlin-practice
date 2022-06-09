package hu.neuron.controller

import hu.neuron.dto.BaseResponse
import hu.neuron.dto.ClientDTO
import hu.neuron.service.ClientService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.server.ResponseStatusException
import java.time.LocalDateTime

@RestController
@RequestMapping("/api")
class ClientController( @Autowired private val clientService: ClientService) {

    //TODO PIPA ResponseEntity.ok csere
    @GetMapping("/clientByName")
    fun getClientByName(@RequestParam clientName: String): ResponseEntity<Set<ClientDTO>> {
        return ResponseEntity(clientService.getClientByName(clientName), HttpStatus.OK)
    }

    @PostMapping("/client")
    fun saveClient(@RequestBody clientDTO: ClientDTO) : ResponseEntity<BaseResponse>{
        return try{
            val clientId: Long = clientService.saveClient(clientDTO)
           ResponseEntity(BaseResponse(true, "User has saved successfully with id: $clientId"), HttpStatus.OK)
        } catch(exception: Exception){
            ResponseEntity(BaseResponse(false, exception.message!!) , HttpStatus.BAD_REQUEST)
        }
    }

    @GetMapping("/clientById")
    fun getClientById(@RequestParam clientId: Long): ResponseEntity<ClientDTO>{
        return ResponseEntity(clientService.getClientById(clientId), HttpStatus.OK)
    }

    @GetMapping("/clientsByContactDate")
    fun getClientsByContactDate(@RequestParam startDate: String, @RequestParam endDate: String): ResponseEntity<Set<ClientDTO>>{
        return  ResponseEntity(clientService.getClientsByContactDate(startDate, endDate), HttpStatus.OK)
    }

}