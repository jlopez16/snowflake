package org.recluit.customerapi.dto;

public record CustomerDto(
        Integer customerId,
        String firstName,
        String lastName,
        Integer birthDay,
        Integer birthMonth,
        Integer birthYear,
        String email,
        AddressDto address
) {
}
