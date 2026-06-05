package cl.GestionDrones.v1.monitoreos.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {

    @Bean
    public WebClient.Builder webClientBuilder() {
        return WebClient.builder();
    }
    
    @Bean
    public WebClient planesVueloWebClient(WebClient.Builder builder) {
        // Asegúrate de que este puerto coincida con tu microservicio de Planes de Vuelo
        return builder.baseUrl("http://localhost:8083/api/v1/planesDeVuelos").build();
    }

    @Bean
    public WebClient incidenciasWebClient(WebClient.Builder builder) {
        // Asegúrate de que este puerto coincida con tu microservicio de Incidencias
        return builder.baseUrl("http://localhost:8089/api/v1/incidencias").build();
    }
}
