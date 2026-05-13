package com.travel.smarttravel.controller;

import com.travel.smarttravel.dto.CityDTO;
import com.travel.smarttravel.dto.CityPageResponse;
import com.travel.smarttravel.entity.CityCategory;
import com.travel.smarttravel.service.CityService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
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

    @PostMapping("/bulk")
    public List<CityDTO> addCities(@Valid @RequestBody List<CityDTO> cityDTOList) {
        return cityService.addCities(cityDTOList);
    }

    @GetMapping
    public ResponseEntity<CityPageResponse> getCities(
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) String country,
            @RequestParam(required = false) CityCategory category,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {

        if (size > 50) size = 50;

        return ResponseEntity.ok(
                cityService.getCities(keyword, country, category, page, size)
        );
    }

    @GetMapping("/{id:\\d+}")
    public CityDTO getCityById(@PathVariable Long id) {
        return cityService.getCityById(id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCity(@PathVariable Long id) {
        cityService.deleteCity(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/hidden-gems")
    public List<CityDTO> getHiddenGems() {
        return cityService.getHiddenGems();
    }
}