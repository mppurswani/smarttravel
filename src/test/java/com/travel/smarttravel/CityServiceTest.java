package com.travel.smarttravel;

import com.travel.smarttravel.dto.CityDTO;
import com.travel.smarttravel.entity.City;
import com.travel.smarttravel.entity.CityCategory;
import com.travel.smarttravel.exception.ResourceNotFoundException;
import com.travel.smarttravel.repository.CityRepository;
import com.travel.smarttravel.service.impl.CityServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CityServiceTest {

    @Mock
    private CityRepository cityRepository;

    @InjectMocks
    private CityServiceImpl cityService;

    private City testCity;
    private CityDTO testCityDTO;

    @BeforeEach
    void setUp() {
        // Create test city entity
        testCity = new City();
        testCity.setId(1L);
        testCity.setName("Goa");
        testCity.setState("Goa");
        testCity.setCountry("India");
        testCity.setCulture("Portuguese beach culture");
        testCity.setFood("Seafood");
        testCity.setCategory(CityCategory.BEACHES);
        testCity.setHiddenGem(false);

        // Create test city DTO
        testCityDTO = new CityDTO();
        testCityDTO.setName("Goa");
        testCityDTO.setState("Goa");
        testCityDTO.setCountry("India");
        testCityDTO.setCulture("Portuguese beach culture");
        testCityDTO.setFood("Seafood");
        testCityDTO.setCategory(CityCategory.BEACHES);
        testCityDTO.setHiddenGem(false);
    }

    // ==========================================
    // TEST 1 — addCity saves and returns DTO
    // ==========================================
    @Test
    void addCity_ShouldSaveAndReturnDTO() {
        when(cityRepository.save(any(City.class)))
            .thenReturn(testCity);

        CityDTO result = cityService.addCity(testCityDTO);

        assertNotNull(result);
        assertEquals("Goa", result.getName());
        assertEquals("Goa", result.getState());
        assertEquals(CityCategory.BEACHES, result.getCategory());
        verify(cityRepository, times(1)).save(any(City.class));
    }

    // ==========================================
    // TEST 2 — getCityById returns correct city
    // ==========================================
    @Test
    void getCityById_WhenExists_ShouldReturnDTO() {
        when(cityRepository.findById(1L))
            .thenReturn(Optional.of(testCity));

        CityDTO result = cityService.getCityById(1L);

        assertNotNull(result);
        assertEquals("Goa", result.getName());
        assertEquals(1L, result.getId());
    }

    // ==========================================
    // TEST 3 — getCityById throws when not found
    // ==========================================
    @Test
    void getCityById_WhenNotExists_ShouldThrowException() {
        when(cityRepository.findById(99L))
            .thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class,
            () -> cityService.getCityById(99L));
    }

    // ==========================================
    // TEST 4 — deleteCity works when city exists
    // ==========================================
    @Test
    void deleteCity_WhenExists_ShouldDelete() {
        when(cityRepository.existsById(1L))
            .thenReturn(true);
        doNothing().when(cityRepository).deleteById(1L);

        assertDoesNotThrow(
            () -> cityService.deleteCity(1L));
        verify(cityRepository, times(1)).deleteById(1L);
    }

    // ==========================================
    // TEST 5 — deleteCity throws when not found
    // ==========================================
    @Test
    void deleteCity_WhenNotExists_ShouldThrowException() {
        when(cityRepository.existsById(99L))
            .thenReturn(false);

        assertThrows(ResourceNotFoundException.class,
            () -> cityService.deleteCity(99L));
        verify(cityRepository, never()).deleteById(any());
    }
}