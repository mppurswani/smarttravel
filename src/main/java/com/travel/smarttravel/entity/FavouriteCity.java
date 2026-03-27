package com.travel.smarttravel.entity;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "favourite_cities",
    uniqueConstraints = {
        @UniqueConstraint(
            columnNames = {"user_id", "city_id"})
    })
public class FavouriteCity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Which user favourited
    @Column(name = "user_id", nullable = false)
    private Long userId;

    // Which city they favourited
    @Column(name = "city_id", nullable = false)
    private Long cityId;

    // When they added it
    @Column(name = "added_at")
    private LocalDateTime addedAt;

    @PrePersist
    public void prePersist() {
        addedAt = LocalDateTime.now();
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }

    public Long getCityId() { return cityId; }
    public void setCityId(Long cityId) { this.cityId = cityId; }

    public LocalDateTime getAddedAt() { return addedAt; }
    public void setAddedAt(LocalDateTime addedAt) {
        this.addedAt = addedAt;
    }
}