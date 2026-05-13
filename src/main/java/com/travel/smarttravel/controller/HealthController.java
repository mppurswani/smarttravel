package com.travel.smarttravel.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.ResponseEntity;
import java.util.Map;
@RestController
@RequestMapping("/api")
public class HealthController {

    @GetMapping("/health")
    public ResponseEntity<?>healthCheck(){
        return ResponseEntity.ok(Map.of("status",
        "UP","service","SmartTravel API"));
    }
}
