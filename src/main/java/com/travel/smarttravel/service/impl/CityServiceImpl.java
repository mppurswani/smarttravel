package com.travel.smarttravel.service.impl;

import com.travel.smarttravel.dto.CityDTO;
import com.travel.smarttravel.dto.CityPageResponse;
import com.travel.smarttravel.entity.City;
import com.travel.smarttravel.entity.CityCategory;
import com.travel.smarttravel.exception.ResourceNotFoundException;
import com.travel.smarttravel.repository.CityRepository;
import com.travel.smarttravel.service.CityService;
import com.travel.smarttravel.specification.CitySpecification;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Caching;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CityServiceImpl implements CityService {

    private final CityRepository cityRepository;

    public CityServiceImpl(CityRepository cityRepository) {
        this.cityRepository = cityRepository;
    }

    // GET BY ID
    @Override
    @Cacheable(value = "city", key = "#id")
    public CityDTO getCityById(Long id) {
        System.out.println("Fetching city by ID from SERVICE: " + id);

        City city = cityRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("City not found with id: " + id));

        return convertToDTO(city);
    }

    // GET CITIES (CACHE FIXED)
    @Override
    @Cacheable(
        value = "cities",
        key = "'cities:' + " +
              "(#keyword == null || #keyword.isBlank() ? 'ALL' : #keyword.toLowerCase()) + ':' + " +
              "(#country == null || #country.isBlank() ? 'ALL' : #country.toLowerCase()) + ':' + " +
              "(#category == null ? 'ALL' : #category.name()) + ':' + " +
              "#page + ':' + #size"
    )
    public CityPageResponse getCities(String keyword,
                                      String country,
                                      CityCategory category,
                                      int page,
                                      int size) {

        // only validation (NO transformation)
        if (page < 0) page = 0;
        if (size <= 0) size = 10;

        System.out.println("Fetching cities from SERVICE");

        Pageable pageable = PageRequest.of(page, size);

        Specification<City> spec =
                CitySpecification.filter(keyword, country, category);

        Page<City> cityPage = cityRepository.findAll(spec, pageable);

        List<CityDTO> content = cityPage.getContent()
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());

        CityPageResponse response = new CityPageResponse();
        response.setContent(content);
        response.setTotalElements(cityPage.getTotalElements());
        response.setTotalPages(cityPage.getTotalPages());
        response.setPage(cityPage.getNumber());

        return response;
    }

    // CREATE CITY
    @Override
    @Caching(evict = {
            @CacheEvict(value = "cities", allEntries = true),
            @CacheEvict(value = "city", allEntries = true),
            @CacheEvict(value = "hiddenGems", allEntries = true)
    })
    public CityDTO addCity(CityDTO cityDTO) {
        System.out.println("Adding new city: " + cityDTO.getName());

        City city = convertToEntity(cityDTO);
        return convertToDTO(cityRepository.save(city));
    }

    // BULK CREATE
    @Override
    @Caching(evict = {
            @CacheEvict(value = "cities", allEntries = true),
            @CacheEvict(value = "city", allEntries = true),
            @CacheEvict(value = "hiddenGems", allEntries = true)
    })
    public List<CityDTO> addCities(List<CityDTO> cityDTOList) {

        List<City> cities = cityDTOList.stream()
                .map(this::convertToEntity)
                .collect(Collectors.toList());

        return cityRepository.saveAll(cities)
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    // DELETE
    @Override
    @Caching(evict = {
            @CacheEvict(value = "cities", allEntries = true),
            @CacheEvict(value = "city", allEntries = true),
            @CacheEvict(value = "hiddenGems", allEntries = true)
    })
    public void deleteCity(Long id) {

        City city = cityRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("City not found with id: " + id));

        cityRepository.delete(city);
    }

    // HIDDEN GEMS
    @Override
    @Cacheable(value = "hiddenGems")
    public List<CityDTO> getHiddenGems() {

        return cityRepository.findByHiddenGemTrue()
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    // MAPPER
    private CityDTO convertToDTO(City city) {

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
        dto.setBestTimeToVisit(city.getBestTimeToVisit());
        dto.setNearbyAttractions(city.getNearbyAttractions());
        dto.setLanguage(city.getLanguage());
        dto.setEntryFee(city.getEntryFee());
        dto.setFavourited(false);

        return dto;
    }

    private City convertToEntity(CityDTO dto) {

        City city = new City();
        city.setName(dto.getName());
        city.setState(dto.getState());
        city.setCountry(dto.getCountry());
        city.setCulture(dto.getCulture());
        city.setTouristSpots(dto.getTouristSpots());
        city.setFood(dto.getFood());
        city.setCategory(dto.getCategory());
        city.setHiddenGem(dto.isHiddenGem());
        city.setBestTimeToVisit(dto.getBestTimeToVisit());
        city.setNearbyAttractions(dto.getNearbyAttractions());
        city.setLanguage(dto.getLanguage());
        city.setEntryFee(dto.getEntryFee());

        return city;
    }
}