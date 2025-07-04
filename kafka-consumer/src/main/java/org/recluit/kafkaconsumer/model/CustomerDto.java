package org.recluit.kafkaconsumer.model;

public record CustomerDto(
        String customerId,
        String firstName,
        String lastName,
        Integer birthDay,
        Integer birthMonth,
        Integer birthYear,
        String email,
        AddressDto address
) {
}
