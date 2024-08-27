package emazon.microservice.stock_microservice.handler;

import emazon.microservice.stock_microservice.aplication.dto.request.BrandRequest;
import emazon.microservice.stock_microservice.aplication.dto.response.BrandResponse;
import emazon.microservice.stock_microservice.aplication.handler.impl.BrandHandler;
import emazon.microservice.stock_microservice.aplication.mapper.request.BrandRequestMapper;
import emazon.microservice.stock_microservice.aplication.mapper.response.BrandResponseMapper;
import emazon.microservice.stock_microservice.domain.api.IBrandServicePort;
import emazon.microservice.stock_microservice.domain.model.Brand;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class BrandHandlerTest {

    @Mock
    private IBrandServicePort brandServicePort;

    @Mock
    private BrandRequestMapper brandRequestMapper;

    @Mock
    private BrandResponseMapper brandResponseMapper;

    @InjectMocks
    private BrandHandler brandHandler;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSaveBrand() {
        BrandRequest brandRequest = new BrandRequest("name", "description");
        Brand brand = new Brand();

        when(brandRequestMapper.requestToBrand(any(BrandRequest.class))).thenReturn(brand);

        brandHandler.saveBrand(brandRequest);

        verify(brandRequestMapper, times(1)).requestToBrand(brandRequest);
        verify(brandServicePort, times(1)).save(brand);
    }

    @Test
    void testGetAllBrands() {
        Brand brand = new Brand();
        BrandResponse brandResponse = new BrandResponse();

        when(brandServicePort.findAll(anyString())).thenReturn(Arrays.asList(brand));
        when(brandResponseMapper.brandToResponse(any(Brand.class))).thenReturn(brandResponse);

        List<BrandResponse> result = brandHandler.getAllBrands("asc");

        verify(brandServicePort, times(1)).findAll("asc");
        verify(brandResponseMapper, times(1)).brandToResponse(brand);
        assertEquals(1, result.size());
        assertEquals(brandResponse, result.get(0));
    }

    @Test
    void testGetBrand() {
        Brand brand = new Brand();
        BrandResponse brandResponse = new BrandResponse();

        when(brandServicePort.findById(anyLong())).thenReturn(brand);
        when(brandResponseMapper.brandToResponse(any(Brand.class))).thenReturn(brandResponse);

        BrandResponse result = brandHandler.getBrand(1L);

        verify(brandServicePort, times(1)).findById(1L);
        verify(brandResponseMapper, times(1)).brandToResponse(brand);
        assertEquals(brandResponse, result);
    }

    @Test
    void testUpdateBrand() {
        BrandRequest brandRequest = new BrandRequest("name", "description");
        Brand brand = new Brand();

        when(brandRequestMapper.requestToBrand(any(BrandRequest.class))).thenReturn(brand);

        brandHandler.updateBrand(brandRequest);

        verify(brandRequestMapper, times(1)).requestToBrand(brandRequest);
        verify(brandServicePort, times(1)).update(brand);
    }

    @Test
    void testDeleteBrand() {
        brandHandler.deleteBrand(1L);

        verify(brandServicePort, times(1)).delete(1L);
    }
}
