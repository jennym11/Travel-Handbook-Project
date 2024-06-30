package com.example.Destination_Microservice.controllers;

import com.example.Destination_Microservice.models.Destination;
import com.example.Destination_Microservice.services.DestinationService;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
public class DestinationController {

    @Autowired
    DestinationService destinationService;

    @PostMapping("/addDestination")
    public void saveDestination(@RequestBody Destination destination) {
        destinationService.saveOrUpdateDestination(destination);
    }

    @GetMapping("/destinations/all")
    public Iterable<Destination> getAllDestinations() {
        return destinationService.getEveryDestination();
    }

    @GetMapping("/destinations/{id}")
    @HystrixCommand(fallbackMethod = "getDestinationFallback")
    public String getDestination(@PathVariable("id") Long id) {
            if (destinationService.getDestinationById(id).isEmpty() == true) {
                throw new RuntimeException();  //I used to have empty double quotes in the parentheses...
            }
            return destinationService.getDestinationById(id).toString();
    }

    private String getDestinationFallback(Long id) {
        return "Error: Destination #" + id + " does not exist in the system. Please search for another" +
                " destination that we provide services for.";
    }

    @PutMapping("/updateDestination")
    public void updateDestination(@RequestBody Destination destination) {
        destinationService.saveOrUpdateDestination(destination);
    }

    @DeleteMapping("/destinations/delete{id}")
    @HystrixCommand(fallbackMethod = "deleteDestinationFallback")
    public String deleteDestination(@PathVariable("id") Long id) {
        return destinationService.deleteDestinationById(id);
    }

    private String deleteDestinationFallback(Long id) {
        return "Error: Destination #" + id + " does not exist in the system. Please try deleting an existing destination.";
    }

}
