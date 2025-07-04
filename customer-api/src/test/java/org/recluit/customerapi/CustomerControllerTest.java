package org.recluit.customerapi;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.recluit.customerapi.dto.CustomerDocument;
import org.recluit.customerapi.dto.CustomerDto;
import org.recluit.customerapi.dto.AddressDto;
import org.recluit.customerapi.repository.CustomerRepository;
import org.recluit.customerapi.service.CustomerController;
import org.recluit.customerapi.service.CustomerService;
import org.recluit.customerapi.service.SnowflakeCustomerService;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class CustomerControllerTest {

    private SnowflakeCustomerService snowflakeService;
    private CustomerService customerService;
    private CustomerRepository repository;
    private CustomerController controller;

    @BeforeEach
    void setUp() {
        snowflakeService = mock(SnowflakeCustomerService.class);
        customerService = mock(CustomerService.class);
        repository = mock(CustomerRepository.class);
        controller = new CustomerController(snowflakeService, customerService, repository);
    }

    @Test
    void getCustomers_shouldReturnPaginatedList() {
        CustomerDto customer = new CustomerDto(
                UUID.randomUUID().toString(), "John", "Doe",
                12, 5, 1990, "john@example.com",
                new AddressDto("Main St", "100", "City", "State", "Country")
        );
        when(snowflakeService.getCustomers(1, 10)).thenReturn(List.of(customer));

        ResponseEntity<List<CustomerDto>> response = controller.getCustomers(1, 10);

        assertEquals(200, response.getStatusCodeValue());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().size());
        assertEquals("John", response.getBody().get(0).firstName());
    }

    @Test
    void fetchAndPublishCustomer_shouldReturnCustomerAndSendToKafka() {
        String customerId = "some-id";
        CustomerDto customer = new CustomerDto(
                "00000000-0000-0000-0000-000000000000",
                "Jane", "Smith",
                10, 6, 1985, "jane@example.com",
                new AddressDto("2nd St", "200", "Another City", "Another State", "Country")
        );
        when(snowflakeService.getCustomerById(customerId)).thenReturn(Optional.of(customer));

        ResponseEntity<Optional<CustomerDto>> response = controller.fetchAndPublishCustomer(customerId);

        assertEquals(200, response.getStatusCodeValue());
        assertTrue(response.getBody().isPresent());
        assertEquals("Jane", response.getBody().get().firstName());
        verify(customerService).publishCustomer(Optional.of(customer));
    }

    @Test
    void getCustomersFromMongo_shouldReturnListOfDocuments() {
        CustomerDocument doc = new CustomerDocument(
                "AAAAAAAAAAAAADCA",
                "Molly",
                "Werner",
                "Molly.Werner@Pyr73maVAYkYa2F7.org",
                28,
                10,
                1978,
                new AddressDto("Tenth Maple","Suite U","Highland", "UT", "United States"),
                "2025-09-30T18:00:00Z"
        );
        when(repository.findAll()).thenReturn(List.of(doc));

        ResponseEntity<List<CustomerDocument>> response = controller.getCustomersFromMongo();

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(1, response.getBody().size());
    }
}
