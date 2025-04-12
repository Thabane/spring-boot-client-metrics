package org.example.config;

import io.micrometer.observation.ObservationRegistry;
import org.springframework.boot.actuate.metrics.web.client.ObservationRestTemplateCustomizer;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.observation.ClientRequestObservationConvention;
import org.springframework.http.client.observation.DefaultClientRequestObservationConvention;
import org.springframework.web.client.RestTemplate;

@Configuration
public class ObservabilityConfig {

    @Bean
    public ClientRequestObservationConvention clientObservationConvention() {
        return new DefaultClientRequestObservationConvention("http.client.requests");
    }

//    @Bean
//    public ObservationRestTemplateCustomizer observationRestTemplateCustomizer(
//            ObservationRegistry observationRegistry,
//            ClientRequestObservationConvention observationConvention) {
//        return new ObservationRestTemplateCustomizer(observationRegistry, observationConvention);
//    }

    @Bean
    public RestTemplate restTemplate(
            RestTemplateBuilder builder,
            ObservationRestTemplateCustomizer observationCustomizer) {
        return builder
                .additionalCustomizers(observationCustomizer)
                .build();
    }
}