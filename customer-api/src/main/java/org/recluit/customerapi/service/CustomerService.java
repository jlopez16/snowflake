package org.recluit.customerapi.service;

import lombok.extern.slf4j.Slf4j;
import org.recluit.customerapi.config.KafkaProducerProperties;
import org.recluit.customerapi.dto.CustomerDto;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;

@Service
@Slf4j
public class CustomerService {

    private final KafkaTemplate<String, CustomerDto> kafkaTemplate;
    private final KafkaProducerProperties properties;

    public CustomerService(KafkaTemplate<String, CustomerDto> kafkaTemplate,
                           KafkaProducerProperties properties) {
        this.kafkaTemplate = kafkaTemplate;
        this.properties = properties;
    }

    public void publishCustomer(Optional<CustomerDto> customer) {
        customer.ifPresent(customerDto -> {
            CompletableFuture<SendResult<String, CustomerDto>> future =
                    kafkaTemplate.send(properties.getTopic(), customerDto.customerId().toString(), customerDto);

            future.whenComplete((result, ex) -> {
                if (ex != null) {
                    log.error("Failed to publish customer [{}] to Kafka topic [{}]: {}",
                            customerDto.customerId(), properties.getTopic(), ex.getMessage(), ex);
                } else {
                    log.info("Customer [{}] published to Kafka topic [{}] at offset {}",
                            customerDto.customerId(), result.getRecordMetadata().topic(), result.getRecordMetadata().offset());
                }
            });
        });
    }
}
