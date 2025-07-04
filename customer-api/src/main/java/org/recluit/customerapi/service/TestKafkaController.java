package org.recluit.customerapi.service;

import org.recluit.customerapi.dto.CustomerDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/customers1")
public class TestKafkaController {

    private final MockCustomerService customerService;
    private final KafkaTemplate<String, CustomerDto> kafkaTemplate;

    public TestKafkaController(MockCustomerService customerService,
                               KafkaTemplate<String, CustomerDto> kafkaTemplate) {
        this.customerService = customerService;
        this.kafkaTemplate = kafkaTemplate;
    }

    @GetMapping("/fetch/{id}")
    public ResponseEntity<String> fetchAndSend(@PathVariable String id) {
        try {
            CustomerDto customer = customerService.getCustomerById(id);
            kafkaTemplate.send("customer-topic", String.valueOf(id), customer);
            return ResponseEntity.ok("Customer sent to Kafka: " + customer.firstName());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error: " + e.getMessage());
        }
    }

}
