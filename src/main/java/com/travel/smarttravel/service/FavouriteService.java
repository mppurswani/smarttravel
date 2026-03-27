package com.travel.smarttravel.service;

import com.travel.smarttravel.dto.CityDTO;
import com.travel.smarttravel.entity.FavouriteCity;
import com.travel.smarttravel.entity.User;
import com.travel.smarttravel.exception.ResourceNotFoundException;
import com.travel.smarttravel.repository.CityRepository;
import com.travel.smarttravel.repository.FavouriteCityRepository;
import com.travel.smarttravel.repository.UserRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class FavouriteService {

    private final FavouriteCityRepository favouriteRepo;
    private final CityRepository cityRepository;
    private final UserRepository userRepository;

    public FavouriteService(
            FavouriteCityRepository favouriteRepo,
            CityRepository cityRepository,
            UserRepository userRepository) {
        this.favouriteRepo = favouriteRepo;
        this.cityRepository = cityRepository;
        this.userRepository = userRepository;
    }

    // ==========================================
    // GET LOGGED-IN USER FROM JWT TOKEN
    // ==========================================
    private User getLoggedInUser() {
        String username = SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName();
        return userRepository.findByUsername(username)
                .orElseThrow(() ->
                    new ResourceNotFoundException(
                        "User not found: " + username));
    }

    // ==========================================
    // ADD CITY TO FAVOURITES
    // ==========================================
    public String addFavourite(Long cityId) {
        User user = getLoggedInUser();

        if (!cityRepository.existsById(cityId)) {
            throw new ResourceNotFoundException(
                "City not found with id: " + cityId);
        }

        if (favouriteRepo.existsByUserIdAndCityId(
                user.getId(), cityId)) {
            return "City already in favourites";
        }

        FavouriteCity fav = new FavouriteCity();
        fav.setUserId(user.getId());
        fav.setCityId(cityId);
        favouriteRepo.save(fav);

        return "City added to favourites";
    }

    // ==========================================
    // REMOVE CITY FROM FAVOURITES
    // ==========================================
    @Transactional
    public String removeFavourite(Long cityId) {
        User user = getLoggedInUser();

        if (!favouriteRepo.existsByUserIdAndCityId(
                user.getId(), cityId)) {
            return "City was not in your favourites";
        }

        favouriteRepo.deleteByUserIdAndCityId(
            user.getId(), cityId);
        return "City removed from favourites";
    }

    // ==========================================
    // GET ALL FAVOURITES FOR LOGGED-IN USER
    // ==========================================
    public List<CityDTO> getMyFavourites() {
        User user = getLoggedInUser();

        return favouriteRepo
            .findByUserId(user.getId())
            .stream()
            .map(fav -> cityRepository
                .findById(fav.getCityId())
                .map(city -> {
                    CityDTO dto = new CityDTO();
                    dto.setId(city.getId());
                    dto.setName(city.getName());
                    dto.setState(city.getState());
                    dto.setCountry(city.getCountry());
                    dto.setCulture(city.getCulture());
                    dto.setTouristSpots(city.getTouristSpots());
                    dto.setFood(city.getFood());
                    dto.setCategory(city.getCategory());
                    dto.setHiddenGem(city.isHiddenGem());
                    dto.setFavourited(true);
                    return dto;
                })
                .orElse(null))
            .filter(dto -> dto != null)
            .collect(Collectors.toList());
    }
}