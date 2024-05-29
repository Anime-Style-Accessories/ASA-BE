package com.miki.animestylebackend.mapper;

import com.miki.animestylebackend.dto.CategoryData;
import com.miki.animestylebackend.dto.CategoryDto;
import com.miki.animestylebackend.model.Category;


public interface CategoryMapper {
    CategoryDto toCategoryDto(Category category, String message);

    CategoryData toCategoryData(Category category);
}
