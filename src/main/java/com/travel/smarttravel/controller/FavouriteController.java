package com.travel.smarttravel.controller;

import com.travel.smarttravel.dto.CityDTO;
import com.travel.smarttravel.service.FavouriteService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/favourites")
public class FavouriteController {

    private final FavouriteService favouriteService;

    public FavouriteController(FavouriteService favouriteService) {
        this.favouriteService = favouriteService;
    }

    // Add city to favourites
    // POST /api/favourites/3
    @PostMapping("/{cityId}")
    public ResponseEntity<String> addFavourite(
            @PathVariable Long cityId) {
        return ResponseEntity.ok(
            favouriteService.addFavourite(cityId));
    }

    // Remove city from favourites
    // DELETE /api/favourites/3
    @DeleteMapping("/{cityId}")
    public ResponseEntity<String> removeFavourite(
            @PathVariable Long cityId) {
        return ResponseEntity.ok(
            favouriteService.removeFavourite(cityId));
    }

    // Get all my favourites
    // GET /api/favourites
    @GetMapping
    public ResponseEntity<List<CityDTO>> getMyFavourites() {
        return ResponseEntity.ok(
            favouriteService.getMyFavourites());
    }
}