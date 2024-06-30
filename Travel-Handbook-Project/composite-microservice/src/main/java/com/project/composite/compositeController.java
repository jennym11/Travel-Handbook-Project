package com.project.composite;

import com.project.recommend.model.recommend;




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




@RestController
public class compositeController {
    
    private RestTemplate restTemplate = new RestTemplate();

    @Autowired
    private LoadBalancerClient loadBalancerClient;


    @GetMapping(value = "/", produces = MediaType.TEXT_PLAIN_VALUE)
    public String index() {

        return "Home page for composite microservice";
    }

    private String getRecommendBaseUrl() {
        ServiceInstance instance = loadBalancerClient.choose("Recommend");
        System.out.println(instance.getUri().toString());
        return instance.getUri().toString();
    }

    @PostMapping("/createRecommendation")
    @io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker(name = "postRecCB", fallbackMethod = "RecommendFallback")
    public ResponseEntity<recommend> createRecommendation(@RequestBody recommend recommendation) {
        return restTemplate.postForEntity(getRecommendBaseUrl() + "/createRecommendation", recommendation, recommend.class);
    }

    @PostMapping("/updateRecommendation/{id}")
    @io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker(name = "postRecCB2", fallbackMethod = "RecommendFallback")
    public ResponseEntity<recommend> updateRecommendation(@PathVariable("id") Long id, @RequestBody recommend recommendation) {
        return restTemplate.postForEntity(getRecommendBaseUrl() + "/updateRecommendation/" + id, recommendation, recommend.class);
    }

    @GetMapping(value = "getRecommendations/{id}")
    @io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker(name = "getRecsCB", fallbackMethod = "RecommendFallback")
    @ResponseBody
    public ResponseEntity<String> getRecommendations(@PathVariable("id") Long id) {
        return new ResponseEntity<String>(restTemplate.getForObject(getRecommendBaseUrl() + "/getRecommendations/" + id, String.class), HttpStatus.OK);
    }

    @GetMapping(value = "getRecommendation/{id}")
    @io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker(name = "getRecCB", fallbackMethod = "RecommendFallback")
    @ResponseBody
    public ResponseEntity<String> getRecommendation(@PathVariable("id") Long id) {
        return new ResponseEntity<String>(restTemplate.getForObject(getRecommendBaseUrl() + "/getRecommendation/" + id, String.class), HttpStatus.OK);
    }

    @GetMapping(value="RecommendFallback")
    public ResponseEntity<String> RecommendFallback(Exception e) {

        return new ResponseEntity<>("Recommend service is down", HttpStatus.OK);
    }
}