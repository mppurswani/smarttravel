package com.travel.smarttravel.service.impl;

import com.travel.smarttravel.dto.CityDTO;
import com.travel.smarttravel.entity.City;
import com.travel.smarttravel.entity.CityCategory;
import com.travel.smarttravel.exception.ResourceNotFoundException;
import com.travel.smarttravel.repository.CityRepository;
import com.travel.smarttravel.service.CityService;
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
    public CityDTO addCity(CityDTO cityDTO) {
        City city = convertToEntity(cityDTO);
        City saved = cityRepository.save(city);
        return convertToDTO(saved);
    }

    @Override
    public List<CityDTO> addCities(List<CityDTO> cityDTOList) {
        List<City> cities = cityDTOList.stream()
                                       .map(this::convertToEntity)
                                       .collect(Collectors.toList());
        List<City> savedCities = cityRepository.saveAll(cities);
        return savedCities.stream()
                          .map(this::convertToDTO)
                          .collect(Collectors.toList());
    }
    @Override
    public CityDTO getCityById(Long id) {
        City city = cityRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("City not found with id: " + id));
        return convertToDTO(city);
    }

    @Override
    public void deleteCity(Long id) {
        if (!cityRepository.existsById(id)) {
            throw new ResourceNotFoundException("City not found with id: " + id);
        }
        cityRepository.deleteById(id);
    }

    @Override
    public List<CityDTO> getAllCitiesWithoutPagination() {
        return cityRepository.findAll()
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<CityDTO> searchByName(String name){
        return cityRepository.findByNameIgnoreCaseContaining(name)
        .stream()
        .map(this::convertToDTO)
        .collect(Collectors.toList());
    }

    @Override
    public List<CityDTO> searchByState(String state){
        return cityRepository.findByStateIgnoreCaseContaining(state)
        .stream()
        .map(this::convertToDTO)
        .collect(Collectors.toList());
    }

    @Override
public List<CityDTO> searchByKeyword(String keyword){

    List<City> cities = cityRepository.searchAll(keyword);

    return cities.stream()
            // remove duplicates by ID
            .collect(Collectors.collectingAndThen(
                    Collectors.toMap(
                            City::getId,
                            city -> city,
                            (c1, c2) -> c1
                    ),
                    map -> map.values()
            ))
            .stream()
            .map(this::convertToDTO)
            .collect(Collectors.toList());
}

    @Override
    public List<CityDTO> searchByCountry(String country){
        return cityRepository.findByCountryIgnoreCaseContaining(country)
        .stream()
        .map(this::convertToDTO)
        .collect(Collectors.toList());
    }

    @Override
    public List<CityDTO> getCitiesByCategory(CityCategory category) {
        return cityRepository.findByCategory(category)
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<CityDTO> getHiddenGems() {
        return cityRepository.findByHiddenGemTrue()
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

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

    private City convertToEntity(CityDTO cityDTO) {
        City city = new City();
        city.setName(cityDTO.getName());
        city.setState(cityDTO.getState());
        city.setCountry(cityDTO.getCountry());
        city.setCulture(cityDTO.getCulture());
        city.setTouristSpots(cityDTO.getTouristSpots());
        city.setFood(cityDTO.getFood());
        city.setCategory(cityDTO.getCategory());
        city.setHiddenGem(cityDTO.isHiddenGem());
        city.setBestTimeToVisit(cityDTO.getBestTimeToVisit());
        city.setNearbyAttractions(cityDTO.getNearbyAttractions());
        city.setLanguage(cityDTO.getLanguage());
        city.setEntryFee(cityDTO.getEntryFee());
        return city;
    }
}