package com.travel.smarttravel.repository;

import com.travel.smarttravel.entity.FavouriteCity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface FavouriteCityRepository
        extends JpaRepository<FavouriteCity, Long> {

    // Get all favourites for a user
    List<FavouriteCity> findByUserId(Long userId);

    // Check if user already favourited this city
    boolean existsByUserIdAndCityId(Long userId, Long cityId);

    // Remove a specific favourite
    void deleteByUserIdAndCityId(Long userId, Long cityId);
}