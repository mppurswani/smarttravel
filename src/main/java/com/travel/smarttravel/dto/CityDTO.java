package com.travel.smarttravel.dto;

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
}
