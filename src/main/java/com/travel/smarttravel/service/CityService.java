package com.travel.smarttravel.service;

import java.util.List;
import com.travel.smarttravel.dto.CityDTO;
import com.travel.smarttravel.entity.CityCategory;
import org.springframework.data.domain.Page;

public interface CityService {

    // Single city add
    CityDTO addCity(CityDTO cityDTO);

    // Bulk add
    List<CityDTO> addCities(List<CityDTO> cityDTOList);

    Page<CityDTO> getAllCities(int page, int size, String sortBy, String sortDir);

    CityDTO getCityById(Long id);

    Page<CityDTO> searchByName(String name, int page, int size, String sortBy, String sortDir);

    Page<CityDTO> searchByState(String state, int page, int size, String sortBy, String sortDir);
    Page<CityDTO> searchByKeyword(String keyword, int page, int size, String sortBy, String sortDir);

    Page<CityDTO> searchByCountry(String country, int page, int size, String sortBy, String sortDir);

    void deleteCity(Long id);

    List<CityDTO> getAllCitiesWithoutPagination();
    List<CityDTO> getCitiesByCategory(CityCategory category);
    List<CityDTO> getHiddenGems();
}