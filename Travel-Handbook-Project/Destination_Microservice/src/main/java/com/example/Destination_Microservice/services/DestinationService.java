package com.example.Destination_Microservice.services;

import com.example.Destination_Microservice.models.Destination;
import com.example.Destination_Microservice.repositories.DestinationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DestinationService {

    @Autowired
    DestinationRepository destinationRepository;

    //I can add more if necessary!

    public Iterable<Destination> getEveryDestination() {  //Iterable or Optional or List?
        return destinationRepository.findAll();
    }

    //If the destination already exists, it will be updated.
    public void saveOrUpdateDestination(Destination destination) {
        destinationRepository.save(destination);
    }

    public Optional<Destination> getDestinationById(Long destinationId) {  //this can return a single Destination instance or nothing at all.
        return destinationRepository.findById(destinationId);
    }

    public String deleteDestinationById(Long destinationId) {
        Optional<Destination> destinationToBeDeleted = destinationRepository.findById(destinationId);

        if (destinationToBeDeleted.isEmpty() == true) {
            throw new RuntimeException();  //I used to have a String in the parentheses, but I transferred it to the fallback method in the controller.
        }

        //This deletes the destination if it exists.
        destinationRepository.deleteById(destinationId);
        return "Destination #" + destinationId + " has been deleted.";
    }
}
