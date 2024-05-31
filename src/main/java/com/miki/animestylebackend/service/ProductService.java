package com.miki.animestylebackend.service;

import com.miki.animestylebackend.dto.*;
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

    Product updateProduct(UUID uuid, UpdateProductRequest product);

    void deleteProduct(UUID uuid);

    PageData<ProductData> findProductsByNameContaining(String name, int page, int size, Sort.Direction sort, String sortBy);
    PageData<ProductData> findProductsByCategoryNameContaining(String name, int page, int size, Sort.Direction sort, String sortBy);

    PageData<ProductData> getProductsByCategoryAndName(String category, String name, int page, int size, Sort.Direction sort, String sortBy);

    PageData<ProductData> getProductsByName(String text, Integer page, Integer size, Sort.Direction sort, String sortBy);

    PageData<ProductData> getProductsByListId(List<UUID> uuids, int page, int size);

    PageData<GetProductGroupByCategoryData> getCategoryAndProductByCategory(Integer page, Integer size, Sort.Direction sort, String sortBy);

//    Page<ProductDto> findProductsBySearch(String text, String category, int page, int size);
}
