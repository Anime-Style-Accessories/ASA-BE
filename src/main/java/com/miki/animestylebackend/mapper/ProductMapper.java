package com.miki.animestylebackend.mapper;

import com.miki.animestylebackend.dto.ProductData;
import com.miki.animestylebackend.dto.ProductDto;
import com.miki.animestylebackend.model.Product;

public interface ProductMapper {


    ProductDto toProductDto(Product product, String message);

    ProductData toProductData(Product product);
}
