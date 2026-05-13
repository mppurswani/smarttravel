//cityrepository updated 
package com.travel.smarttravel.repository;

import com.travel.smarttravel.entity.City;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface CityRepository extends JpaRepository<City, Long>,
        JpaSpecificationExecutor<City> {

    // SIMPLE CASE ONLY
    List<City> findByHiddenGemTrue();
}