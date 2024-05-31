package com.miki.animestylebackend.mapper;

import com.miki.animestylebackend.dto.CategoryData;
import com.miki.animestylebackend.dto.ProductDto;
import com.miki.animestylebackend.dto.ProductData;
import com.miki.animestylebackend.model.Product;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class ProductMapperImpl implements ProductMapper {

    @Override
    public ProductDto toProductDto(Product product, String message) {
        if (product == null) {
            return null;
        }

        ProductData productData = new ProductData();
        productData.setId(product.getId());
        productData.setName(product.getProductName());
        productData.setImage(product.getProductImage());
        productData.setCategoryDto(new CategoryData(product.getCategory().getId(), product.getCategory().getName(), product.getCategory().getDescription()));
        productData.setDescription(product.getProductDescription());
        productData.setPrice(product.getProductPrice());
        productData.setSize(product.getProductSize());
        productData.setColor(product.getProductColor());
        productData.setQuantity(product.getProductQuantity());

        ProductDto productDto = new ProductDto();
        productDto.setSuccess(true);
        productDto.setStatus(200);
        productDto.setMessage(message);
        productDto.setData(productData);
        productDto.setTimestamp(LocalDateTime.now());

        return productDto;
    }

    @Override
    public ProductData toProductData(Product product) {
        if (product == null) {
            return null;
        }

        ProductData productData = new ProductData();
        productData.setId(product.getId());
        productData.setName(product.getProductName());
        productData.setImage(product.getProductImage());
        productData.setCategoryDto(new CategoryData(product.getCategory().getId(), product.getCategory().getName(), product.getCategory().getDescription()));
        productData.setDescription(product.getProductDescription());
        productData.setPrice(product.getProductPrice());
        productData.setSize(product.getProductSize());
        productData.setColor(product.getProductColor());
        productData.setQuantity(product.getProductQuantity());

        return productData;
    }
}