package org.recluit.customerapi.service;

import org.recluit.customerapi.dto.CustomerDocument;
import org.recluit.customerapi.dto.CustomerDto;
import org.recluit.customerapi.repository.CustomerRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/customers")
public class CustomerController {

    private final SnowflakeCustomerService snowflakeService;
    private final CustomerService customerService;
    private final CustomerRepository repository;

    public CustomerController(SnowflakeCustomerService snowflakeService,
                              CustomerService customerService,
                              CustomerRepository repository) {
        this.snowflakeService = snowflakeService;
        this.customerService = customerService;
        this.repository = repository;
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

    @GetMapping("/mongo")
    public ResponseEntity<List<CustomerDocument>> getCustomersFromMongo() {
        List<CustomerDocument> customers = repository.findAll();
        return ResponseEntity.ok(customers);
    }

}

