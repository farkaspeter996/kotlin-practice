package hu.neuron.success

import hu.neuron.dto.ClientDTO
import hu.neuron.dto.ClientWithoutContactDTO
import hu.neuron.dto.ContactDTO
import hu.neuron.entity.Client
import hu.neuron.entity.Contact
import hu.neuron.mapper.toClientDTO
import hu.neuron.mapper.toClientEntity
import hu.neuron.mapper.toClientWithoutContactDTO
import io.kotlintest.matchers.numerics.shouldBeExactly
import io.kotlintest.matchers.types.shouldBeTypeOf
import java.time.LocalDateTime
import org.junit.jupiter.api.Test
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest(properties = ["spring.profiles.active=local"])
@AutoConfigureWebTestClient
class ClientEntityMapperTestSuccess {

    @Test
    fun `Client to ClientDTO (success)`() {
        val client = Client(
            1,
            "Kiss Endre",
            "farkasp@neuron.hu",
            "36202362231",
            "Kossuth 12",
            4900,
            "Fehérgyarmat",
            setOf(
                Contact(
                    1,
                    "Teszt Elek",
                    "tesztelek@test.hu",
                    "36202224411",
                    "CONTACT",
                    LocalDateTime.now(),
                    1
                )
            )
        )

        val clientDTO = client.toClientDTO()

        clientDTO.id.shouldBeExactly(client.id)
        clientDTO.shouldBeTypeOf<ClientDTO>()
    }

    @Test
    fun `Client to ClientWithoutContactDTO (success)`() {
        val client = Client(
            1,
            "Kiss Endre",
            "farkasp@neuron.hu",
            "36202362231",
            "Kossuth 12",
            4900,
            "Fehérgyarmat",
            emptySet()
        )

        val clientWithoutContactDTO = client.toClientWithoutContactDTO()

        clientWithoutContactDTO.id.shouldBeExactly(client.id)
        clientWithoutContactDTO.shouldBeTypeOf<ClientWithoutContactDTO>()
    }

    @Test
    fun `ClientDTO to Client (success)`() {
        val clientDTO = ClientDTO(
            1,
            "Kiss Endre",
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
                    "Tulajdonos",
                    LocalDateTime.now(),
                    1
                )
            )
        )

        val client = clientDTO.toClientEntity()

        client.id.shouldBeExactly(clientDTO.id)
        client.shouldBeTypeOf<Client>()
    }
}
