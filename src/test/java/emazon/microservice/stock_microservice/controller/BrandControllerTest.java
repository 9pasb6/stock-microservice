package emazon.microservice.stock_microservice.controller;

import emazon.microservice.stock_microservice.aplication.dto.request.BrandRequest;
import emazon.microservice.stock_microservice.aplication.dto.response.BrandResponse;
import emazon.microservice.stock_microservice.aplication.handler.IBrandHandler;
import emazon.microservice.stock_microservice.infraestructure.input.rest.controller.BrandController;
import emazon.microservice.stock_microservice.infraestructure.input.rest.exception.GlobalExceptionHandler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class BrandControllerTest {

    private MockMvc mockMvc;

    @Mock
    private IBrandHandler brandHandler;

    @InjectMocks
    private BrandController brandController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(brandController)
                .setControllerAdvice(new GlobalExceptionHandler())
                .build();
    }

    @Test
    void testSaveBrand_Success() throws Exception {

        mockMvc.perform(post("/api/brands")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Test Brand\"}"))
                .andExpect(status().isCreated());

        verify(brandHandler, times(1)).saveBrand(any(BrandRequest.class));
    }

    @Test
    void testSaveBrand_InvalidInput() throws Exception {
        mockMvc.perform(post("/api/brands")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"\"}"))
                .andExpect(status().isBadRequest());

        verify(brandHandler, never()).saveBrand(any(BrandRequest.class));
    }

    @Test
    void testGetAllBrands_Success() throws Exception {
        BrandResponse brand1 = new BrandResponse();
        BrandResponse brand2 = new BrandResponse();
        List<BrandResponse> brands = Arrays.asList(brand1, brand2);

        when(brandHandler.getAllBrands("asc")).thenReturn(brands);

        mockMvc.perform(get("/api/brands")
                        .param("order", "asc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.length()").value(2));

        verify(brandHandler, times(1)).getAllBrands("asc");
    }

    @Test
    void testGetBrandById_Success() throws Exception {
        BrandResponse brand = new BrandResponse();

        when(brandHandler.getBrand(anyLong())).thenReturn(brand);

        mockMvc.perform(get("/api/brands/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));

        verify(brandHandler, times(1)).getBrand(anyLong());
    }

    @Test
    void testGetBrandById_NotFound() throws Exception {
        when(brandHandler.getBrand(1L)).thenReturn(null);

        MvcResult result = mockMvc.perform(get("/api/brands/1"))
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.status").value(HttpStatus.NOT_FOUND.value()))
                .andExpect(jsonPath("$.message").value("The brand with ID 1 was not found."))
                .andReturn();

        String responseBody = result.getResponse().getContentAsString();
        System.out.println("Response Body: " + responseBody);

        verify(brandHandler, times(1)).getBrand(1L);
    }

    @Test
    void testUpdateBrand_Success() throws Exception {

        when(brandHandler.updateBrand(any(BrandRequest.class))).thenReturn(true);

        mockMvc.perform(put("/api/brands")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Updated Brand\"}"))
                .andExpect(status().isOk());

        verify(brandHandler, times(1)).updateBrand(any(BrandRequest.class));
    }

    @Test
    void testUpdateBrand_InvalidInput() throws Exception {
        mockMvc.perform(put("/api/brands")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"\"}"))
                .andExpect(status().isBadRequest());

        verify(brandHandler, never()).updateBrand(any(BrandRequest.class));
    }



    @Test
    void testDeleteBrand_Success() throws Exception {
        when(brandHandler.deleteBrand(anyLong())).thenReturn(true);

        mockMvc.perform(delete("/api/brands/1"))
                .andExpect(status().isOk());

        verify(brandHandler, times(1)).deleteBrand(anyLong());
    }

    @Test
    void testDeleteBrandById_NotFound() throws Exception {
        when(brandHandler.deleteBrand(anyLong())).thenReturn(false);

        mockMvc.perform(delete("/api/brands/1"))
                .andExpect(status().isNotFound());

        verify(brandHandler, times(1)).deleteBrand(anyLong());
    }


}