package hu.neuron.controller

import hu.neuron.dto.BaseResponse
import hu.neuron.dto.ClientDTO
import hu.neuron.dto.ClientWithoutContactDTO
import hu.neuron.service.ClientService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api")
class ClientController(@Autowired private val clientService: ClientService) {

    @GetMapping("/clientByName")
    @Operation(
        summary = "Get client(s) by name ", description = "Returns a (set of) client(s) by clientName",
        responses = [
            ApiResponse(responseCode = "200", description = "OK", content = [Content(mediaType = "application/json", schema = Schema(implementation = ClientDTO::class))]),
            ApiResponse(responseCode = "400", description = "Bad Request", content = [Content(mediaType = "application/json")]),
            ApiResponse(responseCode = "500", description = "Server Error", content = [Content(mediaType = "application/json")])
        ]
    )
    fun getClientByName(@RequestParam clientName: String): ResponseEntity<Set<ClientDTO>> {
        return ResponseEntity(clientService.getClientByName(clientName), HttpStatus.OK)
    }

    @PostMapping("/client")
    @Operation(
        summary = "Save client", description = "Saves a client with contact(s)",
        responses = [
            ApiResponse(responseCode = "200", description = "OK", content = [Content(mediaType = "application/json", schema = Schema(implementation = BaseResponse::class))]),
            ApiResponse(responseCode = "400", description = "Bad Request", content = [Content(mediaType = "application/json", schema = Schema(implementation = BaseResponse::class))]),
            ApiResponse(responseCode = "500", description = "Server Error", content = [Content(mediaType = "application/json", schema = Schema(implementation = BaseResponse::class))])
        ]
    )
    fun saveClient(@RequestBody clientDTO: ClientDTO): ResponseEntity<BaseResponse> {
        return try {
            val clientId: Long = clientService.saveClient(clientDTO)
            ResponseEntity(BaseResponse(true, "User has saved successfully with id: $clientId"), HttpStatus.OK)
        } catch (exception: Exception) {
            ResponseEntity(BaseResponse(false, exception.message!!), HttpStatus.BAD_REQUEST)
        }
    }

    @GetMapping("/clientById")
    @Operation(
        summary = "Get client(s) by id ", description = "Returns a client by clientId",
        responses = [
            ApiResponse(responseCode = "200", description = "OK", content = [Content(mediaType = "application/json", schema = Schema(implementation = ClientDTO::class))]),
            ApiResponse(responseCode = "400", description = "Bad Request", content = [Content(mediaType = "application/json")]),
            ApiResponse(responseCode = "500", description = "Server Error", content = [Content(mediaType = "application/json")])
        ]
    )
    fun getClientById(@RequestParam clientId: Long): ResponseEntity<ClientDTO> {
        return ResponseEntity(clientService.getClientById(clientId), HttpStatus.OK)
    }

    @GetMapping("/clientsByContactDate")
    @Operation(
        summary = "Get client(s) by given dates ", description = "Returns a (set of) client(s) between start date and end date",
        responses = [
            ApiResponse(responseCode = "200", description = "OK", content = [Content(mediaType = "application/json", schema = Schema(implementation = ClientWithoutContactDTO::class))]),
            ApiResponse(responseCode = "400", description = "Bad Request", content = [Content(mediaType = "application/json")]),
            ApiResponse(responseCode = "500", description = "Server Error", content = [Content(mediaType = "application/json")])
        ]
    )
    fun getClientsByContactDate(
        @RequestParam startDate: String,
        @RequestParam endDate: String
    ): ResponseEntity<Set<ClientWithoutContactDTO>> {
        return ResponseEntity(clientService.getClientsByContactDate(startDate, endDate), HttpStatus.OK)
    }
}
