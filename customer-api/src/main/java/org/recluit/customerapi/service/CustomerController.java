package org.recluit.customerapi.service;

import org.recluit.customerapi.dto.CustomerDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/customers")
public class CustomerController {

    private final SnowflakeCustomerService snowflakeService;
    private final CustomerService customerService;

    public CustomerController(SnowflakeCustomerService snowflakeService,
                              CustomerService customerService) {
        this.snowflakeService = snowflakeService;
        this.customerService = customerService;
    }

    @GetMapping("/")
    public ResponseEntity<List<CustomerDto>> getCustomers(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size) {

        List<CustomerDto> customers = snowflakeService.getCustomers(page, size);
        return ResponseEntity.ok(customers);
    }

    @GetMapping("/fetch/{id}")
    public ResponseEntity<Optional<CustomerDto>> fetchAndPublishCustomer(@PathVariable String id) {
        Optional<CustomerDto> customer = snowflakeService.getCustomerById(id);
        customerService.publishCustomer(customer);
        return ResponseEntity.ok(customer);
    }

}

