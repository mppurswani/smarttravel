package com.travel.smarttravel.repository;

import com.travel.smarttravel.entity.City;
import org.springframework.data.jpa.repository.JpaRepository;
import com.travel.smarttravel.entity.CityCategory;
import java.util.List;

public interface CityRepository extends JpaRepository<City, Long> {

    // Search with pagination & sorting
    List<City> findByNameIgnoreCaseContaining(String name);
    List<City> findByStateIgnoreCaseContaining(String state);
    List<City> findByCountryIgnoreCaseContaining(String country);

    // ✅ keyword search across name/state/country
    List<City> findByNameContainingIgnoreCaseOrStateContainingIgnoreCaseOrCountryContainingIgnoreCase(
            String name, String state, String country);

    List<City> findByCategory(CityCategory category);
    List<City> findByHiddenGemTrue();
}