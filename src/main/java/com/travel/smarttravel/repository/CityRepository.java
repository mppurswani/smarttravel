package com.travel.smarttravel.repository;

import com.travel.smarttravel.entity.City;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.travel.smarttravel.entity.CityCategory;
import java.util.List;

public interface CityRepository extends JpaRepository<City, Long> {

    // Search with pagination & sorting
    List<City> findByNameIgnoreCaseContaining(String name);
    List<City> findByStateIgnoreCaseContaining(String state);
    List<City> findByCountryIgnoreCaseContaining(String country);

    // ✅ keyword search across name/state/country
    @Query("SELECT DISTINCT c FROM City c " +
       "WHERE LOWER(c.name) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
       "OR LOWER(c.state) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
       "OR LOWER(c.country) LIKE LOWER(CONCAT('%', :keyword, '%'))")
List<City> searchAll(@Param("keyword") String keyword);

    List<City> findByCategory(CityCategory category);
    List<City> findByHiddenGemTrue();
}