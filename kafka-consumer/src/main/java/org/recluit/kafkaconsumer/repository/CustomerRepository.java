package org.recluit.kafkaconsumer.repository;

import org.recluit.kafkaconsumer.model.CustomerDocument;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CustomerRepository extends MongoRepository<CustomerDocument, Integer> {
}
