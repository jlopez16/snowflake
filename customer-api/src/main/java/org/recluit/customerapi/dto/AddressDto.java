package org.recluit.customerapi.dto;

public record AddressDto(
        String street,
        String number,
        String city,
        String state,
        String country
) { }
