package com.travel.smarttravel.dto;

import com.travel.smarttravel.entity.CityCategory;
import javax.validation.constraints.NotBlank;

public class CityDTO {

    private Long id;

    @NotBlank(message = "City name cannot be empty")
    private String name;

    @NotBlank(message = "State cannot be empty")
    private String state;

    @NotBlank(message = "Country cannot be empty")
    private String country;

    private String culture;
    private String touristSpots;
    private String food;

    private CityCategory category;

    private boolean hiddenGem;

    private String bestTimeToVisit;
    private String nearbyAttractions;
    private String language;
    private String entryFee;

    private boolean favourited;

    // ==========================================
    // GETTERS & SETTERS
    // ==========================================
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getState() { return state; }
    public void setState(String state) { this.state = state; }

    public String getCountry() { return country; }
    public void setCountry(String country) { this.country = country; }

    public String getCulture() { return culture; }
    public void setCulture(String culture) { this.culture = culture; }

    public String getTouristSpots() { return touristSpots; }
    public void setTouristSpots(String touristSpots) { this.touristSpots = touristSpots; }

    public String getFood() { return food; }
    public void setFood(String food) { this.food = food; }

    public CityCategory getCategory() { return category; }
    public void setCategory(CityCategory category) { this.category = category; }

    public boolean isHiddenGem() { return hiddenGem; }
    public void setHiddenGem(boolean hiddenGem) { this.hiddenGem = hiddenGem; }

    public String getBestTimeToVisit() { return bestTimeToVisit; }
    public void setBestTimeToVisit(String bestTimeToVisit) { this.bestTimeToVisit = bestTimeToVisit; }

    public String getNearbyAttractions() { return nearbyAttractions; }
    public void setNearbyAttractions(String nearbyAttractions) { this.nearbyAttractions = nearbyAttractions; }

    public String getLanguage() { return language; }
    public void setLanguage(String language) { this.language = language; }

    public String getEntryFee() { return entryFee; }
    public void setEntryFee(String entryFee) { this.entryFee = entryFee; }

    public boolean isFavourited() { return favourited; }
    public void setFavourited(boolean favourited) { this.favourited = favourited; }
}