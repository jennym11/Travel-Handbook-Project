package com.project.recommend.controller;

import com.project.recommend.model.recommend;
import com.project.recommend.service.recommendImpl;
import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.circuitbreaker.CircuitBreakerFactory;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;

@RestController
public class recommendController {
    @Autowired
    private recommendImpl recommendimpl;

    @Autowired
    private CircuitBreakerFactory circuitBreakerFactory;

    private RestTemplate restTemplate = new RestTemplate();

    @Autowired
    private LoadBalancerClient loadBalancerClient;


    @GetMapping(value="/", produces = MediaType.TEXT_PLAIN_VALUE)
    public String index() {

        return "Test text for testing";
    }
    @PostMapping("/createRecommendation")
    public recommend createRecommendation(@RequestBody recommend recommendation){
        return recommendimpl.createRecommendation(recommendation);
    }

    @PostMapping("/updateRecommendation/{id}")
    public recommend updateRecommendation(@PathVariable("id") Long id, @RequestBody recommend recommendation){
        return recommendimpl.updateRecommendation(id, recommendation);
    }

    @GetMapping(value = "getRecommendations/{id}")
    @io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker(name = "getRecCB", fallbackMethod = "fallback")
    @ResponseBody
    public ResponseEntity<Iterable<recommend>>  getCB(@PathVariable("id") Long id){
        return new ResponseEntity<>(recommendimpl.getAllRecommendationsByLocationId(id), HttpStatus.OK);
    }

    @GetMapping(value="fallback")
    public ResponseEntity<String> fallback(Exception e) {

        return new ResponseEntity<>("recommendationService is down", HttpStatus.OK);
    }


    @GetMapping(value = "getRecommendation/{id}")
    @ResponseBody
    public ResponseEntity<recommend> getRecommendation(@PathVariable("id") Long id){
        return new ResponseEntity<recommend>(recommendimpl.getRecommendation(id), HttpStatus.OK);
    }

//    @GetMapping(value = "getRecommendations/{id}")
//    @ResponseBody
//    public ResponseEntity<Iterable<recommend>>  getRecommendationsByDestId(@PathVariable("id") Long id){
//        return new ResponseEntity<>(recommendimpl.getAllRecommendationsByLocationId(id), HttpStatus.OK);
//    }

//    private String getBaseUrl() {
//        ServiceInstance instance = loadBalancerClient.choose("STUDENT-REST-CLIENT1");
//        return instance.getUri().toString();
//    }

//    @GetMapping("/cbTest")
//    public ResponseEntity<String> callClient1StudentsWithCB(CircuitBreaker circuitBreaker) {
//
//        String ans = circuitBreakerFactory.create("service is down").run(() -> restTemplate.getForObject(getBaseUrl() + "/students", String.class),
//                throwable -> "fallback");
//
//        return new ResponseEntity<String>(ans, HttpStatus.OK);
//    }

}
