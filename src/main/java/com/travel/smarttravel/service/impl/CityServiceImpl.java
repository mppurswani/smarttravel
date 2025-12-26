package com.travel.smarttravel.service.impl;

import com.travel.smarttravel.dto.CityDTO;
import com.travel.smarttravel.entity.City;
import com.travel.smarttravel.exception.ResourceNotFoundException;
import com.travel.smarttravel.repository.CityRepository;
import com.travel.smarttravel.service.CityService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CityServiceImpl implements CityService {

    private final CityRepository cityRepository;

    public CityServiceImpl(CityRepository cityRepository) {
        this.cityRepository = cityRepository;
    }

    @Override
    public CityDTO addCity(CityDTO dto) {
        if (dto == null) throw new IllegalArgumentException("CityDTO cannot be null");
        City city = toEntity(dto);
        City savedCity = cityRepository.save(city);
        return toDTO(savedCity);
    }

    // Pagination + Sorting for all cities
    @Override
    public Page<CityDTO> getAllCities(int page, int size, String sortBy, String sortDir) {
        Sort sort = sortDir.equalsIgnoreCase("desc") ? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
        Pageable pageable = PageRequest.of(page, size, sort);
        Page<City> cityPage = cityRepository.findAll(pageable);
        List<CityDTO> dtoList = cityPage.stream().map(this::toDTO).collect(Collectors.toList());
        return new PageImpl<>(dtoList, pageable, cityPage.getTotalElements());
    }

    @Override
    public CityDTO getCityById(Long id) {
        if (id == null) throw new IllegalArgumentException("Id cannot be null");
        City city = cityRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("City not found with id: " + id));
        return toDTO(city);
    }

    @Override
    public Page<CityDTO> searchByName(String name, int page, int size, String sortBy, String sortDir) {
        Sort sort = sortDir.equalsIgnoreCase("desc") ? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
        Pageable pageable = PageRequest.of(page, size, sort);
        Page<City> cityPage = cityRepository.findByNameContainingIgnoreCase(name, pageable);
        List<CityDTO> dtoList = cityPage.stream().map(this::toDTO).collect(Collectors.toList());
        return new PageImpl<>(dtoList, pageable, cityPage.getTotalElements());
    }

    @Override
    public Page<CityDTO> searchByState(String state, int page, int size, String sortBy, String sortDir) {
        Sort sort = sortDir.equalsIgnoreCase("desc") ? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
        Pageable pageable = PageRequest.of(page, size, sort);
        Page<City> cityPage = cityRepository.findByStateContainingIgnoreCase(state, pageable);
        List<CityDTO> dtoList = cityPage.stream().map(this::toDTO).collect(Collectors.toList());
        return new PageImpl<>(dtoList, pageable, cityPage.getTotalElements());
    }

    @Override
    public Page<CityDTO> searchByCountry(String country, int page, int size, String sortBy, String sortDir) {
        Sort sort = sortDir.equalsIgnoreCase("desc") ? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
        Pageable pageable = PageRequest.of(page, size, sort);
        Page<City> cityPage = cityRepository.findByCountryContainingIgnoreCase(country, pageable);
        List<CityDTO> dtoList = cityPage.stream().map(this::toDTO).collect(Collectors.toList());
        return new PageImpl<>(dtoList, pageable, cityPage.getTotalElements());
    }

    // DTO → ENTITY
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

    // ENTITY → DTO
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
