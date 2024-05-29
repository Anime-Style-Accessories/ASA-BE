package com.miki.animestylebackend.mapper;

import com.miki.animestylebackend.dto.CategoryDto;
import com.miki.animestylebackend.dto.CategoryData;
import com.miki.animestylebackend.model.Category;
import org.springframework.stereotype.Service;

@Service
public class CategoryMapperImpl implements CategoryMapper{
    @Override
    public CategoryDto toCategoryDto(Category category, String message) {
        if (category == null) {
            return null;
        }

        CategoryData categoryData = new CategoryData();
        categoryData.setId(category.getId());
        categoryData.setName(category.getName());
        categoryData.setDescription(category.getDescription());

        CategoryDto categoryDto = new CategoryDto();
        categoryDto.setSuccess(true);
        categoryDto.setStatusCode(200);
        categoryDto.setMessage("Success");
        categoryDto.setData(categoryData);

        return categoryDto;
    }

    @Override
    public CategoryData toCategoryData(Category category) {
        if (category == null) {
            return null;
        }

        CategoryData categoryData = new CategoryData();
        categoryData.setId(category.getId());
        categoryData.setName(category.getName());
        categoryData.setDescription(category.getDescription());

        return categoryData;
    }
}