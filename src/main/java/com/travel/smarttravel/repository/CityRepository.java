package com.travel.smarttravel.repository;
import com.travel.smarttravel.entity.City;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import com.travel.smarttravel.entity.CityCategory;
import java.util.List;
public interface CityRepository extends JpaRepository<City, Long> {

    // Search with pagination & sorting
    Page<City> findByNameIgnoreCaseContaining(String name, Pageable pageable);
    Page<City> findByStateIgnoreCaseContaining(String state, Pageable pageable);
    Page<City> findByCountryIgnoreCaseContaining(String country, Pageable pageable);
 List<City> findByCategory(CityCategory category);
List<City> findByIsHiddenGemTrue();
}
