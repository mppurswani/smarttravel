package com.travel.smarttravel;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.travel.smarttravel.dto.CityDTO;
import com.travel.smarttravel.entity.CityCategory;
import com.travel.smarttravel.service.CityService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class CityControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private CityService cityService;

    // ==========================================
    // TEST 1 — GET /api/cities/all is public
    // ==========================================
    @Test
    void getAllCities_PublicEndpoint_ShouldReturn200() 
            throws Exception {
        CityDTO city = new CityDTO();
        city.setId(1L);
        city.setName("Goa");
        city.setState("Goa");
        city.setCountry("India");

        when(cityService.getAllCitiesWithoutPagination())
            .thenReturn(List.of(city));

        mockMvc.perform(get("/api/cities/all"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$[0].name").value("Goa"));
    }

    // ==========================================
    // TEST 2 — POST /api/cities needs ADMIN
    // ==========================================
    @Test
    void addCity_WithoutAuth_ShouldReturn403() 
            throws Exception {
        CityDTO city = new CityDTO();
        city.setName("Goa");
        city.setState("Goa");
        city.setCountry("India");

        mockMvc.perform(post("/api/cities")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(city)))
            .andExpect(status().isForbidden());
    }

    // ==========================================
    // TEST 3 — POST /api/cities works with ADMIN
    // ==========================================
    @Test
    @WithMockUser(roles = "ADMIN")
    void addCity_WithAdminRole_ShouldReturn200() 
            throws Exception {
        CityDTO city = new CityDTO();
        city.setName("Goa");
        city.setState("Goa");
        city.setCountry("India");
        city.setCategory(CityCategory.BEACHES);

        when(cityService.addCity(any(CityDTO.class)))
            .thenReturn(city);

        mockMvc.perform(post("/api/cities")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(city)))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.name").value("Goa"));
    }

    // ==========================================
    // TEST 4 — GET hidden gems is public
    // ==========================================
    @Test
    void getHiddenGems_PublicEndpoint_ShouldReturn200() 
            throws Exception {
        CityDTO city = new CityDTO();
        city.setName("Ziro Valley");
        city.setHiddenGem(true);

        when(cityService.getHiddenGems())
            .thenReturn(List.of(city));

        mockMvc.perform(get("/api/cities/hidden-gems"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$[0].name")
                .value("Ziro Valley"));
    }
}