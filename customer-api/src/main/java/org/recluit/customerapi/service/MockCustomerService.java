package org.recluit.customerapi.service;

import org.recluit.customerapi.dto.AddressDto;
import org.recluit.customerapi.dto.CustomerDto;
import org.springframework.stereotype.Service;

@Service
public class MockCustomerService {
    public CustomerDto getCustomerById(int id){
        return new CustomerDto(id,
                "Octavio",
                "Paz",
                31,
                3,
                1914,
                "octavio.paz@email.com",
                new AddressDto("Periferico Sur", "1234", "Benito Juarez", "Mexico City", "MX"));
    }
}
