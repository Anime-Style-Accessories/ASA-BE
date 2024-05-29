package com.miki.animestylebackend.mapper;

import com.miki.animestylebackend.dto.CategoryData;
import com.miki.animestylebackend.dto.ProductDto;
import com.miki.animestylebackend.dto.ProductData;
import com.miki.animestylebackend.model.Product;
import org.springframework.stereotype.Service;

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
        productData.setCategoryDto(new CategoryData(product.getCategory().getName(), product.getCategory().getDescription()));
        productData.setDescription(product.getProductDescription());
        productData.setPrice(product.getProductPrice());

        ProductDto productDto = new ProductDto();
        productDto.setSuccess(true);
        productDto.setStatusCode(200);
        productDto.setMessage(message);
        productDto.setData(productData);

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
        productData.setCategoryDto(new CategoryData(product.getCategory().getName(), product.getCategory().getDescription()));
        productData.setDescription(product.getProductDescription());
        productData.setPrice(product.getProductPrice());

        return productData;
    }
}