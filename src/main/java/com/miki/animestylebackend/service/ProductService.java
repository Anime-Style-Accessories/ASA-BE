package com.miki.animestylebackend.service;

import com.miki.animestylebackend.dto.CreateProductRequest;
import com.miki.animestylebackend.dto.ProductDto;
import com.miki.animestylebackend.dto.UpdateProductRequest;
import com.miki.animestylebackend.dto.page.PageData;
import com.miki.animestylebackend.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.UUID;

public interface ProductService {
    List<Product> getAllProducts();

    Product getProductById(UUID uuid);
    Product addProduct(CreateProductRequest product);

    Product updateProduct(UpdateProductRequest product);

    void deleteProduct(UUID uuid);

    PageData<ProductDto> findProductsByNameContaining(String name, int page, int size, Sort.Direction sort, String sortBy);
    PageData<ProductDto> findProductsByCategoryNameContaining(String name, int page, int size, Sort.Direction sort, String sortBy);

    PageData<ProductDto> getProductsByCategoryAndName(String category, String name, int page, int size, Sort.Direction sort, String sortBy);

    PageData<ProductDto> getProductsByName(String text, Integer page, Integer size, Sort.Direction sort, String sortBy);

//    Page<ProductDto> findProductsBySearch(String text, String category, int page, int size);
}
