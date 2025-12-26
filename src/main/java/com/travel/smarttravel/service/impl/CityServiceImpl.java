package com.travel.smarttravel.service.impl;

import com.travel.smarttravel.dto.CityDTO;
import com.travel.smarttravel.entity.City;
import com.travel.smarttravel.repository.CityRepository;
import com.travel.smarttravel.service.CityService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CityServiceImpl implements CityService {

    private final CityRepository cityRepository;

    public CityServiceImpl(CityRepository cityRepository) {
        this.cityRepository = cityRepository;
    }

    @Override
    public CityDTO addCity(CityDTO dto) {
        if (dto == null) {
            throw new IllegalArgumentException("CityDTO cannot be null");
        }
        City city = toEntity(dto);
        if (city == null) {
            throw new RuntimeException("Conversion to Entity failed");
        }
        City savedCity = cityRepository.save(city);
        return toDTO(savedCity);
    }

    @Override
    public List<CityDTO> getAllCities() {
        return cityRepository.findAll()
                .stream()
                .map(this::toDTO)
                .toList();
    }

    @Override
    public CityDTO getCityById(Long id) {
        if(id==null){
            throw new IllegalArgumentException("Id cannot be null");
        }
        City city = cityRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("City not found with id: " + id));
        return toDTO(city);
    }

    @Override
    public List<CityDTO> searchByName(String name) {
        return cityRepository.findByNameContainingIgnoreCase(name)
                .stream()
                .map(this::toDTO)
                .toList();
    }

    @Override
    public List<CityDTO> searchByState(String state) {
        return cityRepository.findByStateContainingIgnoreCase(state)
                .stream()
                .map(this::toDTO)
                .toList();
    }

    @Override
    public List<CityDTO> searchByCountry(String country) {
        return cityRepository.findByCountryContainingIgnoreCase(country)
                .stream()
                .map(this::toDTO)
                .toList();
    }

    // 🔁 DTO → ENTITY
    private City toEntity(CityDTO dto) {
        City city = new City();
        city.setName(dto.getName());
        city.setState(dto.getState());
        city.setCountry(dto.getCountry());
        city.setCulture(dto.getCulture());
        city.setFood(dto.getFood());
        city.setTouristSpots(dto.getTouristSpots());
        return city;
    }

    // 🔁 ENTITY → DTO
    private CityDTO toDTO(City city) {
        CityDTO dto = new CityDTO();
        dto.setId(city.getId());
        dto.setName(city.getName());
        dto.setState(city.getState());
        dto.setCountry(city.getCountry());
        dto.setCulture(city.getCulture());
        dto.setFood(city.getFood());
        dto.setTouristSpots(city.getTouristSpots());
        return dto;
    }
}
