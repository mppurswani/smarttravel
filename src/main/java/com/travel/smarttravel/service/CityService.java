package com.travel.smarttravel.service;

import com.travel.smarttravel.dto.CityDTO;

import java.util.List;

public interface CityService {

    CityDTO addCity(CityDTO cityDTO);

    List<CityDTO> getAllCities();

    CityDTO getCityById(Long id);

    List<CityDTO> searchByName(String name);

    List<CityDTO> searchByState(String state);

    List<CityDTO> searchByCountry(String country);
}
