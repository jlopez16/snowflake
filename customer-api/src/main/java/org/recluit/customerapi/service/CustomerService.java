package org.recluit.customerapi.service;

import org.recluit.customerapi.config.KafkaProducerProperties;
import org.recluit.customerapi.dto.CustomerDto;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerService {

    private final KafkaTemplate<String, CustomerDto> kafkaTemplate;
    private final KafkaProducerProperties properties;

    public CustomerService(KafkaTemplate<String, CustomerDto> kafkaTemplate,
                           KafkaProducerProperties properties){
        this.kafkaTemplate = kafkaTemplate;
        this.properties = properties;
    }

    public void publishCustomers(List<CustomerDto> customers) {
        for (CustomerDto customer : customers) {
            kafkaTemplate.send(properties.getTopic(), customer.customerId().toString(), customer);
        }
    }
}
