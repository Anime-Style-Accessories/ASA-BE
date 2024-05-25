package com.miki.animestylebackend.mapper;

import com.miki.animestylebackend.dto.ProductDto;
import com.miki.animestylebackend.model.Product;
import org.springframework.stereotype.Service;

@Service
public class ProductMapperImpl implements ProductMapper{

    @Override
    public ProductDto toProductDto(Product product) {
        if (product == null) {
            return null;
        }
        ProductDto.CategoryDto categoryDto = new ProductDto.CategoryDto(product.getCategory().getName());
        return new ProductDto(product.getId(), product.getProductName(), product.getProductImage(), categoryDto, product.getProductDescription(),product.getProductPrice());
    }
}
