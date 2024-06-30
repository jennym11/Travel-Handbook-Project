package com.example.Destination_Microservice.repositories;

import com.example.Destination_Microservice.models.Destination;
import org.springframework.data.repository.CrudRepository;

public interface DestinationRepository extends CrudRepository<Destination, Long> {
}
