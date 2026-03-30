package com.travel.smarttravel.entity;

import javax.persistence.*;

@Entity
@Table(name = "city")
public class City {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "state")
    private String state;

    @Column(name = "country")
    private String country;

    @Column(name = "culture")
    private String culture;

    @Column(name = "tourist_spots")
    private String touristSpots;

    @Column(name = "food")
    private String food;

    @Enumerated(EnumType.STRING)
    @Column(name = "category")
    private CityCategory category;

    @Column(name = "is_hidden_gem")
    private boolean hiddenGem = false;

    @Column(name = "best_time_to_visit")
    private String bestTimeToVisit;

    @Column(name = "nearby_attractions")
    private String nearbyAttractions;

    @Column(name = "language")
    private String language;

    @Column(name = "entry_fee")
    private String entryFee;

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
}