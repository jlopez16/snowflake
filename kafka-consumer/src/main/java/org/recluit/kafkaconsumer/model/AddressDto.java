package org.recluit.kafkaconsumer.model;

public record AddressDto(
        String street,
        String number,
        String city,
        String state,
        String country
) { }
