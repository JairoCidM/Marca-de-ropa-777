package cl.duoc.catalogo.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.Contact;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("API de Catálogo - 777 Marca de Ropa")
                        .version("1.0")
                        .description("Documentación oficial del microservicio de Catálogo para la gestión de productos de la tienda."));
    }
}