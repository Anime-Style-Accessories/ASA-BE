package com.miki.animestylebackend.mapper;

import com.miki.animestylebackend.dto.ProductDto;
import com.miki.animestylebackend.model.Product;

public interface ProductMapper {


    ProductDto toProductDto(Product product);
}
