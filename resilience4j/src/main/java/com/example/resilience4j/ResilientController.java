package com.example.resilience4j;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;

@RestController
@RequestMapping("/api")
public class ResilientController {
	 private static final Logger logger = LoggerFactory.getLogger(ResilientController.class);

    @GetMapping("/data")
    @CircuitBreaker(name = "backendA", fallbackMethod = "fallbackMethod")
    @Retry(name = "backendA", fallbackMethod = "fallbackMethod")
    public String getData() {
        
        if (Math.random() > 0.5) {
            throw new RuntimeException("Simulated service failure.");
        }
        return "Data from remote service";
    }


    public String fallbackMethod(Throwable t) {
        logger.warn("Fallback response due to: {}", t.getMessage());
        return "Fallback response due to: " + t.getMessage(); 
       
    }

}
