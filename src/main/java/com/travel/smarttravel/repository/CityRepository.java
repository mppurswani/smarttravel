package com.travel.smarttravel.repository;

import com.travel.smarttravel.entity.City;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CityRepository extends JpaRepository<City, Long> {

    // Search with pagination & sorting
    Page<City> findByNameContainingIgnoreCase(String name, Pageable pageable);
    Page<City> findByStateContainingIgnoreCase(String state, Pageable pageable);
    Page<City> findByCountryContainingIgnoreCase(String country, Pageable pageable);
}
