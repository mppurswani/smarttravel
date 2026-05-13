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

    private City city;
    private CityDTO cityDTO;

    @BeforeEach
    void setUp() {
        city = new City();
        city.setId(1L);
        city.setName("Goa");
        city.setState("Goa");
        city.setCountry("India");
        city.setCulture("Beach culture");
        city.setFood("Seafood");
        city.setCategory(CityCategory.BEACHES);
        city.setHiddenGem(false);

        cityDTO = new CityDTO();
        cityDTO.setName("Goa");
        cityDTO.setState("Goa");
        cityDTO.setCountry("India");
        cityDTO.setCulture("Beach culture");
        cityDTO.setFood("Seafood");
        cityDTO.setCategory(CityCategory.BEACHES);
        cityDTO.setHiddenGem(false);
    }

    // CREATE
    @Test
    void addCity_ShouldSaveAndReturnDTO() {
        when(cityRepository.save(any(City.class))).thenReturn(city);

        CityDTO result = cityService.addCity(cityDTO);

        assertNotNull(result);
        assertEquals("Goa", result.getName());
        verify(cityRepository, times(1)).save(any(City.class));
    }

    // READ BY ID - SUCCESS
    @Test
    void getCityById_WhenExists_ShouldReturnCity() {
        when(cityRepository.findById(1L)).thenReturn(Optional.of(city));

        CityDTO result = cityService.getCityById(1L);

        assertNotNull(result);
        assertEquals("Goa", result.getName());
        assertEquals(1L, result.getId());
    }

    // READ BY ID - FAIL
    @Test
    void getCityById_WhenNotFound_ShouldThrowException() {
        when(cityRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class,
                () -> cityService.getCityById(99L));
    }

    // DELETE - SUCCESS (UPDATED FLOW)
    @Test
    void deleteCity_WhenExists_ShouldDelete() {
        when(cityRepository.findById(1L)).thenReturn(Optional.of(city));
        doNothing().when(cityRepository).delete(city);

        assertDoesNotThrow(() -> cityService.deleteCity(1L));

        verify(cityRepository, times(1)).delete(city);
    }

    // DELETE - FAIL
    @Test
    void deleteCity_WhenNotFound_ShouldThrowException() {
        when(cityRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class,
                () -> cityService.deleteCity(99L));

        verify(cityRepository, never()).delete(any());
    }
}