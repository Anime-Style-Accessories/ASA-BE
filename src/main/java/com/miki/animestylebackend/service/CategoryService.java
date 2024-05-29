package com.miki.animestylebackend.service;


import com.miki.animestylebackend.dto.CategoryDto;
import com.miki.animestylebackend.dto.CreateCategoryRequest;
import com.miki.animestylebackend.dto.page.PageData;
import com.miki.animestylebackend.model.Category;

import java.util.List;
import java.util.UUID;

public interface CategoryService {
    PageData<CategoryDto> getAllCategories(int page, int size);
    Category getCategoryByName(String category);
    PageData<CategoryDto> getAllCategoriesByNameContaining(String name, int page, int size);
    Category createCategory(CreateCategoryRequest category);
    Category updateCategory(Category category);
    void deleteCategory(UUID categoryId);
}
