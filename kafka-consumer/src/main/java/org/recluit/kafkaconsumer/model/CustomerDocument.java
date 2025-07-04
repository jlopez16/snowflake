package org.recluit.kafkaconsumer.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("customer_data")
public record CustomerDocument(
        @Id String customerId,
        String firstName,
        String lastName,
        String email,
        Integer birthDay,
        Integer birthMonth,
        Integer birthYear,
        AddressDto address,
        String receivedAt
) {}