package org.recluit.kafkaconsumer.consumer;

import org.recluit.kafkaconsumer.model.CustomerDocument;
import org.recluit.kafkaconsumer.model.CustomerDto;
import org.recluit.kafkaconsumer.repository.CustomerRepository;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.time.Instant;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class CustomerConsumer {
    private final CustomerRepository repository;

    public CustomerConsumer(CustomerRepository repository) {
        this.repository = repository;
    }

    @KafkaListener(
            topics = "${kafka.topic}",
            groupId = "${kafka.group-id}",
            containerFactory = "kafkaListenerContainerFactory"
    )
    public void consume(CustomerDto dto) {
        CustomerDocument doc = new CustomerDocument(
                dto.customerId(),
                dto.firstName(),
                dto.lastName(),
                dto.email(),
                dto.birthDay(),
                dto.birthMonth(),
                dto.birthYear(),
                dto.address(),
                Instant.now().toString()
        );
        repository.save(doc);
        log.info("Consumer processed customer: {}", doc.customerId());
    }
}