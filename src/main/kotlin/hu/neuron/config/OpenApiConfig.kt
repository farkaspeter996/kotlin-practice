package hu.neuron.config

import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.info.Info
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class OpenApiConfig {

    @Bean
    fun customOpenAPI(@Value("v1") appVersion: String?): OpenAPI? {
        return OpenAPI()
            .info(
                Info().title("Kotlin Practice API").version(appVersion).description(
                    "This API provides methods for Spring Boot Kotlin CRUD operations"
                )
            )
    }
}
