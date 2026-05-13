package com.travel.smarttravel.service;

import com.travel.smarttravel.dto.CityDTO;
import com.travel.smarttravel.dto.CityPageResponse; // ✅ REQUIRED
import com.travel.smarttravel.entity.CityCategory;

import java.util.List;

public interface CityService {

    // CREATE
    CityDTO addCity(CityDTO cityDTO);

    List<CityDTO> addCities(List<CityDTO> cityDTOList);

    // READ SINGLE
    CityDTO getCityById(Long id);

    // DELETE
    void deleteCity(Long id);

    // PAGINATION + FILTER
    CityPageResponse getCities(String keyword,
                              String country,
                              CityCategory category,
                              int page,
                              int size);

    // OPTIONAL
    List<CityDTO> getHiddenGems();
}