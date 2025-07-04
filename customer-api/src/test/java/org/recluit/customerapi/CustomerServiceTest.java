package org.recluit.customerapi;

import org.apache.kafka.clients.producer.RecordMetadata;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.recluit.customerapi.config.KafkaProducerProperties;
import org.recluit.customerapi.dto.AddressDto;
import org.recluit.customerapi.dto.CustomerDto;
import org.recluit.customerapi.service.CustomerService;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;

import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

class CustomerServiceTest {

    private KafkaTemplate<String, CustomerDto> kafkaTemplate;
    private KafkaProducerProperties producerProperties;
    private CustomerService customerService;

    @BeforeEach
    void setUp() {
        kafkaTemplate = mock(KafkaTemplate.class);
        producerProperties = mock(KafkaProducerProperties.class);
        when(producerProperties.getTopic()).thenReturn("customer-topic");

        customerService = new CustomerService(kafkaTemplate, producerProperties);
    }

    @Test
    void shouldPublishCustomerIfPresent() {
        CustomerDto customer = new CustomerDto(
                UUID.randomUUID().toString(),
                "John",
                "Doe",
                15,
                8,
                1990,
                "john.doe@example.com",
                new AddressDto("Main St", "123", "Springfield", "CA", "USA")
        );

        // Mock del futuro real (CompletableFuture)
        SendResult<String, CustomerDto> sendResult = new SendResult<>(
                new ProducerRecord<>("customer-topic", customer.customerId(), customer),
                new RecordMetadata(null, 0, 0, 0L, 0L, 0, 0)
        );
        CompletableFuture<SendResult<String, CustomerDto>> future = CompletableFuture.completedFuture(sendResult);

        when(kafkaTemplate.send(eq("customer-topic"), eq(customer.customerId()), eq(customer)))
                .thenReturn(future);

        customerService.publishCustomer(Optional.of(customer));

        ArgumentCaptor<String> keyCaptor = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<CustomerDto> valueCaptor = ArgumentCaptor.forClass(CustomerDto.class);

        verify(kafkaTemplate, times(1)).send(eq("customer-topic"), keyCaptor.capture(), valueCaptor.capture());

        assertEquals(customer.customerId(), keyCaptor.getValue());
        assertEquals(customer, valueCaptor.getValue());
    }

    @Test
    void shouldNotPublishIfCustomerIsEmpty() {
        customerService.publishCustomer(Optional.empty());

        verify(kafkaTemplate, never()).send(anyString(), anyString(), any());
    }
}
