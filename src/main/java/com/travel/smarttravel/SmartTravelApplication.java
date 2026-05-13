package com.travel.smarttravel;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;

@EnableCaching
@SpringBootApplication
public class SmartTravelApplication {

    @Autowired
    private CacheManager cacheManager;

    public static void main(String[] args) {
        SpringApplication.run(SmartTravelApplication.class, args);
    }

    @PostConstruct
    public void checkCache() {
        System.out.println("CacheManager: " + cacheManager.getClass());
    }
}