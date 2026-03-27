package com.travel.smarttravel.controller;
import com.travel.smarttravel.dto.CityDTO;
import com.travel.smarttravel.entity.CityCategory;
import com.travel.smarttravel.service.CityService;
import org.springframework.data.domain.Page;
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

    // POST /api/cities
    // POST /api/cities/bulk
// POST /api/cities - Add a single city
@PostMapping
public CityDTO addCity(@Valid @RequestBody CityDTO cityDTO) {
    return cityService.addCity(cityDTO);
}
@PostMapping("/bulk")
public List<CityDTO> addCities(@Valid @RequestBody List<CityDTO> cityDTOList) {
    return cityService.addCities(cityDTOList);
}

    // GET /api/cities
    @GetMapping
    public Page<CityDTO> getAllCities(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "name") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDir) {
        return cityService.getAllCities(page, size, sortBy, sortDir);
    }

    // GET /api/cities/{id}
    @GetMapping("/{id}")
    public CityDTO getCityById(@PathVariable Long id) {
        return cityService.getCityById(id);
    }

    // DELETE /api/cities/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCity(@PathVariable Long id) {
        cityService.deleteCity(id);
        return ResponseEntity.noContent().build();
    }

    // GET /api/cities/all
    @GetMapping("/all")
    public List<CityDTO> getAllCitiesWithoutPagination() {
        return cityService.getAllCitiesWithoutPagination();
    }

    // GET /api/cities/search
    @GetMapping("/search")
    public Page<CityDTO> searchCities(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String state,
            @RequestParam(required = false) String country,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "name") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDir) {
        if (name != null && !name.isEmpty()) {
            return cityService.searchByName(name, page, size, sortBy, sortDir);
        } else if (state != null && !state.isEmpty()) {
            return cityService.searchByState(state, page, size, sortBy, sortDir);
        } else if (country != null && !country.isEmpty()) {
            return cityService.searchByCountry(country, page, size, sortBy, sortDir);
        }
        return cityService.getAllCities(page, size, sortBy, sortDir);
    }

    // GET /api/cities/category/BEACHES
    @GetMapping("/category/{category}")
    public List<CityDTO> getCitiesByCategory(
            @PathVariable String category) {
        try {
            CityCategory cat = CityCategory.valueOf(category.toUpperCase());
            return cityService.getCitiesByCategory(cat);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Invalid category: " + category
                + ". Valid: MOUNTAINS, BEACHES, PARTY, RELIGIOUS,"
                + " FOOD_STREET, ADVENTURE, HERITAGE, HIDDEN_GEM");
        }
    }

    // GET /api/cities/hidden-gems
    @GetMapping("/hidden-gems")
    public List<CityDTO> getHiddenGems() {
        return cityService.getHiddenGems();
    }
}
