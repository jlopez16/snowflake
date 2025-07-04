package org.recluit.customerapi.repository;

import org.recluit.customerapi.dto.CustomerDocument;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CustomerRepository extends MongoRepository<CustomerDocument, String> {
}
