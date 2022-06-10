package hu.neuron.success

import hu.neuron.controller.ClientController
import hu.neuron.dto.BaseResponse
import hu.neuron.dto.ClientDTO
import hu.neuron.dto.ClientWithoutContactDTO
import hu.neuron.dto.ContactDTO
import hu.neuron.service.ClientService
import io.kotlintest.matchers.collections.shouldBeOneOf
import io.kotlintest.shouldBe
import io.mockk.clearMocks
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import java.time.LocalDateTime
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient
import org.springframework.boot.test.context.SpringBootTest

private const val clientName = "test"
private const val clientId: Long = 1

@SpringBootTest(properties = ["spring.profiles.active=local"])
@AutoConfigureWebTestClient
class ClientControllerTestSuccess {

    private val clientService: ClientService = mockk(relaxed = true)
    private lateinit var clientController: ClientController

    @BeforeEach
    fun setUp() {
        clearMocks(clientService)
        clientController = ClientController(clientService)
    }

    @Test
    fun `Get clients by name (success)`() {
        val client: Set<ClientDTO> = setOf(
            ClientDTO(
                clientId,
                clientName,
                "farkasp@neuron.hu",
                "36202362231",
                "Kossuth 12",
                4900,
                "Fehérgyarmat",
                setOf(
                    ContactDTO(
                        1,
                        "Teszt Elek",
                        "tesztelek@test.hu",
                        "36202224411",
                        "Kapcsolattartó",
                        LocalDateTime.now(),
                        1
                    )
                )
            )
        )
        every { clientService.getClientByName(any()) } returns client
        val result = clientController.getClientByName(clientName)

        verify(exactly = 1) { clientService.getClientByName(clientName) }
        result.body!!.first().shouldBeOneOf(client)
    }

    @Test
    fun `Save client (success)`() {
        val client = ClientDTO(
            clientId,
            clientName,
            "farkaspeter@neuron.hu",
            "36202332114",
            "Ady 2",
            4900,
            "Fehérgyarmat",
            setOf(
                ContactDTO(
                    1,
                    "Teszt Elek",
                    "tesztelek@test.hu",
                    "36202224411",
                    "Kapcsolattartó",
                    LocalDateTime.now(),
                    1
                )
            )
        )
        every { clientService.saveClient(any()) } returns client.id
        val result = clientController.saveClient(client)

        verify(exactly = 1) { clientService.saveClient(client) }
        result.body.shouldBe(BaseResponse(true, "User has saved successfully with id: 1"))
    }

    @Test
    fun `Get client by id (success)`() {
        val client = ClientDTO(
            clientId,
            clientName,
            "farkasp@neuron.hu",
            "36202362231",
            "Kossuth 12",
            4900,
            "Fehérgyarmat",
            setOf(
                ContactDTO(
                    1,
                    "Teszt Elek",
                    "tesztelek@test.hu",
                    "36202224411",
                    "Kapcsolattartó",
                    LocalDateTime.now(),
                    1
                )
            )
        )
        every { clientService.getClientById(any()) } returns client
        val result = clientController.getClientById(clientId)

        verify(exactly = 1) { clientService.getClientById(clientId) }
        result.body.shouldBeOneOf(client)
    }

    @Test
    fun `Get clients between dates (success)`() {
        val client: Set<ClientWithoutContactDTO> = setOf(
            ClientWithoutContactDTO(
                clientId,
                clientName,
                "farkasp@neuron.hu",
                "36202362231",
                "Kossuth 12",
                4900,
                "Fehérgyarmat"
            )
        )
        every { clientService.getClientsByContactDate(any(), any()) } returns client
        val result = clientController.getClientsByContactDate("2021-08-25 11:25:25", "2023-08-25 11:25:25")

        verify(exactly = 1) { clientService.getClientsByContactDate("2021-08-25 11:25:25", "2023-08-25 11:25:25") }
        result.body!!.first().shouldBeOneOf(client)
    }
}
