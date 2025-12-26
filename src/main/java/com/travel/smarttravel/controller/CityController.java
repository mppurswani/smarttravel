package com.travel.smarttravel.controller;

import com.travel.smarttravel.dto.CityDTO;
import com.travel.smarttravel.service.CityService;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

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

    // ✅ Get all cities with pagination and sorting
    @GetMapping
    public Page<CityDTO> getAllCities(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "name") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDir
    ) {
        return cityService.getAllCities(page, size, sortBy, sortDir);
    }

    @GetMapping("/{id}")
    public CityDTO getCityById(@PathVariable Long id) {
        return cityService.getCityById(id);
    }

    // ✅ Search cities with pagination and sorting
    @GetMapping("/search")
    public Page<CityDTO> searchCities(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String state,
            @RequestParam(required = false) String country,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "name") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDir
    ) {
        if (name != null && !name.isEmpty()) {
            return cityService.searchByName(name, page, size, sortBy, sortDir);
        } else if (state != null && !state.isEmpty()) {
            return cityService.searchByState(state, page, size, sortBy, sortDir);
        } else if (country != null && !country.isEmpty()) {
            return cityService.searchByCountry(country, page, size, sortBy, sortDir);
        }
        return Page.empty(); // Return empty page if no param is provided
    }
}
