package com.travel.smarttravel.service;

import com.travel.smarttravel.dto.CityDTO;
import org.springframework.data.domain.Page;

public interface CityService {

    CityDTO addCity(CityDTO cityDTO);

    Page<CityDTO> getAllCities(int page, int size, String sortBy, String sortDir);

    CityDTO getCityById(Long id);

    Page<CityDTO> searchByName(String name, int page, int size, String sortBy, String sortDir);

    Page<CityDTO> searchByState(String state, int page, int size, String sortBy, String sortDir);

    Page<CityDTO> searchByCountry(String country, int page, int size, String sortBy, String sortDir);
}
