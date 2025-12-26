package com.travel.smarttravel.controller;

import com.travel.smarttravel.dto.CityDTO;
import com.travel.smarttravel.service.CityService;
import javax.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cities")
public class CityController {

    private final CityService cityService;

    public CityController(CityService cityService) {
        this.cityService = cityService;
    }

    @PostMapping
    public CityDTO addCity(@Valid @RequestBody CityDTO cityDTO) {
        return cityService.addCity(cityDTO);
    }

    @GetMapping
    public List<CityDTO> getAllCities() {
        return cityService.getAllCities();
    }

    @GetMapping("/{id}")
    public CityDTO getCityById(@PathVariable Long id) {
        return cityService.getCityById(id);
    }

    @GetMapping("/search")
public List<CityDTO> searchCities(
        @RequestParam(required = false) String name,
        @RequestParam(required = false) String state,
        @RequestParam(required = false) String country
) {
    if (name != null && !name.isEmpty()) {
        return cityService.searchByName(name);
    } else if (state != null && !state.isEmpty()) {
        return cityService.searchByState(state);
    } else if (country != null && !country.isEmpty()) {
        return cityService.searchByCountry(country);
    }
    return List.of(); // Return empty list if no param is provided
}
}
