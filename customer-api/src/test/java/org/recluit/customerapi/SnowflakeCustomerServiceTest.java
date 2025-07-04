package org.recluit.customerapi;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.recluit.customerapi.domain.CustomerRowMapper;
import org.recluit.customerapi.dto.AddressDto;
import org.recluit.customerapi.dto.CustomerDto;
import org.recluit.customerapi.exception.DatabaseException;
import org.recluit.customerapi.service.SnowflakeCustomerService;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class SnowflakeCustomerServiceTest {

    private NamedParameterJdbcTemplate jdbcTemplate;
    private CustomerRowMapper customerRowMapper;
    private SnowflakeCustomerService service;

    @BeforeEach
    void setUp() {
        jdbcTemplate = mock(NamedParameterJdbcTemplate.class);
        customerRowMapper = mock(CustomerRowMapper.class); // mocked but not used directly in test
        service = new SnowflakeCustomerService(jdbcTemplate, customerRowMapper);
    }

    @Test
    void getCustomers_shouldReturnCustomerList() {
        CustomerDto mockCustomer = new CustomerDto(
                "ID001", "Alice", "Smith", 1, 1, 1990, "alice@example.com",
                new AddressDto("Main Street", "10", "Springfield", "CA", "USA")
        );

        when(jdbcTemplate.query(any(String.class), any(Map.class), eq(customerRowMapper)))
                .thenReturn(List.of(mockCustomer));

        List<CustomerDto> result = service.getCustomers(1, 10);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Alice", result.get(0).firstName());
    }

    @Test
    void getCustomers_shouldThrowDatabaseException() {
        when(jdbcTemplate.query(any(String.class), any(Map.class), eq(customerRowMapper)))
                .thenThrow(new RuntimeException("Database error"));

        assertThrows(DatabaseException.class, () -> service.getCustomers(1, 10));
    }

    @Test
    void getCustomerById_shouldReturnCustomer() {
        CustomerDto mockCustomer = new CustomerDto(
                "ID002", "Bob", "Johnson", 2, 2, 1985, "bob@example.com",
                new AddressDto("2nd Ave", "5A", "Metropolis", "NY", "USA")
        );

        when(jdbcTemplate.query(any(String.class), any(Map.class), eq(customerRowMapper)))
                .thenReturn(List.of(mockCustomer));

        Optional<CustomerDto> result = service.getCustomerById("ID002");

        assertTrue(result.isPresent());
        assertEquals("Bob", result.get().firstName());

        // Optional: verify params
        ArgumentCaptor<Map<String, Object>> captor = ArgumentCaptor.forClass(Map.class);
        verify(jdbcTemplate).query(any(String.class), captor.capture(), eq(customerRowMapper));
        assertEquals("ID002", captor.getValue().get("customerId"));
    }

    @Test
    void getCustomerById_shouldReturnEmptyIfNotFound() {
        when(jdbcTemplate.query(any(String.class), any(Map.class), eq(customerRowMapper)))
                .thenReturn(List.of());

        Optional<CustomerDto> result = service.getCustomerById("NOT_FOUND");

        assertTrue(result.isEmpty());
    }

    @Test
    void getCustomerById_shouldThrowDatabaseException() {
        when(jdbcTemplate.query(any(String.class), any(Map.class), eq(customerRowMapper)))
                .thenThrow(new RuntimeException("Unexpected error"));

        assertThrows(DatabaseException.class, () -> service.getCustomerById("ID003"));
    }
}
