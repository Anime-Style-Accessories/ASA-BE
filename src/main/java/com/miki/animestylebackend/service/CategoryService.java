package com.miki.animestylebackend.service;


import com.miki.animestylebackend.dto.CategoryData;
import com.miki.animestylebackend.dto.CategoryDto;
import com.miki.animestylebackend.dto.CreateCategoryRequest;
import com.miki.animestylebackend.dto.page.PageData;
import com.miki.animestylebackend.model.Category;

import java.util.List;
import java.util.UUID;

public interface CategoryService {
    PageData<CategoryData> getAllCategories(int page, int size);
    Category getCategoryByName(String category);
    PageData<CategoryData> getAllCategoriesByNameContaining(String name, int page, int size);
    CategoryDto createCategory(CreateCategoryRequest category);
    Category updateCategory(Category category);
    void deleteCategory(UUID categoryId);
}
