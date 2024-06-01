package com.miki.animestylebackend.controller;

import com.miki.animestylebackend.dto.*;
import com.miki.animestylebackend.dto.page.PageData;
import com.miki.animestylebackend.mapper.ProductMapper;
import com.miki.animestylebackend.model.Product;
import com.miki.animestylebackend.service.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class ProductControllerTest {

    @InjectMocks
    private ProductController productController;

    @Mock
    private ProductService productService;

    @Mock
    private ProductMapper productMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldGetProduct() {
        UUID id = UUID.randomUUID();
        ProductDto productDto = new ProductDto();
        when(productService.getProductById(id)).thenReturn(new Product());
        when(productMapper.toProductDto(any(Product.class), anyString())).thenReturn(productDto);

        ProductDto result = productController.getProduct(id);

        assertEquals(productDto, result);
        verify(productService, times(1)).getProductById(id);
        verify(productMapper, times(1)).toProductDto(any(Product.class), anyString());
    }

    @Test
    void shouldGetProductsByListId() {
        PageData<ProductData> pageData = new PageData<>(null, null);
        when(productService.getProductsByListId(anyList(), anyInt(), anyInt())).thenReturn(pageData);

        PageData<ProductData> result = productController.getProductsByListId(Arrays.asList(UUID.randomUUID()), 0, 10);

        assertEquals(pageData, result);
        verify(productService, times(1)).getProductsByListId(anyList(), anyInt(), anyInt());
    }

    // Add more tests for other methods in the same way
}