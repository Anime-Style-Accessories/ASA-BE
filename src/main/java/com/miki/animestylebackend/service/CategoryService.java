package com.miki.animestylebackend.service;


import com.miki.animestylebackend.dto.CreateCategoryRequest;
import com.miki.animestylebackend.model.Category;

import java.util.List;
import java.util.UUID;

public interface CategoryService {
    List<Category> getAllCategories();
    Category getCategoryByName(String category);
    List<Category> getAllCategoriesByNameContaining(String name);
    Category createCategory(CreateCategoryRequest category);
    Category updateCategory(Category category);
    void deleteCategory(UUID categoryId);
}
