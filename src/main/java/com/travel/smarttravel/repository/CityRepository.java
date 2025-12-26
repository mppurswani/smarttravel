package com.travel.smarttravel.repository;

import com.travel.smarttravel.entity.City;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface CityRepository extends JpaRepository<City, Long> {

    List<City> findByNameContainingIgnoreCase(String name);

    List<City> findByStateContainingIgnoreCase(String state);

    List<City> findByCountryContainingIgnoreCase(String country);
}
