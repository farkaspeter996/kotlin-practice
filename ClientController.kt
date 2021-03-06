package hu.neuron.controller

import hu.neuron.dto.BaseResponse
import hu.neuron.dto.ClientDTO
import hu.neuron.service.ClientService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api")
class ClientController( @Autowired private val clientService: ClientService) {

    @GetMapping("/clientByName")
    fun getClientByName(@RequestParam clientName: String): ResponseEntity<List<ClientDTO>> {
        return ResponseEntity.ok(clientService.getClientByName(clientName))
    }

    @PostMapping("/client")
    fun saveClient(@RequestBody clientDTO: ClientDTO) : ResponseEntity<BaseResponse>{
        return try{
            val clientId: Long = clientService.saveClient(clientDTO)
            ResponseEntity.ok(BaseResponse(true, "User has saved successfully with id: $clientId"))
        } catch(exception: Exception){
            ResponseEntity.ok(BaseResponse(false, exception.message!!))
        }
    }

    @GetMapping("/id/{clientId}")
    fun getClientById(@PathVariable clientId: Long): ResponseEntity<ClientDTO>{
        val clientDTO : ClientDTO = clientService.getClientById(clientId)
        return ResponseEntity.ok(clientDTO)
    }

}