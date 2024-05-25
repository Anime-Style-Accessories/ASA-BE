package com.miki.animestylebackend.mapper;

import com.miki.animestylebackend.dto.CategoryDto;
import com.miki.animestylebackend.model.Category;
import org.springframework.stereotype.Service;

@Service
public class CategoryMapperImpl implements CategoryMapper{
    @Override
    public CategoryDto toCategoryDto(Category category) {
        if (category == null) {
            return null;
        }
        return new CategoryDto(category.getId(), category.getName(), category.getDescription());
    }
}
