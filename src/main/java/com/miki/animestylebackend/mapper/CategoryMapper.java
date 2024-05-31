package com.miki.animestylebackend.mapper;

import com.miki.animestylebackend.dto.CategoryData;
import com.miki.animestylebackend.dto.CategoryDto;
import com.miki.animestylebackend.dto.GetProductGroupByCategoryData;
import com.miki.animestylebackend.dto.ProductData;
import com.miki.animestylebackend.model.Category;
import com.miki.animestylebackend.model.Product;

import java.util.List;


public interface CategoryMapper {
    CategoryDto toCategoryDto(Category category, String message);

    CategoryData toCategoryData(Category category);
    GetProductGroupByCategoryData toGetProductGroupByCategoryData(Category category, List<ProductData> productData);
}
