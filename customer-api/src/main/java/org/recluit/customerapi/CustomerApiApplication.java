package org.recluit.customerapi;

import org.recluit.customerapi.config.KafkaProducerProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(KafkaProducerProperties.class)
public class CustomerApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(CustomerApiApplication.class, args);
    }

}
