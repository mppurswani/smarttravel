package com.travel.smarttravel.service;

import java.util.List;
import com.travel.smarttravel.dto.CityDTO;
import com.travel.smarttravel.entity.CityCategory;

public interface CityService {

    // Single city add
    CityDTO addCity(CityDTO cityDTO);

    // Bulk add
    List<CityDTO> addCities(List<CityDTO> cityDTOList);

    List<CityDTO> getAllCitiesWithoutPagination();

    CityDTO getCityById(Long id);

    List<CityDTO> searchByName(String name);

    List<CityDTO> searchByState(String state);
    List<CityDTO> searchByKeyword(String keyword);

    List<CityDTO> searchByCountry(String country);

    void deleteCity(Long id);

    List<CityDTO> getCitiesByCategory(CityCategory category);
    List<CityDTO> getHiddenGems();
}