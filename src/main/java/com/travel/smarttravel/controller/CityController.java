package com.travel.smarttravel.controller;

import com.travel.smarttravel.dto.CityDTO;
import com.travel.smarttravel.entity.CityCategory;
import com.travel.smarttravel.service.CityService;
import org.springframework.http.HttpStatus;
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

    // POST /api/cities - Add a single city
    @PostMapping
    public CityDTO addCity(@Valid @RequestBody CityDTO cityDTO) {
        return cityService.addCity(cityDTO);
    }

    // POST /api/cities/bulk - Add multiple cities
    @PostMapping("/bulk")
    public List<CityDTO> addCities(@Valid @RequestBody List<CityDTO> cityDTOList) {
        return cityService.addCities(cityDTOList);
    }

    // GET /api/cities
    @GetMapping
    public List<CityDTO> getAllCities(){
        return cityService.getAllCitiesWithoutPagination();
    }

    // GET /api/cities/{id}
    @GetMapping("/{id}")
    public CityDTO getCityById(@PathVariable Long id) {
        return cityService.getCityById(id);
    }

    // DELETE /api/cities/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCity(@PathVariable Long id) {
        try {
            cityService.deleteCity(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("City with id " + id + " not found");
        }
    }

    // GET /api/cities/search?keyword=mumbai
   @GetMapping("/search")
public List<CityDTO> searchCities(@RequestParam(required = false) String keyword) {

    if (keyword == null || keyword.trim().isEmpty()) {
        return cityService.getAllCitiesWithoutPagination();
    }

    return cityService.searchByKeyword(keyword);
}

    // GET /api/cities/category/BEACHES
    @GetMapping("/category/{category}")
    public ResponseEntity<?> getCitiesByCategory(@PathVariable String category) {
        try {
            CityCategory cat = CityCategory.valueOf(category.toUpperCase());
            return ResponseEntity.ok(cityService.getCitiesByCategory(cat));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(
                    "Invalid category: " + category
                            + ". Valid: MOUNTAINS, BEACHES, PARTY, RELIGIOUS, FOOD_STREET, ADVENTURE, HERITAGE, HIDDEN_GEM"
            );
        }
    }

    // GET /api/cities/hidden-gems
    @GetMapping("/hidden-gems")
    public List<CityDTO> getHiddenGems() {
        return cityService.getHiddenGems();
    }
}