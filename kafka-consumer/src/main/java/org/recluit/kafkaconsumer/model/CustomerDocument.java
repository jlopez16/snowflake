package org.recluit.kafkaconsumer.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;

@Document("customer_data")
public record CustomerDocument(
        @Id Integer customerId,
        String firstName,
        String lastName,
        String email,
        Integer birthDay,
        Integer birthMonth,
        Integer birthYear,
        AddressDto address,
        Instant receivedAt
) {}