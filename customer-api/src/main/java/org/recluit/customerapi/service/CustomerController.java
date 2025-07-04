package org.recluit.customerapi.service;

import org.recluit.customerapi.dto.CustomerDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/customers")
public class CustomerController {

    private final SnowflakeCustomerService snowflakeService;

    public CustomerController(SnowflakeCustomerService snowflakeService) {
        this.snowflakeService = snowflakeService;
    }

    @GetMapping("/")
    public ResponseEntity<List<CustomerDto>> getCustomers(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size) {

        List<CustomerDto> customers = snowflakeService.getCustomers(page, size);
        return ResponseEntity.ok(customers);
    }
}

