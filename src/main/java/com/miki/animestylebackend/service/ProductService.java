package com.miki.animestylebackend.service;

import com.miki.animestylebackend.dto.CreateProductRequest;
import com.miki.animestylebackend.dto.UpdateProductRequest;
import com.miki.animestylebackend.model.Product;

import java.util.List;
import java.util.UUID;

public interface ProductService {
    List<Product> getAllProducts();

    Product getProductById(UUID uuid);
    Product addProduct(CreateProductRequest product);

    Product updateProduct(UpdateProductRequest product);

    void deleteProduct(UUID uuid);

    List<Product> findProductsByCategoryNameContaining(String category);
}
