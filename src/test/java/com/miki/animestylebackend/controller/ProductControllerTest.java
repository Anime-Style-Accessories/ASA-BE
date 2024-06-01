package com.miki.animestylebackend.controller;

import com.miki.animestylebackend.dto.*;
import com.miki.animestylebackend.dto.page.PageData;
import com.miki.animestylebackend.mapper.ProductMapper;
import com.miki.animestylebackend.model.Product;
import com.miki.animestylebackend.service.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class ProductControllerTest {
    @Mock
    private ProductService productService;
    @Mock
    private ProductMapper productMapper;
    private ProductController productController;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        productController = new ProductController(productService, productMapper);
    }

    @Test
    void getProductById() {
        UUID id = UUID.randomUUID();
        ProductDto productDto = new ProductDto();
        when(productService.getProductById(id)).thenReturn(new Product());
        when(productMapper.toProductDto(any(Product.class), anyString())).thenReturn(productDto);

        ResponseEntity<ProductDto> result = productController.getProduct(id);

        assertEquals(productDto, result.getBody());
        verify(productService, times(1)).getProductById(id);
        verify(productMapper, times(1)).toProductDto(any(Product.class), anyString());
    }

    @Test
    void getProductsByListId() {
        List<UUID> uuids = Arrays.asList(UUID.randomUUID(), UUID.randomUUID());
        PageData<ProductData> pageData = new PageData<>();
        when(productService.getProductsByListId(uuids, 0, 10)).thenReturn(pageData);

        ResponseEntity<PageData<ProductData>> result = productController.getProductsByListId(uuids, 0, 10);

        assertEquals(pageData, result.getBody());
        verify(productService, times(1)).getProductsByListId(uuids, 0, 10);
    }

    @Test
    void getCategoryAndProductByCategory() {
        PageData<GetProductGroupByCategoryData> pageData = new PageData<>();
        when(productService.getCategoryAndProductByCategory(0, 10, Sort.Direction.ASC, "productPrice")).thenReturn(pageData);

        ResponseEntity<PageData<GetProductGroupByCategoryData>> result = productController.getCategoryAndProductByCategory(0, 10, Sort.Direction.ASC, "productPrice");

        assertEquals(pageData, result.getBody());
        verify(productService, times(1)).getCategoryAndProductByCategory(0, 10, Sort.Direction.ASC, "productPrice");
    }

    @Test
    void getProductsBySearch(){
        PageData<ProductData> pageData = new PageData<>();
        when(productService.getProductsByName("name", 0, 10, Sort.Direction.ASC, "productPrice")).thenReturn(pageData);

        ResponseEntity<PageData<ProductData>> result = productController.getProductsByName("name", 0, 10, Sort.Direction.ASC, "productPrice");

        assertEquals(pageData, result.getBody());
        verify(productService, times(1)).getProductsByName("name", 0, 10, Sort.Direction.ASC, "productPrice");
    }

    @Test
    void getProductsByCategoryAndName(){
        PageData<ProductData> pageData = new PageData<>();
        when(productService.getProductsByCategoryAndName("category", "name", 0, 10, Sort.Direction.ASC, "productPrice")).thenReturn(pageData);

        ResponseEntity<PageData<ProductData>> result = productController.getProductsByCategoryAndName("category", "name", 0, 10, Sort.Direction.ASC, "productPrice");

        assertEquals(pageData, result.getBody());
        verify(productService, times(1)).getProductsByCategoryAndName("category", "name", 0, 10, Sort.Direction.ASC, "productPrice");
    }
}