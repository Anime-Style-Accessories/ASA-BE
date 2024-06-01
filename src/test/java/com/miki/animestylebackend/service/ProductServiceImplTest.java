package com.miki.animestylebackend.service;

import com.miki.animestylebackend.mapper.ProductMapper;
import com.miki.animestylebackend.model.Product;
import com.miki.animestylebackend.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class ProductServiceImplTest {
    @Mock
    private ProductRepository productRepository;
    @Mock
    private CategoryService categoryService;
    @Mock
    private ProductMapper productMapper;
    private ProductServiceImpl productServiceImpl;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        productServiceImpl = new ProductServiceImpl(productRepository, categoryService, productMapper);
    }

    @Test
    void getAllPoduct() {
        Product product1 = new Product();
        Product product2 = new Product();
        List<Product> expectedProducts = Arrays.asList(product1, product2);
        when(productRepository.findAll()).thenReturn(expectedProducts);

        List<Product> actualProducts = productServiceImpl.getAllProducts();

        assertEquals(expectedProducts, actualProducts);
        verify(productRepository, times(1)).findAll();
    }

}